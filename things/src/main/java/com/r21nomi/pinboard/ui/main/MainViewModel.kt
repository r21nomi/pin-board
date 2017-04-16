package com.r21nomi.pinboard.ui.main

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.databinding.ObservableField
import android.net.Uri
import android.os.Handler
import android.os.Looper
import com.flaviofaria.kenburnsview.KenBurnsView
import com.flaviofaria.kenburnsview.Transition
import com.google.android.things.pio.Gpio
import com.google.android.things.pio.GpioCallback
import com.google.android.things.pio.PeripheralManagerService
import com.r21nomi.core.pin.entity.Page
import com.r21nomi.core.pin.entity.Pin
import com.r21nomi.pinboard.domain.pin.GetPins
import com.r21nomi.pinboard.util.BoardUtil
import rx.android.schedulers.AndroidSchedulers
import timber.log.Timber
import java.io.IOException
import java.util.*

/**
 * Created by r21nomi on 2017/04/15.
 */
class MainViewModel(val getPins: GetPins) {

    companion object {
        val LIMIT = 50
        val DIFF = 5
    }

    val uri_1: ObservableField<Uri> = ObservableField()
    val uri_2: ObservableField<Uri> = ObservableField()
    val alpha_1: ObservableField<Float> = ObservableField()
    val alpha_2: ObservableField<Float> = ObservableField()

    val transitionListener = object : KenBurnsView.TransitionListener {
        override fun onTransitionStart(transition: Transition?) {
            Timber.v("onTransitionStart")

            val duration = transition?.duration ?: 0
            val offset: Long = duration / 5

            if (duration - offset > 0) {
                handler.removeCallbacks(runnable)
                runnable = object : Runnable {
                    override fun run() {
                        startCrossFading(offset)
                    }
                }
                handler.postDelayed(runnable, duration - offset)
            }
        }

        override fun onTransitionEnd(transition: Transition?) {
            Timber.v("onTransitionEnd")
        }
    }

    private val handler: Handler = Handler(Looper.getMainLooper())

    private var runnable: Runnable? = null
    private var nextForegroundTarget: Target = Target.TARGET_1

    private enum class Target {
        TARGET_1 {
            override fun getUri(viewModel: MainViewModel): ObservableField<Uri> {
                return viewModel.uri_1
            }

            override fun getAlpha(viewModel: MainViewModel): ObservableField<Float> {
                return viewModel.alpha_1
            }
        },
        TARGET_2 {
            override fun getUri(viewModel: MainViewModel): ObservableField<Uri> {
                return viewModel.uri_2
            }

            override fun getAlpha(viewModel: MainViewModel): ObservableField<Float> {
                return viewModel.alpha_2
            }
        };

        abstract fun getUri(viewModel: MainViewModel): ObservableField<Uri>
        abstract fun getAlpha(viewModel: MainViewModel): ObservableField<Float>
    }

    private var dataSet: MutableList<Pin> = ArrayList()
    private var lastPage: Page? = null
    private var latestFetchedImagePosition: Int = 0

    private val nextButtonGpio: Gpio by lazy {
        val service = PeripheralManagerService()
        val skipNextPinName = BoardUtil.RPI3_PIN_21
        service.openGpio(skipNextPinName)
    }
    private val prevButtonGpio: Gpio by lazy {
        val service = PeripheralManagerService()
        val skipNextPinName = BoardUtil.RPI3_PIN_20
        service.openGpio(skipNextPinName)
    }

    init {
        // This is necessary to avoid "IllegalArgumentException: Parameter specified as non-null is null"
        uri_1.set(Uri.EMPTY)
        uri_2.set(Uri.EMPTY)
        alpha_1.set(1f)
        alpha_2.set(0f)

        initGpio()
    }

    /**
     * Fetch next pins from API.
     */
    fun fetch(cursor: String) {
        getPins
                .execute(LIMIT, cursor)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    dataSet.addAll(it.data)
                    lastPage = it.page

                    if (latestFetchedImagePosition == 0) {
                        // Load foreground image and background image at the first time.
                        loadImage()
                        preLoadImage()
                    }
                }, {
                    Timber.e(it)
                })
    }

    fun onDestroy() {
        try {
            // Close the Gpio pin
            nextButtonGpio.close()
        } catch (e: IOException) {
            Timber.e("Error on PeripheralIO API", e)
        }
    }

    private fun initGpio() {
        try {
            // Setup for prevButton.
            prevButtonGpio.setDirection(Gpio.DIRECTION_IN)
            prevButtonGpio.setEdgeTriggerType(Gpio.EDGE_FALLING)
            prevButtonGpio.registerGpioCallback(object : GpioCallback() {
                override fun onGpioEdge(gpio: Gpio?): Boolean {
                    Timber.d("GPIO changed, prev button pressed.")
                    skipToPrevImage()
                    return true
                }
            })

            // Setup for nextButton.
            nextButtonGpio.setDirection(Gpio.DIRECTION_IN)
            nextButtonGpio.setEdgeTriggerType(Gpio.EDGE_FALLING)
            nextButtonGpio.registerGpioCallback(object : GpioCallback() {
                override fun onGpioEdge(gpio: Gpio?): Boolean {
                    Timber.d("GPIO changed, next button pressed.")
                    skipToNextImage()
                    return true
                }
            })
        } catch (e: IOException) {
            Timber.e(e.message, e)
        }
    }

    private fun startCrossFading(duration: Long) {
        val foregroundAnimator: ValueAnimator = ValueAnimator.ofFloat(1f, 0f).apply {
            addUpdateListener {
                getNextForegroundTarget().getAlpha(this@MainViewModel).set(it.animatedValue as Float)
            }
        }

        val backgroundAnimator: ValueAnimator = ValueAnimator.ofFloat(0f, 1f).apply {
            addUpdateListener {
                getNextBackgroundTarget().getAlpha(this@MainViewModel).set(it.animatedValue as Float)
            }
        }

        AnimatorSet().run {
            playTogether(
                    foregroundAnimator,
                    backgroundAnimator
            )
            setDuration(duration)
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    super.onAnimationEnd(animation)
                    // Prepare for next image.
                    prepareForNextImage()
                }
            })
            start()
        }
    }

    private fun switchTarget() {
        if (nextForegroundTarget == Target.TARGET_1) {
            nextForegroundTarget = Target.TARGET_2
        } else {
            nextForegroundTarget = Target.TARGET_1
        }
    }

    /**
     * This should be called after cross fading.
     */
    private fun prepareForNextImage() {
        skipToNextImage()
        switchTarget()
    }

    private fun skipToPrevImage() {
        latestFetchedImagePosition--
        loadImage()
    }

    private fun skipToNextImage() {
        latestFetchedImagePosition++
        loadImage()

        if (shouldFetchNext()) {
            fetch(lastPage?.cursor ?: "")
        }
    }

    private fun shouldFetchNext(): Boolean {
        return dataSet.size > 0 && (dataSet.size - 1) - latestFetchedImagePosition < DIFF
    }

    private fun loadImage() {
        if (latestFetchedImagePosition >= dataSet.size - 1) {
            latestFetchedImagePosition = 0

        } else if (latestFetchedImagePosition < 0) {
            latestFetchedImagePosition = dataSet.size - 1
        }
        getNextForegroundTarget().getUri(this).set(Uri.parse(dataSet[latestFetchedImagePosition].images.image.url))
    }

    /**
     * This should be called on first fetching.
     */
    private fun preLoadImage() {
        latestFetchedImagePosition++

        if (latestFetchedImagePosition >= dataSet.size - 1) {
            latestFetchedImagePosition = 0
        }
        getNextBackgroundTarget().getUri(this).set(Uri.parse(dataSet[latestFetchedImagePosition].images.image.url))
    }

    private fun getNextForegroundTarget(): Target {
        return nextForegroundTarget
    }

    private fun getNextBackgroundTarget(): Target {
        if (nextForegroundTarget == Target.TARGET_1) {
            return Target.TARGET_2
        } else {
            return Target.TARGET_1
        }
    }
}
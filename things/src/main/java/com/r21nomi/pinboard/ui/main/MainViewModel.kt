package com.r21nomi.pinboard.ui.main

import android.databinding.ObservableField
import android.net.Uri
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

    val uri: ObservableField<Uri> = ObservableField()

    val transitionListener = object : KenBurnsView.TransitionListener {
        override fun onTransitionStart(transition: Transition?) {
            Timber.v("onTransitionStart")
        }

        override fun onTransitionEnd(transition: Transition?) {
            Timber.v("onTransitionEnd")

            skipToNextImage()
        }
    }

    private var dataSet: MutableList<Pin> = ArrayList()
    private var lastPage: Page? = null
    private var currentImagePosition: Int = 0
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
        uri.set(Uri.EMPTY)

        initGpio()
    }

    /**
     * Fetch next pins from API.
     */
    fun fetch(cursor: String) {
        getPins
                .execute(MainActivity.LIMIT, cursor)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    dataSet.addAll(it.data)
                    lastPage = it.page

                    if (currentImagePosition == 0) {
                        loadImage()
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

    private fun skipToPrevImage() {
        currentImagePosition--
        loadImage()
    }

    private fun skipToNextImage() {
        currentImagePosition++
        loadImage()

        if (shouldFetchNext()) {
            fetch(lastPage?.cursor ?: "")
        }
    }

    private fun shouldFetchNext(): Boolean {
        return dataSet.size > 0 && (dataSet.size - 1) - currentImagePosition < MainActivity.DIFF
    }

    private fun loadImage() {
        if (currentImagePosition >= dataSet.size - 1) {
            currentImagePosition = 0

        } else if (currentImagePosition < 0) {
            currentImagePosition = dataSet.size - 1
        }
        uri.set(Uri.parse(dataSet[currentImagePosition].images.image.url))
    }
}
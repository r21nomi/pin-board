package com.r21nomi.pinboard.ui.offline

import android.databinding.ObservableField
import android.net.Uri
import com.flaviofaria.kenburnsview.KenBurnsView
import com.flaviofaria.kenburnsview.Transition
import com.google.android.things.pio.Gpio
import com.google.android.things.pio.GpioCallback
import com.google.android.things.pio.PeripheralManagerService
import com.r21nomi.core.util.RxUtil
import com.r21nomi.pinboard.domain.offline_image.GetOfflineImages
import com.r21nomi.pinboard.util.BoardUtil
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.subjects.PublishSubject
import rx.subjects.SerializedSubject
import timber.log.Timber
import java.io.IOException

/**
 * Created by r21nomi on 2017/08/03.
 */
class OfflineViewModel(val getOfflineImages: GetOfflineImages) {
    companion object {
        val LIMIT = 20
    }

    val uri: ObservableField<Uri> = ObservableField()

    val skipSubject: SerializedSubject<RxUtil.Event, RxUtil.Event>
            = PublishSubject.create<RxUtil.Event>().toSerialized()

    val transitionListener = object : KenBurnsView.TransitionListener {
        override fun onTransitionStart(transition: Transition?) {
            Timber.v("onTransitionStart")
        }

        override fun onTransitionEnd(transition: Transition?) {
            Timber.v("onTransitionEnd")
            if (dataSet.isEmpty()) return

            (currentImageIndex + 1).let { newIndex ->
                if (newIndex >= dataSet.size) {
                    currentImageIndex = 0
                    return
                }
                uri.set(dataSet[newIndex])
                currentImageIndex = newIndex
            }
        }
    }

    private val nextButtonGpio: Gpio by lazy {
        val service = PeripheralManagerService()
        service.openGpio(BoardUtil.RPI3_PIN_21)
    }
    private val prevButtonGpio: Gpio by lazy {
        val service = PeripheralManagerService()
        service.openGpio(BoardUtil.RPI3_PIN_20)
    }

    private val dataSet: MutableList<Uri> = ArrayList()
    private var currentImageIndex = 0

    init {
        uri.set(Uri.EMPTY)

        initGpio()
    }

    fun fetch() {
        getOfflineImages.execute(LIMIT)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.isEmpty()) return@subscribe

                    dataSet.addAll(it)
                    uri.set(it[0])
                }, {
                    Timber.e(it)
                })
    }

    fun skipRequest(): Observable<RxUtil.Event> {
        return skipSubject.onBackpressureLatest()
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
        currentImageIndex--

        if (currentImageIndex < 0) {
            currentImageIndex = dataSet.size - 1
        }

        uri.set(dataSet[currentImageIndex])

        skipSubject.onNext(RxUtil.Event.INSTANCE)
    }

    private fun skipToNextImage() {
        currentImageIndex++

        if (currentImageIndex > dataSet.size - 1) {
            currentImageIndex = 0
        }

        uri.set(dataSet[currentImageIndex])

        skipSubject.onNext(RxUtil.Event.INSTANCE)
    }
}
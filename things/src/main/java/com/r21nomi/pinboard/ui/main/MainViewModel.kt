package com.r21nomi.pinboard.ui.main

import android.databinding.ObservableField
import android.net.Uri
import com.flaviofaria.kenburnsview.KenBurnsView
import com.flaviofaria.kenburnsview.Transition
import com.r21nomi.core.pin.entity.Page
import com.r21nomi.core.pin.entity.Pin
import com.r21nomi.pinboard.domain.pin.GetPins
import rx.android.schedulers.AndroidSchedulers
import timber.log.Timber
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

    init {
        uri.set(Uri.EMPTY)
    }

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

    fun skipToNextImage() {
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
            return
        }
        uri.set(Uri.parse(dataSet[currentImagePosition].images.image.url))
    }
}
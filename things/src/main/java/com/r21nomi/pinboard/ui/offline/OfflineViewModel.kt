package com.r21nomi.pinboard.ui.offline

import android.databinding.ObservableField
import android.net.Uri
import com.flaviofaria.kenburnsview.KenBurnsView
import com.flaviofaria.kenburnsview.Transition
import com.r21nomi.pinboard.domain.offline_image.GetOfflineImages
import rx.android.schedulers.AndroidSchedulers
import timber.log.Timber

/**
 * Created by r21nomi on 2017/08/03.
 */
class OfflineViewModel(val getOfflineImages: GetOfflineImages) {
    companion object {
        val LIMIT = 20
    }

    val uri: ObservableField<Uri> = ObservableField()

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

    private val dataSet: MutableList<Uri> = ArrayList()
    private var currentImageIndex = 0

    init {
        uri.set(Uri.EMPTY)
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
}
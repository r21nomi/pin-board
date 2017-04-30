package com.r21nomi.pinboard.ui.main

import android.net.Uri
import com.r21nomi.core.pin.entity.Page
import com.r21nomi.core.pin.entity.Pin
import com.r21nomi.pinboard.domain.login.SaveAccessToken
import com.r21nomi.pinboard.domain.pin.GetPins
import com.r21nomi.pinboard.ui.pin_detail.PinDetailViewModel
import com.r21nomi.pinboard.util.DeepLinkRouter
import rx.Completable
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import java.util.*

/**
 * Created by r21nomi on 2017/04/30.
 */
class MainViewModel(private val getPins: GetPins,
                    private val saveAccessToken: SaveAccessToken) {

    companion object {
        val LIMIT = 50
    }

    private val dataSet: MutableList<Pin> = ArrayList()
    private var lastPage: Page? = null

    fun fetch(): Observable<List<Pin>> {
        return fetch("")
    }

    fun fetch(cursor: String): Observable<List<Pin>> {
        return getPins
                .execute(PinDetailViewModel.LIMIT, cursor)
                .doOnNext {
                    dataSet.addAll(it.data)
                    lastPage = it.page
                }
                .map { dataSet }
    }

    fun saveAccessTokenIfNeeded(uri: Uri?): Completable {
        if (uri != null) {
            return DeepLinkRouter
                    .getAccessToken(uri)
                    .observeOn(AndroidSchedulers.mainThread())
                    .toSingle()
                    .flatMapCompletable { accessToken ->
                        return@flatMapCompletable saveAccessToken.execute(accessToken)
                    }
        } else {
            return Completable.complete()
        }
    }
}
package com.r21nomi.pinboard.ui.pin_detail

import android.content.Context
import com.r21nomi.core.pin.entity.Page
import com.r21nomi.core.pin.entity.Pin
import com.r21nomi.pinboard.domain.pin.GetPins
import com.r21nomi.pinboard.util.WindowUtil
import rx.Observable
import java.util.*


/**
 * Created by r21nomi on 2017/03/24.
 */
class PinDetailViewModel(private val context: Context,
                         private val getPins: GetPins) {

    companion object {
        val LIMIT = 50
    }

    private val dataSet: MutableList<Pin> = ArrayList()
    private var lastPage: Page? = null

    val maximumItemWidth: Int by lazy {
        WindowUtil.getWidth(context)
    }

    fun fetch(): Observable<List<Pin>> {
        return fetch("")
    }

    fun fetch(cursor: String): Observable<List<Pin>> {
        return getPins
                .execute(LIMIT, cursor)
                .doOnNext {
                    dataSet.addAll(it.data)
                    lastPage = it.page
                }
                .map { dataSet }
    }
}
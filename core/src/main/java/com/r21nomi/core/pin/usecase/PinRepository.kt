package com.r21nomi.core.pin.usecase

import com.r21nomi.core.pin.entity.PinResponse
import rx.Observable

/**
 * Created by r21nomi on 2017/02/05.
 */
interface PinRepository {
    fun fetchPins(accessToken: String, limit: Int): Observable<PinResponse>
}
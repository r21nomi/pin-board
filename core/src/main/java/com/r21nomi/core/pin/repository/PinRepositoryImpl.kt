package com.r21nomi.core.pin.repository

import com.r21nomi.core.pin.entity.PinResponse
import com.r21nomi.core.pin.usecase.PinRepository
import rx.Observable
import rx.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by r21nomi on 2017/02/05.
 */
@Singleton
class PinRepositoryImpl @Inject constructor(val pinApi: PinApi) : PinRepository {

    companion object {
        val FIELDS = "id,creator,note,image"
    }

    override fun fetchPins(accessToken: String, limit: Int, cursor: String): Observable<PinResponse> {
        return pinApi
                .getPins(
                        accessToken,
                        FIELDS,
                        limit,
                        cursor
                )
                .subscribeOn(Schedulers.io())
    }
}
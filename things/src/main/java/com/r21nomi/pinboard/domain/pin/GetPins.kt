package com.r21nomi.pinboard.domain.pin

import com.r21nomi.core.login.usecase.LoginRepository
import com.r21nomi.core.pin.entity.PinResponse
import com.r21nomi.core.pin.usecase.PinRepository
import rx.Observable
import javax.inject.Inject

/**
 * Created by r21nomi on 2017/02/05.
 */
class GetPins @Inject constructor(val loginRepository: LoginRepository, val pinRepository: PinRepository) {

    fun execute(limit: Int): Observable<PinResponse> {
        return execute(limit, "")
    }

    fun execute(limit: Int, cursor: String): Observable<PinResponse> {
        val accessToken = loginRepository.getAccessTokenFromPref()
        return execute(accessToken, limit, cursor)
    }

    fun execute(accessToken: String, limit: Int, cursor: String): Observable<PinResponse> {
        return pinRepository.fetchPins(accessToken, limit, cursor)
    }
}
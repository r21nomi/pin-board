package com.r21nomi.core.pin.usecase

import com.r21nomi.core.login.usecase.LoginRepository
import com.r21nomi.core.pin.entity.PinResponse
import rx.Observable
import javax.inject.Inject

/**
 * Created by r21nomi on 2017/02/05.
 */
class GetPins @Inject constructor(val loginRepository: LoginRepository, val pinRepository: PinRepository) {
    fun execute(limit: Int): Observable<PinResponse> {
        val accessToken = loginRepository.getAccessToken()
        return pinRepository.fetchPins(accessToken, limit)
    }
}
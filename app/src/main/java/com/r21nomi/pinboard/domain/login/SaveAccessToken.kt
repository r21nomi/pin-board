package com.r21nomi.pinboard.domain.login

import com.r21nomi.core.login.usecase.LoginRepository
import rx.Completable
import javax.inject.Inject

/**
 * Created by r21nomi on 2017/01/31.
 */
class SaveAccessToken @Inject constructor(val loginRepository: LoginRepository) {

    fun execute(token: String): Completable {
        return Completable.fromEmitter {
            loginRepository.setAccessTokenToPref(token)
            loginRepository.setAccessTokenToDB(token)
            return@fromEmitter it.onCompleted()
        }
    }
}
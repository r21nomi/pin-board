package com.r21nomi.pinboard.ui.login

import com.r21nomi.core.login.usecase.LoginRepository
import rx.Completable
import rx.Subscription

/**
 * Created by r21nomi on 2017/03/12.
 */
class LoginViewModel(private val loginRepository: LoginRepository) {

    fun observeAccessTokenChanges(): Completable {
        var subscription: Subscription? = null

        return Completable.fromEmitter {
            val emitter = it
            subscription = loginRepository.observeChanges()
                    .filter { !it.isNullOrBlank() }
                    .subscribe({  // FIXME : I don't want to use subscribe() here.
                        loginRepository.setAccessTokenToPref(it)
                        emitter.onCompleted()
                    }, {
                        emitter.onError(it)
                    })
        }.doOnUnsubscribe { subscription?.unsubscribe() }
    }
}
package com.r21nomi.core.login.usecase

import javax.inject.Inject

/**
 * Created by r21nomi on 2017/01/31.
 */
class GetAccessToken @Inject constructor(val loginRepository: LoginRepository) {
    fun execute(): String {
        return loginRepository.getAccessToken()
    }
}
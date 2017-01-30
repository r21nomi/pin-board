package com.r21nomi.core.login.usecase

import android.content.Intent
import javax.inject.Inject

/**
 * Created by r21nomi on 2017/01/31.
 */
class GetOAuthIntent @Inject constructor(val loginRepository: LoginRepository) {

    fun execute(): Intent {
        return loginRepository.getOAuthIntent()
    }
}
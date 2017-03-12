package com.r21nomi.pinboard.domain.login

import android.content.Intent
import com.r21nomi.core.login.usecase.LoginRepository
import javax.inject.Inject

/**
 * Created by r21nomi on 2017/01/31.
 */
class GetOAuthIntent @Inject constructor(val loginRepository: LoginRepository) {

    fun execute(): Intent {
        return loginRepository.getOAuthIntent()
    }
}
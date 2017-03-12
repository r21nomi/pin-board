package com.r21nomi.pinboard.ui.login

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import com.r21nomi.pinboard.R
import com.r21nomi.pinboard.databinding.ActivityLoginBinding
import com.r21nomi.pinboard.ui.BaseActivity
import com.r21nomi.pinboard.ui.main.MainActivity
import rx.android.schedulers.AndroidSchedulers
import timber.log.Timber
import javax.inject.Inject


/**
 * Created by r21nomi on 2017/03/12.
 */
class LoginActivity : BaseActivity() {

    companion object {
        fun createIntent(context: Context): Intent {
            val intent = Intent(context, LoginActivity::class.java)
            return intent
        }
    }

    @Inject
    lateinit var loginViewModel: LoginViewModel

    lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView<ActivityLoginBinding>(this, R.layout.activity_login)

        DaggerLoginComponent.builder()
                .applicationComponent(getApplicationComponent())
                .build()
                .inject(this)

        loginViewModel.observeAccessTokenChanges()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    startMainActivity()
                }, {
                    Timber.e(it)
                })
    }

    private fun startMainActivity() {
        val intent = MainActivity.createIntent(this)
        startActivity(intent)
        finish()
    }
}
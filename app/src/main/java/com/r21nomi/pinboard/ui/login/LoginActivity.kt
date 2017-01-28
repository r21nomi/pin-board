package com.r21nomi.pinboard.ui.login

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.View
import com.r21nomi.core.model.LoginModel
import com.r21nomi.pinboard.R
import com.r21nomi.pinboard.databinding.ActivityLoginBinding
import com.r21nomi.pinboard.di.ActivityComponent
import com.r21nomi.pinboard.ui.BaseActivity
import com.r21nomi.pinboard.ui.login.LoginActivityListener
import javax.inject.Inject

class LoginActivity : BaseActivity() {

    @Inject
    lateinit var mLoginModel: LoginModel

    private val binding: ActivityLoginBinding by lazy {
        DataBindingUtil.setContentView<ActivityLoginBinding>(this, R.layout.activity_login)
    }

    private val listener: LoginActivityListener = object : LoginActivityListener {
        override fun onLoginButtonClick(view: View) {
            val oauthIntent = mLoginModel.getOAuthIntent()
            startActivity(oauthIntent)
        }
    }

    companion object {
        fun createIntent(context: Context): Intent {
            val intent = Intent(context, LoginActivity::class.java)
            return intent
        }
    }

    override fun injectDependency(component: ActivityComponent) {
        component.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        binding.listener = listener
    }
}

package com.r21nomi.pinboard.ui.login

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import com.r21nomi.pinboard.R
import com.r21nomi.pinboard.databinding.ActivityLoginBinding
import com.r21nomi.pinboard.ui.BaseActivity
import javax.inject.Inject

class LoginActivity : BaseActivity<LoginComponent>() {

    @Inject
    lateinit var loginViewModel: LoginViewModel

    private val binding: ActivityLoginBinding by lazy {
        DataBindingUtil.setContentView<ActivityLoginBinding>(this, R.layout.activity_login)
    }

    companion object {
        fun createIntent(context: Context): Intent {
            val intent = Intent(context, LoginActivity::class.java)
            return intent
        }
    }

    override fun buildComponent(): LoginComponent {
        return DaggerLoginComponent.builder()
                .applicationComponent(getApplicationComponent())
                .build()
    }

    override fun injectDependency(component: LoginComponent) {
        component.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.viewModel = loginViewModel
    }
}

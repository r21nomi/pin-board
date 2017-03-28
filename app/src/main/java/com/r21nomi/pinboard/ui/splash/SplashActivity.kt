package com.r21nomi.pinboard.ui.splash

import android.os.Bundle
import android.os.Handler
import com.r21nomi.pinboard.R
import com.r21nomi.pinboard.domain.login.GetAccessToken
import com.r21nomi.pinboard.ui.BaseActivity
import com.r21nomi.pinboard.ui.login.LoginActivity
import com.r21nomi.pinboard.ui.main.MainActivity
import javax.inject.Inject

class SplashActivity : BaseActivity<SplashComponent>() {

    companion object {
        val LAUNCH_DELAY : Long = 1000
    }

    @Inject
    lateinit var getAccessToken: GetAccessToken

    private val handler = Handler()

    override fun buildComponent(): SplashComponent {
        return DaggerSplashComponent.builder()
                .applicationComponent(getApplicationComponent())
                .build()
    }

    override fun injectDependency(component: SplashComponent) {
        component.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        handler.postDelayed({
            if (getAccessToken.execute().isNotBlank()) {
                startActivity(MainActivity.createIntent(this@SplashActivity))
            } else {
                startActivity(LoginActivity.createIntent(this@SplashActivity))
            }
            finish()
        }, LAUNCH_DELAY)
    }
}

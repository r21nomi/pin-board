package com.r21nomi.pinboard.ui

import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import com.r21nomi.pinboard.R
import com.r21nomi.pinboard.ui.login.LoginActivity

class SplashActivity : AppCompatActivity() {

    companion object {
        val LAUNCH_DELAY : Long = 1000
    }

    private val handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        handler.postDelayed({
            startActivity(LoginActivity.createIntent(this@SplashActivity))
            finish()
        }, LAUNCH_DELAY)
    }
}

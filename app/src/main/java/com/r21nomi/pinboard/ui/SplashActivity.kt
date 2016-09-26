package com.r21nomi.pinboard.ui

import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import com.r21nomi.pinboard.R

class SplashActivity : AppCompatActivity() {

    companion object {
        val LAUNCH_DELAY : Long = 1000
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            startActivity(LoginActivity.createIntent(this@SplashActivity))
            finish()
        }, LAUNCH_DELAY)
    }
}

package com.r21nomi.pinboard.ui

import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import com.nomi.artwatch.ui.util.DeepLinkRouter
import com.r21nomi.pinboard.R
import rx.android.schedulers.AndroidSchedulers

class MainActivity: BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val uri: Uri = intent.data

        DeepLinkRouter
                .getAccessToken(uri)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ accessToken ->
                    Toast.makeText(this, accessToken, Toast.LENGTH_SHORT).show()
                })
    }
}

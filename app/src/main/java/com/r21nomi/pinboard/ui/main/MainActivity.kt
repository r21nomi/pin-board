package com.r21nomi.pinboard.ui.main

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import com.nomi.artwatch.ui.util.DeepLinkRouter
import com.r21nomi.core.login.usecase.SaveAccessToken
import com.r21nomi.core.pin.usecase.GetPins
import com.r21nomi.pinboard.R
import com.r21nomi.pinboard.databinding.ActivityMainBinding
import com.r21nomi.pinboard.ui.BaseActivity
import rx.Completable
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import timber.log.Timber
import javax.inject.Inject

class MainActivity: BaseActivity() {

    companion object {
        fun createIntent(context: Context): Intent {
            val intent = Intent(context, MainActivity::class.java)
            return intent
        }
        val LIMIT = 50
    }

    @Inject
    lateinit var saveAccessToken: SaveAccessToken
    @Inject
    lateinit var getPins: GetPins

    private val binding: ActivityMainBinding by lazy {
        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DaggerMainComponent.builder()
                .applicationComponent(getApplicationComponent())
                .build()
                .inject(this)

        val uri: Uri? = intent.data
        val observable: Completable

        if (uri != null) {
            observable = DeepLinkRouter
                    .getAccessToken(uri)
                    .observeOn(AndroidSchedulers.mainThread())
                    .flatMap { accessToken ->
                        Toast.makeText(this, accessToken, Toast.LENGTH_SHORT).show()
                        saveAccessToken.execute(accessToken)
                        return@flatMap Observable.just(null)
                    }.toCompletable()
        } else {
            observable = Completable.complete()
        }

        observable.subscribe {
            binding.text.text = "completed!!!!"

            getPins
                    .execute(LIMIT)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        Timber.d("data : " + it.data.size)
                    }
        }
    }
}

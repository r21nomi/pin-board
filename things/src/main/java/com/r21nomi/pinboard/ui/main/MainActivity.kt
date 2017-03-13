package com.r21nomi.pinboard.ui.main

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.net.Uri
import android.os.Bundle
import com.bumptech.glide.Glide
import com.flaviofaria.kenburnsview.KenBurnsView
import com.flaviofaria.kenburnsview.Transition
import com.r21nomi.core.pin.entity.Page
import com.r21nomi.core.pin.entity.Pin
import com.r21nomi.pinboard.R
import com.r21nomi.pinboard.databinding.ActivityMainBinding
import com.r21nomi.pinboard.domain.pin.GetPins
import com.r21nomi.pinboard.ui.BaseActivity
import rx.android.schedulers.AndroidSchedulers
import timber.log.Timber
import java.util.*
import javax.inject.Inject

class MainActivity : BaseActivity() {

    companion object {
        fun createIntent(context: Context): Intent {
            val intent = Intent(context, MainActivity::class.java)
            return intent
        }

        val LIMIT = 50
        val DIFF = 5
    }

    @Inject
    lateinit var getPins: GetPins

    private val binding: ActivityMainBinding by lazy {
        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
    }
    private var dataSet: MutableList<Pin> = ArrayList()
    private var lastPage: Page? = null
    private var currentImagePosition: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DaggerMainComponent.builder()
                .applicationComponent(getApplicationComponent())
                .build()
                .inject(this)

        fetch("")

        binding.kenBurnsView.setTransitionListener(object : KenBurnsView.TransitionListener {
            override fun onTransitionStart(transition: Transition?) {
                Timber.v("onTransitionStart")
            }

            override fun onTransitionEnd(transition: Transition?) {
                Timber.v("onTransitionEnd")

                currentImagePosition++
                loadImage()

                if (shouldFetchNext()) {
                    fetch(lastPage?.cursor ?: "")
                }
            }
        })
    }

    private fun fetch(cursor: String) {
        getPins
                .execute(LIMIT, cursor)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    dataSet.addAll(it.data)
                    lastPage = it.page

                    if (currentImagePosition == 0) {
                        loadImage()
                    }
                }, {
                    Timber.e(it)
                })
    }

    private fun loadImage() {
        if (currentImagePosition >= dataSet.size - 1) {
            currentImagePosition = 0
            return
        }
        val uri = Uri.parse(dataSet[currentImagePosition].images.image.url)
        Glide.with(this)
                .load(uri)
                .into(binding.kenBurnsView)
    }

    private fun shouldFetchNext(): Boolean {
        return dataSet.size > 0 && (dataSet.size - 1) - currentImagePosition < DIFF
    }
}

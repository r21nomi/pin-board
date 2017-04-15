package com.r21nomi.pinboard.ui.main

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import com.r21nomi.pinboard.R
import com.r21nomi.pinboard.databinding.ActivityMainBinding
import com.r21nomi.pinboard.ui.BaseActivity
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
    lateinit var mainViewModel: MainViewModel

    private val binding: ActivityMainBinding by lazy {
        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DaggerMainComponent.builder()
                .applicationComponent(getApplicationComponent())
                .build()
                .inject(this)

        binding.viewModel = mainViewModel

        mainViewModel.fetch("")
    }
}

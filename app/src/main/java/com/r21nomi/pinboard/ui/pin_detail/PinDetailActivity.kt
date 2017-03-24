package com.r21nomi.pinboard.ui.pin_detail

import android.databinding.DataBindingUtil
import android.os.Bundle
import com.r21nomi.pinboard.R
import com.r21nomi.pinboard.databinding.ActivityPinDetailBinding
import com.r21nomi.pinboard.ui.BaseActivity
import com.r21nomi.pinboard.ui.Navigator.Companion.KEY_PIN

class PinDetailActivity : BaseActivity() {

    private val binding: ActivityPinDetailBinding by lazy {
        DataBindingUtil.setContentView<ActivityPinDetailBinding>(this, R.layout.activity_pin_detail)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.viewModel = PinDetailViewModel(intent.getParcelableExtra(KEY_PIN))
    }
}

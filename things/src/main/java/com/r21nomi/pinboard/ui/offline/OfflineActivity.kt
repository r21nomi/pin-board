package com.r21nomi.pinboard.ui.offline

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import com.r21nomi.pinboard.R
import com.r21nomi.pinboard.databinding.ActivityOfflineBinding
import com.r21nomi.pinboard.ui.BaseActivity
import javax.inject.Inject

/**
 * Created by r21nomi on 2017/08/03.
 */
class OfflineActivity : BaseActivity() {

    companion object {
        fun createIntent(context: Context): Intent {
            return Intent(context, OfflineActivity::class.java)
        }
    }

    @Inject
    lateinit var offlineViewModel: OfflineViewModel

    val binding: ActivityOfflineBinding by lazy {
        DataBindingUtil.setContentView<ActivityOfflineBinding>(this, R.layout.activity_offline)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DaggerOfflineComponent.builder()
                .applicationComponent(getApplicationComponent())
                .build()
                .inject(this)

        binding.viewModel = offlineViewModel

        offlineViewModel.fetch()
    }
}
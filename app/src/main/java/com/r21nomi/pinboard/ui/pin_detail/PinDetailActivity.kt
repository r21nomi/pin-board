package com.r21nomi.pinboard.ui.pin_detail

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.FragmentTransaction
import com.r21nomi.core.pin.entity.Pin
import com.r21nomi.pinboard.R
import com.r21nomi.pinboard.databinding.ActivityPinDetailBinding
import com.r21nomi.pinboard.ui.BaseActivity
import com.r21nomi.pinboard.ui.Navigator.Companion.KEY_PIN
import com.r21nomi.pinboard.ui.pin_detail.di.DaggerPinDetailComponent
import com.r21nomi.pinboard.ui.pin_detail.di.PinDetailComponent

class PinDetailActivity : BaseActivity<PinDetailComponent>() {

    private val binding: ActivityPinDetailBinding by lazy {
        DataBindingUtil.setContentView<ActivityPinDetailBinding>(this, R.layout.activity_pin_detail)
    }
    val pin: Pin by lazy {
        intent.getParcelableExtra<Pin>(KEY_PIN)
    }

    override fun buildComponent(): PinDetailComponent {
         return DaggerPinDetailComponent.builder()
                .applicationComponent(getApplicationComponent())
                .build()
    }

    override fun injectDependency(component: PinDetailComponent) {
        component.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.container // TODO: delete

        val pin: Pin = intent.getParcelableExtra(KEY_PIN)

        supportFragmentManager.beginTransaction()
                .replace(R.id.container, PinDetailFragment.newInstance(pin))
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit()
    }
}

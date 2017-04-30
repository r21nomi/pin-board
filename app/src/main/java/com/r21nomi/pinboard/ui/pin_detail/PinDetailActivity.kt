package com.r21nomi.pinboard.ui.pin_detail

import android.app.Activity
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.SharedElementCallback
import android.support.v4.view.ViewPager
import android.view.View
import com.r21nomi.core.pin.entity.Pin
import com.r21nomi.pinboard.R
import com.r21nomi.pinboard.databinding.ActivityPinDetailBinding
import com.r21nomi.pinboard.ui.BaseActivity
import com.r21nomi.pinboard.ui.Navigator
import com.r21nomi.pinboard.ui.Navigator.Companion.KEY_PIN
import com.r21nomi.pinboard.ui.pin_detail.di.DaggerPinDetailComponent
import com.r21nomi.pinboard.ui.pin_detail.di.PinDetailComponent
import com.r21nomi.pinboard.util.ViewUtil
import rx.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class PinDetailActivity : BaseActivity<PinDetailComponent>(), PinDetailFragment.Listener {

    companion object {
        val POSITION = "position"
    }

    private val binding: ActivityPinDetailBinding by lazy {
        DataBindingUtil.setContentView<ActivityPinDetailBinding>(this, R.layout.activity_pin_detail)
    }
    private val pin: Pin by lazy {
        intent.getParcelableExtra<Pin>(KEY_PIN)
    }
    private val position: Int by lazy {
        intent.getIntExtra(POSITION, 0)
    }

    @Inject
    lateinit var viewModel: PinDetailViewModel

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

        binding.viewPager

        ViewUtil.getActionBarWithTransitionName(this)

        supportPostponeEnterTransition()

        viewModel
                .fetch()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    val pagerAdapter = PinDetailFragmentPagerAdapter(supportFragmentManager, it)
                    binding.viewPager.pageMargin = resources.getDimensionPixelSize(R.dimen.detail_viewpager_horizontal_margin)
                    binding.viewPager.adapter = pagerAdapter
                    binding.viewPager.currentItem = 0
                    binding.viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                        override fun onPageScrollStateChanged(state: Int) {
                            // no-op
                        }

                        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                            // no-op
                        }

                        override fun onPageSelected(position: Int) {
                            setResultPosition(position)
                        }
                    })

                    setEnterSharedElementCallback(object : SharedElementCallback() {
                        override fun onMapSharedElements(names: List<String>?, sharedElements: MutableMap<String, View>?) {
                            val position = binding.viewPager.currentItem
                            val view = binding.viewPager.findViewWithTag(PinDetailFragment.getTag(position))
                            sharedElements!!.clear()
                            sharedElements.put(Navigator.SHARED_ELEMENT_NAME, view)
                        }
                    })
                }

        setResultPosition(position)
    }

    override fun onSharedElementViewPreDrawn() {
        supportStartPostponedEnterTransition()
    }

    private fun setResultPosition(position: Int) {
        val intent = Intent()
        val bundle = Bundle()
        bundle.putInt(POSITION, position)
        intent.putExtras(bundle)

        setResult(Activity.RESULT_OK, intent)
    }
}

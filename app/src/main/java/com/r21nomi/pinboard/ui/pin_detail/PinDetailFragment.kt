package com.r21nomi.pinboard.ui.pin_detail

import android.os.Bundle
import android.support.v4.view.ViewCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import com.facebook.drawee.drawable.ScalingUtils.ScaleType.CENTER_CROP
import com.facebook.drawee.drawable.ScalingUtils.ScaleType.FIT_CENTER
import com.facebook.drawee.view.DraweeTransition
import com.r21nomi.core.pin.entity.Pin
import com.r21nomi.pinboard.R
import com.r21nomi.pinboard.databinding.FragmentPinDetailBinding
import com.r21nomi.pinboard.domain.pin.GetPins
import com.r21nomi.pinboard.ui.BaseFragment
import com.r21nomi.pinboard.ui.Navigator
import com.r21nomi.pinboard.util.WindowUtil
import javax.inject.Inject

/**
 * Created by r21nomi on 2017/03/26.
 */
class PinDetailFragment : BaseFragment<PinDetailFragment.Component>() {

    companion object {
        val KEY_PIN = "key_pin"

        fun newInstance(pin: Pin): PinDetailFragment {
            val fragment = PinDetailFragment()
            val bundle = Bundle()
            bundle.putParcelable(KEY_PIN, pin)
            fragment.arguments = bundle
            return fragment
        }
    }

    val pin: Pin by lazy {
        arguments.getParcelable<Pin>(KEY_PIN)
    }
    val binding: FragmentPinDetailBinding by lazy {
        FragmentPinDetailBinding.bind(view)
    }

    @Inject
    lateinit var getPins: GetPins

    override fun getLayout(): Int {
        return R.layout.fragment_pin_detail
    }

    override fun injectDependency(component: Component) {
        component.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)

        postponeEnterTransition()

        // These are necessary for shared element transition with SimpleDraweeView.
        activity.window.sharedElementEnterTransition = DraweeTransition.createTransitionSet(CENTER_CROP, FIT_CENTER)
        activity.window.sharedElementReturnTransition = DraweeTransition.createTransitionSet(FIT_CENTER, CENTER_CROP)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        init()
    }

    fun init() {
        ViewCompat.setTransitionName(binding.thumb, Navigator.SHARED_ELEMENT_NAME)

        binding.viewModel = PinDetailViewModel(pin, WindowUtil.getWidth(activity))

        binding.thumb.viewTreeObserver.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
            override fun onPreDraw(): Boolean {
                binding.thumb.viewTreeObserver.removeOnPreDrawListener(this)
                startPostponedEnterTransition()
                return true
            }
        })
    }

    interface Component {
        fun inject(fragment: PinDetailFragment)
    }
}
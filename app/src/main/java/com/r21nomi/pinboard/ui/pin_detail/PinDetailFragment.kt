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
import com.r21nomi.pinboard.ui.BaseFragment
import com.r21nomi.pinboard.ui.Navigator
import javax.inject.Inject

/**
 * Created by r21nomi on 2017/03/26.
 */
class PinDetailFragment : BaseFragment<PinDetailFragment.Component>() {

    companion object {
        private val KEY_PIN = "key_pin"
        private val TAG_PREFIX = "tag_item"

        fun getTag(position: Int): String {
            return TAG_PREFIX + position
        }

        fun newInstance(pin: Pin): PinDetailFragment {
            val fragment = PinDetailFragment()
            val bundle = Bundle()
            bundle.putParcelable(KEY_PIN, pin)
            fragment.arguments = bundle
            return fragment
        }
    }

    // If you use lazy, data binding does not work correctly.
    private lateinit var binding: FragmentPinDetailBinding

    private val listener: Listener by lazy { activity as Listener }

    private val pin: Pin by lazy { arguments.getParcelable<Pin>(KEY_PIN) }

    private var tagName: String = ""

    @Inject
    lateinit var viewModel: PinDetailViewModel

    override fun getLayout(): Int {
        return R.layout.fragment_pin_detail
    }

    override fun injectDependency(component: Component) {
        component.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)

        binding = FragmentPinDetailBinding.bind(view)

        binding.pin = pin
        binding.viewModel = viewModel

        // Set tag name to shared element view.
        binding.thumb.tag = tagName

        // These are necessary for shared element transition with SimpleDraweeView.
        activity.window.sharedElementEnterTransition = DraweeTransition.createTransitionSet(CENTER_CROP, FIT_CENTER)
        activity.window.sharedElementReturnTransition = DraweeTransition.createTransitionSet(FIT_CENTER, CENTER_CROP)

        initTransition()

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    fun setTag(position: Int) {
        tagName = getTag(position)
    }

    private fun initTransition() {
        ViewCompat.setTransitionName(binding.thumb, Navigator.SHARED_ELEMENT_NAME)

        activity.window.sharedElementEnterTransition = DetailTransitionSet()
        activity.window.sharedElementReturnTransition = DetailTransitionSet()

        binding.thumb.viewTreeObserver.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
            override fun onPreDraw(): Boolean {
                binding.thumb.viewTreeObserver.removeOnPreDrawListener(this)
                listener.onSharedElementViewPreDrawn()
                return true
            }
        })
    }

    interface Component {
        fun inject(fragment: PinDetailFragment)
    }

    interface Listener {
        fun onSharedElementViewPreDrawn()
    }
}
package com.r21nomi.pinboard.ui.pin_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.r21nomi.pinboard.R
import com.r21nomi.pinboard.domain.pin.GetPins
import com.r21nomi.pinboard.ui.BaseFragment
import javax.inject.Inject

/**
 * Created by r21nomi on 2017/03/26.
 */
class PinDetailFragment : BaseFragment<PinDetailFragment.Component>() {

    companion object {
        fun newInstance(): PinDetailFragment {
            return PinDetailFragment()
        }
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

        return view
    }

    interface Component {
        fun inject(fragment: PinDetailFragment)
    }
}
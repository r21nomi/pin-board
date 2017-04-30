package com.r21nomi.pinboard.ui.pin_detail

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.r21nomi.core.pin.entity.Pin

/**
 * Created by r21nomi on 2017/04/29.
 */
class PinDetailFragmentPagerAdapter(
        fragmentManager: FragmentManager,
        val dataSet: List<Pin>
) : FragmentPagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment {
        return PinDetailFragment.newInstance(dataSet[position])
                .apply {
                    setTag(position)
                }
    }

    override fun getCount(): Int {
        return dataSet.size
    }
}
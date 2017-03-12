package com.r21nomi.pinboard.ui.main

import android.databinding.DataBindingUtil
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.r21nomi.core.pin.entity.Pin
import com.r21nomi.pinboard.R
import com.r21nomi.pinboard.databinding.PinViewholderBinding
import com.yqritc.recyclerviewmultipleviewtypesadapter.DataBindAdapter
import com.yqritc.recyclerviewmultipleviewtypesadapter.DataBinder
import java.util.*

/**
 * Created by r21nomi on 2017/02/05.
 */
class PinBinder(dataBindAdapter: DataBindAdapter, val maximumItemWidth: Int) : DataBinder<PinBinder.ViewHolder>(dataBindAdapter) {

    private var dataSet: MutableList<Pin> = ArrayList()

    override fun newViewHolder(parent: ViewGroup): ViewHolder {
        val binding = DataBindingUtil.inflate<PinViewholderBinding>(
                LayoutInflater.from(parent.context), R.layout.pin_viewholder, parent, false
        )
        return ViewHolder(binding)
    }

    override fun bindViewHolder(holder: ViewHolder, position: Int) {
        val item: Pin = dataSet[position]

        val uri = Uri.parse(item.images.image.url)
        holder.binding.thumb.setImageURI(uri, null)

        val param = holder.binding.thumb.layoutParams
        param.height = item.images.image.height * maximumItemWidth / item.images.image.width
        holder.binding.thumb.layoutParams = param
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    fun addDataSet(dataSet: List<Pin>) {
        this.dataSet.addAll(dataSet)
    }

    fun getDataSet(): List<Pin> {
        return ArrayList(dataSet)
    }

    inner class ViewHolder(val binding: PinViewholderBinding): RecyclerView.ViewHolder(binding.root) {
        // no-op
    }
}
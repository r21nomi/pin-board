package com.r21nomi.pinframe.ui.common.adapter

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager

/**
 * Created by Ryota Niinomi on 2017/03/06.
 */
abstract class InfiniteScrollRecyclerListener : RecyclerView.OnScrollListener {

    companion object {
        private var THRESHOLD_BUFFER_VIEW_TYPE_LINE = 10
    }

    abstract fun onLoadMore(page: Int, totalItemCount: Int)

    private val visibleThreshold: Int
    private val layoutManager: StaggeredGridLayoutManager

    private var loading: Boolean = false
    private var currentPage: Int = 0
    private var previousTotal: Int = 0

    constructor(layoutManager: StaggeredGridLayoutManager) : this(layoutManager, THRESHOLD_BUFFER_VIEW_TYPE_LINE)

    constructor(layoutManager: StaggeredGridLayoutManager, visibleThreshold: Int) : super() {
        this.layoutManager = layoutManager
        this.visibleThreshold = visibleThreshold
        init()
    }

    private fun init() {
        loading = true
        currentPage = 0
        previousTotal = 0
    }

    override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
        val visibleItemCount = recyclerView!!.childCount
        val totalItemCount = layoutManager.itemCount
        val firstVisibleItem = layoutManager.findFirstVisibleItemPositions(null)

        if (dy < 0) {
            // Do nothing if up direction.
            return
        }
        if (totalItemCount < previousTotal) {
            init()
        }

        if (loading && totalItemCount > previousTotal) {
            loading = false
            previousTotal = totalItemCount
            currentPage++
        }

        // Not loading.
        // The item being Displayed is within 'visibleThreshold'.
        if (!loading && totalItemCount - visibleItemCount <= firstVisibleItem[firstVisibleItem.size - 1] + visibleThreshold) {
            // End has been reached

            onLoadMore(currentPage + 1, totalItemCount)

            loading = true
        }
    }
}
package com.r21nomi.pinboard.ui.main

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.net.Uri
import android.os.Bundle
import android.support.v7.widget.StaggeredGridLayoutManager
import android.widget.Toast
import com.r21nomi.core.pin.entity.Page
import com.r21nomi.pinboard.R
import com.r21nomi.pinboard.databinding.ActivityMainBinding
import com.r21nomi.pinboard.domain.login.SaveAccessToken
import com.r21nomi.pinboard.domain.pin.GetPins
import com.r21nomi.pinboard.ui.BaseActivity
import com.r21nomi.pinboard.util.DeepLinkRouter
import com.r21nomi.pinboard.util.ViewUtil
import com.r21nomi.pinboard.util.WindowUtil
import com.r21nomi.qiitaclientandroid.ui.adapter.InfiniteScrollRecyclerListener
import com.yqritc.recyclerviewmultipleviewtypesadapter.ListBindAdapter
import rx.Completable
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import timber.log.Timber
import javax.inject.Inject

class MainActivity: BaseActivity() {

    companion object {
        fun createIntent(context: Context): Intent {
            val intent = Intent(context, MainActivity::class.java)
            return intent
        }
        val LIMIT = 50
        val COLUMN = 2
    }

    @Inject
    lateinit var saveAccessToken: SaveAccessToken
    @Inject
    lateinit var getPins: GetPins

    private val binding: ActivityMainBinding by lazy {
        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
    }
    private val adapter: ListBindAdapter = ListBindAdapter()
    private val binder: PinBinder by lazy {
        PinBinder(adapter, WindowUtil.getWidth(this) / 2)
    }
    private var lastPage: Page ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DaggerMainComponent.builder()
                .applicationComponent(getApplicationComponent())
                .build()
                .inject(this)

        initAdapter()

        val uri: Uri? = intent.data
        val observable: Completable

        if (uri != null) {
            observable = DeepLinkRouter
                    .getAccessToken(uri)
                    .observeOn(AndroidSchedulers.mainThread())
                    .flatMap { accessToken ->
                        Toast.makeText(this, accessToken, Toast.LENGTH_SHORT).show()
                        saveAccessToken.execute(accessToken)
                        return@flatMap Observable.just(null)
                    }.toCompletable()
        } else {
            observable = Completable.complete()
        }

        observable.subscribe {
            fetch("")
        }
    }

    private fun initAdapter() {
        val gridLayoutManager = StaggeredGridLayoutManager(COLUMN, 1)
        adapter.addBinder(binder)
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.layoutManager = gridLayoutManager
        binding.recyclerView.adapter = adapter
        binding.recyclerView.addOnScrollListener(object : InfiniteScrollRecyclerListener(gridLayoutManager) {
            override fun onLoadMore(page: Int, totalItemCount: Int) {
                if (lastPage == null) {
                    return
                }
                fetch(lastPage?.cursor ?: "")
            }
        })
    }

    private fun fetch(cursor: String) {
        getPins
                .execute(LIMIT, cursor)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Timber.d("data : " + it.data.size)
                    lastPage = it.page
                    binder.addDataSet(it.data)
                    binder.notifyBinderDataSetChanged()
                }, {
                    Timber.e(it)
                    ViewUtil.showSnackBar(this, R.string.error)
                })
    }
}

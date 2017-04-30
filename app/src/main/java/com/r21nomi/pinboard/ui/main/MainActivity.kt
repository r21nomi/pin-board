package com.r21nomi.pinboard.ui.main

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.SharedElementCallback
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.View
import com.r21nomi.core.pin.entity.Page
import com.r21nomi.pinboard.R
import com.r21nomi.pinboard.databinding.ActivityMainBinding
import com.r21nomi.pinboard.ui.BaseActivity
import com.r21nomi.pinboard.ui.Navigator
import com.r21nomi.pinboard.ui.login.MainModule
import com.r21nomi.pinboard.ui.pin_detail.PinDetailActivity
import com.r21nomi.pinboard.util.ViewUtil
import com.r21nomi.pinboard.util.WindowUtil
import com.r21nomi.qiitaclientandroid.ui.adapter.InfiniteScrollRecyclerListener
import com.yqritc.recyclerviewmultipleviewtypesadapter.ListBindAdapter
import rx.android.schedulers.AndroidSchedulers
import rx.functions.Func0
import timber.log.Timber
import javax.inject.Inject

class MainActivity: BaseActivity<MainComponent>() {

    companion object {
        fun createIntent(context: Context): Intent {
            val intent = Intent(context, MainActivity::class.java)
            return intent
        }
        val COLUMN = 2
    }

    @Inject
    lateinit var viewModel: MainViewModel

    private val binding: ActivityMainBinding by lazy {
        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
    }
    private val adapter: ListBindAdapter = ListBindAdapter()
    private val binder: PinBinder by lazy {
        PinBinder(adapter, WindowUtil.getWidth(this) / 2, Func0 { return@Func0 this })
    }
    private var lastPage: Page ?= null

    override fun buildComponent(): MainComponent {
        return DaggerMainComponent.builder()
                .applicationComponent(getApplicationComponent())
                .mainModule(MainModule(this))
                .build()
    }

    override fun injectDependency(component: MainComponent) {
        component.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initAdapter()

        viewModel.saveAccessTokenIfNeeded(intent.data)
                .subscribe({
                    fetch("")
                }, {
                    Timber.e(it)
                    ViewUtil.showSnackBar(this, R.string.error)
                })
    }

    override fun onActivityReenter(resultCode: Int, data: Intent) {
        super.onActivityReenter(resultCode, data)
        if (resultCode != Activity.RESULT_OK) {
            return
        }

        val dataSetPosition = data.getIntExtra(PinDetailActivity.POSITION, 0)
        val viewHolder = binding.recyclerView.findViewHolderForAdapterPosition(dataSetPosition)
                as? PinBinder.ViewHolder ?: return
        val sharedElementView = viewHolder.binding.thumb

        setExitSharedElementCallback(object : SharedElementCallback() {
            override fun onMapSharedElements(names: List<String>, sharedElements: MutableMap<String, View>) {
                sharedElements.clear()
                sharedElements.put(Navigator.SHARED_ELEMENT_NAME, sharedElementView)
                setExitSharedElementCallback(null as SharedElementCallback?)
            }
        })
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
        viewModel.fetch(cursor)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    binder.addDataSet(it)
                    binder.notifyBinderDataSetChanged()
                }, {
                    Timber.e(it)
                    ViewUtil.showSnackBar(this, R.string.error)
                })
    }
}

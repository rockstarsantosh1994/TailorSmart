package com.praxello.tailorsmart.widget

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.LinearLayout.HORIZONTAL
import android.widget.RelativeLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.praxello.tailorsmart.R
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit

class InfiniteRotationView(context: Context, attributeSet: AttributeSet)
    : RelativeLayout(context, attributeSet) {

    //    @BindView(R.id.recyclerView_horizontalList)
    var recyclerView: RecyclerView? = null

    private val layoutManager: LinearLayoutManager
    private lateinit var onScrollListener: OnScrollListener

    private var dispose: Disposable? = null

    init {
        val view = View.inflate(context, R.layout.view_infinite_rotation, this)
//        ButterKnife.bind(this, view)
        recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView_horizontalList)
        layoutManager = LinearLayoutManager(context, HORIZONTAL, false)
        Log.e("init", "block")
    }

    fun setAdapter(adapter: InfiniteRotationAdapter) {
        Log.e("set", "adapter")
        recyclerView?.layoutManager = layoutManager
        recyclerView?.adapter = adapter
        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(recyclerView)

        adapter.itemCount
                .takeIf { it > 1 }
                ?.apply {
                    onScrollListener = OnScrollListener(
                            adapter.itemCount,
                            layoutManager
                    ) {
                        // When dragging, we assume user swiped. So we will stop auto rotation
                        if (it == RecyclerView.SCROLL_STATE_DRAGGING) {
                            dispose?.dispose()
                        }
                    }
                    recyclerView?.addOnScrollListener(onScrollListener)
                    recyclerView?.scrollToPosition(1)
                }
    }

    fun autoScroll(listSize: Int, intervalInMillis: Long) {
        dispose?.let {
            if (!it.isDisposed) return
        }
        dispose = Flowable.interval(intervalInMillis, TimeUnit.MILLISECONDS)
                .map { it % listSize + 1 }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    recyclerView?.smoothScrollToPosition(it.toInt() + 1)
                }
    }

    fun stopAutoScroll() {
        dispose?.let(Disposable::dispose)
    }

    class OnScrollListener(
            val itemCount: Int,
            val layoutManager: LinearLayoutManager,
            val stateChanged: (Int) -> Unit) : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val firstItemVisible = layoutManager.findFirstVisibleItemPosition()

            if (firstItemVisible > 0 && firstItemVisible % (itemCount - 1) == 0) {
                // When position reaches end of the list, it should go back to the beginning
                recyclerView.scrollToPosition(1)
            } else if (firstItemVisible == 0) {
                // When position reaches beginning of the list, it should go back to the end
                recyclerView.scrollToPosition(itemCount - 1)
            }
        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            stateChanged(newState)
        }
    }
}
package com.sunny.classcome.widget.refresh

import android.content.Context
import android.support.v4.util.SparseArrayCompat
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup

class KRecyclerView : RecyclerView {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle)

    private val ITEM_TYPE_HEADER_INIT = 100000
    private val ITEM_TYPE_FOOTER_INIT = 200000
    private val ITEM_TYPE_LOADMORE = 300000

    var hasMore: Boolean = false
        set(value) {
            field = value
            if (value) showMore = true
        }

    var showMore: Boolean = false
    private var mLoading: Boolean = false
    private var loadMoreEnable: Boolean = true

    private var mWrapAdapter: WrapAdapter = WrapAdapter()
    private var mInnerAdapter: Adapter<ViewHolder>? = null

    private var mLoadMoreView: KLoadMoreView? = null
    private val mHeaderViews = SparseArrayCompat<View>()
    private val mFooterViews = SparseArrayCompat<View>()

    private var mLoadMoreListener: ((RecyclerView) -> Unit)? = null


    override fun setAdapter(adapter: Adapter<ViewHolder>?) {
        if (mInnerAdapter != null) {
            mInnerAdapter?.unregisterAdapterDataObserver(mDataObserver)
        }
        mInnerAdapter = adapter
        mInnerAdapter?.registerAdapterDataObserver(mDataObserver)
        super.setAdapter(mWrapAdapter)
    }

    /**
     * 添加HeaderView
     */
    fun addHeaderView(view: View) {
        mHeaderViews.put(mHeaderViews.size() + ITEM_TYPE_HEADER_INIT, view)
        mWrapAdapter.notifyDataSetChanged()
    }

    /**
     * 添加FooterView
     */
    fun addFooterView(view: View) {
        mFooterViews.put(mFooterViews.size() + ITEM_TYPE_FOOTER_INIT, view)
        mWrapAdapter.notifyDataSetChanged()
    }

    /**
     * 设置LoadMoreView
     * 必须是一个视图View
     */
    fun setLoadMoreView(loadMoreView: KLoadMoreView) {
        if (loadMoreView !is View) {
            throw IllegalStateException("KLoadMoreView must is a View?")
        }

        this.mLoadMoreView = loadMoreView

        mWrapAdapter.notifyDataSetChanged()

        removeOnScrollListener(defaultScrollListener)
        if (mLoadMoreView?.shouldLoadMore(this) == false) {
            addOnScrollListener(defaultScrollListener)
        }
    }

    /**
     * 开始加载更多
     */
    fun startLoadMore() {
        if (!mLoading && loadMoreEnable && hasMore && mLoadMoreView != null) {
            mLoading = true
            mLoadMoreView?.onLoadMore(this)
            mLoadMoreListener?.invoke(this)
            if (parent is KRefreshLayout) {
                (parent as KRefreshLayout).refreshEnable = false
            }
        }
        if (mLoading) {
            mLoadMoreView?.let {
                (it as ClassicalFooter).startAnim()
            }
        }

    }

    /**
     * 加载完成
     */
    fun loadMoreComplete(hasMore: Boolean) {
        mLoading = false
        mLoadMoreView?.onComplete(this, hasMore)
        this.hasMore = hasMore

        if (parent is KRefreshLayout) {
            (parent as KRefreshLayout).refreshEnable = true
        }
    }

    fun loadMoreError(errorCode: Int) {
        mLoading = false
        mLoadMoreView?.onError(this, errorCode)
    }

    /**
     * 设置加载更多监听
     */
    fun setLoadMoreListener(loadMoreListener: (recyclerView: RecyclerView) -> Unit) {
        mLoadMoreListener = loadMoreListener
    }

    /**
     * 默认的加载触发时机
     */
    private val defaultScrollListener = object : OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            if (parent is KRefreshLayout && mLoadMoreView!= null){
                if ((parent as KRefreshLayout).getRefreshing()){
                    mLoadMoreView = null
                    mWrapAdapter.notifyDataSetChanged()
                }
            }

            if (!recyclerView.canScrollVertically(1)) {
                startLoadMore()
            }
        }
    }

    private inner class WrapAdapter : Adapter<ViewHolder>() {
        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            if (!isContent(position)) {
                return
            }
            mInnerAdapter?.onBindViewHolder(holder, position - mHeaderViews.size())
        }

        override fun getItemCount(): Int {
            val adapterCount = mInnerAdapter?.itemCount ?: 0

            return if (adapterCount > 0) {
                adapterCount + mHeaderViews.size() + mFooterViews.size() + if (mLoadMoreView == null || !showMore) 0 else 1
            } else {//防止没有内容的时候加载更多显示出来
                mHeaderViews.size() + mFooterViews.size()
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            mHeaderViews[viewType]?.let {
                return object : ViewHolder(it) {
                    init {
                        itemView.layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
                    }
                }
            }

            if (viewType == ITEM_TYPE_LOADMORE)
                return object : ViewHolder(mLoadMoreView as View) {
                    init {
                        itemView.layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
                    }
                }

            mFooterViews[viewType]?.let {
                return object : ViewHolder(it) {
                    init {
                        itemView.layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
                    }
                }
            }
            return mInnerAdapter?.onCreateViewHolder(parent, viewType)!!
        }

        override fun getItemViewType(position: Int): Int {
            if (position < mHeaderViews.size()) {
                return mHeaderViews.keyAt(position)
            }

            if (mLoadMoreView != null && position == itemCount - 1 && showMore) {
                mFooterViews[ITEM_TYPE_LOADMORE]?.let {
                    (it as ClassicalFooter).startAnim()
                }
                return ITEM_TYPE_LOADMORE
            }

            if (position >= mHeaderViews.size() + (mInnerAdapter?.itemCount ?: 0)) {
                return mFooterViews.keyAt(position - mHeaderViews.size() - (mInnerAdapter?.itemCount
                        ?: 0))
            }

            return mInnerAdapter?.getItemViewType(position - mHeaderViews.size()) ?: -1
        }

        override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
            mInnerAdapter?.onAttachedToRecyclerView(recyclerView)
            val layoutManager = layoutManager
            if (layoutManager is GridLayoutManager) {
                layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                    override fun getSpanSize(position: Int): Int {
                        if (!isContent(position)) {
                            return layoutManager.spanCount
                        }
                        return 1
                    }
                }
            }
        }

        override fun onViewAttachedToWindow(holder: ViewHolder) {
            mInnerAdapter?.onViewAttachedToWindow(holder)
            val position = holder.layoutPosition
            val layoutParams = holder.itemView.layoutParams
            if (!isContent(position) && layoutParams != null && layoutParams is StaggeredGridLayoutManager.LayoutParams) {
                layoutParams.isFullSpan = true
            }
        }

        fun isContent(position: Int): Boolean {
            if (position < mHeaderViews.size())
                return false
            if (mLoadMoreView != null && position == itemCount - 1)
                return false
            if (position >= mHeaderViews.size() + (mInnerAdapter?.itemCount ?: 0))
                return false
            return true
        }

    }

    private val mDataObserver = object : AdapterDataObserver() {
        override fun onChanged() {
            mWrapAdapter.notifyDataSetChanged()
        }

        override fun onItemRangeChanged(positionStart: Int, itemCount: Int) {
            mWrapAdapter.notifyItemRangeChanged(positionStart, itemCount)
        }

        override fun onItemRangeChanged(positionStart: Int, itemCount: Int, payload: Any?) {
            mWrapAdapter.notifyItemRangeChanged(positionStart, itemCount, payload)
        }

        override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
            mWrapAdapter.notifyItemRangeInserted(positionStart, itemCount)
        }

        override fun onItemRangeMoved(fromPosition: Int, toPosition: Int, itemCount: Int) {
            mWrapAdapter.notifyItemMoved(fromPosition, toPosition)
        }

        override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
            mWrapAdapter.notifyItemRangeRemoved(positionStart, itemCount)
        }
    }

}
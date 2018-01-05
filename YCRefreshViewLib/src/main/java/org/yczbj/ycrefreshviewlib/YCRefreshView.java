package org.yczbj.ycrefreshviewlib;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.ColorRes;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;


import org.yczbj.ycrefreshviewlib.adapter.RecyclerArrayAdapter;
import org.yczbj.ycrefreshviewlib.observer.EasyDataObserver;

import java.util.ArrayList;

/**
 * @author          杨充
 * @version         1.0
 * @date            2017/4/22
 *                  支持多种状态切换；支持上拉加载更多，下拉刷新；支持添加头部或底部view
 */
public class YCRefreshView extends FrameLayout {

    public static final String TAG = "YCRefreshView";
    public static boolean DEBUG = false;
    protected RecyclerView mRecycler;
    protected ViewGroup mProgressView;
    protected ViewGroup mEmptyView;
    protected ViewGroup mErrorView;
    private int mProgressId;
    private int mEmptyId;
    private int mErrorId;
    protected boolean mClipToPadding;
    protected int mPadding;
    protected int mPaddingTop;
    protected int mPaddingBottom;
    protected int mPaddingLeft;
    protected int mPaddingRight;
    protected int mScrollbarStyle;
    protected int mScrollbar;

    protected RecyclerView.OnScrollListener mInternalOnScrollListener;
    protected RecyclerView.OnScrollListener mExternalOnScrollListener;
    protected ArrayList<RecyclerView.OnScrollListener> mExternalOnScrollListenerList = new ArrayList<>();
    protected SwipeRefreshLayout mPtrLayout;
    protected SwipeRefreshLayout.OnRefreshListener mRefreshListener;

    public SwipeRefreshLayout getSwipeToRefresh() {
        return mPtrLayout;
    }

    public RecyclerView getRecyclerView() {
        return mRecycler;
    }

    public YCRefreshView(Context context) {
        super(context);
        initView();
    }

    public YCRefreshView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(attrs);
        initView();
    }

    public YCRefreshView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initAttrs(attrs);
        initView();
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return mPtrLayout.dispatchTouchEvent(ev);
    }


    protected void initAttrs(AttributeSet attrs) {
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.YCRefreshView);
        try {
            mClipToPadding = a.getBoolean(R.styleable.YCRefreshView_recyclerClipToPadding, false);
            mPadding = (int) a.getDimension(R.styleable.YCRefreshView_recyclerPadding, -1.0f);
            mPaddingTop = (int) a.getDimension(R.styleable.YCRefreshView_recyclerPaddingTop, 0.0f);
            mPaddingBottom = (int) a.getDimension(R.styleable.YCRefreshView_recyclerPaddingBottom, 0.0f);
            mPaddingLeft = (int) a.getDimension(R.styleable.YCRefreshView_recyclerPaddingLeft, 0.0f);
            mPaddingRight = (int) a.getDimension(R.styleable.YCRefreshView_recyclerPaddingRight, 0.0f);
            mScrollbarStyle = a.getInteger(R.styleable.YCRefreshView_ycScrollbarStyle, -1);
            mScrollbar = a.getInteger(R.styleable.YCRefreshView_ycScrollbars, -1);
            mEmptyId = a.getResourceId(R.styleable.YCRefreshView_layout_empty, 0);
            mProgressId = a.getResourceId(R.styleable.YCRefreshView_layout_progress, 0);
            mErrorId = a.getResourceId(R.styleable.YCRefreshView_layout_error, 0);
        } finally {
            a.recycle();
        }
    }

    private void initView() {
        if (isInEditMode()) {
            return;
        }
        //生成主View
        View v = LayoutInflater.from(getContext()).inflate(R.layout.refresh_recyclerview, this);
        mPtrLayout = (SwipeRefreshLayout) v.findViewById(R.id.ptr_layout);
        mPtrLayout.setEnabled(false);

        mProgressView = (ViewGroup) v.findViewById(R.id.progress);
        if (mProgressId!=0){
            LayoutInflater.from(getContext()).inflate(mProgressId,mProgressView);
        }
        mEmptyView = (ViewGroup) v.findViewById(R.id.empty);
        if (mEmptyId!=0){
            LayoutInflater.from(getContext()).inflate(mEmptyId,mEmptyView);
        }
        mErrorView = (ViewGroup) v.findViewById(R.id.error);
        if (mErrorId!=0){
            LayoutInflater.from(getContext()).inflate(mErrorId,mErrorView);
        }
        initRecyclerView(v);
    }


    public void setRecyclerPadding(int left,int top,int right,int bottom){
        this.mPaddingLeft = left;
        this.mPaddingTop = top;
        this.mPaddingRight = right;
        this.mPaddingBottom = bottom;
        mRecycler.setPadding(mPaddingLeft, mPaddingTop, mPaddingRight, mPaddingBottom);
    }

    @Override
    public void setClipToPadding(boolean isClip){
        mRecycler.setClipToPadding(isClip);
    }


    public void scrollToPosition(int position){
        getRecyclerView().scrollToPosition(position);
    }


    protected void initRecyclerView(View view) {
        mRecycler = (RecyclerView) view.findViewById(android.R.id.list);
        setItemAnimator(null);
        if (mRecycler != null) {
            mRecycler.setHasFixedSize(true);
            mRecycler.setClipToPadding(mClipToPadding);
            mInternalOnScrollListener = new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    if (mExternalOnScrollListener != null){
                        mExternalOnScrollListener.onScrolled(recyclerView, dx, dy);
                    }
                    for (RecyclerView.OnScrollListener listener : mExternalOnScrollListenerList) {
                        listener.onScrolled(recyclerView, dx, dy);
                    }
                }
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                    if (mExternalOnScrollListener != null){
                        mExternalOnScrollListener.onScrollStateChanged(recyclerView, newState);
                    }
                    for (RecyclerView.OnScrollListener listener : mExternalOnScrollListenerList) {
                        listener.onScrollStateChanged(recyclerView, newState);
                    }
                }
            };
            mRecycler.addOnScrollListener(mInternalOnScrollListener);
            if (mPadding != -1.0f) {
                mRecycler.setPadding(mPadding, mPadding, mPadding, mPadding);
            } else {
                mRecycler.setPadding(mPaddingLeft, mPaddingTop, mPaddingRight, mPaddingBottom);
            }
            if (mScrollbarStyle != -1) {
                mRecycler.setScrollBarStyle(mScrollbarStyle);
            }
            switch (mScrollbar){
                case 0:
                    setVerticalScrollBarEnabled(false);
                    break;
                case 1:
                    setHorizontalScrollBarEnabled(false);
                    break;
                case 2:
                    setVerticalScrollBarEnabled(false);
                    setHorizontalScrollBarEnabled(false);
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void setVerticalScrollBarEnabled(boolean verticalScrollBarEnabled) {
        mRecycler.setVerticalScrollBarEnabled(verticalScrollBarEnabled);
    }

    @Override
    public void setHorizontalScrollBarEnabled(boolean horizontalScrollBarEnabled) {
        mRecycler.setHorizontalScrollBarEnabled(horizontalScrollBarEnabled);
    }

    /**
     * 设置RecyclerView的LayoutManager
     * @param manager           LayoutManager
     */
    public void setLayoutManager(RecyclerView.LayoutManager manager) {
        mRecycler.setLayoutManager(manager);
    }

    /**
     * 设置加载数据为空的状态
     * @param emptyView         自定义布局
     */
    public void setEmptyView(View emptyView){
        mEmptyView.removeAllViews();
        mEmptyView.addView(emptyView);
    }

    /**
     * 设置加载数据为空的状态
     * @param emptyView         自定义布局
     */
    public void setEmptyView(int emptyView){
        mEmptyView.removeAllViews();
        LayoutInflater.from(getContext()).inflate(emptyView, mEmptyView);
    }


    /**
     * 设置加载数据中
     * @param progressView      自定义布局
     */
    public void setProgressView(View progressView){
        mProgressView.removeAllViews();
        mProgressView.addView(progressView);
    }


    /**
     * 设置加载数据中
     * @param progressView      自定义布局
     */
    public void setProgressView(int progressView){
        mProgressView.removeAllViews();
        LayoutInflater.from(getContext()).inflate(progressView, mProgressView);
    }


    /**
     * 设置加载错误的状态
     * @param errorView         自定义布局
     */
    public void setErrorView(View errorView){
        mErrorView.removeAllViews();
        mErrorView.addView(errorView);
    }


    /**
     * 设置加载错误的状态
     * @param errorView         自定义布局
     */
    public void setErrorView(int errorView){
        mErrorView.removeAllViews();
        LayoutInflater.from(getContext()).inflate(errorView, mErrorView);
    }


    /**
     * 设置适配器，关闭所有副view。展示recyclerView
     * 适配器有更新，自动关闭所有副view。根据条数判断是否展示EmptyView
     * @param adapter               Adapter适配器
     */
    public void setAdapter(RecyclerView.Adapter adapter) {
        mRecycler.setAdapter(adapter);
        adapter.registerAdapterDataObserver(new EasyDataObserver(this));
        showRecycler();
    }


    /**
     * 设置适配器，关闭所有副view。展示进度条View
     * 适配器有更新，自动关闭所有副view。根据条数判断是否展示EmptyView
     * @param adapter               Adapter适配器
     */
    public void setAdapterWithProgress(RecyclerView.Adapter adapter) {
        mRecycler.setAdapter(adapter);
        adapter.registerAdapterDataObserver(new EasyDataObserver(this));
        //只有Adapter为空时才显示ProgressView
        if (adapter instanceof RecyclerArrayAdapter){
            if (((RecyclerArrayAdapter) adapter).getCount() == 0){
                showProgress();
            }else {
                showRecycler();
            }
        }else {
            if (adapter.getItemCount() == 0){
                showProgress();
            }else {
                showRecycler();
            }
        }
    }


    /**
     * 从recycler清除adapter
     */
    public void clear() {
        mRecycler.setAdapter(null);
    }


    /**
     * 展示有数据时的布局，隐藏其他布局
     */
    private void hideAll(){
        mEmptyView.setVisibility(View.GONE);
        mProgressView.setVisibility(View.GONE);
        mErrorView.setVisibility(GONE);
        mPtrLayout.setRefreshing(false);
        mRecycler.setVisibility(View.INVISIBLE);
    }


    /**
     * 设置加载错误状态
     */
    public void showError() {
        log("showError");
        if (mErrorView.getChildCount()>0){
            hideAll();
            mErrorView.setVisibility(View.VISIBLE);
        }else {
            showRecycler();
        }

    }

    /**
     * 设置加载数据为空状态
     */
    public void showEmpty() {
        log("showEmpty");
        if (mEmptyView.getChildCount()>0){
            hideAll();
            mEmptyView.setVisibility(View.VISIBLE);
        }else {
            showRecycler();
        }
    }


    /**
     * 设置加载数据中状态
     */
    public void showProgress() {
        log("showProgress");
        if (mProgressView.getChildCount()>0){
            hideAll();
            mProgressView.setVisibility(View.VISIBLE);
        }else {
            showRecycler();
        }
    }


    /**
     * 设置加载数据完毕状态
     */
    public void showRecycler() {
        log("showRecycler");
        hideAll();
        mRecycler.setVisibility(View.VISIBLE);
    }


    /**
     * 设置下拉刷新监听
     * @param listener              OnRefreshListener监听
     */
    public void setRefreshListener(SwipeRefreshLayout.OnRefreshListener listener) {
        mPtrLayout.setEnabled(true);
        mPtrLayout.setOnRefreshListener(listener);
        this.mRefreshListener = listener;
    }

    /**
     * 设置是否刷新
     * @param isRefreshing          是否刷新
     */
    public void setRefreshing(final boolean isRefreshing){
        mPtrLayout.post(new Runnable() {
            @Override
            public void run() {
                mPtrLayout.setRefreshing(isRefreshing);
            }
        });
    }

    /**
     * 设置是否刷新
     * @param isRefreshing          是否刷新
     * @param isCallbackListener    是否回调监听
     */
    public void setRefreshing(final boolean isRefreshing, final boolean isCallbackListener){
        mPtrLayout.post(new Runnable() {
            @Override
            public void run() {
                mPtrLayout.setRefreshing(isRefreshing);
                if (isRefreshing&&isCallbackListener&&mRefreshListener!=null){
                    mRefreshListener.onRefresh();
                }
            }
        });
    }

    /**
     * 设置刷新颜色
     * @param colRes                int类型颜色值
     */
    public void setRefreshingColorResources(@ColorRes int... colRes) {
        mPtrLayout.setColorSchemeResources(colRes);
    }

    /**
     * 设置刷新颜色
     * @param col                   int类型颜色值
     */
    public void setRefreshingColor(int... col) {
        mPtrLayout.setColorSchemeColors(col);
    }

    /**
     * 设置滚动监听
     * @param listener              OnScrollListener监听器
     */
    @Deprecated
    public void setOnScrollListener(RecyclerView.OnScrollListener listener) {
        mExternalOnScrollListener = listener;
    }


    /**
     * 添加滚动监听器
     * @param listener              OnScrollListener监听器
     */
    public void addOnScrollListener(RecyclerView.OnScrollListener listener) {
        mExternalOnScrollListenerList.add(listener);
    }


    /**
     * 移除滚动监听器
     * @param listener              OnScrollListener监听器
     */
    public void removeOnScrollListener(RecyclerView.OnScrollListener listener) {
        mExternalOnScrollListenerList.remove(listener);
    }


    /**
     * 移除所有的滚动监听
     */
    public void removeAllOnScrollListeners() {
        mExternalOnScrollListenerList.clear();
    }


    public void addOnItemTouchListener(RecyclerView.OnItemTouchListener listener) {
        mRecycler.addOnItemTouchListener(listener);
    }


    public void removeOnItemTouchListener(RecyclerView.OnItemTouchListener listener) {
        mRecycler.removeOnItemTouchListener(listener);
    }


    public RecyclerView.Adapter getAdapter() {
        return mRecycler.getAdapter();
    }


    public void setOnTouchListener(OnTouchListener listener) {
        mRecycler.setOnTouchListener(listener);
    }


    public void setItemAnimator(RecyclerView.ItemAnimator animator) {
        mRecycler.setItemAnimator(animator);
    }


    public void addItemDecoration(RecyclerView.ItemDecoration itemDecoration) {
        mRecycler.addItemDecoration(itemDecoration);
    }


    public void addItemDecoration(RecyclerView.ItemDecoration itemDecoration, int index) {
        mRecycler.addItemDecoration(itemDecoration, index);
    }


    public void removeItemDecoration(RecyclerView.ItemDecoration itemDecoration) {
        mRecycler.removeItemDecoration(itemDecoration);
    }


    public View getErrorView() {
        if (mErrorView.getChildCount()>0)return mErrorView.getChildAt(0);
        return null;
    }


    public View getProgressView() {
        if (mProgressView.getChildCount()>0)return mProgressView.getChildAt(0);
        return null;
    }


    public View getEmptyView() {
        if (mEmptyView.getChildCount()>0)return mEmptyView.getChildAt(0);
        return null;
    }

    private static void log(String content){
        if (DEBUG){
            Log.i(TAG,content);
        }
    }


}

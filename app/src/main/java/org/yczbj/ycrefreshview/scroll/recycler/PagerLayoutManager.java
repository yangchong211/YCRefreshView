package org.yczbj.ycrefreshview.scroll.recycler;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;

import org.yczbj.ycrefreshview.scroll.inter.OnPagerListener;


/**
 * <pre>
 *     @author yangchong
 *     blog  : https://github.com/yangchong211
 *     time  : 2019/7/23
 *     desc  : 自定义LinearLayoutManager
 *     revise:
 * </pre>
 */
public class PagerLayoutManager extends LinearLayoutManager {

    private RecyclerView mRecyclerView;
    private ScrollPageHelper mPagerSnapHelper;
    private OnPagerListener mOnViewPagerListener;
    private static final int HORIZONTAL = OrientationHelper.HORIZONTAL;
    private static final int VERTICAL = OrientationHelper.VERTICAL;
    private int mOrientation;
    /**
     * 位移，用来判断移动方向
     */
    private int mDrift;

    public PagerLayoutManager(Context context, int orientation) {
        super(context, orientation, false);
        this.mOrientation = orientation;
        init();
    }

    public PagerLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
        this.mOrientation = orientation;
        init();
    }

    /**
     * 初始化操作
     */
    private void init() {
        switch (mOrientation){
            case HORIZONTAL:
                mPagerSnapHelper = new ScrollPageHelper(Gravity.START,false);
                break;
            case VERTICAL:
                mPagerSnapHelper = new ScrollPageHelper(Gravity.TOP,false);
                break;
            default:
                mPagerSnapHelper = new ScrollPageHelper(Gravity.TOP,false);
                break;
        }
    }

    /**
     * attach到window窗口时，该方法必须调用
     * @param recyclerView                          recyclerView
     */
    @Override
    public void onAttachedToWindow(RecyclerView recyclerView) {
        if (recyclerView == null) {
            throw new IllegalArgumentException("The attach RecycleView must not null!!");
        }
        super.onAttachedToWindow(recyclerView);
        this.mRecyclerView = recyclerView;
        if (mPagerSnapHelper==null){
            init();
        }
        mPagerSnapHelper.attachToRecyclerView(mRecyclerView);
        mRecyclerView.addOnChildAttachStateChangeListener(mChildAttachStateChangeListener);
    }

    /**
     * 销毁的时候调用该方法，需要移除监听事件
     * @param view                                  view
     * @param recycler                              recycler
     */
    @Override
    public void onDetachedFromWindow(RecyclerView view, RecyclerView.Recycler recycler) {
        super.onDetachedFromWindow(view, recycler);
        if (mRecyclerView!=null){
            mRecyclerView.removeOnChildAttachStateChangeListener(mChildAttachStateChangeListener);
        }
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        super.onLayoutChildren(recycler, state);
    }

    /**
     * 滑动状态的改变
     * 缓慢拖拽-> SCROLL_STATE_DRAGGING
     * 快速滚动-> SCROLL_STATE_SETTLING
     * 空闲状态-> SCROLL_STATE_IDLE
     * @param state                         状态
     */
    @Override
    public void onScrollStateChanged(int state) {
        switch (state) {
            case RecyclerView.SCROLL_STATE_IDLE:
                View viewIdle = mPagerSnapHelper.findSnapView(this);
                int positionIdle = 0;
                if (viewIdle != null) {
                    positionIdle = getPosition(viewIdle);
                }
                int childCount = getChildCount();
                if (mOnViewPagerListener != null && childCount == 1) {
                    mOnViewPagerListener.onPageSelected(positionIdle,
                            positionIdle == childCount - 1);
                }
                break;
            case RecyclerView.SCROLL_STATE_DRAGGING:
                View viewDrag = mPagerSnapHelper.findSnapView(this);
                if (viewDrag != null) {
                    int positionDrag = getPosition(viewDrag);
                }
                break;
            case RecyclerView.SCROLL_STATE_SETTLING:
                View viewSettling = mPagerSnapHelper.findSnapView(this);
                if (viewSettling != null) {
                    int positionSettling = getPosition(viewSettling);
                }
                break;
            default:
                break;
        }
    }


    /**
     * 监听竖直方向的相对偏移量
     * @param dy                                y轴滚动值
     * @param recycler                          recycler
     * @param state                             state滚动状态
     * @return                                  int值
     */
    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler,
                                  RecyclerView.State state) {
        if (getChildCount() == 0 || dy == 0) {
            return 0;
        }
        this.mDrift = dy;
        return super.scrollVerticallyBy(dy, recycler, state);
    }

    /**
     * 监听水平方向的相对偏移量
     * @param dx                                x轴滚动值
     * @param recycler                          recycler
     * @param state                             state滚动状态
     * @return                                  int值
     */
    @Override
    public int scrollHorizontallyBy(int dx, RecyclerView.Recycler recycler,
                                    RecyclerView.State state) {
        if (getChildCount() == 0 || dx == 0) {
            return 0;
        }
        this.mDrift = dx;
        return super.scrollHorizontallyBy(dx, recycler, state);
    }

    /**
     * 设置监听
     * @param listener                      listener
     */
    public void setOnViewPagerListener(OnPagerListener listener){
        this.mOnViewPagerListener = listener;
    }

    private RecyclerView.OnChildAttachStateChangeListener mChildAttachStateChangeListener =
            new RecyclerView.OnChildAttachStateChangeListener() {
        /**
         * 第一次进入界面的监听，可以做初始化方面的操作
         * @param view                      view
         */
        @Override
        public void onChildViewAttachedToWindow(@NonNull View view) {
            if (mOnViewPagerListener != null && getChildCount() == 1) {
                mOnViewPagerListener.onInitComplete();
            }
        }

        /**
         * 页面销毁的时候调用该方法，可以做销毁方面的操作
         * @param view                      view
         */
        @Override
        public void onChildViewDetachedFromWindow(@NonNull View view) {
            if (mDrift >= 0){
                if (mOnViewPagerListener != null) {
                    mOnViewPagerListener.onPageRelease(true , getPosition(view));
                }
            }else {
                if (mOnViewPagerListener != null) {
                    mOnViewPagerListener.onPageRelease(false , getPosition(view));
                }
            }
        }
    };
}

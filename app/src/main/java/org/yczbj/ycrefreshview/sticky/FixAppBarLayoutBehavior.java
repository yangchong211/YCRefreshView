package org.yczbj.ycrefreshview.sticky;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.OverScroller;

import java.lang.reflect.Field;

public class FixAppBarLayoutBehavior extends AppBarLayout.Behavior {

    private OverScroller mScroller;
    private Context context;


    public FixAppBarLayoutBehavior() {
        super();
    }

    public FixAppBarLayoutBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        bindScrollerValue(context);
    }


    /**
     * 反射注入Scroller以获取其引用
     *
     * @param context
     */
    private void bindScrollerValue(Context context) {
        if (mScroller != null) {
            return;
        }
        mScroller = new OverScroller(context);
        try {
            Class<?> superclass = getClass().getSuperclass();
            Class<?> clzHeaderBehavior = null;
            if (superclass != null) {
                clzHeaderBehavior = superclass.getSuperclass();
            }
            Field fieldScroller = null;
            if (clzHeaderBehavior != null) {
                fieldScroller = clzHeaderBehavior.getDeclaredField("mScroller");
            }
            if (fieldScroller != null) {
                fieldScroller.setAccessible(true);
                fieldScroller.set(this, mScroller);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, AppBarLayout child, View target, int dx, int dy, int[] consumed, int type) {
        if (type == ViewCompat.TYPE_NON_TOUCH) {
            //fling上滑appbar然后迅速fling下滑list时, HeaderBehavior的mScroller并未停止, 会导致上下来回晃动
            if (mScroller == null) {
                bindScrollerValue(context);
            }
            if (mScroller.computeScrollOffset()) {
                mScroller.abortAnimation();
            }
            //当target滚动到边界时主动停止target fling,与下一次滑动产生冲突
            if (getTopAndBottomOffset() == 0) {
                ViewCompat.stopNestedScroll(target, type);
            }
        }
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type);
    }


    @Override
    public boolean onInterceptTouchEvent(CoordinatorLayout parent, AppBarLayout child, MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            if (mScroller == null) {
                bindScrollerValue(context);
            }
            //fling上滑appbar然后迅速fling下滑list时, HeaderBehavior的mScroller并未停止, 会导致上下来回晃动
            if (mScroller.computeScrollOffset()) {
                mScroller.abortAnimation();
            }
        }
        return super.onInterceptTouchEvent(parent, child, ev);
    }


}

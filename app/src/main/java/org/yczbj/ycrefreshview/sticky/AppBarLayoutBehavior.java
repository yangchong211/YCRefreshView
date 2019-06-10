package org.yczbj.ycrefreshview.sticky;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.OverScroller;

import java.lang.reflect.Field;


/**
 * <pre>
 *     @author yangchong
 *     blog  : https://github.com/yangchong211
 *     time  : 2019/03/13
 *     desc  : 自定义Behavior
 *     revise: 解决appbarLayout若干问题
 *              1）快速滑动appbarLayout会出现回弹
 *              2）快速滑动appbarLayout到折叠状态下，立马下滑，会出现抖动的问题
 *              3）滑动appbarLayout，无法通过手指按下让其停止滑动
 * </pre>
 */
public class AppBarLayoutBehavior extends AppBarLayout.Behavior {

    private static final String TAG = "AppbarLayoutBehavior";
    private static final int TYPE_FLING = 1;
    /**
     * 记录是否有fling
     */
    private boolean isFlinging;
    /**
     * 记录是否
     */
    private boolean shouldBlockNestedScroll;

    public AppBarLayoutBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 是否拦截触摸事件
     * @param parent                        CoordinatorLayout
     * @param child                         AppBarLayout
     * @param ev                            ev
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(CoordinatorLayout parent, AppBarLayout child, MotionEvent ev) {
        LogUtil.d(TAG, "onInterceptTouchEvent:" + child.getTotalScrollRange());
        shouldBlockNestedScroll = isFlinging;
        switch (ev.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                //手指触摸屏幕的时候停止fling事件
                stopAppbarLayoutFling(child);
                break;
            default:
                break;
        }
        return super.onInterceptTouchEvent(parent, child, ev);
    }

    /**
     * 反射获取私有的flingRunnable 属性，考虑support 28以后变量名修改的问题
     * @return Field
     * @throws NoSuchFieldException
     */
    private Field getFlingRunnableField() throws NoSuchFieldException {
        Class<?> superclass = this.getClass().getSuperclass();
        try {
            // support design 27及一下版本
            Class<?> headerBehaviorType = null;
            if (superclass != null) {
                String name = superclass.getName();
                LogUtil.d("AppBarLayout.Behavior父类",name);
                headerBehaviorType = superclass.getSuperclass();
            }
            if (headerBehaviorType != null) {
                String name = headerBehaviorType.getName();
                LogUtil.d("AppBarLayout.Behavior父类的父类1",name);
                return headerBehaviorType.getDeclaredField("mFlingRunnable");
            }else {
                return null;
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            // 可能是28及以上版本
            Class<?> headerBehaviorType = superclass.getSuperclass().getSuperclass();
            if (headerBehaviorType != null) {
                String name = headerBehaviorType.getName();
                LogUtil.d("AppBarLayout.Behavior父类的父类2",name);
                return headerBehaviorType.getDeclaredField("flingRunnable");
            } else {
                return null;
            }
        }
    }

    /**
     * 反射获取私有的scroller 属性，考虑support 28以后变量名修改的问题
     * @return Field
     * @throws NoSuchFieldException
     */
    private Field getScrollerField() throws NoSuchFieldException {
        Class<?> superclass = this.getClass().getSuperclass();
        try {
            // support design 27及一下版本
            Class<?> headerBehaviorType = null;
            if (superclass != null) {
                headerBehaviorType = superclass.getSuperclass();
            }
            if (headerBehaviorType != null) {
                return headerBehaviorType.getDeclaredField("mScroller");
            }else {
                return null;
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            // 可能是28及以上版本
            Class<?> headerBehaviorType = superclass.getSuperclass().getSuperclass();
            if (headerBehaviorType != null) {
                return headerBehaviorType.getDeclaredField("scroller");
            }else {
                return null;
            }
        }
    }

    /**
     * 停止appbarLayout的fling事件
     * @param appBarLayout
     */
    private void stopAppbarLayoutFling(AppBarLayout appBarLayout) {
        //通过反射拿到HeaderBehavior中的flingRunnable变量
        try {
            Field flingRunnableField = getFlingRunnableField();
            Field scrollerField = getScrollerField();
            if (flingRunnableField != null) {
                flingRunnableField.setAccessible(true);
            }
            if (scrollerField != null) {
                scrollerField.setAccessible(true);
            }
            Runnable flingRunnable = null;
            if (flingRunnableField != null) {
                flingRunnable = (Runnable) flingRunnableField.get(this);
            }
            OverScroller overScroller = null;
            if (scrollerField != null) {
                overScroller = (OverScroller) scrollerField.get(this);
            }
            //下面是关键点
            if (flingRunnable != null) {
                LogUtil.d(TAG, "存在flingRunnable");
                appBarLayout.removeCallbacks(flingRunnable);
                flingRunnableField.set(this, null);
            }
            if (overScroller != null && !overScroller.isFinished()) {
                overScroller.abortAnimation();
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 嵌套滑动开始（ACTION_DOWN），确定Behavior是否要监听此次事件
     */
    @Override
    public boolean onStartNestedScroll(CoordinatorLayout parent, AppBarLayout child,
                                       View directTargetChild, View target,
                                       int nestedScrollAxes, int type) {
        LogUtil.d(TAG, "onStartNestedScroll");
        stopAppbarLayoutFling(child);
        return super.onStartNestedScroll(parent, child, directTargetChild, target,
                nestedScrollAxes, type);
    }

    /**
     * 嵌套滑动进行中，要监听的子 View将要滑动，滑动事件即将被消费（但最终被谁消费，可以通过代码控制）
     */
    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout,
                                  AppBarLayout child, View target,
                                  int dx, int dy, int[] consumed, int type) {
        LogUtil.d(TAG, "onNestedPreScroll:" + child.getTotalScrollRange()
                + " ,dx:" + dx + " ,dy:" + dy + " ,type:" + type);
        //type返回1时，表示当前target处于非touch的滑动，
        //该bug的引起是因为appbar在滑动时，CoordinatorLayout内的实现NestedScrollingChild2接口的滑动
        //子类还未结束其自身的fling
        //所以这里监听子类的非touch时的滑动，然后block掉滑动事件传递给AppBarLayout
        if (type == TYPE_FLING) {
            isFlinging = true;
        }
        if (!shouldBlockNestedScroll) {
            super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type);
        }
    }

    /**
     * 嵌套滑动进行中，要监听的子 View的滑动事件已经被消费
     */
    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, AppBarLayout child,
                               View target, int dxConsumed, int dyConsumed, int
            dxUnconsumed, int dyUnconsumed, int type) {
        LogUtil.d(TAG, "onNestedScroll: target:" + target.getClass() + " ,"
                + child.getTotalScrollRange() + " ,dxConsumed:"
                + dxConsumed + " ,dyConsumed:" + dyConsumed + " " + ",type:" + type);
        if (!shouldBlockNestedScroll) {
            super.onNestedScroll(coordinatorLayout, child, target, dxConsumed,
                    dyConsumed, dxUnconsumed, dyUnconsumed, type);
        }
    }

    /**
     * 嵌套滑动结束（ACTION_UP或ACTION_CANCEL）
     */
    @Override
    public void onStopNestedScroll(CoordinatorLayout coordinatorLayout, AppBarLayout abl,
                                   View target, int type) {
        LogUtil.d(TAG, "onStopNestedScroll");
        super.onStopNestedScroll(coordinatorLayout, abl, target, type);
        isFlinging = false;
        shouldBlockNestedScroll = false;
    }

    private static class LogUtil{
        static void d(String tag, String string){
            Log.d(tag,string);
        }
    }

}
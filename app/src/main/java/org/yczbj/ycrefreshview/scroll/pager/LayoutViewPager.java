package org.yczbj.ycrefreshview.scroll.pager;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import androidx.viewpager.widget.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.Scroller;

import org.yczbj.ycrefreshview.scroll.inter.OnPagerListener;

import java.lang.reflect.Field;

/**
 * <pre>
 *     @author 杨充
 *     blog  : https://github.com/yangchong211
 *     time  : 2019/6/20
 *     desc  : 自定义ViewPager，主要是处理边界极端情况
 *     revise:
 * </pre>
 */
public class LayoutViewPager extends ViewPager implements ViewPager.OnPageChangeListener {

    private boolean scrollable = true;
    private long mRecentTouchTime;
    private OnPagerListener mOnViewPagerListener;
    /**
     * 位移，用来判断移动方向
     */
    private int mDrift;
    /**
     * 索引
     */
    private int position;

    public LayoutViewPager(Context context) {
        super(context);
    }

    public LayoutViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if(this.scrollable) {
            if (getCurrentItem() == 0 && getChildCount() == 0) {
                return false;
            }
            return super.onTouchEvent(ev);
        } else {
            return false;
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        mRecentTouchTime = System.currentTimeMillis();
        if(this.scrollable) {
            if (getCurrentItem() == 0 && getChildCount() == 0) {
                return false;
            }
            return super.onInterceptTouchEvent(ev);
        } else {
            return false;
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.addOnAttachStateChangeListener(listener);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.removeOnAttachStateChangeListener(listener);
    }

    /**
     * 这是为了响应该视图中的内部滚动(即,视图滚动了它自己的内容)。这通常是由于
     * {@link #scrollBy(int, int)}或{@link #scrollTo(int, int)}已经存在调用。
     * @param l                         横向位移
     * @param t                         竖直方向滑动位移
     * @param oldl                      之前横向位移
     * @param oldt                      之前竖直方向滑动位移
     */
    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        mDrift = t - oldt;
    }

    /**
     * 设置监听
     * @param listener                      listener
     */
    public void setOnViewPagerListener(OnPagerListener listener){
        this.mOnViewPagerListener = listener;
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {
        super.onPageScrolled(i, v, i1);
    }

    @Override
    public void onPageSelected(int i) {
        this.position = i;
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    /**
     * 设置viewPager滑动动画持续时间
     * API>19
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void setAnimationDuration(final int during){
        try {
            // viewPager平移动画事件
            Field mField = ViewPager.class.getDeclaredField("mScroller");
            mField.setAccessible(true);
            // 动画效果与ViewPager的一致
            Interpolator interpolator = new Interpolator() {
                @Override
                public float getInterpolation(float t) {
                    t -= 1.0f;
                    return t * t * t * t * t + 1.0f;
                }
            };
            Scroller mScroller = new Scroller(getContext(),interpolator){
                final int time = 2000;
                @Override
                public void startScroll(int x, int y, int dx, int dy, int duration) {
                    // 如果手工滚动,则加速滚动
                    if (System.currentTimeMillis() - mRecentTouchTime > time) {
                        duration = during;
                    } else {
                        duration /= 2;
                    }
                    super.startScroll(x, y, dx, dy, duration);
                }

                @Override
                public void startScroll(int x, int y, int dx, int dy) {
                    super.startScroll(x, y, dx, dy,during);
                }
            };
            mField.set(this, mScroller);
        } catch (NoSuchFieldException | IllegalAccessException | IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    /**
     * attach状态变化的监听listener
     */
    private OnAttachStateChangeListener listener = new OnAttachStateChangeListener() {
        @Override
        public void onViewAttachedToWindow(View v) {
            if (mOnViewPagerListener != null && getChildCount() == 1) {
                mOnViewPagerListener.onInitComplete();
            }
        }

        @Override
        public void onViewDetachedFromWindow(View v) {
            if (mDrift >= 0){
                if (mOnViewPagerListener != null) {
                    mOnViewPagerListener.onPageRelease(true , position);
                }
            }else {
                if (mOnViewPagerListener != null) {
                    mOnViewPagerListener.onPageRelease(false , position);
                }
            }
        }
    };

    /**
     * 设置是否可以滚动
     * @param scrollable                    布尔值
     */
    public void setScrollable(boolean scrollable) {
        this.scrollable = scrollable;
    }


}

package org.yczbj.ycrefreshview.scroll.recycler;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;

import java.util.Locale;

/**
 * <pre>
 *     @author yangchong
 *     blog  : https://github.com/yangchong211
 *     time  : 2018/3/18
 *     desc  : 自定义SnapHelper，设置左对齐，滑动时候会限制item只滑动一个。可以设置gravity位置对齐
 *     revise: 关于SnapHelper源码分析可以看我博客：https://blog.csdn.net/m0_37700275/article/details/83901677
 * </pre>
 */
public class ScrollPageHelper extends PagerSnapHelper {

    private OrientationHelper mHorizontalHelper, mVerticalHelper;
    private int gravity;
    private boolean snapLastItem;
    private boolean isRtlHorizontal;

    /**
     * 构造方法
     * @param gravity                           位置
     * @param enableSnapLast                    最后一个是否按照位置对齐
     */
    public ScrollPageHelper(int gravity, boolean enableSnapLast){
        this.snapLastItem = enableSnapLast;
        this.gravity = gravity;
    }

    @Override
    public void attachToRecyclerView(@Nullable RecyclerView recyclerView)
            throws IllegalStateException {
        if (recyclerView != null && !(recyclerView.getLayoutManager()
                instanceof LinearLayoutManager)) {
            throw new IllegalStateException("ScrollPageHelper needs a " +
                    "RecyclerView with a LinearLayoutManager");
        }
        if (recyclerView != null) {
            //设置fling监听为null
            recyclerView.setOnFlingListener(null);
            if ((gravity == Gravity.START || gravity == Gravity.END)) {
                isRtlHorizontal = isRtl();
            }
        }
        super.attachToRecyclerView(recyclerView);
    }


    /**
     *
     * @param layoutManager                         layoutManager
     * @param targetView                            目标view
     * @return
     */
    @Nullable
    @Override
    public int[] calculateDistanceToFinalSnap(@NonNull RecyclerView.LayoutManager layoutManager,
                                              @NonNull View targetView) {
        //创建数组
        int[] out = new int[2];
        //判断是否是横向方法
        if (layoutManager.canScrollHorizontally()) {
            if (gravity == Gravity.START) {
                out[0] = distanceToStart(targetView, getHorizontalHelper(layoutManager), false);
            } else { // END
                out[0] = distanceToEnd(targetView, getHorizontalHelper(layoutManager), false);
            }
        } else {
            out[0] = 0;
        }
        //判断是否是竖直方法
        if (layoutManager.canScrollVertically()) {
            if (gravity == Gravity.TOP) {
                out[1] = distanceToStart(targetView, getVerticalHelper(layoutManager), false);
            } else { // BOTTOM
                out[1] = distanceToEnd(targetView, getVerticalHelper(layoutManager), false);
            }
        } else {
            out[1] = 0;
        }
        return out;
    }

    /**
     * 找到当前时刻的SnapView
     * @param layoutManager                         layoutManager
     * @return
     */
    @Nullable
    @Override
    public View findSnapView(RecyclerView.LayoutManager layoutManager) {
        View snapView = null;
        if (layoutManager instanceof LinearLayoutManager) {
            switch (gravity) {
                case Gravity.START:
                    snapView = findStartView(layoutManager, getHorizontalHelper(layoutManager));
                    break;
                case Gravity.END:
                    snapView = findEndView(layoutManager, getHorizontalHelper(layoutManager));
                    break;
                case Gravity.TOP:
                    snapView = findStartView(layoutManager, getVerticalHelper(layoutManager));
                    break;
                case Gravity.BOTTOM:
                    snapView = findEndView(layoutManager, getVerticalHelper(layoutManager));
                    break;
                default:
                    break;
            }
        }
        return snapView;
    }


    private boolean isRtl() {
        return TextUtils.getLayoutDirectionFromLocale(
                Locale.getDefault()) == View.LAYOUT_DIRECTION_RTL;
    }


    @NonNull
    private OrientationHelper getVerticalHelper(@NonNull RecyclerView.LayoutManager layoutManager) {
        if (mVerticalHelper == null) {
            mVerticalHelper = OrientationHelper.createVerticalHelper(layoutManager);
        }
        return mVerticalHelper;
    }

    @NonNull
    private OrientationHelper getHorizontalHelper(
            @NonNull RecyclerView.LayoutManager layoutManager) {
        if (mHorizontalHelper == null) {
            mHorizontalHelper = OrientationHelper.createHorizontalHelper(layoutManager);
        }
        return mHorizontalHelper;
    }


    private int distanceToStart(View targetView, @NonNull OrientationHelper helper, boolean fromEnd) {
        if (isRtlHorizontal && !fromEnd) {
            return distanceToEnd(targetView, helper, true);
        }

        return helper.getDecoratedStart(targetView) - helper.getStartAfterPadding();
    }

    private int distanceToEnd(View targetView, @NonNull OrientationHelper helper, boolean fromStart) {
        if (isRtlHorizontal && !fromStart) {
            return distanceToStart(targetView, helper, true);
        }

        return helper.getDecoratedEnd(targetView) - helper.getEndAfterPadding();
    }


    @Nullable
    private View findStartView(RecyclerView.LayoutManager layoutManager,
                               @NonNull OrientationHelper helper) {

        if (layoutManager instanceof LinearLayoutManager) {
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
            boolean reverseLayout = linearLayoutManager.getReverseLayout();
            int firstChild = reverseLayout ? linearLayoutManager.findLastVisibleItemPosition()
                    : linearLayoutManager.findFirstVisibleItemPosition();
            int offset = 1;

            if (layoutManager instanceof GridLayoutManager) {
                offset += ((GridLayoutManager) layoutManager).getSpanCount() - 1;
            }

            if (firstChild == RecyclerView.NO_POSITION) {
                return null;
            }

            View child = layoutManager.findViewByPosition(firstChild);

            float visibleWidth;
            if (isRtlHorizontal) {
                visibleWidth = (float) (helper.getTotalSpace() - helper.getDecoratedStart(child))
                        / helper.getDecoratedMeasurement(child);
            } else {
                visibleWidth = (float) helper.getDecoratedEnd(child)
                        / helper.getDecoratedMeasurement(child);
            }
            boolean endOfList;
            if (!reverseLayout) {
                endOfList = ((LinearLayoutManager) layoutManager)
                        .findLastCompletelyVisibleItemPosition()
                        == layoutManager.getItemCount() - 1;
            } else {
                endOfList = ((LinearLayoutManager) layoutManager)
                        .findFirstCompletelyVisibleItemPosition()
                        == 0;
            }

            if (visibleWidth > 0.5f && !endOfList) {
                return child;
            } else if (snapLastItem && endOfList) {
                return child;
            } else if (endOfList) {
                return null;
            } else {
                return reverseLayout ? layoutManager.findViewByPosition(firstChild - offset)
                        : layoutManager.findViewByPosition(firstChild + offset);
            }
        }

        return null;
    }

    @Nullable
    private View findEndView(RecyclerView.LayoutManager layoutManager,
                             @NonNull OrientationHelper helper) {

        if (layoutManager instanceof LinearLayoutManager) {
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
            boolean reverseLayout = linearLayoutManager.getReverseLayout();
            int lastChild = reverseLayout ? linearLayoutManager.findFirstVisibleItemPosition()
                    : linearLayoutManager.findLastVisibleItemPosition();
            int offset = 1;

            if (layoutManager instanceof GridLayoutManager) {
                offset += ((GridLayoutManager) layoutManager).getSpanCount() - 1;
            }

            if (lastChild == RecyclerView.NO_POSITION) {
                return null;
            }

            View child = layoutManager.findViewByPosition(lastChild);

            float visibleWidth;

            if (isRtlHorizontal) {
                visibleWidth = (float) helper.getDecoratedEnd(child)
                        / helper.getDecoratedMeasurement(child);
            } else {
                visibleWidth = (float) (helper.getTotalSpace() - helper.getDecoratedStart(child))
                        / helper.getDecoratedMeasurement(child);
            }

            boolean startOfList;
            if (!reverseLayout) {
                startOfList = ((LinearLayoutManager) layoutManager)
                        .findFirstCompletelyVisibleItemPosition() == 0;
            } else {
                startOfList = ((LinearLayoutManager) layoutManager)
                        .findLastCompletelyVisibleItemPosition()
                        == layoutManager.getItemCount() - 1;
            }

            if (visibleWidth > 0.5f && !startOfList) {
                return child;
            } else if (snapLastItem && startOfList) {
                return child;
            } else if (startOfList) {
                return null;
            } else {
                return reverseLayout ? layoutManager.findViewByPosition(lastChild + offset)
                        : layoutManager.findViewByPosition(lastChild - offset);
            }
        }
        return null;
    }


}

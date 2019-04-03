package org.yczbj.ycrefreshviewlib.inter;

/**
 * <pre>
 *     @author yangchong
 *     blog  : https://github.com/yangchong211
 *     time  : 2017/3/13
 *     desc  : 上拉加载更多监听
 *     revise:
 * </pre>
 */
public interface OnMoreListener {

    /**
     * 上拉加载更多操作
     */
    void onMoreShow();
    /**
     * 上拉加载更多操作，手动触发
     */
    void onMoreClick();

}

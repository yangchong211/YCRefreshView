package org.yczbj.ycrefreshviewlib.inter;


/**
 * <pre>
 *     @author yangchong
 *     blog  : https://github.com/yangchong211
 *     time  : 2017/3/13
 *     desc  : 上拉加载没有更多数据监听
 *     revise:
 * </pre>
 */
public interface OnNoMoreListener {

    /**
     * 上拉加载，没有更多数据展示，这个方法可以暂停或者停止加载数据
     */
    void onNoMoreShow();

    /**
     * 这个方法是点击没有更多数据展示布局的操作，比如可以做吐司等等
     */
    void onNoMoreClick();

}

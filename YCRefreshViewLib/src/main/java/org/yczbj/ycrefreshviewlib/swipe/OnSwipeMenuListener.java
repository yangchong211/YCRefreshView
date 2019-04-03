package org.yczbj.ycrefreshviewlib.swipe;

/**
 * <pre>
 *     @author yangchong
 *     blog  : https://github.com/yangchong211
 *     time  : 2017/9/21
 *     desc  : 侧滑删除和置顶监听事件
 *     revise:
 * </pre>
 */
public interface OnSwipeMenuListener {

    /**
     * 点击侧滑删除
     * @param position          索引位置
     */
    void toDelete(int position);

    /**
     * 点击置顶
     * @param position          索引位置
     */
    void toTop(int position);

}

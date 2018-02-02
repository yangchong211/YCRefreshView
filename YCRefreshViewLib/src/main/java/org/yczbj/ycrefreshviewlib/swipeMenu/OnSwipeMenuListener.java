package org.yczbj.ycrefreshviewlib.swipeMenu;


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

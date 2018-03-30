package org.yczbj.ycrefreshviewlib.inter;

import android.view.View;

import org.yczbj.ycrefreshviewlib.adapter.RecyclerArrayAdapter;

/**
 * @author          杨充
 * @version         1.0
 * @date            2016/4/28
 *                  接口
 */
public interface AbsEventDelegate {

    /**
     * 添加数据
     * @param length            长度
     */
    void addData(int length);

    /**
     * 清除数据
     */
    void clear();

    /**
     * 停止加载更多
     */
    void stopLoadMore();

    /**
     * 暂停加载更多
     */
    void pauseLoadMore();

    /**
     * 恢复加载更多
     */
    void resumeLoadMore();

    /**
     * 设置加载更多监听
     * @param view                  view
     * @param listener              listener
     */
    void setMore(View view, RecyclerArrayAdapter.OnMoreListener listener);

    /**
     * 设置没有更多监听
     * @param view                  view
     * @param listener              listener
     */
    void setNoMore(View view, RecyclerArrayAdapter.OnNoMoreListener listener);

    /**
     * 设置加载更多错误监听
     * @param view                  view
     * @param listener              listener
     */
    void setErrorMore(View view, RecyclerArrayAdapter.OnErrorListener listener);

    /**
     * 设置加载更多监听
     * @param res                   res
     * @param listener              listener
     */
    void setMore(int res, RecyclerArrayAdapter.OnMoreListener listener);

    /**
     * 设置没有更多监听
     * @param res                   res
     * @param listener              listener
     */
    void setNoMore(int res, RecyclerArrayAdapter.OnNoMoreListener listener);

    /**
     * 设置加载更多错误监听
     * @param res                   res
     * @param listener              listener
     */
    void setErrorMore(int res, RecyclerArrayAdapter.OnErrorListener listener);

}

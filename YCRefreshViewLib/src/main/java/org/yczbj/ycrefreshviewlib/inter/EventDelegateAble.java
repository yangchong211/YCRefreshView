package org.yczbj.ycrefreshviewlib.inter;

import android.view.View;

import org.yczbj.ycrefreshviewlib.adapter.RecyclerArrayAdapter;

/**
 * @author          杨充
 * @version         1.0
 * @date            2017/4/28
 *                  接口
 */
public interface EventDelegateAble {
    void addData(int length);
    void clear();

    void stopLoadMore();
    void pauseLoadMore();
    void resumeLoadMore();

    void setMore(View view, RecyclerArrayAdapter.OnMoreListener listener);
    void setNoMore(View view, RecyclerArrayAdapter.OnNoMoreListener listener);
    void setErrorMore(View view, RecyclerArrayAdapter.OnErrorListener listener);
    void setMore(int res, RecyclerArrayAdapter.OnMoreListener listener);
    void setNoMore(int res, RecyclerArrayAdapter.OnNoMoreListener listener);
    void setErrorMore(int res, RecyclerArrayAdapter.OnErrorListener listener);
}

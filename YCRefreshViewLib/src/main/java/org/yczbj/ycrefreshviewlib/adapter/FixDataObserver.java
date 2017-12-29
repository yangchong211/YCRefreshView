package org.yczbj.ycrefreshviewlib.adapter;

import android.support.v7.widget.RecyclerView;

/**
 * @author          杨充
 * @version         1.0
 * @date            2017/5/2
 */
public class FixDataObserver extends RecyclerView.AdapterDataObserver {

    private RecyclerView recyclerView;
    FixDataObserver(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }


    @Override
    public void onItemRangeInserted(int positionStart, int itemCount) {
        if (recyclerView.getAdapter() instanceof RecyclerArrayAdapter) {
            RecyclerArrayAdapter adapter = (RecyclerArrayAdapter) recyclerView.getAdapter();
            if (adapter.getFooterCount() > 0 && adapter.getCount() == itemCount) {
                recyclerView.scrollToPosition(0);
            }
        }
    }


}

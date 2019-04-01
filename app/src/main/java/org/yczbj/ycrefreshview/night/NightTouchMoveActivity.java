package org.yczbj.ycrefreshview.night;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.widget.LinearLayout;

import org.yczbj.ycrefreshview.R;
import org.yczbj.ycrefreshview.first.PersonAdapter;
import org.yczbj.ycrefreshview.data.DataProvider;
import org.yczbj.ycrefreshview.data.PersonData;
import org.yczbj.ycrefreshview.data.AppUtils;
import org.yczbj.ycrefreshviewlib.callback.DefaultItemTouchHelpCallback;
import org.yczbj.ycrefreshviewlib.item.RecycleViewItemLine;

import java.util.Collections;
import java.util.List;

/**
 * Created by yc on 2018/2/9.
 */

public class NightTouchMoveActivity extends AppCompatActivity{


    private RecyclerView recyclerView;
    private PersonAdapter adapter;
    private DefaultItemTouchHelpCallback mCallback;
    private List<PersonData> personList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);
        initRecyclerView();
        initCallBack();
    }

    private void initRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        final RecycleViewItemLine line = new RecycleViewItemLine(this, LinearLayout.HORIZONTAL,
                (int) AppUtils.convertDpToPixel(1,this),
                this.getResources().getColor(R.color.color_f9f9f9));
        recyclerView.addItemDecoration(line);
        recyclerView.setAdapter(adapter = new PersonAdapter(this));
        personList = DataProvider.getPersonList(0);
        adapter.addAll(personList);
        adapter.notifyDataSetChanged();
    }

    private void initCallBack() {
        mCallback = new DefaultItemTouchHelpCallback(new DefaultItemTouchHelpCallback.OnItemTouchCallbackListener() {
            @Override
            public void onSwiped(int adapterPosition) {
                // 滑动删除的时候，从数据库、数据源移除，并刷新UI
                if (personList != null) {
                    adapter.remove(adapterPosition);
                }
            }

            @Override
            public boolean onMove(int srcPosition, int targetPosition) {
                if (personList != null) {
                    // 更换数据库中的数据Item的位置
                    boolean isPlus = srcPosition < targetPosition;
                    // 更换数据源中的数据Item的位置
                    Collections.swap(personList, srcPosition, targetPosition);
                    // 更新UI中的Item的位置，主要是给用户看到交互效果
                    adapter.notifyItemMoved(srcPosition, targetPosition);
                    return true;
                }
                return false;
            }
        });
        mCallback.setDragEnable(true);
        mCallback.setSwipeEnable(true);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(mCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }
}

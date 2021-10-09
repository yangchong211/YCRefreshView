package org.yczbj.ycrefreshview.touchmove;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.ItemTouchHelper;
import android.widget.LinearLayout;

import org.yczbj.ycrefreshview.R;
import org.yczbj.ycrefreshview.refresh.PersonAdapter;
import org.yczbj.ycrefreshview.data.DataProvider;
import org.yczbj.ycrefreshview.data.PersonData;
import org.yczbj.ycrefreshview.data.AppUtils;
import org.yczbj.ycrefreshviewlib.item.RecycleViewItemLine;
import org.yczbj.ycrefreshviewlib.touch.ItemTouchHelpCallback;
import java.util.Collections;
import java.util.List;

/**
 * Created by yc on 2018/2/9.
 */

public class NightTouchMoveActivity extends AppCompatActivity{


    private RecyclerView recyclerView;
    private PersonAdapter adapter;
    private List<PersonData> personList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);
        initRecyclerView();
        initCallBack();
    }

    private void initRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView);
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
        ItemTouchHelpCallback callback = new ItemTouchHelpCallback(
                new ItemTouchHelpCallback.OnItemTouchCallbackListener() {
            @Override
            public void onSwiped(int adapterPosition) {
                // 滑动删除的时候，从数据库、数据源移除，并刷新UI
                if (personList != null) {
                    //adapter.remove(adapterPosition);

                    //1、删除数据
                    personList.remove(adapterPosition);
                    //2、刷新
                    adapter.notifyItemRemoved(adapterPosition);
                }
            }

            @Override
            public boolean onMove(int srcPosition, int targetPosition) {
                if (personList != null) {
                    // 更换数据源中的数据Item的位置
                    Collections.swap(personList, srcPosition, targetPosition);
                    // 更新UI中的Item的位置，主要是给用户看到交互效果
                    adapter.notifyItemMoved(srcPosition, targetPosition);
                    return true;
                }
                return true;
            }
        });
        callback.setDragEnable(true);
        callback.setSwipeEnable(true);
        callback.setColor(this.getResources().getColor(R.color.colorAccent));
        //创建helper对象，callback监听recyclerView item 的各种状态
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        //关联recyclerView，一个helper对象只能对应一个recyclerView
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }
}

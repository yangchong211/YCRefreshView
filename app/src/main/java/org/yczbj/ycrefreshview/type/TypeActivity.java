package org.yczbj.ycrefreshview.type;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.yc.cn.ycbannerlib.LibUtils;

import org.yczbj.ycrefreshview.R;
import org.yczbj.ycrefreshview.data.DataProvider;
import org.yczbj.ycrefreshviewlib.inter.OnItemClickListener;
import org.yczbj.ycrefreshviewlib.item.DividerViewItemLine;
import org.yczbj.ycrefreshviewlib.touch.ItemTouchHelpCallback;

import java.util.Collections;

/**
 * @author yc
 */
public class TypeActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TypeAdapter adapter;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);
        initRecyclerView();
        initData();
        initCallBack();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (handler!=null){
            handler.removeCallbacksAndMessages(null);
            handler = null;
        }
    }

    private void initRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        DividerViewItemLine itemDecoration = new DividerViewItemLine(
                this.getResources().getColor(R.color.color_f9f9f9)
                , LibUtils.dip2px(this,1f),
                LibUtils.dip2px(this,30),30);
        recyclerView.addItemDecoration(itemDecoration);
        adapter = new TypeAdapter(this);
        recyclerView.setAdapter(adapter);
        adapter.setOnClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

            }
        });
    }


    private void initData() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                adapter.setData(DataProvider.getPersonList(0));
            }
        }, 50);
    }



    private void initCallBack() {
        ItemTouchHelpCallback callback = new ItemTouchHelpCallback(
                new ItemTouchHelpCallback.OnItemTouchCallbackListener() {
                    @Override
                    public void onSwiped(int adapterPosition) {
                        // 滑动删除的时候，从数据库、数据源移除，并刷新UI
                        if (adapter.getData() != null) {
                            //1、删除数据
                            adapter.getData().remove(adapterPosition);
                            //2、刷新
                            adapter.notifyItemRemoved(adapterPosition);
                        }
                    }

                    @Override
                    public boolean onMove(int srcPosition, int targetPosition) {
                        if (adapter.getData() != null) {
                            // 更换数据源中的数据Item的位置
                            Collections.swap(adapter.getData(), srcPosition, targetPosition);
                            // 更新UI中的Item的位置，主要是给用户看到交互效果
                            adapter.notifyItemMoved(srcPosition, targetPosition);
                            return true;
                        }
                        return true;
                    }
                });
        callback.setDragEnable(true);
        callback.setSwipeEnable(true);
        //创建helper对象，callback监听recyclerView item 的各种状态
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        //关联recyclerView，一个helper对象只能对应一个recyclerView
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

}

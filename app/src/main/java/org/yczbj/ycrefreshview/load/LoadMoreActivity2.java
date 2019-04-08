package org.yczbj.ycrefreshview.load;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.yc.cn.ycbannerlib.LibUtils;

import org.yczbj.ycrefreshview.R;
import org.yczbj.ycrefreshview.data.DataProvider;
import org.yczbj.ycrefreshview.data.PersonData;
import org.yczbj.ycrefreshviewlib.inter.OnItemClickListener;
import org.yczbj.ycrefreshviewlib.item.DividerViewItemLine;

import java.util.List;

/**
 * @author yc
 */
public class LoadMoreActivity2 extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LoadMoreAdapter2 adapter;
    private Handler handler = new Handler();
    private int lastVisibleItem = 0;
    private final int PAGE_COUNT = 10;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);
        initRecyclerView();
        initData();
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
        final GridLayoutManager layoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);
        DividerViewItemLine itemDecoration = new DividerViewItemLine(
                this.getResources().getColor(R.color.color_f9f9f9)
                , LibUtils.dip2px(this,1f),
                LibUtils.dip2px(this,30),30);
        recyclerView.addItemDecoration(itemDecoration);
        adapter = new LoadMoreAdapter2(this,false);
        recyclerView.setAdapter(adapter);
        adapter.setOnClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

            }
        });

        // 实现上拉加载重要步骤，设置滑动监听器，RecyclerView自带的ScrollListener
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                // 在newState为滑到底部时
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    // 如果没有隐藏footView，那么最后一个条目的位置就比我们的getItemCount少1，自己可以算一下
                    if (!adapter.isFadeTips() && lastVisibleItem + 1 == adapter.getItemCount()) {
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                updateRecyclerView(adapter.getRealLastPosition(),
                                        adapter.getRealLastPosition() + PAGE_COUNT);
                            }
                        }, 500);
                    }

                    // 如果隐藏了提示条，我们又上拉加载时，那么最后一个条目就要比getItemCount要少2
                    if (adapter.isFadeTips() && lastVisibleItem + 2 == adapter.getItemCount()) {
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                updateRecyclerView(adapter.getRealLastPosition(),
                                        adapter.getRealLastPosition() + PAGE_COUNT);
                            }
                        }, 500);
                    }
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                // 在滑动完成后，拿到最后一个可见的item的位置
                lastVisibleItem = layoutManager.findLastVisibleItemPosition();
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

    private void updateRecyclerView(int fromIndex, int toIndex) {
        List<PersonData> newDatas = DataProvider.getPersonList(0);
        if (newDatas.size() > 0) {
            adapter.updateList(newDatas, true);
        } else {
            adapter.updateList(null, false);
        }
    }


}

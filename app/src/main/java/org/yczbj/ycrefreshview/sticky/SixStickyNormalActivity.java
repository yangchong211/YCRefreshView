package org.yczbj.ycrefreshview.sticky;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.yc.cn.ycbannerlib.LibUtils;

import org.yczbj.ycrefreshview.R;
import org.yczbj.ycrefreshview.data.DataProvider;
import org.yczbj.ycrefreshview.data.PersonData;
import org.yczbj.ycrefreshview.refresh.PersonViewHolder;
import org.yczbj.ycrefreshviewlib.adapter.RecyclerArrayAdapter;
import org.yczbj.ycrefreshviewlib.holder.BaseViewHolder;
import org.yczbj.ycrefreshviewlib.inter.OnItemLongClickListener;
import org.yczbj.ycrefreshviewlib.inter.OnLoadMoreListener;
import org.yczbj.ycrefreshviewlib.item.DividerViewItemLine;
import org.yczbj.ycrefreshviewlib.view.YCRefreshView;

import java.util.ArrayList;


public class SixStickyNormalActivity extends AppCompatActivity implements OnLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {

    private YCRefreshView recyclerView;
    private FloatingActionButton top;
    private RecyclerArrayAdapter<PersonData> adapter;
    private Handler handler = new Handler();

    private int page = 0;
    private boolean hasNetWork = true;
    private ArrayList<Integer> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_recyclerview);

        top = findViewById(R.id.top);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DividerViewItemLine itemDecoration = new DividerViewItemLine(
                this.getResources().getColor(R.color.color_f9f9f9),
                LibUtils.dip2px(this, 1f),
                LibUtils.dip2px(this, 72), 0);
        itemDecoration.setDrawLastItem(false);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setAdapterWithProgress(adapter = new RecyclerArrayAdapter<PersonData>(this) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                return new PersonViewHolder(parent);
            }
        });
        adapter.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(int position) {
                adapter.remove(position);
                return true;
            }
        });
        // StickyHeader
        StickyNormalItemLine decoration = new StickyNormalItemLine(
                new StickyNormalAdapter(this));
        recyclerView.addItemDecoration(decoration);
        top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView.scrollToPosition(0);
            }
        });
        recyclerView.setRefreshListener(this);
        onRefresh();

    }

    //第四页会返回空,意为数据加载结束
    @Override
    public void onLoadMore() {
        Log.i("EasyRecyclerView", "onLoadMore");
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //刷新
                if (!hasNetWork) {
                    adapter.pauseMore();
                    return;
                }
                adapter.addAll(DataProvider.getPersonList(page));
                page++;
            }
        }, 2000);
    }

    @Override
    public void onRefresh() {
        page = 0;
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                adapter.clear();
                //刷新
                if (!hasNetWork) {
                    adapter.pauseMore();
                    return;
                }
                adapter.addAll(DataProvider.getPersonList(page));
                page = 1;
            }
        }, 2000);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem item = menu.findItem(R.id.checkbox);
        CheckBox box = (CheckBox) item.getActionView();
        box.setChecked(true);
        box.setText("网络");
        box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                hasNetWork = isChecked;
            }
        });
        return true;
    }


}

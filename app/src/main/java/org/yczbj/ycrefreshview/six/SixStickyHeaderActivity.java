package org.yczbj.ycrefreshview.six;

import android.graphics.Color;
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
import com.jude.rollviewpager.Util;

import org.yczbj.ycrefreshview.other.DataProvider;
import org.yczbj.ycrefreshview.R;
import org.yczbj.ycrefreshview.other.Person;
import org.yczbj.ycrefreshview.first.PersonViewHolder;
import org.yczbj.ycrefreshviewlib.YCRefreshView;
import org.yczbj.ycrefreshviewlib.adapter.RecyclerArrayAdapter;
import org.yczbj.ycrefreshviewlib.item.DividerViewItemLine;
import org.yczbj.ycrefreshviewlib.item.StickyHeaderItemLine;
import org.yczbj.ycrefreshviewlib.viewHolder.BaseViewHolder;


public class SixStickyHeaderActivity extends AppCompatActivity implements RecyclerArrayAdapter.OnLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {

    private YCRefreshView recyclerView;
    private FloatingActionButton top;
    private RecyclerArrayAdapter<Person> adapter;
    private Handler handler = new Handler();

    private int page = 0;
    private boolean hasNetWork = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loadmore);

        top = (FloatingActionButton) findViewById(R.id.top);
        recyclerView = (YCRefreshView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DividerViewItemLine itemDecoration = new DividerViewItemLine(Color.GRAY, Util.dip2px(this, 0.5f), Util.dip2px(this, 72), 0);
        itemDecoration.setDrawLastItem(false);
        recyclerView.addItemDecoration(itemDecoration);


        recyclerView.setAdapterWithProgress(adapter = new RecyclerArrayAdapter<Person>(this) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                return new PersonViewHolder(parent);
            }
        });
        adapter.setMore(R.layout.view_more, this);
        adapter.setNoMore(R.layout.view_nomore, new RecyclerArrayAdapter.OnNoMoreListener() {
            @Override
            public void onNoMoreShow() {
                adapter.resumeMore();
            }

            @Override
            public void onNoMoreClick() {
                adapter.resumeMore();
            }
        });
        adapter.setOnItemLongClickListener(new RecyclerArrayAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(int position) {
                adapter.remove(position);
                return true;
            }
        });
        adapter.setError(R.layout.view_error, new RecyclerArrayAdapter.OnErrorListener() {
            @Override
            public void onErrorShow() {
                adapter.resumeMore();
            }

            @Override
            public void onErrorClick() {
                adapter.resumeMore();
            }
        });
        // StickyHeader
        StickyHeaderItemLine decoration = new StickyHeaderItemLine(new StickyHeaderAdapter(this));
        decoration.setIncludeHeader(false);
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

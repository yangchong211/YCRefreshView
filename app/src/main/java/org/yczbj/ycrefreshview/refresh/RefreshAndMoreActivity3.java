package org.yczbj.ycrefreshview.refresh;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import com.yc.cn.ycbannerlib.gallery.GalleryLayoutManager;

import org.yczbj.ycrefreshview.R;
import org.yczbj.ycrefreshview.data.AppUtils;
import org.yczbj.ycrefreshview.data.DataProvider;
import org.yczbj.ycrefreshview.data.PersonData;
import org.yczbj.ycrefreshviewlib.adapter.RecyclerArrayAdapter;
import org.yczbj.ycrefreshviewlib.holder.BaseViewHolder;
import org.yczbj.ycrefreshviewlib.inter.OnErrorListener;
import org.yczbj.ycrefreshviewlib.inter.OnItemLongClickListener;
import org.yczbj.ycrefreshviewlib.inter.OnLoadMoreListener;
import org.yczbj.ycrefreshviewlib.inter.OnMoreListener;
import org.yczbj.ycrefreshviewlib.inter.OnNoMoreListener;
import org.yczbj.ycrefreshviewlib.item.RecycleViewItemLine;
import org.yczbj.ycrefreshviewlib.view.YCRefreshView;


public class RefreshAndMoreActivity3 extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{

    private YCRefreshView recyclerView;
    private FloatingActionButton top;
    private RecyclerArrayAdapter<PersonData> adapter;
    private Handler handler = new Handler();

    private boolean hasNetWork = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_recyclerview);

        initView();
        initListener();
        onRefresh();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (handler!=null){
            handler.removeCallbacksAndMessages(null);
            handler = null;
        }
    }

    @Override
    public void onRefresh() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                adapter.clear();
                //刷新
                if (!hasNetWork) {
                    adapter.pauseMore();
                    return;
                }
                adapter.add(new PersonData());
                adapter.addAll(DataProvider.getPersonList(15));
                recyclerView.showRecycler();
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

    private void initView() {
        top = findViewById(R.id.top);
        recyclerView = findViewById(R.id.recyclerView);
        GridLayoutManager layoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);


        final RecycleViewItemLine line = new RecycleViewItemLine(this, LinearLayout.HORIZONTAL,
                (int)AppUtils.convertDpToPixel(1,this),
                this.getResources().getColor(R.color.color_f9f9f9));
        recyclerView.addItemDecoration(line);
        adapter = new RecyclerArrayAdapter<PersonData>(this) {
                    @Override
                    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                        return new PersonViewHolder(parent);
                    }
                };
        adapter.setHeaderAndFooterSpan(true);
        recyclerView.setAdapterWithProgress(adapter);
        adapter.setMore(R.layout.view_more, new OnMoreListener() {
            @Override
            public void onMoreShow() {
                //点击触发加载下一页数据
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //刷新
                        if (!hasNetWork) {
                            adapter.pauseMore();
                            return;
                        }
                        adapter.addAll(DataProvider.getPersonList(10));
                    }
                }, 2000);
            }

            @Override
            public void onMoreClick() {

            }
        });

        adapter.setNoMore(R.layout.view_nomore, new OnNoMoreListener() {
            @Override
            public void onNoMoreShow() {
                adapter.pauseMore();
            }

            @Override
            public void onNoMoreClick() {
                Log.e("逗比","没有更多数据了");
            }
        });
        adapter.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(int position) {
                adapter.remove(position);
                return true;
            }
        });
        adapter.setError(R.layout.view_error, new OnErrorListener() {
            @Override
            public void onErrorShow() {
                adapter.resumeMore();
            }

            @Override
            public void onErrorClick() {
                adapter.resumeMore();
            }
        });
    }


    private void initListener() {
        recyclerView.setRefreshListener(this);
        top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView.scrollToPosition(0);
            }
        });
    }

}

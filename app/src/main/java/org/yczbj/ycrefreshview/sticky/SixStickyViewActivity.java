package org.yczbj.ycrefreshview.sticky;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import org.yczbj.ycrefreshview.R;
import org.yczbj.ycrefreshview.data.AppUtils;
import org.yczbj.ycrefreshview.data.DataProvider;
import org.yczbj.ycrefreshview.data.PersonData;
import org.yczbj.ycrefreshview.refresh.PersonAdapter;
import org.yczbj.ycrefreshviewlib.adapter.RecyclerArrayAdapter;
import org.yczbj.ycrefreshviewlib.inter.OnErrorListener;
import org.yczbj.ycrefreshviewlib.inter.OnItemLongClickListener;
import org.yczbj.ycrefreshviewlib.inter.OnLoadMoreListener;
import org.yczbj.ycrefreshviewlib.inter.OnNoMoreListener;
import org.yczbj.ycrefreshviewlib.item.RecycleViewItemLine;
import org.yczbj.ycrefreshviewlib.utils.RefreshLogUtils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class SixStickyViewActivity extends AppCompatActivity {

    private CoordinatorLayout coordinator;
    private AppBarLayout appbar;
    private RecyclerView recyclerView;
    private RecyclerArrayAdapter<PersonData> adapter;
    private boolean hasNetWork = true;
    private Handler handler = new Handler();
    @Retention(RetentionPolicy.SOURCE)
    public @interface STATES{
        int EXPANDED = 3;
        int COLLAPSED = 2;
        int INTERMEDIATE = 1;
    }
    private int state;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sticky_header);


        coordinator = findViewById(R.id.coordinator);
        appbar = findViewById(R.id.appbar);
        recyclerView = findViewById(R.id.recyclerView);

        initCoordinator();
        initRecyclerView();
        initData();
    }


    private void initCoordinator() {
        appbar.addOnOffsetChangedListener(listener);
    }


    private AppBarLayout.OnOffsetChangedListener listener =  new AppBarLayout.OnOffsetChangedListener() {
        @Override
        public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
            int totalScrollRange = appBarLayout.getTotalScrollRange();
            if (verticalOffset == 0) {
                if (state != STATES.EXPANDED) {
                    //修改状态标记为展开
                    state = STATES.EXPANDED;
                    RefreshLogUtils.e("OnOffsetChangedListener"+ "修改状态标记为展开");
                }
            } else if (Math.abs(verticalOffset) >= totalScrollRange) {
                if (state != STATES.COLLAPSED) {
                    //修改状态标记为折叠
                    state = STATES.COLLAPSED;
                    RefreshLogUtils.e("OnOffsetChangedListener"+ "修改状态标记为折叠");
                }
            } else {
                if (state != STATES.INTERMEDIATE) {
                    //修改状态标记为中间
                    state = STATES.INTERMEDIATE;
                    //代码设置是否拦截事件
                    RefreshLogUtils.e("OnOffsetChangedListener"+ "修改状态标记为中间");
                }
                
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (handler!=null){
            handler.removeCallbacksAndMessages(null);
            handler = null;
        }
    }

    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        final RecycleViewItemLine line = new RecycleViewItemLine(this, LinearLayout.HORIZONTAL,
                (int)AppUtils.convertDpToPixel(1,this),
                this.getResources().getColor(R.color.color_f9f9f9));
        recyclerView.addItemDecoration(line);
        adapter = new PersonAdapter(this);
        recyclerView.setAdapter(adapter);
        adapter.setMore(R.layout.view_more, new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
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
        });
        adapter.setNoMore(R.layout.view_nomore, new OnNoMoreListener() {
            @Override
            public void onNoMoreShow() {
                adapter.pauseMore();
            }

            @Override
            public void onNoMoreClick() {

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


    private void initData() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                adapter.clear();
                //刷新
                if (!hasNetWork) {
                    adapter.pauseMore();
                    return;
                }
                adapter.addAll(DataProvider.getPersonList(10));
            }
        }, 50);
    }

}

package org.yczbj.ycrefreshview.header;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.yc.cn.ycbannerlib.LibUtils;
import com.yc.cn.ycbannerlib.banner.BannerView;
import com.yc.cn.ycbannerlib.banner.hintview.ColorPointHintView;

import org.yczbj.ycrefreshview.data.DataProvider;
import org.yczbj.ycrefreshview.R;
import org.yczbj.ycrefreshview.data.AppUtils;
import org.yczbj.ycrefreshview.data.PersonData;
import org.yczbj.ycrefreshview.refresh.PersonAdapter;
import org.yczbj.ycrefreshviewlib.view.YCRefreshView;
import org.yczbj.ycrefreshviewlib.inter.InterItemView;
import org.yczbj.ycrefreshviewlib.inter.OnItemChildClickListener;
import org.yczbj.ycrefreshviewlib.inter.OnLoadMoreListener;
import org.yczbj.ycrefreshviewlib.item.DividerViewItemLine;
import org.yczbj.ycrefreshviewlib.item.RecycleViewItemLine;
import org.yczbj.ycrefreshviewlib.item.SpaceViewItemLine;



public class HeaderFooterActivity extends AppCompatActivity {

    private YCRefreshView recyclerView;
    private PersonAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refresh_view);
        recyclerView = findViewById(R.id.recyclerView);
        adapter = new PersonAdapter(this);
        recyclerView.setAdapter(adapter);

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        DividerViewItemLine itemDecoration = new DividerViewItemLine(
                this.getResources().getColor(R.color.color_f9f9f9),
                LibUtils.dip2px(this,0.5f),
                LibUtils.dip2px(this,72),0);
        itemDecoration.setDrawLastItem(true);
        itemDecoration.setDrawHeaderFooter(true);
        recyclerView.addItemDecoration(itemDecoration);

        final RecycleViewItemLine line = new RecycleViewItemLine(this,
                LinearLayout.HORIZONTAL, 1, getResources().getColor(R.color.colorAccent));
        recyclerView.addItemDecoration(line);

        recyclerView.setRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                recyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        adapter.clear();
                        adapter.addAll(DataProvider.getPersonList(0));
                    }
                },1500);
            }
        });
        //recyclerView.setRefreshing(true);
        recyclerView.setRefreshingColorResources(R.color.colorAccent);
        initHeader();
        adapter.addAll(DataProvider.getPersonList(0));
        adapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(View view, int position) {
                switch (view.getId()){
                    case R.id.iv_news_image:
                        Toast.makeText(HeaderFooterActivity.this,
                                "点击图片了",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.tv_title:
                        Toast.makeText(HeaderFooterActivity.this,
                                "点击标题",Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
            }
        });
    }




    private void initHeader() {
        adapter.removeAllFooter();
        adapter.removeAllHeader();

        InterItemView interItemView = new InterItemView() {
            @Override
            public View onCreateView(ViewGroup parent) {
                BannerView header = new BannerView(HeaderFooterActivity.this);
                header.setHintView(new ColorPointHintView(HeaderFooterActivity.this,
                        Color.YELLOW, Color.GRAY));
                header.setHintPadding(0, 0, 0, (int) AppUtils.convertDpToPixel(
                        8, HeaderFooterActivity.this));
                header.setPlayDelay(2000);
                header.setLayoutParams(new RecyclerView.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        (int) AppUtils.convertDpToPixel(200, HeaderFooterActivity.this)));
                header.setAdapter(new BannerAdapter(HeaderFooterActivity.this));
                return header;
            }

            @Override
            public void onBindView(View headerView) {

            }
        };
        adapter.addHeader(interItemView);
        adapter.addHeader(new InterItemView() {
            @Override
            public View onCreateView(ViewGroup parent) {
                View inflate = LayoutInflater.from(HeaderFooterActivity.this)
                        .inflate(R.layout.header_view, null);
                return inflate;
            }

            @Override
            public void onBindView(View headerView) {
                TextView tvTitle = headerView.findViewById(R.id.tvTitle);
            }
        });
        adapter.addHeader(new InterItemView() {
            @Override
            public View onCreateView(ViewGroup parent) {
                RecyclerView recyclerView = new RecyclerView(parent.getContext()){
                    //为了不打扰横向RecyclerView的滑动操作，可以这样处理
                    @SuppressLint("ClickableViewAccessibility")
                    @Override
                    public boolean onTouchEvent(MotionEvent event) {
                        super.onTouchEvent(event);
                        return true;
                    }
                };
                recyclerView.setLayoutParams(new RecyclerView.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        (int) AppUtils.convertDpToPixel(200, HeaderFooterActivity.this)));
                final NarrowImageAdapter adapter;
                recyclerView.setAdapter(adapter = new NarrowImageAdapter(parent.getContext()));
                recyclerView.setLayoutManager(new LinearLayoutManager(parent.getContext(),
                        LinearLayoutManager.HORIZONTAL,false));

                recyclerView.addItemDecoration(new SpaceViewItemLine((int)
                        AppUtils.convertDpToPixel(8,parent.getContext())));

                adapter.setMore(R.layout.view_more_horizontal, new OnLoadMoreListener() {
                    @Override
                    public void onLoadMore() {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                adapter.addAll(DataProvider.getNarrowImage(0));
                            }
                        },1000);
                    }
                });
                adapter.addAll(DataProvider.getNarrowImage(0));
                return recyclerView;
            }

            @Override
            public void onBindView(View headerView) {
                //这里的处理别忘了
                ((ViewGroup)headerView).requestDisallowInterceptTouchEvent(true);
            }
        });
        adapter.addFooter(new InterItemView() {
            @Override
            public View onCreateView(ViewGroup parent) {
                View inflate = LayoutInflater.from(HeaderFooterActivity.this)
                        .inflate(R.layout.footer_view, null);
                return inflate;
            }

            @Override
            public void onBindView(View headerView) {

            }
        });
        adapter.addFooter(new InterItemView() {
            @Override
            public View onCreateView(ViewGroup parent) {
                TextView tv = new TextView(HeaderFooterActivity.this);
                tv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        (int) AppUtils.convertDpToPixel(56,HeaderFooterActivity.this)));
                tv.setGravity(Gravity.CENTER);
                tv.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
                tv.setText("这个是底部布局");
                return tv;
            }

            @Override
            public void onBindView(View headerView) {

            }
        });
    }


    private void test(){
        //删除索引处的数据
        adapter.remove(0);
        //触发清空所有数据
        //添加数据，注意这个是在最后索引处添加
        adapter.add(new PersonData());
        //添加所有数据
        adapter.addAll(DataProvider.getPersonList(0));
        //插入数据
        adapter.insert(new PersonData(),3);
        //在某个索引处插入集合数据
        adapter.insertAll(DataProvider.getPersonList(0),3);
        //获取item索引位置
        //adapter.getPosition(data);
        //触发清空所有的数据
        adapter.clear();
        //获取所有的数据
        adapter.getAllData();

        InterItemView view = adapter.getHeader(0);

        //清除所有footer
        adapter.removeAllFooter();
        //清除所有header
        adapter.removeAllHeader();
        //添加footerView
        adapter.addFooter(view);
        //添加headerView
        adapter.addHeader(view);
        //移除某个headerView
        adapter.removeHeader(view);
        //移除某个footerView
        adapter.removeFooter(view);
        //获取某个索引处的headerView
        adapter.getHeader(0);
        //获取某个索引处的footerView
        adapter.getFooter(0);
        //获取footer的数量
        adapter.getFooterCount();
        //获取header的数量
        adapter.getHeaderCount();

        //设置上拉加载更多的自定义布局和监听
        //adapter.setMore(R.layout.view_more,listener);
        //设置上拉加载更多的自定义布局和监听
        //adapter.setMore(view,listener);
        //设置上拉加载没有更多数据布局
        adapter.setNoMore(R.layout.view_nomore);
        //设置上拉加载没有更多数据布局
        //adapter.setNoMore(view);
        //设置上拉加载没有更多数据监听
        adapter.setNoMore(R.layout.view_nomore,null);
        //设置上拉加载异常的布局
        adapter.setError(R.layout.view_error);
        //设置上拉加载异常的布局
        //adapter.setError(view);
        //设置上拉加载异常的布局和异常监听
        adapter.setError(R.layout.view_error,null);
        //暂停上拉加载更多
        adapter.pauseMore();
        //停止上拉加载更多
        adapter.stopMore();
        //恢复上拉加载更多
        adapter.resumeMore();

        //获取上下文
        adapter.getContext();
        //应该使用这个获取item个数
        adapter.getCount();
        //设置操作数据[增删改查]后，是否刷新adapter
        adapter.setNotifyOnChange(true);

        //设置孩子点击事件
        adapter.setOnItemChildClickListener(null);
        //设置条目点击事件
        adapter.setOnItemClickListener(null);
        //设置条目长按事件
        adapter.setOnItemLongClickListener(null);
    }

}

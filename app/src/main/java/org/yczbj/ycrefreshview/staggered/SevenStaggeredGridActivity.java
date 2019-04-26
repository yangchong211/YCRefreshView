package org.yczbj.ycrefreshview.staggered;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;


import com.yc.cn.ycbannerlib.banner.BannerView;
import com.yc.cn.ycbannerlib.banner.hintview.ColorPointHintView;

import org.yczbj.ycrefreshview.data.DataProvider;
import org.yczbj.ycrefreshview.R;
import org.yczbj.ycrefreshview.data.AppUtils;
import org.yczbj.ycrefreshview.header.BannerAdapter;
import org.yczbj.ycrefreshviewlib.view.YCRefreshView;
import org.yczbj.ycrefreshviewlib.inter.InterItemView;
import org.yczbj.ycrefreshviewlib.inter.OnMoreListener;
import org.yczbj.ycrefreshviewlib.item.SpaceViewItemLine;

public class SevenStaggeredGridActivity extends AppCompatActivity {

    private YCRefreshView recyclerView;
    private ImageAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refresh_view);
        recyclerView = (YCRefreshView) findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL));
        adapter = new ImageAdapter(this);
        recyclerView.setAdapter(adapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,4);
        gridLayoutManager.setSpanSizeLookup(adapter.obtainGridSpanSizeLookUp(4));
        recyclerView.setLayoutManager(gridLayoutManager);
        adapter.addHeader(new InterItemView() {
            @Override
            public View onCreateView(ViewGroup parent) {
                BannerView header = new BannerView(SevenStaggeredGridActivity.this);
                header.setHintView(new ColorPointHintView(SevenStaggeredGridActivity.this, Color.YELLOW,Color.GRAY));
                header.setHintPadding(0, 0, 0, (int) AppUtils.convertDpToPixel(8, SevenStaggeredGridActivity.this));
                header.setPlayDelay(2000);
                header.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) AppUtils.convertDpToPixel(200, SevenStaggeredGridActivity.this)));
                header.setAdapter(new BannerAdapter(SevenStaggeredGridActivity.this));
                return header;
            }

            @Override
            public void onBindView(View headerView) {

            }
        });
        SpaceViewItemLine itemDecoration = new SpaceViewItemLine(
                (int) AppUtils.convertDpToPixel(8,this));
        itemDecoration.setPaddingEdgeSide(true);
        itemDecoration.setPaddingStart(true);
        itemDecoration.setPaddingHeaderFooter(true);
        recyclerView.addItemDecoration(itemDecoration);
        adapter.setMore(R.layout.view_more, new OnMoreListener() {
            @Override
            public void onMoreShow() {
                addData();
            }

            @Override
            public void onMoreClick() {

            }
        });
        adapter.setNoMore(R.layout.view_nomore);
        recyclerView.setRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                recyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        adapter.clear();
                        adapter.addAll(DataProvider.getPictures());
                    }
                },1000);
            }
        });
        addData();
    }

    private void addData(){
        recyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                adapter.addAll(DataProvider.getPictures());
            }
        },300);
    }
}

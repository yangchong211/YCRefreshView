package org.yczbj.ycrefreshview.scroll;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;

import org.yczbj.ycrefreshview.R;
import org.yczbj.ycrefreshview.data.DataProvider;
import org.yczbj.ycrefreshview.scroll.inter.OnPagerListener;
import org.yczbj.ycrefreshview.scroll.recycler.PagerLayoutManager;
import org.yczbj.ycrefreshviewlib.utils.RefreshLogUtils;

public class ScrollActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);
        recyclerView = findViewById(R.id.recyclerView);
        initRecyclerView();
    }

    private void initRecyclerView() {
        PagerLayoutManager viewPagerLayoutManager = new PagerLayoutManager(
                this, OrientationHelper.VERTICAL);
        ScrollAdapter adapter = new ScrollAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(viewPagerLayoutManager);
        viewPagerLayoutManager.setOnViewPagerListener(new OnPagerListener() {
            @Override
            public void onInitComplete() {
                System.out.println("OnPagerListener---onInitComplete--"+"初始化完成");
                RefreshLogUtils.d("OnPagerListener---onInitComplete--"+"初始化完成");
            }

            @Override
            public void onPageRelease(boolean isNext, int position) {
                System.out.println("OnPagerListener---onPageRelease--"+position+"-----"+isNext);
                RefreshLogUtils.d("OnPagerListener---onPageRelease--"+position+"-----"+isNext);
            }

            @Override
            public void onPageSelected(int position, boolean isBottom) {
                System.out.println("OnPagerListener---onPageSelected--"+position+"-----"+isBottom);
                RefreshLogUtils.d("OnPagerListener---onPageSelected--"+position+"-----"+isBottom);
            }
        });
        adapter.setData(DataProvider.getList(30));
    }



}

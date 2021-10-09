package org.yczbj.ycrefreshview.collapsing;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yc.cn.ycbannerlib.banner.BannerView;
import com.yc.cn.ycbannerlib.banner.adapter.AbsStaticPagerAdapter;
import com.yc.cn.ycbannerlib.banner.hintview.ColorPointHintView;


import org.yczbj.ycrefreshview.data.DataProvider;
import org.yczbj.ycrefreshview.R;
import org.yczbj.ycrefreshview.data.AdData;
import org.yczbj.ycrefreshview.refresh.PersonAdapter;
import org.yczbj.ycrefreshviewlib.view.YCRefreshView;
import org.yczbj.ycrefreshviewlib.inter.OnLoadMoreListener;

import java.util.List;


public class EightCollapsingActivity extends AppCompatActivity {

    YCRefreshView recyclerView;
    PersonAdapter adapter;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collapsing);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter = new PersonAdapter(this));
        adapter.setMore(R.layout.view_more, new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        adapter.addAll(DataProvider.getPersonList(0));
                    }
                }, 1000);
            }
        });
        adapter.addAll(DataProvider.getPersonList(0));
        BannerView rollPagerView = findViewById(R.id.rollPagerView);
        rollPagerView.setHintView(new ColorPointHintView(this, Color.YELLOW,Color.GRAY));
        rollPagerView.setAdapter(new BannerAdapter());
    }

    private class BannerAdapter extends AbsStaticPagerAdapter {
        private List<AdData> list;
        public BannerAdapter(){
            list = DataProvider.getAdList();
        }

        @Override
        public View getView(ViewGroup container, final int position) {
            ImageView imageView = new ImageView(EightCollapsingActivity.this);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            //加载图片
            Glide.with(EightCollapsingActivity.this)
                    .load(list.get(position).getDrawable())
                    .placeholder(R.drawable.default_image)
                    .into(imageView);
            //点击事件
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            return imageView;
        }

        @Override
        public int getCount() {
            return list.size();
        }
    }
}

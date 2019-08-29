package org.yczbj.ycrefreshview.scroll;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import org.yczbj.ycrefreshview.R;
import org.yczbj.ycrefreshview.data.DataProvider;
import org.yczbj.ycrefreshview.data.PersonData;
import org.yczbj.ycrefreshview.scroll.pager.LayoutViewPager;
import org.yczbj.ycrefreshview.scroll.pager.AbsPagerAdapter;
import org.yczbj.ycrefreshview.scroll.recycler.OnPagerListener;

import java.util.List;

public class ScrollPagerActivity extends AppCompatActivity {

    private LayoutViewPager vp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);
        vp = findViewById(R.id.vp);
        initViewPager();
    }

    private void initViewPager() {
        List<PersonData> list = DataProvider.getList(30);
        vp.setOffscreenPageLimit(1);
        vp.setCurrentItem(0);
        BannerPagerAdapter adapter = new BannerPagerAdapter(list);
        vp.setAdapter(adapter);
        vp.setAnimationDuration(1000);
        vp.setOnViewPagerListener(new OnPagerListener() {
            @Override
            public void onInitComplete() {

            }

            @Override
            public void onPageRelease(boolean isNext, int position) {

            }

            @Override
            public void onPageSelected(int position, boolean isBottom) {

            }
        });
    }

    class BannerPagerAdapter extends AbsPagerAdapter {

        private List<PersonData> data;

        public BannerPagerAdapter(List<PersonData> dataList) {
            super(dataList);
            this.data = dataList;
        }


        @Override
        public View getView(ViewGroup container, int position) {
            View view = LayoutInflater.from(container.getContext()).inflate(
                    R.layout.item_image_pager, container, false);
            ImageView imageView = view.findViewById(R.id.iv_image);
            imageView.setImageResource(data.get(position).getImage());
            return view;
        }

        @Override
        public int getCount() {
            return data.size();
        }
    }
}

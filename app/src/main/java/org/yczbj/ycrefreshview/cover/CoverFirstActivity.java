package org.yczbj.ycrefreshview.cover;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;


import org.yczbj.ycrefreshview.R;
import org.yczbj.ycrefreshview.cover.cover.GalleryImageView;

import java.util.ArrayList;
import java.util.List;

public class CoverFirstActivity extends AppCompatActivity {

    GalleryImageView scrollGalleryView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cover_first);
        scrollGalleryView = findViewById(R.id.scroll_gallery_view);
        initImage();
    }

    private void initImage(){
        List<Object> picsBeanList = new ArrayList<>();
        for (int i=0 ; i<10  ; i++){
            picsBeanList.add("https://pic1.yilu.cn/20190917/fqlcmvycypjmmgdvdbrhgfzpsiidmjva.jpg");
            picsBeanList.add("https://pic1.yilu.cn/20190916/asphpxyzcymmbhcpkkqejspgyzjqvsbs.jpg");
            picsBeanList.add("https://pic1.yilu.cn/20190917/fqlcmvycypjmmgdvdbrhgfzpsiidmjva.jpg");
            picsBeanList.add("https://pic1.yilu.cn/201909/982823160a0693d37768a2c7ae787cef.jpeg");
            picsBeanList.add("https://pic1.yilu.cn/20190917/fqlcmvycypjmmgdvdbrhgfzpsiidmjva.jpg");
            picsBeanList.add("https://pic1.yilu.cn/20190917/fqlcmvycypjmmgdvdbrhgfzpsiidmjva.jpg");
            picsBeanList.add("https://pic1.yilu.cn/20190917/fqlcmvycypjmmgdvdbrhgfzpsiidmjva.jpg");
            picsBeanList.add("https://pic1.yilu.cn/20190916/asphpxyzcymmbhcpkkqejspgyzjqvsbs.jpg");
            picsBeanList.add("https://pic1.yilu.cn/20190917/fqlcmvycypjmmgdvdbrhgfzpsiidmjva.jpg");
            picsBeanList.add("https://pic1.yilu.cn/201909/982823160a0693d37768a2c7ae787cef.jpeg");
            picsBeanList.add("https://pic1.yilu.cn/20190917/fqlcmvycypjmmgdvdbrhgfzpsiidmjva.jpg");
            picsBeanList.add("https://pic1.yilu.cn/20190917/fqlcmvycypjmmgdvdbrhgfzpsiidmjva.jpg");
            picsBeanList.add("https://pic1.yilu.cn/20190917/fqlcmvycypjmmgdvdbrhgfzpsiidmjva.jpg");
            picsBeanList.add("https://pic1.yilu.cn/20190917/fqlcmvycypjmmgdvdbrhgfzpsiidmjva.jpg");
            picsBeanList.add("https://pic1.yilu.cn/20190916/asphpxyzcymmbhcpkkqejspgyzjqvsbs.jpg");
            picsBeanList.add("https://pic1.yilu.cn/20190917/fqlcmvycypjmmgdvdbrhgfzpsiidmjva.jpg");
            picsBeanList.add("https://pic1.yilu.cn/201909/982823160a0693d37768a2c7ae787cef.jpeg");
            picsBeanList.add("https://pic1.yilu.cn/20190917/fqlcmvycypjmmgdvdbrhgfzpsiidmjva.jpg");
            picsBeanList.add("https://pic1.yilu.cn/20190917/fqlcmvycypjmmgdvdbrhgfzpsiidmjva.jpg");
            picsBeanList.add("https://pic1.yilu.cn/20190917/fqlcmvycypjmmgdvdbrhgfzpsiidmjva.jpg");
            picsBeanList.add("https://pic1.yilu.cn/20190916/asphpxyzcymmbhcpkkqejspgyzjqvsbs.jpg");
            picsBeanList.add("https://pic1.yilu.cn/20190917/fqlcmvycypjmmgdvdbrhgfzpsiidmjva.jpg");
            picsBeanList.add("https://pic1.yilu.cn/201909/982823160a0693d37768a2c7ae787cef.jpeg");
            picsBeanList.add("https://pic1.yilu.cn/20190917/fqlcmvycypjmmgdvdbrhgfzpsiidmjva.jpg");
            picsBeanList.add("https://pic1.yilu.cn/20190917/fqlcmvycypjmmgdvdbrhgfzpsiidmjva.jpg");
            picsBeanList.add("https://pic1.yilu.cn/20190917/fqlcmvycypjmmgdvdbrhgfzpsiidmjva.jpg");
        }

        scrollGalleryView
                //设置viewPager底部缩略图大小尺寸
                //设置是否支持缩放
                //设置切换的图片索引
                .setPosition(0)
                //设置缩放的倍数
                //设置是否隐藏底部缩略图，主要是防止后期产品参考懂车帝，又不要底部滑动，非常灵活修改
                .hideThumbnails(false)
                //添加滑动事件，也可以不用添加
                .addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                    }

                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onPageSelected(int position) {

                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {

                    }
                })
                .addUrlToRecyclerView(picsBeanList);
    }
}

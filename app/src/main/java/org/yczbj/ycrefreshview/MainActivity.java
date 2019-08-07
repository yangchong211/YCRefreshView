package org.yczbj.ycrefreshview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.load.engine.Resource;

import org.yczbj.ycrefreshview.collapsing.EightCollapsingActivity;
import org.yczbj.ycrefreshview.layout.LayoutActivity;
import org.yczbj.ycrefreshview.load.LoadMoreActivity;
import org.yczbj.ycrefreshview.load.LoadMoreActivity2;
import org.yczbj.ycrefreshview.normal.NormalRecyclerViewActivity;
import org.yczbj.ycrefreshview.normal.SpanRecyclerViewActivity;
import org.yczbj.ycrefreshview.refresh.RefreshAndMoreActivity1;
import org.yczbj.ycrefreshview.refresh.RefreshAndMoreActivity2;
import org.yczbj.ycrefreshview.multistyle.FiveMultiStyleActivity;
import org.yczbj.ycrefreshview.horizontal.FourHorizontalActivity;
import org.yczbj.ycrefreshview.refresh.RefreshAndMoreActivity3;
import org.yczbj.ycrefreshview.staggered.SevenStaggeredActivity;
import org.yczbj.ycrefreshview.staggered.StageredLoadMoreActivity;
import org.yczbj.ycrefreshview.sticky.SixStickyNormalActivity;
import org.yczbj.ycrefreshview.sticky.SixStickyViewActivity;
import org.yczbj.ycrefreshview.tag.TagRecyclerViewActivity;
import org.yczbj.ycrefreshview.touchmove.NightTouchMoveActivity;
import org.yczbj.ycrefreshview.header.HeaderFooterActivity;
import org.yczbj.ycrefreshview.staggered.SevenStaggeredGridActivity;
import org.yczbj.ycrefreshview.sticky.SixStickyHeaderActivity;
import org.yczbj.ycrefreshview.delete.DeleteAndTopActivity;
import org.yczbj.ycrefreshview.insert.ThirdInsertActivity;
import org.yczbj.ycrefreshview.type.HomePageActivity;
import org.yczbj.ycrefreshview.type.TypeActivity;
import org.yczbj.ycrefreshviewlib.utils.RefreshLogUtils;

import java.io.InputStream;
import java.sql.Statement;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RefreshLogUtils.setLog(true);
        init();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void init() {
        findViewById(R.id.tv_1_1).setOnClickListener(this);
        findViewById(R.id.tv_1_2).setOnClickListener(this);
        findViewById(R.id.tv_1_3).setOnClickListener(this);
        findViewById(R.id.tv_2).setOnClickListener(this);
        findViewById(R.id.tv_3).setOnClickListener(this);
        findViewById(R.id.tv_4).setOnClickListener(this);
        findViewById(R.id.tv_5_1).setOnClickListener(this);
        findViewById(R.id.tv_6_1).setOnClickListener(this);
        findViewById(R.id.tv_6_2).setOnClickListener(this);
        findViewById(R.id.tv_6_3).setOnClickListener(this);
        findViewById(R.id.tv_7).setOnClickListener(this);
        findViewById(R.id.tv_7_2).setOnClickListener(this);
        findViewById(R.id.tv_7_3).setOnClickListener(this);
        findViewById(R.id.tv_8).setOnClickListener(this);
        findViewById(R.id.tv_9).setOnClickListener(this);
        findViewById(R.id.tv_10).setOnClickListener(this);
        findViewById(R.id.tv_11).setOnClickListener(this);
        findViewById(R.id.tv_11_2).setOnClickListener(this);
        findViewById(R.id.tv_12).setOnClickListener(this);
        findViewById(R.id.tv_13_1).setOnClickListener(this);
        findViewById(R.id.tv_13_2).setOnClickListener(this);
        findViewById(R.id.tv_14).setOnClickListener(this);
        findViewById(R.id.tv_15).setOnClickListener(this);
        findViewById(R.id.tv_16).setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_1_1:
                startActivity(new Intent(this, RefreshAndMoreActivity1.class));
                break;
            case R.id.tv_1_2:
                startActivity(new Intent(this, RefreshAndMoreActivity2.class));
                break;
            case R.id.tv_1_3:
                startActivity(new Intent(this, RefreshAndMoreActivity3.class));
                break;
            case R.id.tv_2:
                startActivity(new Intent(this, HeaderFooterActivity.class));
                break;
            case R.id.tv_3:
                startActivity(new Intent(this, ThirdInsertActivity.class));
                break;
            case R.id.tv_4:
                startActivity(new Intent(this, FourHorizontalActivity.class));
                break;
            case R.id.tv_5_1:
                startActivity(new Intent(this, FiveMultiStyleActivity.class));
                break;
            case R.id.tv_6_1:
                startActivity(new Intent(this, SixStickyHeaderActivity.class));
                break;
            case R.id.tv_6_2:
                startActivity(new Intent(this, SixStickyViewActivity.class));
                break;
            case R.id.tv_6_3:
                startActivity(new Intent(this, SixStickyNormalActivity.class));
                break;
            case R.id.tv_7:
                startActivity(new Intent(this, SevenStaggeredGridActivity.class));
                break;
            case R.id.tv_7_2:
                startActivity(new Intent(this, SevenStaggeredActivity.class));
                break;
            case R.id.tv_7_3:
                startActivity(new Intent(this, StageredLoadMoreActivity.class));
                break;
            case R.id.tv_8:
                startActivity(new Intent(this, EightCollapsingActivity.class));
                break;
            case R.id.tv_9:
                startActivity(new Intent(this, NightTouchMoveActivity.class));
                break;
            case R.id.tv_10:
                startActivity(new Intent(this, DeleteAndTopActivity.class));
                break;
            case R.id.tv_11:
                startActivity(new Intent(this, NormalRecyclerViewActivity.class));
                break;
            case R.id.tv_11_2:
                startActivity(new Intent(this, SpanRecyclerViewActivity.class));
                break;
            case R.id.tv_12:
                startActivity(new Intent(this, TypeActivity.class));
                break;
            case R.id.tv_13_1:
                startActivity(new Intent(this, LoadMoreActivity.class));
                break;
            case R.id.tv_13_2:
                startActivity(new Intent(this, LoadMoreActivity2.class));
                break;
            case R.id.tv_14:
                startActivity(new Intent(this, LayoutActivity.class));
                break;
            case R.id.tv_15:
                startActivity(new Intent(this, HomePageActivity.class));
                break;
            case R.id.tv_16:
                startActivity(new Intent(this, TagRecyclerViewActivity.class));
                break;
            default:
                break;
        }
    }


}

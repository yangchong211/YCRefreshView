package org.yczbj.ycrefreshview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import org.yczbj.ycrefreshview.eight.EightCollapsingActivity;
import org.yczbj.ycrefreshview.first.RefreshAndMoreActivity;
import org.yczbj.ycrefreshview.five.FiveMultiStyleActivity;
import org.yczbj.ycrefreshview.four.FourHorizontalActivity;
import org.yczbj.ycrefreshview.night.NightTouchMoveActivity;
import org.yczbj.ycrefreshview.second.HeaderFooterActivity;
import org.yczbj.ycrefreshview.seven.SevenStaggeredGridActivity;
import org.yczbj.ycrefreshview.six.SixStickyHeaderActivity;
import org.yczbj.ycrefreshview.three.ThirdInsertActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {
        findViewById(R.id.tv_1).setOnClickListener(this);
        findViewById(R.id.tv_2).setOnClickListener(this);
        findViewById(R.id.tv_3).setOnClickListener(this);
        findViewById(R.id.tv_4).setOnClickListener(this);
        findViewById(R.id.tv_5).setOnClickListener(this);
        findViewById(R.id.tv_6).setOnClickListener(this);
        findViewById(R.id.tv_7).setOnClickListener(this);
        findViewById(R.id.tv_8).setOnClickListener(this);
        findViewById(R.id.tv_9).setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_1:
                startActivity(new Intent(this, RefreshAndMoreActivity.class));
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
            case R.id.tv_5:
                startActivity(new Intent(this, FiveMultiStyleActivity.class));
                break;
            case R.id.tv_6:
                startActivity(new Intent(this, SixStickyHeaderActivity.class));
                break;
            case R.id.tv_7:
                startActivity(new Intent(this, SevenStaggeredGridActivity.class));
                break;
            case R.id.tv_8:
                startActivity(new Intent(this, EightCollapsingActivity.class));
                break;
            case R.id.tv_9:
                startActivity(new Intent(this, NightTouchMoveActivity.class));
                break;
        }
    }




}

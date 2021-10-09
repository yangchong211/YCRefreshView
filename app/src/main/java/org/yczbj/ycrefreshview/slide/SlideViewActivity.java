package org.yczbj.ycrefreshview.slide;

import android.graphics.Color;
import android.os.Bundle;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.yczbj.ycrefreshview.R;
import org.yczbj.ycrefreshviewlib.item.RecycleViewItemLine;

import java.util.ArrayList;

/**
 * ================================================
 * 作    者：杨充
 * 版    本：1.0
 * 创建日期：2017/6/21
 * 描    述：新芽项目的小案例
 * 修订历史：
 * ================================================
 */
public class SlideViewActivity extends AppCompatActivity implements View.OnClickListener {

    private FrameLayout ll_title_menu;
    private TextView toolbar_title;
    private TextView tv_title_right;
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private OnDeleteClickListener mDeleteListener;
    private ItemSlideAdapter adapter;
    private ArrayList<YCBean> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide_view);
        initViewId();
        initToolBar();
        initDeleteListener();
        initRecycleView();
        initListener();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if ("取消编辑".equals(tv_title_right.getText().toString())) {
            tv_title_right.setText("编辑");
            adapter.slideClose();
        }
    }

    private void initViewId() {
        ll_title_menu = (FrameLayout) findViewById(R.id.ll_title_menu);
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        tv_title_right = (TextView) findViewById(R.id.tv_title_right);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
    }


    private void initToolBar() {
        toolbar_title.setText("我的项目");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            //去除默认Title显示
            actionBar.setDisplayShowTitleEnabled(false);
        }
        tv_title_right.setVisibility(View.VISIBLE);
        tv_title_right.setText("编辑");
    }

    private void initListener() {
        ll_title_menu.setOnClickListener(this);
        tv_title_right.setOnClickListener(this);
        adapter.setOnItemClickListener(new HhItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if ("取消编辑".equals(tv_title_right.getText().toString())) {
                    tv_title_right.setText("编辑");
                    adapter.slideClose();
                }
                Toast.makeText(SlideViewActivity.this,"条目点击事件"+position,Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initDeleteListener() {
        mDeleteListener = new OnDeleteClickListener() {
            @Override
            public void onEditDeleteClick(View view, TextView textView, int position) {
                Toast.makeText(SlideViewActivity.this,"条目删除事件"+position,Toast.LENGTH_SHORT).show();
                if (position > -1) {
                    list.remove(position);
                    adapter.notifyItemRemoved(position);
                }
            }
        };
    }


    private void initRecycleView() {
        for(int a=0 ; a<20 ; a++){
            YCBean bean;
            if(a==3||a==8||a==14||a==16){
                bean = new YCBean("",2,"修改","","杨充"+a,"2000","美元","北京","你好");
            }else {
                bean = new YCBean("",2,"完成","","杨充"+a,"2000","美元","北京","你好");
            }
            list.add(bean);
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);                             // 如果Item够简单，高度是确定的，打开FixSize将提高性能。
        recyclerView.setItemAnimator(new DefaultItemAnimator());        // 设置Item默认动画，加也行，不加也行。
        final RecycleViewItemLine line = new RecycleViewItemLine(this, LinearLayout.HORIZONTAL,
                20, Color.parseColor("#f5f5f7"));
        recyclerView.addItemDecoration(line);// 添加分割线。
        adapter = new ItemSlideAdapter(this, mDeleteListener);
        recyclerView.setAdapter(adapter);
        adapter.setData(list);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_title_menu:
                finish();
                break;
            case R.id.tv_title_right:
                if ("编辑".equals(tv_title_right.getText().toString())) {
                    tv_title_right.setText("取消编辑");
                    adapter.slideOpen();

                } else if ("取消编辑".equals(tv_title_right.getText().toString())) {
                    tv_title_right.setText("编辑");
                    adapter.slideClose();
                }
                break;
            default:
                break;
        }
    }



}

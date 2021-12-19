package org.yczbj.ycrefreshview.select;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;


import com.yc.selectviewlib.SelectRecyclerView;

import org.yczbj.ycrefreshview.R;
import org.yczbj.ycrefreshviewlib.item.SpaceViewItemLine;

import java.util.ArrayList;
import java.util.List;


/**
 * <pre>
 *     @author yangchong
 *     blog  : https://github.com/yangchong211
 *     time  : 2016/03/22
 *     desc  : 关注点页面
 *     revise: https://github.com/yangchong211/YCRecycleView
 * </pre>
 */
public class SelectFollowActivity extends AppCompatActivity implements View.OnClickListener {

    FrameLayout llTitleMenu;
    TextView toolbarTitle;
    TextView tvTitleRight;
    SelectRecyclerView selectView;
    TextView tvClean;
    TextView tvStart;

    private SelectFollowAdapter adapter;
    private List<SelectPoint> lists = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_follow);
        initView();
        initListener();
        initData();
    }


    public void initView() {
        initFindViewById();
        initToolBar();
        initRecycleView();
    }

    private void initFindViewById() {
        llTitleMenu = findViewById(R.id.ll_title_menu);
        toolbarTitle = findViewById(R.id.toolbar_title);
        tvTitleRight = findViewById(R.id.tv_title_right);
        selectView = findViewById(R.id.select_view);
        tvClean = findViewById(R.id.tv_clean);
        tvStart = findViewById(R.id.tv_start);
    }


    private void initToolBar() {
        toolbarTitle.setText("关注点");
        tvTitleRight.setVisibility(View.VISIBLE);
        tvTitleRight.setText("跳过");
    }


    public void initListener() {
        llTitleMenu.setOnClickListener(this);
        tvTitleRight.setOnClickListener(this);
        tvClean.setOnClickListener(this);
        tvStart.setOnClickListener(this);
        adapter.setOnItemClickListener(new SelectFollowAdapter.OnListItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (adapter.data != null && adapter.data.size() > 0) {
                    adapter.toggleSelected(position);
                }
            }
        });
    }

    public void initData() {
        List<SelectPoint> list = new ArrayList<>();
        String[] titles = this.getResources().getStringArray(R.array.select_follow);
        for(int a=0 ; a<titles.length ; a++){
            SelectPoint selectPoint = new SelectPoint();
            selectPoint.setName(titles[a]);
            list.add(selectPoint);
        }
        refreshData(list);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_title_menu:
                break;
            case R.id.tv_title_right:
                break;
            case R.id.tv_clean:
                if(adapter!=null && adapter.data!=null){
                    adapter.clearSelected();
                }
                break;
            default:
                break;
        }
    }


    private void initRecycleView() {
        selectView.setLayoutManager(new GridLayoutManager(this, 4));
        adapter = new SelectFollowAdapter(this, lists);
        selectView.setAdapter(adapter);
        //下划线
        SpaceViewItemLine itemDecoration = new SpaceViewItemLine(10);
        itemDecoration.setPaddingEdgeSide(false);
        itemDecoration.setPaddingStart(false);
        itemDecoration.setPaddingHeaderFooter(false);
        selectView.addItemDecoration(itemDecoration);
    }


    public void refreshData(List<SelectPoint> list) {
        lists.clear();
        lists.addAll(list);
        adapter.notifyDataSetChanged();
    }


}

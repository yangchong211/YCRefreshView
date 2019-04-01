package org.yczbj.ycrefreshview.ten;

import android.annotation.SuppressLint;
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

import com.yc.cn.ycbannerlib.first.BannerView;
import com.yc.cn.ycbannerlib.first.hintview.ColorPointHintView;
import com.yc.cn.ycbannerlib.first.util.SizeUtil;

import org.yczbj.ycrefreshview.R;
import org.yczbj.ycrefreshview.data.AppUtils;
import org.yczbj.ycrefreshview.data.DataProvider;
import org.yczbj.ycrefreshview.data.PersonData;
import org.yczbj.ycrefreshview.second.BannerAdapter;
import org.yczbj.ycrefreshview.second.NarrowImageAdapter;
import org.yczbj.ycrefreshviewlib.view.YCRefreshView;
import org.yczbj.ycrefreshviewlib.inter.InterItemView;
import org.yczbj.ycrefreshviewlib.inter.OnItemChildClickListener;
import org.yczbj.ycrefreshviewlib.inter.OnLoadMoreListener;
import org.yczbj.ycrefreshviewlib.item.DividerViewItemLine;
import org.yczbj.ycrefreshviewlib.item.RecycleViewItemLine;
import org.yczbj.ycrefreshviewlib.item.SpaceViewItemLine;
import org.yczbj.ycrefreshviewlib.swipe.OnSwipeMenuListener;


public class DeleteAndTopActivity extends AppCompatActivity {

    private YCRefreshView recyclerView;
    private DeleteAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refresh_view);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setAdapter(adapter = new DeleteAdapter(this));

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        DividerViewItemLine itemDecoration = new DividerViewItemLine(
                this.getResources().getColor(R.color.color_f9f9f9),
                SizeUtil.dip2px(this,0.5f),
                SizeUtil.dip2px(this,72),0);
        itemDecoration.setDrawLastItem(true);
        itemDecoration.setDrawHeaderFooter(true);
        recyclerView.addItemDecoration(itemDecoration);

        final RecycleViewItemLine line = new RecycleViewItemLine(this, LinearLayout.HORIZONTAL,
                1, this.getResources().getColor(R.color.colorAccent));
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
        adapter.addAll(DataProvider.getPersonList(0));
        adapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(View view, int position) {
                switch (view.getId()){
                    case R.id.iv_news_image:
                        Toast.makeText(DeleteAndTopActivity.this,"点击图片了",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.tv_title:
                        Toast.makeText(DeleteAndTopActivity.this,"点击标题",Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
            }
        });

        adapter.setOnSwipeMenuListener(new OnSwipeMenuListener() {
            //删除功能
            @Override
            public void toDelete(int position) {
                if(adapter.getAllData().size()>position && position>-1){
                    //移除数据
                    adapter.getAllData().remove(position);
                    //推荐用这个，刷新adapter
                    adapter.notifyItemRemoved(position);
                }
            }

            //置顶功能
            @Override
            public void toTop(int position) {
                //先移除那个位置的数据，然后将其添加到索引为0的位置，然后刷新数据
                if (position > 0 && adapter.getAllData().size()>position) {
                    PersonData person = adapter.getAllData().get(position);
                    adapter.getAllData().remove(person);
                    adapter.notifyItemInserted(0);
                    adapter.getAllData().add(0, person);
                    adapter.notifyItemRemoved(position + 1);
                    if (linearLayoutManager.findFirstVisibleItemPosition() == 0) {
                        recyclerView.scrollToPosition(0);
                    }
                }
            }
        });
    }




    private void initHeader() {
        adapter.addHeader(new InterItemView() {
            @Override
            public View onCreateView(ViewGroup parent) {
                BannerView header = new BannerView(DeleteAndTopActivity.this);
                header.setHintView(new ColorPointHintView(DeleteAndTopActivity.this, Color.YELLOW,Color.GRAY));
                header.setHintPadding(0, 0, 0, (int) AppUtils.convertDpToPixel(8, DeleteAndTopActivity.this));
                header.setPlayDelay(2000);
                header.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) AppUtils.convertDpToPixel(200, DeleteAndTopActivity.this)));
                header.setAdapter(new BannerAdapter(DeleteAndTopActivity.this));
                return header;
            }

            @Override
            public void onBindView(View headerView) {

            }
        });
        adapter.addHeader(new InterItemView() {
            @Override
            public View onCreateView(ViewGroup parent) {
                View inflate = LayoutInflater.from(DeleteAndTopActivity.this).inflate(R.layout.header_view, null);
                return inflate;
            }

            @Override
            public void onBindView(View headerView) {

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
                recyclerView.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,(int) AppUtils.convertDpToPixel(300, DeleteAndTopActivity.this)));
                final NarrowImageAdapter adapter;
                recyclerView.setAdapter(adapter = new NarrowImageAdapter(parent.getContext()));
                recyclerView.setLayoutManager(new LinearLayoutManager(parent.getContext(), LinearLayoutManager.HORIZONTAL,false));


                recyclerView.addItemDecoration(new SpaceViewItemLine((int) AppUtils.convertDpToPixel(8,parent.getContext())));

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
                TextView tv = new TextView(DeleteAndTopActivity.this);
                tv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) AppUtils.convertDpToPixel(56,DeleteAndTopActivity.this)));
                tv.setGravity(Gravity.CENTER);
                tv.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
                tv.setText("(-_-)/~~~死宅真是恶心");
                return tv;
            }

            @Override
            public void onBindView(View headerView) {

            }
        });
    }


}

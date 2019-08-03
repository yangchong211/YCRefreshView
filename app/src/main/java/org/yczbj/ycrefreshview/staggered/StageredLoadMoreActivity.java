package org.yczbj.ycrefreshview.staggered;

import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.yc.cn.ycbannerlib.LibUtils;

import org.yczbj.ycrefreshview.R;
import org.yczbj.ycrefreshview.data.DataProvider;
import org.yczbj.ycrefreshview.data.PersonData;
import org.yczbj.ycrefreshview.load.LoadMoreAdapter2;
import org.yczbj.ycrefreshviewlib.inter.OnItemClickListener;
import org.yczbj.ycrefreshviewlib.item.DividerViewItemLine;
import org.yczbj.ycrefreshviewlib.item.SpaceViewItemLine;
import org.yczbj.ycrefreshviewlib.utils.RefreshLogUtils;

import java.util.List;

/**
 * @author yc
 */
public class StageredLoadMoreActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private StageredLoadMoreAdapter adapter;
    private Handler handler = new Handler();
    private int lastVisibleItem = 0;
    private final int PAGE_COUNT = 10;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);
        initRecyclerView();
        initData();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (handler!=null){
            handler.removeCallbacksAndMessages(null);
            handler = null;
        }
    }

    private void initRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView);
        final StaggeredGridLayoutManager staggeredGridLayoutManager =
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        /*recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view,
                                       @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                int position = parent.getChildAdapterPosition(view);
                int spanCount = 0;
                int spanIndex = 0;
                RecyclerView.Adapter adapter = parent.getAdapter();
                RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
                if (adapter==null || layoutManager==null){
                    return;
                }
                if (layoutManager instanceof StaggeredGridLayoutManager){
                    spanCount = ((StaggeredGridLayoutManager) layoutManager).getSpanCount();
                    spanIndex = ((StaggeredGridLayoutManager.LayoutParams) view.getLayoutParams()).getSpanIndex();
                }
                //普通Item的尺寸
                //TODO 会出现错位的问题
                int itemCount = adapter.getItemCount();
                int childCount = layoutManager.getChildCount();
                RefreshLogUtils.d("SpaceViewItemLine--count--"+itemCount + "-----"+childCount+"---索引--"+position+"---"+spanIndex);
                if (position<itemCount && spanCount==2) {
                    if (childCount % 2 == 0){
                        //这个是右边item
                        outRect.left = 5;
                        outRect.right = 20;
                    } else {
                        //这个是左边item
                        outRect.left = 20;
                        outRect.right = 5;
                    }
                    if (childCount==1 || childCount==2){
                        outRect.top = 0;
                    } else {
                        outRect.top = 20;
                    }
                    RefreshLogUtils.d("SpaceViewItemLine--间距--"+childCount+"----"+outRect.left+"-----"+outRect.right);
                }
            }
        });*/
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view,
                                       @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                int position = parent.getChildAdapterPosition(view);
                int spanCount = 0;
                int spanIndex = 0;
                RecyclerView.Adapter adapter = parent.getAdapter();
                RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
                if (adapter==null || layoutManager==null){
                    return;
                }
                if (layoutManager instanceof StaggeredGridLayoutManager){
                    spanCount = ((StaggeredGridLayoutManager) layoutManager).getSpanCount();
                    spanIndex = ((StaggeredGridLayoutManager.LayoutParams) view.getLayoutParams()).getSpanIndex();
                }
                //普通Item的尺寸
                //TODO 会出现错位的问题
                int itemCount = adapter.getItemCount();
                int childCount = layoutManager.getChildCount();
                RefreshLogUtils.d("SpaceViewItemLine--count--"+itemCount + "-----"+childCount+"---索引--"+position+"---"+spanIndex);
                if (position<itemCount && spanCount==2) {
                    if (spanIndex != GridLayoutManager.LayoutParams.INVALID_SPAN_ID) {
                        //getSpanIndex方法不管控件高度如何，始终都是左右左右返回index
                        if (spanIndex % 2 == 0) {
                            //这个是左边item
                            outRect.left = 20;
                            outRect.right = 5;
                        } else {
                            //这个是右边item
                            outRect.left = 5;
                            outRect.right = 20;
                        }
                        if (childCount==1 || childCount==2){
                            outRect.top = 0;
                        } else {
                            outRect.top = 20;
                        }
                    }
                    //outRect.top = space;
                    RefreshLogUtils.d("SpaceViewItemLine--间距--"+spanIndex+"----"+outRect.left+"-----"+outRect.right);
                }
            }
        });
        adapter = new StageredLoadMoreAdapter(this,false);
        recyclerView.setAdapter(adapter);
        // 实现上拉加载重要步骤，设置滑动监听器，RecyclerView自带的ScrollListener
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                // 在newState为滑到底部时
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    // 如果没有隐藏footView，那么最后一个条目的位置就比我们的getItemCount少1，自己可以算一下
                    if (!adapter.isFadeTips() && lastVisibleItem + 1 == adapter.getItemCount()) {
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                updateRecyclerView(adapter.getRealLastPosition(),
                                        adapter.getRealLastPosition() + PAGE_COUNT);
                            }
                        }, 2500);
                    }

                    // 如果隐藏了提示条，我们又上拉加载时，那么最后一个条目就要比getItemCount要少2
                    if (adapter.isFadeTips() && lastVisibleItem + 2 == adapter.getItemCount()) {
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                updateRecyclerView(adapter.getRealLastPosition(),
                                        adapter.getRealLastPosition() + PAGE_COUNT);
                            }
                        }, 2500);
                    }
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                // 在滑动完成后，拿到最后一个可见的item的位置
                int positions[] = staggeredGridLayoutManager.findLastVisibleItemPositions(null);
                for(int pos : positions){
                    if(pos > lastVisibleItem){
                        lastVisibleItem = pos;//得到最后一个可见的item的position
                    }
                }
            }
        });
    }


    private void initData() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                adapter.setData(DataProvider.getPersonList(16));
            }
        }, 50);
    }

    private void updateRecyclerView(int fromIndex, int toIndex) {
        List<PersonData> newDatas = DataProvider.getPersonList(0);
        if (newDatas.size() > 0) {
            adapter.updateList(newDatas, true);
        } else {
            adapter.updateList(null, false);
        }
    }


}

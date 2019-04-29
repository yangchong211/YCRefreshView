package org.yczbj.ycrefreshview.layout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.GridLayoutHelper;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.yc.cn.ycbannerlib.banner.BannerView;

import org.yczbj.ycrefreshview.R;
import org.yczbj.ycrefreshview.app.Constant;
import org.yczbj.ycrefreshview.data.DataProvider;
import org.yczbj.ycrefreshview.header.BannerAdapter;
import org.yczbj.ycrefreshview.horizontal.NarrowImageAdapter;
import org.yczbj.ycrefreshviewlib.holder.BaseViewHolder;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class LayoutActivity extends AppCompatActivity {


    private VirtualLayoutManager layoutManager;
    /**
     * 存放各个模块的适配器
     */
    private List<DelegateAdapter.Adapter> mAdapters;
    private DelegateAdapter delegateAdapter;
    private RecyclerView recyclerView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);
        recyclerView = findViewById(R.id.recyclerView);
        initVLayout();
    }


    private void initVLayout() {
        mAdapters = new LinkedList<>();
        //初始化
        //创建VirtualLayoutManager对象
        layoutManager = new VirtualLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //设置回收复用池大小，（如果一屏内相同类型的 View 个数比较多，需要设置一个合适的大小，防止来回滚动时重新创建 View）
        RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
        recyclerView.setRecycledViewPool(viewPool);
        viewPool.setMaxRecycledViews(0, 20);

        //设置适配器
        delegateAdapter = new DelegateAdapter(layoutManager, true);
        recyclerView.setAdapter(delegateAdapter);

        //自定义各种不同适配器
        initAllTypeView();
        //设置适配器
        delegateAdapter.setAdapters(mAdapters);
    }

    /**
     * 添加不同类型数据布局
     */
    private void initAllTypeView() {
        initBannerView();
        initListFirstView();
        initRecyclerView();
        initFirstAdView();
        initListSecondView();
        initSecondAdView();
        initListThirdView();
        initListFourView();
        initListFiveView();
        initListSixView();
    }



    private void initBannerView() {
        final ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("http://bpic.wotucdn.com/11/66/23/55bOOOPIC3c_1024.jpg!/fw/780/quality/90/unsharp/true/compress/true/watermark/url/L2xvZ28ud2F0ZXIudjIucG5n/repeat/true");
        arrayList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1505470629546&di=194a9a92bfcb7754c5e4d19ff1515355&imgtype=0&src=http%3A%2F%2Fpics.jiancai.com%2Fimgextra%2Fimg01%2F656928666%2Fi1%2FT2_IffXdxaXXXXXXXX_%2521%2521656928666.jpg");
        arrayList.add("http://bpic.wotucdn.com/11/66/23/55bOOOPIC3c_1024.jpg!/fw/780/quality/90/unsharp/true/compress/true/watermark/url/L2xvZ28ud2F0ZXIudjIucG5n/repeat/true");
        arrayList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1505470629546&di=194a9a92bfcb7754c5e4d19ff1515355&imgtype=0&src=http%3A%2F%2Fpics.jiancai.com%2Fimgextra%2Fimg01%2F656928666%2Fi1%2FT2_IffXdxaXXXXXXXX_%2521%2521656928666.jpg");
        //banner
        //banner
        BaseDelegateAdapter adapter = new BaseDelegateAdapter(LayoutActivity.this, new LinearLayoutHelper(), R.layout.view_vlayout_banner, 1, Constant.viewType.typeBanner) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                // 绑定数据
                BannerView mBanner = (BannerView) holder.getView(R.id.banner);
                mBanner.setHintGravity(1);
                mBanner.setAnimationDuration(1000);
                mBanner.setPlayDelay(2000);
                mBanner.setAdapter(new BannerAdapter(LayoutActivity.this));
            }
        };
        mAdapters.add(adapter);
    }



    private void initListFirstView() {
        initTitleView(1);
        GridLayoutHelper gridLayoutHelper = new GridLayoutHelper(2);
        gridLayoutHelper.setPadding(0, 16, 0, 16);
        // 控制子元素之间的垂直间距
        gridLayoutHelper.setVGap(16);
        // 控制子元素之间的水平间距
        gridLayoutHelper.setHGap(0);
        gridLayoutHelper.setBgColor(Color.WHITE);
        BaseDelegateAdapter adapter = new BaseDelegateAdapter(LayoutActivity.this, gridLayoutHelper, R.layout.view_vlayout_grid, 4, Constant.viewType.typeGv) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                final ImageView ivImage = (ImageView) holder.getView(R.id.iv_image);
                ivImage.setBackgroundResource(R.drawable.bg_small_tree_min);
                //String image = ConstantImage.homePageConcentration[position];
                //ImageUtil.loadImgByPicasso(LayoutActivity.this,ConstantImage.homePageConcentration[position],R.drawable.image_default,ivImage);
            }
        };
        mAdapters.add(adapter);
        initMoreView(1);
    }

    private void initRecyclerView() {
        initTitleView(4);
        BaseDelegateAdapter adapter = new BaseDelegateAdapter(LayoutActivity.this, new LinearLayoutHelper(),
                R.layout.view_vlayout_recycler, 1, Constant.viewType.typeRecycler) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                final RecyclerView recycler = (RecyclerView) holder.getView(R.id.recyclerView);
                recycler.setLayoutManager(new LinearLayoutManager(LayoutActivity.this,
                        LinearLayoutManager.HORIZONTAL,false));
                NarrowImageAdapter narrowImageAdapter = new NarrowImageAdapter(LayoutActivity.this);
                recycler.setAdapter(narrowImageAdapter);
                narrowImageAdapter.addAll(DataProvider.getNarrowImage(0));
            }
        };
        mAdapters.add(adapter);
    }


    private void initFirstAdView() {
        initTitleView(6);
        BaseDelegateAdapter adAdapter = new BaseDelegateAdapter(LayoutActivity.this, new LinearLayoutHelper(), R.layout.view_vlayout_ad, 1, Constant.viewType.typeAd) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
            }
        };
        mAdapters.add(adAdapter);
    }


    private void initListSecondView() {
        initTitleView(2);
        LinearLayoutHelper linearLayoutHelper = new LinearLayoutHelper();
        linearLayoutHelper.setAspectRatio(4.0f);
        linearLayoutHelper.setDividerHeight(5);
        linearLayoutHelper.setMargin(0, 0, 0, 0);
        linearLayoutHelper.setPadding(0, 0, 0, 10);
        BaseDelegateAdapter adapter = new BaseDelegateAdapter(LayoutActivity.this, linearLayoutHelper, R.layout.view_vlayout_news, 3, Constant.viewType.typeList2) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
            }
        };
        mAdapters.add(adapter);
        initMoreView(2);
    }


    private void initSecondAdView() {
        BaseDelegateAdapter adAdapter = new BaseDelegateAdapter(LayoutActivity.this, new LinearLayoutHelper(), R.layout.view_vlayout_ad, 1, Constant.viewType.typeAd2) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                holder.getView(R.id.iv_image_ad).setBackgroundResource(R.drawable.bg_small_leaves_min);

            }
        };
        mAdapters.add(adAdapter);
    }


    private void initListThirdView() {
        initTitleView(3);
        GridLayoutHelper gridLayoutHelper = new GridLayoutHelper(2);
        gridLayoutHelper.setPadding(0, 16, 0, 16);
        // 控制子元素之间的垂直间距
        gridLayoutHelper.setVGap(16);
        // 控制子元素之间的水平间距
        gridLayoutHelper.setHGap(0);
        gridLayoutHelper.setBgColor(Color.WHITE);
        BaseDelegateAdapter adapter = new BaseDelegateAdapter(LayoutActivity.this,
                gridLayoutHelper, R.layout.view_vlayout_grid, 2, Constant.viewType.typeGv3) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                ImageView ivImage = (ImageView) holder.getView(R.id.iv_image);
                ivImage.setImageResource(R.drawable.bg_small_autumn_tree_min);
            }
        };
        mAdapters.add(adapter);
        initMoreView(3);
    }


    private void initListFourView() {
        initTitleView(4);
        LinearLayoutHelper linearLayoutHelper = new LinearLayoutHelper();
        linearLayoutHelper.setAspectRatio(4.0f);
        linearLayoutHelper.setDividerHeight(5);
        linearLayoutHelper.setMargin(0, 0, 0, 0);
        linearLayoutHelper.setPadding(0, 0, 0, 10);
        BaseDelegateAdapter adapter = new BaseDelegateAdapter(LayoutActivity.this, linearLayoutHelper, R.layout.view_vlayout_news, 3, Constant.viewType.typeList4) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);

            }
        };
        mAdapters.add(adapter);
        initMoreView(4);
    }


    private void initListFiveView() {
        initTitleView(5);
        GridLayoutHelper gridLayoutHelper = new GridLayoutHelper(3);
        gridLayoutHelper.setPadding(0, 16, 0, 16);
        // 控制子元素之间的垂直间距
        gridLayoutHelper.setVGap(16);
        // 控制子元素之间的水平间距
        gridLayoutHelper.setHGap(0);
        gridLayoutHelper.setBgColor(Color.WHITE);
        BaseDelegateAdapter adapter = new BaseDelegateAdapter(LayoutActivity.this, gridLayoutHelper, R.layout.view_vlayout_grid, 6, Constant.viewType.typeGvBottom) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                ImageView ivImage = (ImageView) holder.getView(R.id.iv_image);
                ivImage.setImageResource(R.drawable.bg_small_leaves_min);
            }
        };
        mAdapters.add(adapter);
        initMoreView(4);
    }


    private void initListSixView() {
        initTitleView(6);
        LinearLayoutHelper linearLayoutHelper = new LinearLayoutHelper();
        linearLayoutHelper.setAspectRatio(4.0f);
        linearLayoutHelper.setDividerHeight(5);
        linearLayoutHelper.setMargin(0, 0, 0, 0);
        linearLayoutHelper.setPadding(0, 0, 0, 10);
        BaseDelegateAdapter adapter = new BaseDelegateAdapter(LayoutActivity.this, linearLayoutHelper, R.layout.view_vlayout_news, 3, Constant.viewType.typeList5) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);

            }
        };
        mAdapters.add(adapter);
        initMoreView(6);
    }


    private void initTitleView(final int type) {
        BaseDelegateAdapter titleAdapter = new BaseDelegateAdapter(LayoutActivity.this, new LinearLayoutHelper(), R.layout.view_vlayout_title, 1, Constant.viewType.typeTitle) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                switch (type) {
                    case 1:
                        holder.setText(R.id.tv_title, "为你精选");
                        break;
                    case 2:
                        holder.setText(R.id.tv_title, "推广专区");
                        break;
                    case 3:
                        holder.setText(R.id.tv_title, "行业动态");
                        break;
                    case 4:
                        holder.setText(R.id.tv_title, "趋势分析");
                        break;
                    case 5:
                        holder.setText(R.id.tv_title, "大牛分享");
                        break;
                    case 6:
                        holder.setText(R.id.tv_title, "潇湘剑雨");
                        break;
                    default:
                        holder.setText(R.id.tv_title, "这个是标题");
                        break;
                }
                holder.getView(R.id.tv_change).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (type) {
                            case 1:
                                showToast(LayoutActivity.this, "刷新为你精选数据");
                                break;
                            case 2:
                                showToast(LayoutActivity.this, "刷新推广专区数据");
                                break;
                            case 3:
                                showToast(LayoutActivity.this, "刷新行业动态数据");
                                break;
                            case 4:
                                showToast(LayoutActivity.this, "刷新趋势分析数据");
                                break;
                            case 5:
                                showToast(LayoutActivity.this, "刷新大牛分享数据");
                                break;
                            case 6:
                                showToast(LayoutActivity.this, "刷新潇湘剑雨的数据");
                                break;
                            default:
                                showToast(LayoutActivity.this, "刷新XXXX数据");
                                break;
                        }
                    }
                });
            }
        };
        mAdapters.add(titleAdapter);
    }


    private void initMoreView(final int type) {
        BaseDelegateAdapter moreAdapter = new BaseDelegateAdapter(LayoutActivity.this, new LinearLayoutHelper(), R.layout.view_vlayout_more, 1, Constant.viewType.typeMore) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                switch (type) {
                    case 1:
                        holder.setText(R.id.tv_more, "查看更多");
                        break;
                    case 2:
                        holder.setText(R.id.tv_more, "查看更多");
                        break;
                    case 3:
                        holder.setText(R.id.tv_more, "查看更多");
                        break;
                    case 4:
                        holder.setText(R.id.tv_more, "查看更多");
                        break;
                    case 5:
                        holder.setText(R.id.tv_more, "查看更多");
                        break;
                    case 6:
                        holder.setText(R.id.tv_more, "没有更多数据");
                        break;
                    default:
                        holder.setText(R.id.tv_more, "查看更多");
                        break;
                }
                holder.getView(R.id.tv_more).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (type) {
                            case 1:
                                showToast(LayoutActivity.this, "跳转为你精选数据");
                                break;
                            case 2:
                                showToast(LayoutActivity.this, "跳转推广专区数据");
                                break;
                            case 3:
                                showToast(LayoutActivity.this, "跳转行业动态数据");
                                break;
                            case 4:
                                showToast(LayoutActivity.this, "跳转趋势分析数据");
                                break;
                            case 5:
                                showToast(LayoutActivity.this, "跳转大牛分享数据");
                                break;
                            default:
                                showToast(LayoutActivity.this, "跳转XXXX数据");
                                break;
                        }
                    }
                });
            }
        };
        mAdapters.add(moreAdapter);
    }


    private static Toast toast;
    @SuppressLint("ShowToast")
    public static void showToast(Context context, String content) {
        if (toast == null) {
            toast = Toast.makeText(context.getApplicationContext(), content, Toast.LENGTH_SHORT);
        } else {
            toast.setText(content);
        }
        toast.show();
    }


}

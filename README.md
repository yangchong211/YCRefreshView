# YCRefreshView
自定义支持上拉加载更多，下拉刷新，支持自由切换状态【加载中，加载成功，加载失败，没网络等状态】的控件。
具体使用方法，可以直接参考demo。

## 目录介绍
- 1.关于EasyRecycleView开源库
- 2.关于该开源库的思路
- 3.如何使用
- 4.该库的优点
- 5.实现效果
- 6.版本更新说明
- 7.其他

###  1.关于界面切换状态介绍
- **关于EasyRecycleView开源库**
- 关于EasyRecycleView库的开源地址，可以先看看作者的思路，十分不错：https://github.com/Jude95/EasyRecyclerView
- 由于最开始接触项目的时候，是同事选用的这款库，我们这边的项目目前用这个库已经有18个月多了，一起发现了一些问题：
- 1.当数据不足的时候，会走两遍setMore方法
- 2.当瀑布流显示大小规格不同的图片，上拉加载更多有问题
- 3.当一个页面有自定义头部【包含轮播图或者其他控件】，还有list数据的时候。如果设置加载中状态，则是整个页面显示加载中。而我们的产品要求，只要局部页面刷新。
- 4.如果想要实现那种复杂的页面【例如，不同类型list，类似商城那种】，目前做法是在继承你封装的adapter处理不同类型item的布局，将不同类型实体类汇总到一个实体类。但是随着后来ui更改，以及添加或删除功能，而且RecyclerArrayAdapter<实体类>，由于不同item的返回实体类字段不一样，而这个时候便很难优雅处理多种类型。所以我想在源代码基础上做些更改
- 5.在swipe包中的SwipeRefreshLayout类，可以直接使用谷歌官方的SwipeRefreshLayout类，这样swipe包中那几个类都可以去掉。不得不说，在之前官方没有发布刷新控件时，就能够写出该自定义控件。水平还是相当高，大神，佩服！！
- 6.安装阿里编码规约插件后，黄色警告和提示部分实在太多
- 7.在有header时，如果update会导致数据错乱
- 8.与ScrollView嵌套会有滑动冲突，即使通过自定义ScrollView避免滑动冲突。但是EasyRecycleView外层还要通过嵌套RelativeLayout才展示数据，这样会导致过渡绘制
- 9.在BaseViewHolder中为了减少findviewbyid，可以复用，比如：
```
    // SparseArray 比 HashMap 更省内存，在某些条件下性能更好，只能存储 key 为 int 类型的数据，
    // 用来存放 View 以减少 findViewById 的次数
    private SparseArray<View> viewSparseArray;

    public BaseViewHolder(ViewGroup parent, @LayoutRes int res) {
        super(LayoutInflater.from(parent.getContext()).inflate(res, parent, false));
        if(viewSparseArray==null){
            viewSparseArray = new SparseArray<>();
        }
    }

    /**
     * 根据 ID 来获取 View
     * @param viewId viewID
     * @param <T>    泛型
     * @return 将结果强转为 View 或 View 的子类型
     */
    @SuppressWarnings("unchecked")
    protected <T extends View> T getView(int viewId) {
        // 先从缓存中找，找打的话则直接返回
        // 如果找不到则 findViewById ，再把结果存入缓存中
        View view = viewSparseArray.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            viewSparseArray.put(viewId, view);
        }
        return (T) view;
    }
```

###  2.关于该开源库的思路
- 2.1 先来看看布局



###  3.如何使用
- 3.1 首先在集成：compile 'org.yczbj:YCRefreshViewLib:1.4'
- 3.2 在布局中：
``` 
  <org.yczbj.ycrefreshviewlib.YCRefreshView
        xmlns:app="http://schemas.android.com/apk/res-auto"
        android:id="@+id/recyclerView"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:layout_progress="@layout/view_custom_loading_data"
        app:layout_empty="@layout/view_custom_empty_data"
        app:layout_error="@layout/view_custom_data_error"/>
``` 
- 3.3 在代码中
``` 
recyclerView.setLayoutManager(new FullyGridLayoutManager(activity, 3));
        adapter = new DouBookAdapter(activity);
        recyclerView.setAdapter(adapter);
        //加载更多
        adapter.setMore(R.layout.view_recycle_more, new RecyclerArrayAdapter.OnMoreListener() {
            @Override
            public void onMoreShow() {
                if (NetworkUtils.isConnected()) {
                    if (adapter.getAllData().size() > 0) {
                        getTopMovieData(mType, adapter.getAllData().size(), adapter.getAllData().size() + 21);
                    } else {
                        adapter.pauseMore();
                    }
                } else {
                    adapter.pauseMore();
                    Toast.makeText(activity, "网络不可用", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onMoreClick() {

            }
        });

        //设置没有数据
        adapter.setNoMore(R.layout.view_recycle_no_more, new RecyclerArrayAdapter.OnNoMoreListener() {
            @Override
            public void onNoMoreShow() {
                if (NetworkUtils.isConnected()) {
                    adapter.resumeMore();
                } else {
                    Toast.makeText(activity, "网络不可用", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNoMoreClick() {
                if (NetworkUtils.isConnected()) {
                    adapter.resumeMore();
                } else {
                    Toast.makeText(activity, "网络不可用", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //设置错误
        adapter.setError(R.layout.view_recycle_error, new RecyclerArrayAdapter.OnErrorListener() {
            @Override
            public void onErrorShow() {
                adapter.resumeMore();
            }

            @Override
            public void onErrorClick() {
                adapter.resumeMore();
            }
        });

        //刷新
        recyclerView.setRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (NetworkUtils.isConnected()) {
                    getTopMovieData(mType , 0 , 30);
                } else {
                    recyclerView.setRefreshing(false);
                    Toast.makeText(activity, "网络不可用", Toast.LENGTH_SHORT).show();
                }
            }
        });
``` 



###  4.关于该状态切换工具优点分析
- 4.1 不仅可以实现上拉加载，下拉刷新。还可以根据获取数据来切换页面的状态，可以自定义状态页面，如下所示：

``` 
//设置加载中
recyclerView.showProgress();

//设置有数据展示
recyclerView.showRecycler();

//设置为空
recyclerView.setEmptyView(R.layout.view_custom_empty_data);
recyclerView.showEmpty();

//设置错误
recyclerView.setErrorView(R.layout.view_custom_data_error);
recyclerView.showError();
LinearLayout ll_error_view = (LinearLayout) recyclerView.findViewById(R.id.ll_error_view);
ll_error_view.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        
    }
});

//设置网络错误
recyclerView.setErrorView(R.layout.view_custom_network_error);
recyclerView.showError();
LinearLayout ll_set_network = (LinearLayout) recyclerView.findViewById(R.id.ll_set_network);
ll_set_network.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        if(NetworkUtils.isConnected()){
            initData();
        }else {
            Intent intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
            startActivity(intent);
        }
    }
});

``` 


###  5.实现效果


###  6.版本更新说明
- v1.0 更新于2017年4月22日
- v1.1 更新于2017年8月9日
- v1.4 更新于2018年1月5日

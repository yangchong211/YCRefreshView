#### 目录介绍
- 1.复杂页面库介绍
- 2.本库优势亮点
    - 2.1 支持多种状态切换管理
    - 2.2 支持添加多个header和footer
    - 2.3 支持侧滑功能和拖拽移动
    - 2.4 其他亮点介绍
- 3.如何使用介绍
    - 3.1 最基础的使用
    - 3.2 添加下拉刷新和加载更多监听
    - 3.3 添加header和footer操作
    - 3.4 设置数据和刷新
    - 3.5 设置adapter
    - 3.6 设置条目点击事件
    - 3.7 设置侧滑删除功能[QQ侧滑删除]
    - 3.8 轻量级拖拽排序与滑动删除
- 4.关于状态切换
    - 4.1 关于布局内容
    - 4.2 关于实现思路
    - 4.3 关于状态切换api调用
    - 4.4 关于自定义状态布局
    - 4.5 关于自定义布局交互事件处理
- 5.常用api介绍
    - 5.1 状态切换方法说明
    - 5.2 viewHolder方法说明
    - 5.3 adapter方法说明
    - 5.4 分割线方法说明
    - 5.5 swipe侧滑方法说明
    - 5.6 其他api说明
- 6.recyclerView的wiki文档【更新中】
    - 6.1 封装库部分思路介绍
    - 6.2 优化处理逻辑介绍
    - 6.3 recyclerView相关类
    - 6.4 recyclerView滑动冲突
    - 6.5 recyclerView缓存机制
    - **6.5 recyclerView相关博客(25篇)**
- 7.实现效果展示
    - 7.1 下拉刷新，上拉加载
    - 7.2 添加多个headerView和FooterView
    - 7.3 横向滑动
    - 7.4 粘贴头部
    - 7.5 与coordinatorLayout结合
    - 7.6 瀑布流效果
- 8.版本更新说明
- 9.参考资料说明
- 10.其他内容介绍



### 0.案例演示
- [Apk下载地址](https://github.com/yangchong211/YCRefreshView/tree/master/apk)
- ![image](https://github.com/yangchong211/YCRefreshView/blob/master/image/gif1.gif)
- ![image](https://github.com/yangchong211/YCRefreshView/blob/master/image/gif2.gif)
- ![image](https://github.com/yangchong211/YCRefreshView/blob/master/image/gif3.gif)
- ![image](https://github.com/yangchong211/YCRefreshView/blob/master/image/gif4.gif)




### 1.复杂页面库介绍
- **自定义支持上拉加载更多【加载中，加载失败[比如没有更多数据]，加载异常[无网络]，加载成功等多种状态】，下拉刷新，可以实现复杂的状态页面，支持自由切换状态【加载中，加载成功，加载失败，没网络等状态】的控件，拓展功能[支持长按拖拽，侧滑删除]可以选择性添加。具体使用方法，可以直接参考demo案例。**
- 支持复杂type页面，例如添加自定义头部HeaderView和底部布局FooterView，支持横向滑动list，还可以支持粘贴头部list[类似微信好友分组]，支持不规则瀑布流效果，支持侧滑删除功能。


### 2.本库优势亮点
#### 2.1 支持多种状态切换管理
- 支持在布局中或者代码设置自定义不同状态的view，一行代码既可以切换不同的状态，十分方便。
- 针对自定义状态view或者layout，可以实现交互功能，比如当切换到网络异常的页面，可以点击页面控件取刷新数据或者跳转设置网络页面



#### 2.2 支持添加多个header和footer
- 支持添加多个自定义header头部布局，可以自定义footer底部布局。十分方便实现复杂type的布局页面，结构上层次分明，便于维护。
- 支持复杂界面使用，比如有的页面包含有轮播图，按钮组合，横向滑动list，还有复杂list，那么用这个控件就可以搞定。



#### 2.3 支持侧滑功能和拖拽移动
- 轻量级侧滑删除菜单，直接嵌套item布局即可使用，使用十分简单
- 通过自定义ItemTouchHelper实现RecyclerView条目Item拖拽排序，只是设置是否拖拽，设置拖拽item的背景颜色，优化了拖拽效果，比如在拖拽过程中设置item的缩放和渐变效果



#### 2.4 其他亮点介绍
- 支持上拉加载，下拉刷新。当上拉加载更多失败或者异常时，可以设置自定义加载更多失败或者异常布局(比如没有网络时的场景)，同时点击该异常或者失败布局可以恢复加载更多数据；当上拉加载更多没有更多数据时，可以设置自定义加载更多无数据布局。
- 可以设置上拉加载更多后自动加载下一页数据，也可以上拉加载更多后手动触发加载下一页数据。在上拉加载更多时，可以设置加载更多的布局，支持加载监听。
- 支持粘贴头部的需求效果，这种效果类似微信好友分组的那种功能界面。
- 支持插入【插入指定索引】，更新【更新指定索引或者data数据】或者删除某条数据，支持删除所有数据。同时在多线程条件下，添加了锁机制避免数据错乱！
- 支持横向滑动list效果，支持瀑布流的效果，还支持与CoordinatorLayout结合实现炫酷的效果。这种效果特别不错……
- 已经用于实际开发项目投资界，新芽，沙丘大学中……且经过近三年时间的迭代与维护，持续更新维护中！



### 3.如何使用介绍
#### 3.1 最基础的使用
- 首先在集成：
    ``` java
    //recyclerView封装库
    implementation 'com.github.yangchong211.YCRefreshView:RefreshViewLib:3.0.2'
    //整体item侧滑库
    implementation 'com.github.yangchong211.YCRefreshView:SlideViewLib:3.0.2'
    //仿汽车之家画廊库
    implementation 'com.github.yangchong211.YCRefreshView:PhotoCoverLib:3.0.2'
    //标签多选单选库
    implementation 'com.github.yangchong211.YCRefreshView:SelectViewLib:3.0.2'
    //之前简单型adapter
    implementation 'com.github.yangchong211.YCRefreshView:EastAdapterLib:3.0.2'
    ```
- 在布局中
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
- 在代码中，初始化recyclerView
    ```
    adapter = new PersonAdapter(this);
    recyclerView.setAdapter(adapter);
    final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
    recyclerView.setLayoutManager(linearLayoutManager);
    adapter.addAll(data);
    ```
- 在代码中，创建adapter实现RecyclerArrayAdapter<T>
    ```
    public class PersonAdapter extends RecyclerArrayAdapter<PersonData> {

        public PersonAdapter(Context context) {
            super(context);
        }

        @Override
        public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
            return new PersonViewHolder(parent);
        }

        public class PersonViewHolder extends BaseViewHolder<PersonData> {

            private ImageView iv_news_image;

            PersonViewHolder(ViewGroup parent) {
                super(parent, R.layout.item_news);
                iv_news_image = getView(R.id.iv_news_image);
            }

            @Override
            public void setData(final PersonData person){

            }
        }
    }
    ```




#### 3.2 添加下拉刷新和加载更多监听
- 下拉刷新监听操作
    ```
    //设置刷新listener
    recyclerView.setRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            //刷新操作
        }
    });
    //设置是否刷新
    recyclerView.setRefreshing(false);
    //设置刷新颜色
    recyclerView.setRefreshingColorResources(R.color.colorAccent);
    ```
- 上拉加载更多监听操作
    - 第一种情况，上拉加载更多后自动加载下一页数据
    ```
    //设置上拉加载更多时布局，以及监听事件
    adapter.setMore(R.layout.view_more, new OnLoadMoreListener() {
        @Override
        public void onLoadMore() {
            //可以做请求下一页操作
        }
    });
    ```
    - 第二种情况，上拉加载更多后手动触发加载下一页数据
    ```
    adapter.setMore(R.layout.view_more2, new OnMoreListener() {
        @Override
        public void onMoreShow() {
            //不做处理
        }

        @Override
        public void onMoreClick() {
            //点击触发加载下一页数据
        }
    });
    ```
- 在上拉加载更多时，可能出现没有更多数据，或者上拉加载失败，该如何处理呢？
    ```
    //设置上拉加载没有更多数据监听
    adapter.setNoMore(R.layout.view_no_more, new OnNoMoreListener() {
        @Override
        public void onNoMoreShow() {
            //上拉加载，没有更多数据展示，这个方法可以暂停或者停止加载数据
            adapter.pauseMore();
        }

        @Override
        public void onNoMoreClick() {
            //这个方法是点击没有更多数据展示布局的操作，比如可以做吐司等等
            Log.e("逗比","没有更多数据了");
        }
    });
    //设置上拉加载更多异常监听数据监听
    adapter.setError(R.layout.view_error, new OnErrorListener() {
        @Override
        public void onErrorShow() {
            //上拉加载，加载更多数据异常展示，这个方法可以暂停或者停止加载数据
            adapter.pauseMore();
        }

        @Override
        public void onErrorClick() {
            //这个方法是点击加载更多数据异常展示布局的操作，比如恢复加载更多等等
            adapter.resumeMore();
        }
    });
    ```



#### 3.3 添加header和footer操作
- 添加headerView操作。至于添加footerView的操作，几乎和添加header步骤是一样的。
    - 添加普通的布局【非listView或者RecyclerView布局】
    ```
    adapter.addHeader(new InterItemView() {
        @Override
        public View onCreateView(ViewGroup parent) {
            View inflate = LayoutInflater.from(context).inflate(R.layout.header_view, null);
            return inflate;
        }

        @Override
        public void onBindView(View headerView) {
            TextView tvTitle = headerView.findViewById(R.id.tvTitle);
        }
    });
    ```
    - 添加list布局【以横向recyclerView为例子】
    ```
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
            return recyclerView;
        }

        @Override
        public void onBindView(View headerView) {
            //这里的处理别忘了
            ((ViewGroup)headerView).requestDisallowInterceptTouchEvent(true);
        }
    });
    ```
- 注意要点
    - 如果添加了HeaderView，凡是通过ViewHolder拿到的position都要减掉HeaderView的数量才能得到正确的position。



#### 3.4 设置数据和刷新
- 添加所有数据，可以是集合，也可以是数组
    ```
    //添加所有数据
    adapter.addAll(data);
    //添加单挑数据
    adapter.add(data);
    ```
- 插入，刷新和删除数据
    ```
    //插入指定索引数据，单个数据
    adapter.insert(data, pos);
    //插入指定索引数据，多个数据
    adapter.insertAll(data, pos);
    //刷新指定索引数据
    adapter.update(data, pos);
    //删除数据，指定数据
    adapter.remove(data);
    //删除数据，指定索引
    adapter.remove(pos);
    //清空所有数据
    ```


#### 3.5 设置adapter
- 注意自定义adapter需要实现RecyclerArrayAdapter<T>，其中T是泛型，就是你要使用的bean数据类型
    ```
    public class PersonAdapter extends RecyclerArrayAdapter<PersonData> {

        public PersonAdapter(Context context) {
            super(context);
        }

        @Override
        public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
            return new PersonViewHolder(parent);
        }

        public class PersonViewHolder extends BaseViewHolder<PersonData> {

            private TextView tv_title;
            private ImageView iv_news_image;

            PersonViewHolder(ViewGroup parent) {
                super(parent, R.layout.item_news);
                iv_news_image = getView(R.id.iv_news_image);
                tv_title = getView(R.id.tv_title);

                //添加孩子的点击事件
                addOnClickListener(R.id.iv_news_image);
                addOnClickListener(R.id.tv_title);
            }

            @Override
            public void setData(final PersonData person){
                Log.i("ViewHolder","position"+getDataPosition());
                tv_title.setText(person.getName());
            }
        }
    }
    ```


#### 3.6 设置条目点击事件[item条目点击事件，item条目孩子view点击事件]
- 条目单击点击事件，长按事件[省略，可以自己看代码]
    ```
    adapter.setOnItemClickListener(new OnItemClickListener() {
        @Override
        public void onItemClick(int position) {
            if (adapter.getAllData().size()>position && position>=0){
                //处理点击事件逻辑
            }
        }
    });
    ```
- 条目中孩子的点击事件
    ```
    //添加孩子的点击事件，可以看3.5设置adapter
    addOnClickListener(R.id.iv_news_image);
    addOnClickListener(R.id.tv_title);

    //设置孩子的点击事件
    adapter.setOnItemChildClickListener(new OnItemChildClickListener() {
        @Override
        public void onItemChildClick(View view, int position) {
            switch (view.getId()){
                case R.id.iv_news_image:
                    Toast.makeText(HeaderFooterActivity.this,
                            "点击图片了",Toast.LENGTH_SHORT).show();
                    break;
                case R.id.tv_title:
                    Toast.makeText(HeaderFooterActivity.this,
                            "点击标题",Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    });
    ```



#### 3.7 设置侧滑删除功能[QQ侧滑删除]
- 在布局文件中，这里省略部分代码
    ```
    <org.yczbj.ycrefreshviewlib.swipeMenu.YCSwipeMenu
        android:orientation="horizontal">
        <!--item内容-->
        <RelativeLayout
        </RelativeLayout>

        <!-- 侧滑菜单 -->
        <Button
            android:id="@+id/btn_del"/>
        <Button
            android:id="@+id/btn_top"/>
    </org.yczbj.ycrefreshviewlib.swipeMenu.YCSwipeMenu>
    ```
- 在代码中设置
    - 在adapter中定义接口
    ```
    private OnSwipeMenuListener listener;
    public void setOnSwipeMenuListener(OnSwipeMenuListener listener) {
        this.listener = listener;
    }
    ```
    - 在adapter设置点击事件
    ```
    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_del:
                    if (null != listener) {
                        listener.toDelete(getAdapterPosition());
                    }
                    break;
                case R.id.btn_top:
                    if (null != listener) {
                        listener.toTop(getAdapterPosition());
                    }
                    break;
            }
        }
    };
    btn_del.setOnClickListener(clickListener);
    btn_top.setOnClickListener(clickListener);
    ```
- 处理置顶或者删除的功能
    ```
    adapter.setOnSwipeMenuListener(new OnSwipeMenuListener() {
        //删除功能
        @Override
        public void toDelete(int position) {

        }

        //置顶功能
        @Override
        public void toTop(int position) {

        }
    });
    ```


#### 3.8 轻量级拖拽排序与滑动删除
- 处理长按拖拽，滑动删除的功能。轻量级，自由选择是否实现。
    ```
    mCallback = new DefaultItemTouchHelpCallback(new DefaultItemTouchHelpCallback
                            .OnItemTouchCallbackListener() {
        @Override
        public void onSwiped(int adapterPosition) {
            // 滑动删除的时候，从数据库、数据源移除，并刷新UI
        }

        @Override
        public boolean onMove(int srcPosition, int targetPosition) {
            return false;
        }
    });
    mCallback.setDragEnable(true);
    mCallback.setSwipeEnable(true);
    mCallback.setColor(this.getResources().getColor(R.color.colorAccent));
    ItemTouchHelper itemTouchHelper = new ItemTouchHelper(mCallback);
    itemTouchHelper.attachToRecyclerView(recyclerView);
    ```




### 4.关于状态切换
#### 4.1 关于布局内容
- YCRecyclerView是一个组合自定义控件，其布局如下所示
    ```
     <!--刷新控件    省略部分代码-->
    <android.support.v4.widget.SwipeRefreshLayout>
        <FrameLayout>
            <!--RecyclerView控件-->
            <android.support.v7.widget.RecyclerView/>
            <!--加载数据为空时的布局-->
            <FrameLayout/>
            <!--正在加载数据中的布局-->
            <FrameLayout/>
            <!--加载错误时的布局：网络错误或者请求数据错误-->
            <FrameLayout/>
        </FrameLayout>
    </android.support.v4.widget.SwipeRefreshLayout>
    ```



#### 4.2 关于实现思路
- 关于页面状态切换的思路
    - 第一种方式：直接把这些界面include到main界面中，然后动态去切换界面，后来发现这样处理不容易复用到其他项目中，而且在activity中处理这些状态的显示和隐藏比较乱
    - 第二种方式：利用子类继承父类特性，在父类中写切换状态，但有些界面如果没有继承父类，又该如何处理
- 而本库采用的做法思路
    - 一个帧布局FrameLayout里写上4种不同类型布局，正常布局，空布局，加载loading布局，错误布局[网络异常，加载数据异常]
    - 当然也可以自定义这些状态的布局，通过addView的形式，将不同状态布局添加到对应的FrameLayout中。而切换状态，只需要设置布局展示或者隐藏即可。



#### 4.3 关于状态切换api调用
- 如下所示
    ```
    //设置加载数据完毕状态
    recyclerView.showRecycler();
    //设置加载数据为空状态
    recyclerView.showEmpty();
    //设置加载错误状态
    recyclerView.showError();
    //设置加载数据中状态
    recyclerView.showProgress();
    ```


#### 4.4 关于自定义状态布局
- 如下所示
    ```
    //设置空状态页面自定义布局
    recyclerView.setEmptyView(R.layout.view_custom_empty_data);
    recyclerView.setEmptyView(view);
    //获取空页面自定义布局
    View emptyView = recyclerView.getEmptyView();

    //设置异常状态页面自定义布局
    recyclerView.setErrorView(R.layout.view_custom_data_error);
    recyclerView.setErrorView(view);

    //设置加载loading状态页面自定义布局
    recyclerView.setProgressView(R.layout.view_progress_loading);
    recyclerView.setProgressView(view);
    ```

#### 4.5 关于自定义布局交互事件处理
- 有时候，加载页面出现异常情况，比如没有网络会显示自定义的网络异常页面。现在需要点击异常页面按钮等等操作，那么该如何做呢？
    ```
    //注意需要
    LinearLayout ll_error_view = (LinearLayout) recyclerView.findViewById(R.id.ll_error_view);
    ll_error_view.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //比如，跳转到网络设置页面，或者再次刷新数据，或者其他操作等等
        }
    });
    ```



### 5.常用api介绍
- 状态切换方法说明
    ```
    //设置加载数据完毕状态
    recyclerView.showRecycler();
    //设置加载数据为空状态
    recyclerView.showEmpty();
    //设置加载错误状态
    recyclerView.showError();
    //设置加载数据中状态
    recyclerView.showProgress();
    //设置自定义布局，其他几个方法同理
    recyclerView.setEmptyView(R.layout.view_custom_empty_data);
    ```
- viewHolder方法说明
    ```
    //子类设置数据方法
    setData方法
    //findViewById方式
    iv_news_image = getView(R.id.iv_news_image);
    //获取上下文
    Context context = getContext();
    //获取数据索引的位置
    int dataPosition = getDataPosition();
    //添加item中子控件的点击事件
    addOnClickListener(R.id.tv_title);
    ```
- adapter方法说明
    ```
    //删除索引处的数据
    adapter.remove(0);
    //触发清空所有数据
    adapter.removeAll();
    //添加数据，注意这个是在最后索引处添加
    adapter.add(new PersonData());
    //添加所有数据
    adapter.addAll(DataProvider.getPersonList(0));
    //插入数据
    adapter.insert(data,3);
    //在某个索引处插入集合数据
    adapter.insertAll(data,3);
    //获取item索引位置
    adapter.getPosition(data);
    //触发清空所有的数据
    adapter.clear();
    //获取所有的数据
    adapter.getAllData();

    //清除所有footer
    adapter.removeAllFooter();
    //清除所有header
    adapter.removeAllHeader();
    //添加footerView
    adapter.addFooter(view);
    //添加headerView
    adapter.addHeader(view);
    //移除某个headerView
    adapter.removeHeader(view);
    //移除某个footerView
    adapter.removeFooter(view);
    //获取某个索引处的headerView
    adapter.getHeader(0);
    //获取某个索引处的footerView
    adapter.getFooter(0);
    //获取footer的数量
    adapter.getFooterCount();
    //获取header的数量
    adapter.getHeaderCount();

    //设置上拉加载更多的自定义布局和监听
    adapter.setMore(R.layout.view_more,listener);
    //设置上拉加载更多的自定义布局和监听
    adapter.setMore(view,listener);
    //设置上拉加载没有更多数据布局
    adapter.setNoMore(R.layout.view_nomore);
    //设置上拉加载没有更多数据布局
    adapter.setNoMore(view);
    //设置上拉加载没有更多数据监听
    adapter.setNoMore(R.layout.view_nomore,listener);
    //设置上拉加载异常的布局
    adapter.setError(R.layout.view_error);
    //设置上拉加载异常的布局
    adapter.setError(view);
    //设置上拉加载异常的布局和异常监听
    adapter.setError(R.layout.view_error,listener);
    //暂停上拉加载更多
    adapter.pauseMore();
    //停止上拉加载更多
    adapter.stopMore();
    //恢复上拉加载更多
    adapter.resumeMore();

    //获取上下文
    adapter.getContext();
    //应该使用这个获取item个数
    adapter.getCount();
    //设置操作数据[增删改查]后，是否刷新adapter
    adapter.setNotifyOnChange(true);

    //设置孩子点击事件
    adapter.setOnItemChildClickListener(listener);
    //设置条目点击事件
    adapter.setOnItemClickListener(listener);
    //设置条目长按事件
    adapter.setOnItemLongClickListener(listener);
    ```
- 分割线方法说明
    ```
    //可以设置线条颜色和宽度的分割线
    //四个参数，上下文，方向，线宽，颜色
    final RecycleViewItemLine line = new RecycleViewItemLine(this, LinearLayout.HORIZONTAL,
            (int)AppUtils.convertDpToPixel(1,this),
            this.getResources().getColor(R.color.color_f9f9f9));
    recyclerView.addItemDecoration(line);

    //适用于瀑布流中的间距设置
    SpaceViewItemLine itemDecoration = new SpaceViewItemLine(
            (int) AppUtils.convertDpToPixel(8,this));
    itemDecoration.setPaddingEdgeSide(true);
    itemDecoration.setPaddingStart(true);
    itemDecoration.setPaddingHeaderFooter(true);
    recyclerView.addItemDecoration(itemDecoration);

    //可以设置线条颜色和宽度，并且可以设置距离左右的间距
    DividerViewItemLine itemDecoration = new
            DividerViewItemLine( this.getResources().getColor(R.color.color_f9f9f9)
            , LibUtils.dip2px(this, 1f),
            LibUtils.dip2px(this, 72), 0);
    itemDecoration.setDrawLastItem(false);
    recyclerView.addItemDecoration(itemDecoration);
    ```
- 其他api说明



### 6.recyclerView的wiki文档【更新中】
#### [6.1 封装库部分思路介绍](https://github.com/yangchong211/YCRefreshView/blob/master/read/wiki1.md)
- 01.整体封装思路
- 02.添加下拉刷新
- 03.添加header和footer
- 04.添加上拉加载
- 05.接口分离法则
- 06.泛型在框架中应用
- 07.封装adapter
- 08.封装ViewHolder
- 09.状态管理切换
- 10.View事件分发运用
- 11.侧滑删除功能
- 12.Item拖拽排序与滑动删除
- 13.自定义ItemDecoration


#### [6.2 优化处理逻辑介绍](https://github.com/yangchong211/YCRefreshView/blob/master/read/wiki2.md)
- 01.接口分离优化
- 02.去除淡黄色警告
- 03.SparseArray替代HashMap
- 04.瀑布流图片错乱问题解决
- 05.关于点击事件放在哪里优化
- 06.ViewHolder优化
- 07.连续上拉加载更多优化
- 08.拖拽排序与滑动删除优化
- 09.暂停或停止加载数据优化
- 10.状态切换交互优化
- 11.异常情况下保存状态
- 12.多线程下插入数据优化
- 13.recyclerView滑动卡顿优化



#### [6.3 recyclerView相关类](https://github.com/yangchong211/YCRefreshView/blob/master/read/wiki3.md)
- 01.recyclerView
- 02.layoutManager
- 03.adapter
- 04.viewHolder
- 05.SnapHelper
- 06.ItemTouchHelper
- 07.SpanSizeLookup
- 08.ItemDecoration



#### [6.4 recyclerView滑动冲突](https://github.com/yangchong211/YCRefreshView/blob/master/read/wiki4.md)
- 01.如何判断RecyclerView控件滑动到顶部和底部
- 02.RecyclerView嵌套RecyclerView 条目自动上滚的Bug
- 03.ScrollView嵌套RecyclerView滑动冲突
- 04.ViewPager嵌套水平RecyclerView横向滑动到底后不滑动ViewPager
- 05.RecyclerView嵌套RecyclerView的滑动冲突问题
- 06.RecyclerView使用Glide加载图片导致图片错乱问题解决


#### [6.5 recyclerView缓存机制](https://github.com/yangchong211/YCRefreshView/blob/master/read/wiki5.md)
- 01.性能如何优化
- 02.RecyclerView绘制原理图
- 03.绘制原理分析过程
- 04.缓存机制
- 05.屏幕滑动时分析


#### 6.6 recyclerView相关博客
- [01.RecyclerView](https://github.com/yangchong211/YCBlogs/blob/master/android/recyclerView/01.RecyclerView.md)
- [02.Adapter](https://github.com/yangchong211/YCBlogs/blob/master/android/recyclerView/02.Adapter.md)
- [03.ViewHolder](https://github.com/yangchong211/YCBlogs/blob/master/android/recyclerView/03.ViewHolder.md)
- [04.LayoutManager](https://github.com/yangchong211/YCBlogs/blob/master/android/recyclerView/04.LayoutManager.md)
- [05.SnapHelper](https://github.com/yangchong211/YCBlogs/blob/master/android/recyclerView/05.SnapHelper.md)
- [06.ItemTouchHelper](https://github.com/yangchong211/YCBlogs/blob/master/android/recyclerView/06.ItemTouchHelper.md)
- [07.SpanSizeLookup](https://github.com/yangchong211/YCBlogs/blob/master/android/recyclerView/07.SpanSizeLookup.md)
- [08.ItemDecoration](https://github.com/yangchong211/YCBlogs/blob/master/android/recyclerView/08.ItemDecoration.md)
- [09.RecycledViewPool](https://github.com/yangchong211/YCBlogs/blob/master/android/recyclerView/09.RecycledViewPool.md)
- [11.RecyclerView上拉加载](https://github.com/yangchong211/YCBlogs/blob/master/android/recyclerView/11.RecyclerView%E4%B8%8A%E6%8B%89%E5%8A%A0%E8%BD%BD.md)
- [12.RecyclerView缓存原理](https://github.com/yangchong211/YCBlogs/blob/master/android/recyclerView/12.RecyclerView%E7%BC%93%E5%AD%98%E5%8E%9F%E7%90%86.md)
- [13.SnapHelper源码分析](https://github.com/yangchong211/YCBlogs/blob/master/android/recyclerView/13.SnapHelper%E6%BA%90%E7%A0%81%E5%88%86%E6%9E%90.md)
- [16.自定义SnapHelper](https://github.com/yangchong211/YCBlogs/blob/master/android/recyclerView/16.%E8%87%AA%E5%AE%9A%E4%B9%89SnapHelper.md)
- [19.自定义ItemDecoration分割线](https://github.com/yangchong211/YCBlogs/blob/master/android/recyclerView/19.%E8%87%AA%E5%AE%9A%E4%B9%89ItemDecoration%E5%88%86%E5%89%B2%E7%BA%BF.md)
- [22.RecyclerView问题汇总](https://github.com/yangchong211/YCBlogs/blob/master/android/recyclerView/22.RecyclerView%E9%97%AE%E9%A2%98%E6%B1%87%E6%80%BB.md)
- [23.RecyclerView滑动冲突](https://github.com/yangchong211/YCBlogs/blob/master/android/recyclerView/23.RecyclerView%E6%BB%91%E5%8A%A8%E5%86%B2%E7%AA%81.md)
- [24.ScrollView嵌套RecyclerView问题](https://github.com/yangchong211/YCBlogs/blob/master/android/recyclerView/24.ScrollView%E5%B5%8C%E5%A5%97RecyclerView%E9%97%AE%E9%A2%98.md)



### 7.实现效果展示
#### 7.1 使用过YCRefreshView库的案例代码
- 可以直接参考demo，或者直接参考的我其他案例，其中这几个案例中使用到了该库
- https://github.com/yangchong211/LifeHelper
- https://github.com/yangchong211/YCVideoPlayer



#### 7.2 图片展示效果
![1.jpg](https://upload-images.jianshu.io/upload_images/4432347-3487797e987afe9e.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
![2.jpg](https://upload-images.jianshu.io/upload_images/4432347-29784cfd264833de.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
![3.jpg](https://upload-images.jianshu.io/upload_images/4432347-811e2e10ccd621ac.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
![4.jpg](https://upload-images.jianshu.io/upload_images/4432347-b1901773a339486f.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
![5.jpg](https://upload-images.jianshu.io/upload_images/4432347-5d113487d837c954.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
![6.jpg](https://upload-images.jianshu.io/upload_images/4432347-ab9506e693299ee6.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
![7.jpg](https://upload-images.jianshu.io/upload_images/4432347-62d5cc1d9e56dedd.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
![8.jpg](https://upload-images.jianshu.io/upload_images/4432347-352c6362cf171a8a.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
![9.jpg](https://upload-images.jianshu.io/upload_images/4432347-d7e215f6ee4142a3.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
![10.jpg](https://upload-images.jianshu.io/upload_images/4432347-e1f7c2ae45c8756c.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
![11.jpg](https://upload-images.jianshu.io/upload_images/4432347-a21e54beb8710c3e.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
![12.jpg](https://upload-images.jianshu.io/upload_images/4432347-903a177bc62da545.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)





#### 7.3 部分案例图展示[部分案例图可以参考7.1]
![image](https://upload-images.jianshu.io/upload_images/4432347-c3d1cd1c02f05be0.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/300)
![image](https://upload-images.jianshu.io/upload_images/4432347-ca314a2714877604.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/270)



### 8.版本更新说明
- v1.0 更新于2016年11月2日
- v1.1 更新于2017年3月13日
- v1.3 更新于2017年8月9日
- v1.…… 更新于2018年1月5日
- v2.2 更新于2018年1月17日
- v2.3 更新于2018年2月9日
- v2.4 更新于2018年3月19日
- v2.5.6 更新于2018年8月6日
- v2.5.7 更新于2019年3月3日
- v2.5.8 更新于2019年3月4日



### 9.参考资料说明
- **非常感谢前辈大神的封装思路和代码案例，感谢！！！**
    - https://github.com/XRecyclerView/XRecyclerView
    - BGARefreshLayout-Android：https://github.com/bingoogolapple/BGARefreshLayout-Android
    - Android-PullToRefresh：https://github.com/chrisbanes/Android-PullToRefresh
    - adapter：https://github.com/CymChad/BaseRecyclerViewAdapterHelper
    - fastAdapter：https://github.com/mikepenz/FastAdapter
    - Jude95/EasyRecyclerView：https://github.com/Jude95/EasyRecyclerView
    - UltimateRecyclerView：https://github.com/cymcsg/UltimateRecyclerView
    - MultiType：https://github.com/drakeet/MultiType
- 关于viewHolder的封装，参考是鸿洋大神的[baseAdapter](https://github.com/hongyangAndroid/baseAdapter)
- 关于RecyclerView实现条目Item拖拽排序与滑动删除，参看是严正杰大神的博客，[拖拽排序](https://github.com/yanzhenjie/SwipeRecyclerView)
- 关于仿照QQ侧滑删除，参考是SwipeMenu的案例，具体可以看：[SwipeMenu](https://github.com/TUBB/SwipeMenu/)




### 10.其他内容介绍
#### 关于其他内容介绍
![image](https://upload-images.jianshu.io/upload_images/4432347-7100c8e5a455c3ee.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


#### 01.关于博客汇总链接
- 1.[技术博客汇总](https://www.jianshu.com/p/614cb839182c)
- 2.[开源项目汇总](https://blog.csdn.net/m0_37700275/article/details/80863574)
- 3.[生活博客汇总](https://blog.csdn.net/m0_37700275/article/details/79832978)
- 4.[喜马拉雅音频汇总](https://www.jianshu.com/p/f665de16d1eb)
- 5.[其他汇总](https://www.jianshu.com/p/53017c3fc75d)



#### 02.关于我的博客
- 我的个人站点：www.yczbj.org，www.ycbjie.cn
- github：https://github.com/yangchong211
- 知乎：https://www.zhihu.com/people/yczbj/activities
- 简书：http://www.jianshu.com/u/b7b2c6ed9284
- csdn：http://my.csdn.net/m0_37700275
- 喜马拉雅听书：http://www.ximalaya.com/zhubo/71989305/
- 开源中国：https://my.oschina.net/zbj1618/blog
- 泡在网上的日子：http://www.jcodecraeer.com/member/content_list.php?channelid=1
- 邮箱：yangchong211@163.com
- 阿里云博客：https://yq.aliyun.com/users/article?spm=5176.100- 239.headeruserinfo.3.dT4bcV
- segmentfault头条：https://segmentfault.com/u/xiangjianyu/articles
- 掘金：https://juejin.im/user/5939433efe88c2006afa0c6e



#### 03.勘误及提问
- 如果有疑问或者发现错误，可以在相应的 issues 进行提问或勘误。如果喜欢或者有所启发，欢迎star，对作者也是一种鼓励。


#### 04.关于LICENSE
```
Copyright 2017 yangchong211（github.com/yangchong211）

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```













# YCRefreshView
- 自定义支持上拉加载更多，下拉刷新，支持自由切换状态【加载中，加载成功，加载失败，没网络等状态】的控件。
具体使用方法，可以直接参考demo。
- 轻量级侧滑删除菜单，支持recyclerView，listView，直接嵌套item布局即可使用，整个侧滑菜单思路是：跟随手势将item向左滑动
- 该库已经用到了实际开发项目中，会持续更新并且修改bug。如果觉得可以，可以star一下，多谢支持！

## 目录介绍
- 1.关于EasyRecycleView开源库
- 2.关于该开源库的思路
- 3.如何使用
- 4.该库的优点
- 5.实现效果
- 6.版本更新说明
- 7.其他

###  1.关于复杂页面封装库介绍
- 1.1 支持上拉加载，下拉刷新，可以自定义foot底部布局
- 1.2 支持切换不同的状态，比如加载中[目前是ProgressBar，后期要定制，加载成功，加载失败，加载错误等
- 1.3 支持复杂界面使用，比如有的页面包含有轮播图，按钮组合，横向滑动，还有复杂list，那么用这个控件就可以搞定
- 1.4 具体的使用可以直接参考Demo案例，非常感谢Jude大神的开源项目！
- 1.5 轻量级侧滑删除菜单，直接嵌套item布局即可使用，使用十分简单。


###  2.关于该开源库
- 2.0 参考并借鉴了大量的优秀开源库，由于后期业务需求发生变化，因此做了一些功能的延伸与定制。继续完善并修改库bug！！！已经用于实际开发中。
- 2.1 先来看看布局，实际上只是在recyclerView基础上做了大量拓展……
```
<?xml version="1.0" encoding="utf-8"?>
<android.support.v4.widget.SwipeRefreshLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:id="@+id/ptr_layout"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <!--RecyclerView控件，支持添加头部和底部view-->
    <android.support.v7.widget.RecyclerView
        android:id="@android:id/list"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:scrollbars="vertical|horizontal"
        android:clickable="true"
        android:focusable="true" />

    <!--加载数据为空时的布局-->
    <FrameLayout
        android:id="@+id/empty"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:clickable="true"
        android:focusable="true"/>

    <!--正在加载数据中的布局-->
    <FrameLayout
        android:id="@+id/progress"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:clickable="true"
        android:focusable="true"/>

    <!--加载错误时的布局：网络错误或者请求数据错误-->
    <FrameLayout
        android:id="@+id/error"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:clickable="true"
        android:focusable="true"/>

</android.support.v4.widget.SwipeRefreshLayout>
```


###  3.如何使用
- 3.1 首先在集成：compile 'org.yczbj:YCRefreshViewLib:2.1'
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

- **3.4 如何使用侧滑菜单删除功能呢？**
- 3.4.1 在布局文件中

```
<?xml version="1.0" encoding="utf-8"?>
<org.yczbj.ycrefreshviewlib.swipeMenu.YCSwipeMenu
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:orientation="horizontal"
    android:layout_height="wrap_content"
    android:layout_width="match_parent">

    <!--item内容-->
    <RelativeLayout
        android:orientation="horizontal"
        android:layout_width="match_parent"
        android:layout_height="72dp"
        android:gravity="center_vertical"
        android:foreground="?android:attr/selectableItemBackground"
        android:background="@android:color/white">
    </RelativeLayout>


    <!-- 侧滑菜单 -->
    <Button
        android:id="@+id/btn_del"
        android:layout_width="70dp"
        android:layout_height="match_parent"
        android:background="@color/colorPrimary"
        android:text="删除"
        android:textColor="@android:color/white"/>
    <Button
        android:id="@+id/btn_top"
        android:layout_width="70dp"
        android:layout_height="match_parent"
        android:background="@color/colorAccent"
        android:text="置顶"
        android:textColor="@android:color/white"/>

</org.yczbj.ycrefreshviewlib.swipeMenu.YCSwipeMenu>
```

- **3.4.2 在代码中设置**
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
		adapter.getAllData().remove(position);
		adapter.notifyItemRemoved(position);//推荐用这个
	}

	//置顶功能
	@Override
	public void toTop(int position) {
		//先移除那个位置的数据，然后将其添加到索引为0的位置，然后刷新数据
		if (position > 0 && adapter.getAllData().size()>position) {
			Person person = adapter.getAllData().get(position);
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
- 可以直接参考demo，或者直接参考的我其他案例，其中这几个案例中使用到了该库
- https://github.com/yangchong211/LifeHelper
- https://github.com/yangchong211/YCVideoPlayer

###  6.版本更新说明
- v1.0 更新于2017年4月22日
- v1.1 更新于2017年8月9日
- v1.…… 更新于2018年1月5日
- v2.2 更新于2018年2月6日
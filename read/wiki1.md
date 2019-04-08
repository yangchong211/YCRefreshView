#### 封装库目录介绍
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





### 01.整体封装思路



### 02.添加下拉刷新



### 03.添加header和footer



### 04.添加上拉加载
- recyclerView在上拉的时候，可以定义一个footerView，而这个view就可以设置上拉加载布局
    - 具体可以看view_more布局
- 在adapter中，可以上拉加载时处理footerView的逻辑
    - 在getItemViewType方法中设置最后一个Item为FooterView
    - 在onCreateViewHolder方法中根据viewType来加载不同的布局
    - 最后在onBindViewHolder方法中设置一下加载的状态显示就可以
    - 由于多了一个FooterView，所以要记得在getItemCount方法的返回值中加上1。
    ```
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // 根据返回的ViewType，绑定不同的布局文件，这里只有两种
        if (viewType == normalType) {
            return new MyViewHolder(LayoutInflater.from(mContext)
                    .inflate(R.layout.item_news, parent,false));
        } else {
            //这个是上拉加载更多的view
            return new FootHolder(LayoutInflater.from(mContext)
                    .inflate(R.layout.view_more, parent,false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyViewHolder){
            PersonData person = data.get(position);
            setBindViewHolder((MyViewHolder)holder,person,position);
        }else {
            setFootBindViewHolder((FootHolder)holder ,position);
        }
    }


    /**
     * 获取条目数量，之所以要加1是因为增加了一条footView
     */
    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size()+1;
    }


    /**
     * 根据条目位置返回ViewType，以供onCreateViewHolder方法内获取不同的Holder
     */
    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return footType;
        } else {
            return normalType;
        }
    }
    ```
- 接着设置滑动监听器，也就是RecyclerView自带的ScrollListener
    ```
    // 实现上拉加载重要步骤，设置滑动监听器，RecyclerView自带的ScrollListener
    recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            // 在newState为滑到底部时
            if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                // 如果没有隐藏footView，那么最后一个条目的位置就比我们的getItemCount少1
                if (!adapter.isFadeTips() && lastVisibleItem + 1 == adapter.getItemCount()) {
                    //加载下一页数据
                }

                // 如果隐藏了提示条，我们又上拉加载时，那么最后一个条目就要比getItemCount要少2
                if (adapter.isFadeTips() && lastVisibleItem + 2 == adapter.getItemCount()) {

                }
            }
        }

        @Override
        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            // 在滑动完成后，拿到最后一个可见的item的位置
            lastVisibleItem = layoutManager.findLastVisibleItemPosition();
        }
    });
    ```
- 那么既然知道了最简单的recyclerView设置上拉加载逻辑，如何优化一下，并封装成库呢？
    - 如果是要封装的话，则使用者应该可以很方便的设置监听事件和设置自定义加载更多View。可以自定义一个FooterView继承InterItemView接口，然后重写两个方法！
    - 同时，关于上拉刷新，可以自动上拉刷新，可以手动触发上拉刷新，则可以自定义一个上拉加载更多的listener，同时里面包含这两个方法。
- 如何控制上拉刷新view的显示与隐藏
    - 给RecyclerView增加一个FooterView，然后通过判断是否滑动到了最后一条Item，来控制FooterView的显示和隐藏。
- 网格布局上拉刷新优化
    - 如果是网格布局，那么上拉刷新的view则不是居中显示，到加载更多的进度条显示在了一个Item上，如果想要正常显示的话，进度条需要横跨两个Item，这该怎么办呢？
    - 在adapter中的onAttachedToRecyclerView方法中处理网格布局情况，代码如下所示，主要逻辑是如果当前是footer的位置，那么该item占据2个单元格，正常情况下占据1个单元格。
    ```
    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    // 如果当前是footer的位置，那么该item占据2个单元格，正常情况下占据1个单元格
                    return getItemViewType(position) == footType ? gridManager.getSpanCount() : 1;
                }
            });
        }
    }
    ```
- 那么如何实现自动进行上拉刷新？
    - 设置滑动监听，判断是否滑动到底部，也就是最后一条数据，当滑动到最后时就开始加载下一页数据，并且显示加载下一页loading。当加载数据成功后，则直接隐藏该布局。
- 那么如何实现手动上拉刷新呢？
    -



### 05.接口分离法则



### 06.泛型在框架中应用



### 07.封装adapter



### 08.封装ViewHolder



### 09.状态管理切换



### 10.View事件分发运用



### 11.侧滑删除功能




### 12.Item拖拽排序与滑动删除
- 需要实现拖拽的功能如下所示
    - 长按item后拖动，与其他item交换位置
    - 按住item右面的图标后拖动，与其他item交换位置
    - 左滑item变透明并缩小，超出屏幕后，其他item补上
    - 右滑item变透明并缩小，超出屏幕后，其他item补上
- 几个重要的方法说明
    - 需要自定义类实现ItemTouchHelper.Callback类，并重写其中几个方法
    ```
    isLongPressDragEnabled                  是否可以长按拖拽排序
    isItemViewSwipeEnabled                  Item是否可以被滑动
    getMovementFlags                        当用户拖拽或者滑动Item的时候需要我们告诉系统滑动或者拖拽的方向
    onMove                                  当Item被拖拽的时候被回调
    onSwiped                                当View被滑动删除的时候
    onSelectedChanged                       当item被拖拽或侧滑时触发
    ```
- 几个方法中代码思路
    - 要想达到上面功能需求，在getMovementFlags方法中，当用户拖拽或者滑动Item的时候需要我们告诉系统滑动或者拖拽的方向，那我们知道支持拖拽和滑动删除的无非就是LinearLayoutManager和GridLayoutManager了，所以可以根据布局管理器的不同做了响应的区分。
    - 在onMove方法中处理拖拽的回调逻辑，那么什么时候被调用？当Item被拖拽排序移动到另一个Item的位置的时候被调用。在onSwiped方法[当Item被滑动删除到不见]中处理被删除后的逻辑。为了降低代码耦合度，可以通过接口listener回调的方式交给外部处理。
- 上下拖动时与其他item进行位置交换
    - ItemTouchHelper.Callback本身不具备将两个item互换位置的功能，但RecyclerView可以，我们可以在item拖动的时候把当前item与另一个item的数据位置交换，再调用RecyclerView的notifyItemMoved()方法刷新布局，同时，因为RecyclerView自带item动画，就可以完成上面的交互效果。
- 左右滑出屏幕时其他item补上
    - 只要在item滑出屏幕时，将对应的数据删掉，再调用RecyclerView的notifyItemRemoved()方法刷新布局即可。
- 拖拽效果优化
    - 在item被拖拽或侧滑时修改背景色，当动作结束后将背景色恢复回来，而ItemTouchHelper.Callback中正好有对应这两个状态的方法，分别是：onSelectedChanged()、clearView()。那么优化处理其实可以放到这两个方法中处理。
    - 左右滑动使item透明度变浅且缩小该如何实现呢？让item执行了两种属性动画而已，在ItemTouchHelper.Callback中有一个方法可以拿到item被拖拽或滑动时的位移变化，那就是onChildDraw()方法，在该方法中设置item渐变和缩放属性动画。
    - 出现问题，按照上面做法会出现删除后有空白item留出来，那么为什么会出现这种情况呢？并不是多出了两条空白数据，它们是正常的数据，只是看不到了，这是因为RecyclerView条目（itemView）覆用导致的，前面在onChildDraw()方法中对itemView设置了透明和缩小，而一个列表中固定只有几个itemView而已，当那两个透明缩小的itemView被再次使用时，之前设置的透明度和高度比例已经是0，所以就出现了这种情况，解决方法也很简单，只要在item被移除后，将itemView的透明度和高度比例设置回来即可。
- 完整代码可以看lib中的ItemTouchHelpCallback类



### 13.自定义ItemDecoration
- 需要实现的分割线功能
    - 可以设置分割线的颜色，宽度，以及到左右两边的宽度间距。
- 几个重要的方法说明
    - 需要自定义类实现RecyclerView.ItemDecoration类，并选择重写合适方法。注意下面这三个方法有着强烈的因果关系！
    ```
    //获取当前view的位置信息，该方法主要是设置条目周边的偏移量
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, State state)
    //在item背后draw
    public void onDraw(Canvas c, RecyclerView parent, State state)
    //在item上边draw
    public void onDrawOver(Canvas c, RecyclerView parent, State state)
    ```
- 注意的是三个方法的调用顺序
    - 首先调用的是getItemOffsets会被多次调用，在layoutManager每次测量可摆放的view的时候回调用一次，在当前状态下需要摆放多少个view这个方法就会回调多少次。
    - 其次会调用onDraw方法，ItemDecoration的onDraw方法是在RecyclerView的onDraw方法中调用的，注意这时候传入的canvas是RecyclerView的canvas，要时刻注意这点，它是和RecyclerView的边界是一致的。这个时候绘制的内容相当于背景，会被item覆盖。
    - 最后调用的是onDrawOver方法，ItemDecoration的onDrawOver方法是在RecyclerView的draw方法中调用的，同样传入的是RecyclerView的canvas，这时候onlayout已经调用，所以此时绘制的内容会覆盖item。
- 为每个item实现索引的思路
    - 要实现上面的可以设置分割线颜色和宽度，肯定是要绘制的，也就是需要使用到onDraw方法。那么在getItemOffsets方法中需要让view摆放位置距离bottom的距离是分割线的宽度。
    - 然后通过parent.getChildCount()方法拿到当前显示的view的数量[注意，该方法并不会获取不显示的view的数量]，循环遍历后，直接用paint画笔进行绘制[注意至于分割线的颜色就是需要设置画笔的颜色]。



#### 参考博客
- https://segmentfault.com/a/1190000007771723







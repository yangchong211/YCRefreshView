#### 目录介绍
- 01.recyclerView
- 02.layoutManager
- 03.adapter
- 04.viewHolder
- 05.SnapHelper
- 06.ItemTouchHelper
- 07.SpanSizeLookup
- 08.ItemDecoration



### 01.recyclerView
- 关于RecyclerView，大家都已经很熟悉了，用途十分广泛，大概结构如下所示
	* RecyclerView.Adapter - 处理数据集合并负责绑定视图
	* ViewHolder - 持有所有的用于绑定数据或者需要操作的View
	* LayoutManager - 负责摆放视图等相关操作
	* ItemDecoration - 负责绘制Item附近的分割线
	* ItemAnimator - 为Item的一般操作添加动画效果，如，增删条目等
- 如图所示，直观展示结构
    - ![image](https://upload-images.jianshu.io/upload_images/4432347-6301e0c8563ecda0.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
- 针对上面几个属性，最简单用法如下所示
    ```
    recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
    LinearLayoutManager layoutManager = new LinearLayoutManager(this);
    //设置layoutManager
    recyclerView.setLayoutManager(layoutManager);
    adapter = new MultipleItemAdapter(this，list);
    //设置adapter
    recyclerView.setAdapter(adapter);
    //设置刷新
    adapter.notifyDataSetChanged();
    ```


### 02.layoutManager
- LayoutManager作用
    - LayoutManager的职责是摆放Item的位置，并且负责决定何时回收和重用Item。
    - RecyclerView 允许自定义规则去放置子 view，这个规则的控制者就是 LayoutManager。一个 RecyclerView 如果想展示内容，就必须设置一个 LayoutManager
- LayoutManager样式
    - LinearLayoutManager 水平或者垂直的Item视图。
    - GridLayoutManager 网格Item视图。
    - StaggeredGridLayoutManager 交错的网格Item视图。
- LayoutManager当前有且仅有一个抽象函数
    - 具体如下：
    ```
    public LayoutParams generateDefaultLayoutParams()
    ```
- setLayoutManager(LayoutManager layout)源码
    - a.setLayoutManager入口源码
    - 分析：当之前设置过 LayoutManager 时，移除之前的视图，并缓存视图在 Recycler 中，将新的 mLayout 对象与 RecyclerView 绑定，更新缓存 View 的数量。最后去调用 requestLayout ，重新请求 measure、layout、draw。
    ```
    public void setLayoutManager(LayoutManager layout) {
        if (layout == mLayout) {
            return;
        }
        // 停止滑动
        stopScroll();
        if (mLayout != null) {
            // 如果有动画，则停止所有的动画
            if (mItemAnimator != null) {
                mItemAnimator.endAnimations();
            }
            // 移除并回收视图
            mLayout.removeAndRecycleAllViews(mRecycler);
            // 回收废弃视图
            mLayout.removeAndRecycleScrapInt(mRecycler);
            //清除mRecycler
            mRecycler.clear();
            if (mIsAttached) {
                mLayout.dispatchDetachedFromWindow(this, mRecycler);
            }
            mLayout.setRecyclerView(null);
            mLayout = null;
        } else {
            mRecycler.clear();
        }
        mChildHelper.removeAllViewsUnfiltered();
        mLayout = layout;
        if (layout != null) {
            if (layout.mRecyclerView != null) {
                throw new IllegalArgumentException("LayoutManager " + layout +
                        " is already attached to a RecyclerView: " + layout.mRecyclerView);
            }
            mLayout.setRecyclerView(this);
            if (mIsAttached) {
                mLayout.dispatchAttachedToWindow(this);
            }
        }
        //更新新的缓存数据
        mRecycler.updateViewCacheSize();
        //重新请求 View 的测量、布局、绘制
        requestLayout();
    }
    ```




### 03.adapter
- RecyclerView.Adapter扮演的角色
    - 一是，根据不同ViewType创建与之相应的的Item-Layout
    - 二是，访问数据集合并将数据绑定到正确的View上
- 重写的方法
    - 一般常用的重写方法有以下这么几个：
    ```
    public VH onCreateViewHolder(ViewGroup parent, int viewType)
    创建Item视图，并返回相应的ViewHolder
    public void onBindViewHolder(VH holder, int position)
    绑定数据到正确的Item视图上。
    public int getItemCount()
    返回该Adapter所持有的Itme数量
    public int getItemViewType(int position)
    用来获取当前项Item(position参数)是哪种类型的布局
    ```
- notifyDataSetChanged()刷新数据
    - 当时据集合发生改变时，我们通过调用.notifyDataSetChanged()，来刷新列表，因为这样做会触发列表的重绘，所以并不会出现任何动画效果，因此需要调用一些以notifyItem*()作为前缀的特殊方法，比如：
	* public final void notifyItemInserted(int position) 向指定位置插入Item
	* public final void notifyItemRemoved(int position) 移除指定位置Item
	* public final void notifyItemChanged(int position) 更新指定位置Item




### 04.viewHolder
- ViewHolder作用大概有这些：
    - adapter应当拥有ViewHolder的子类，并且ViewHolder内部应当存储一些子view，避免时间代价很大的findViewById操作
    - 其RecyclerView内部定义的ViewHolder类包含很多复杂的属性，内部使用场景也有很多，而我们经常使用的也就是onCreateViewHolder()方法和onBindViewHolder()方法，onCreateViewHolder()方法在RecyclerView需要一个新类型。item的ViewHolder时调用来创建一个ViewHolder，而onBindViewHolder()方法则当RecyclerView需要在特定位置的item展示数据时调用。
- ViewHolder与复用
    - 在复写RecyclerView.Adapter的时候，需要我们复写两个方法：
        - onCreateViewHolder
        - onBindViewHolder
        - 这两个方法从字面上看就是创建ViewHolder和绑定ViewHolder的意思
    - 复用机制是怎样的？
        - 模拟场景：只有一种ViewType，上下滑动的时候需要的ViewHolder种类是只有一种，但是需要的ViewHolder对象数量并不止一个。所以在后面创建了9个ViewHolder之后，需要的数量够了，无论怎么滑动，都只需要复用以前创建的对象就行了。那么逗比程序员们思考一下，为什么会出现这种情况呢
        - 看到了下面log之后，第一反应是在这个ViewHolder对象的数量“够用”之后就停止调用onCreateViewHolder方法，但是onBindViewHolder方法每次都会调用的
        - ![image](https://upload-images.jianshu.io/upload_images/4432347-8cb81b547d4e8613.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
- 关于ViewHolder简单的封装代码如下所示：
    - 具体可以看：https://github.com/yangchong211/YCRefreshView




### 05.SnapHelper
- SnapHelper作用
    - 在某些场景下，卡片列表滑动浏览[有的叫轮播图]，希望当滑动停止时可以将当前卡片停留在屏幕某个位置，比如停在左边，以吸引用户的焦点。那么可以使用RecyclerView + Snaphelper来实现，SnapHelper旨在支持RecyclerView的对齐方式，也就是通过计算对齐RecyclerView中TargetView 的指定点或者容器中的任何像素点。
- SnapHelper类分析
    - 查阅可知，SnapHelper继承自RecyclerView.OnFlingListener，并且重写了onFling方法，这个类代码并不多，下面会对重要方法一一解析。
        - 支持SnapHelper的RecyclerView.LayoutManager必须实现的方式：
            - RecyclerView.SmoothScroller.ScrollVectorProvider接口
            - 或者自己实现onFling(int,int)方法手动处理逻辑。
    - SnapHelper类重要的方法
        - attachToRecyclerView: 将SnapHelper attach 到指定的RecyclerView 上。
        - calculateDistanceToFinalSnap:复写这个方法计算对齐到TargetView或容器指定点的距离，这是一个抽象方法，由子类自己实现，返回的是一个长度为2的int 数组out，out[0]是x方向对齐要移动的距离，out[1]是y方向对齐要移动的距离。
        - calculateScrollDistance: 根据每个方向给定的速度估算滑动的距离，用于Fling 操作。
        - findSnapView:提供一个指定的目标View 来对齐,抽象方法，需要子类实现
        - findTargetSnapPosition:提供一个用于对齐的Adapter 目标position,抽象方法，需要子类自己实现。
        - onFling:根据给定的x和 y 轴上的速度处理Fling。
    - 什么是Fling操作
        - 手指在屏幕上滑动 RecyclerView然后松手，RecyclerView中的内容会顺着惯性继续往手指滑动的方向继续滚动直到停止，这个过程叫做 Fling 。 Fling 操作从手指离开屏幕瞬间被触发，在滚动停止时结束。
- LinearSnapHelper类分析
    - LinearSnapHelper 使当前Item居中显示，常用场景是横向的RecyclerView，类似ViewPager效果，但是又可以快速滑动（滑动多页）。
    - 最简单的使用就是，如下代码
    - 几行代码就可以用RecyclerView实现一个类似ViewPager的效果，并且效果还不错。可以快速滑动多页，当前页剧中显示，并且显示前一页和后一页的部分。
    ```
    LinearSnapHelper snapHelper = new LinearSnapHelper();
    snapHelper.attachToRecyclerView(mRecyclerView);
    ```
- PagerSnapHelper类分析
    - PagerSnapHelper看名字可能就能猜到，使RecyclerView像ViewPager一样的效果，每次只能滑动一页（LinearSnapHelper支持快速滑动）, PagerSnapHelper也是Item居中对齐。
    - 最简单的使用就是，如下代码
    ```
    PagerSnapHelper snapHelper = new PagerSnapHelper();
    snapHelper.attachToRecyclerView(mRecyclerView);
    ```




### 06.ItemTouchHelper



### 07.SpanSizeLookup



### 08.ItemDecoration
- 作用
    - 通过设置recyclerView.addItemDecoration(new DividerDecoration(this));来改变Item之间的偏移量或者对Item进行装饰。
    - 当然，你也可以对RecyclerView设置多个ItemDecoration，列表展示的时候会遍历所有的ItemDecoration并调用里面的绘制方法，对Item进行装饰。
- RecyclerView.ItemDecoration是一个抽象类
    - 该抽象类常见的方法如下所示：
    ```
    public void onDraw(Canvas c, RecyclerView parent)
    装饰的绘制在Item条目绘制之前调用，所以这有可能被Item的内容所遮挡
    public void onDrawOver(Canvas c, RecyclerView parent)
    装饰的绘制在Item条目绘制之后调用，因此装饰将浮于Item之上
    public void getItemOffsets(Rect outRect, int itemPosition, RecyclerView parent)
    与padding或margin类似，LayoutManager在测量阶段会调用该方法，计算出每一个Item的正确尺寸并设置偏移量。
    ```
















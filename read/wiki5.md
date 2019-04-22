#### RecyclerView缓存机制
- 01.性能如何优化
- 02.RecyclerView绘制原理图
- 03.绘制原理分析过程
- 04.缓存机制
- 05.屏幕滑动时分析



### 01.性能如何优化
- RecyclerView做性能优化要说复杂也复杂，比如说布局优化，缓存，预加载，复用池，刷新数据等等。
    - 其优化的点很多，在这些看似独立的点之间，其实存在一个枢纽：Adapter。因为所有的ViewHolder的创建和内容的绑定都需要经过Adapter的两个函数onCreateViewHolder和onBindViewHolder。
- 因此性能优化的本质就是要减少这两个函数的调用时间和调用的次数。
    - 如果我们想对RecyclerView做性能优化，必须清楚的了解到我们的每一步操作背后，onCreateViewHolder和onBindViewHolder调用了多少次。因此，了解RecyclerView的缓存机制是RecyclerView性能优化的基础。




### 02.RecyclerView绘制原理图
- ![image](https://upload-images.jianshu.io/upload_images/4432347-cf5449f6ff35db67.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)




### 03.绘制原理分析过程
- 简化问题
    ```
    RecyclerView
        以LinearLayoutManager为例
        忽略ItemDecoration
        忽略ItemAnimator
        忽略Measure过程
        假设RecyclerView的width和height是确定的
    Recycler
        忽略mViewCacheExtension
    ```
- 绘制过程
    - 类的职责介绍
        - LayoutManager：接管RecyclerView的Measure，Layout，Draw的过程
        - Recycler：缓存池
        - Adapter：ViewHolder的生成器和内容绑定器。
    - 绘制过程简介
        - RecyclerView.requestLayout开始发生绘制，忽略Measure的过程
        - 在Layout的过程会通过LayoutManager.fill去将RecyclerView填满
        - LayoutManager.fill会调用LayoutManager.layoutChunk去生成一个具体的ViewHolder
        - 然后LayoutManager就会调用Recycler.getViewForPosition向Recycler去要ViewHolder
        - Recycler首先去一级缓存（Cache）里面查找是否命中，如果命中直接返回。如果一级缓存没有找到，则去三级缓存查找，如果三级缓存找到了则调用Adapter.bindViewHolder来绑定内容，然后返回。如果三级缓存没有找到，那么就通过Adapter.createViewHolder创建一个ViewHolder，然后调用Adapter.bindViewHolder绑定其内容，然后返回为Recycler。
        - 一直重复步骤3-5，知道创建的ViewHolder填满了整个RecyclerView为止。



### 04.缓存机制
#### 4.1 Recycler源码解析
- 首先看看代码
    ```
    public final class Recycler {
        final ArrayList<ViewHolder> mAttachedScrap = new ArrayList<>();
        ArrayList<ViewHolder> mChangedScrap = null;

        final ArrayList<ViewHolder> mCachedViews = new ArrayList<ViewHolder>();

        private final List<ViewHolder>
                mUnmodifiableAttachedScrap = Collections.unmodifiableList(mAttachedScrap);

        private int mRequestedCacheMax = DEFAULT_CACHE_SIZE;
        int mViewCacheMax = DEFAULT_CACHE_SIZE;

        RecycledViewPool mRecyclerPool;

        private ViewCacheExtension mViewCacheExtension;

        static final int DEFAULT_CACHE_SIZE = 2;
    }
    ```
- RecyclerView在Recyler里面实现ViewHolder的缓存，Recycler里面的实现缓存的主要包含以下5个对象：
    - ArrayList mAttachedScrap：未与RecyclerView分离的ViewHolder列表,如果仍依赖于 RecyclerView （比如已经滑动出可视范围，但还没有被移除掉），但已经被标记移除的 ItemView 集合会被添加到 mAttachedScrap 中
        - 按照id和position来查找ViewHolder
    - ArrayList mChangedScrap：表示数据已经改变的viewHolder列表,存储 notifXXX 方法时需要改变的 ViewHolder,匹配机制按照position和id进行匹配
    - ArrayList mCachedViews：缓存ViewHolder，主要用于解决RecyclerView滑动抖动时的情况，还有用于保存Prefetch的ViewHoder
        - 最大的数量为：mViewCacheMax = mRequestedCacheMax + extraCache（extraCache是由prefetch的时候计算出来的）
    - ViewCacheExtension mViewCacheExtension：开发者可自定义的一层缓存，是虚拟类ViewCacheExtension的一个实例，开发者可实现方法getViewForPositionAndType(Recycler recycler, int position, int type)来实现自己的缓存。
        - 位置固定
        - 内容不变
        - 数量有限
    - mRecyclerPool ViewHolder缓存池，在有限的mCachedViews中如果存不下ViewHolder时，就会把ViewHolder存入RecyclerViewPool中。
        - 按照Type来查找ViewHolder
        - 每个Type默认最多缓存5个


#### 4.2 recyclerView三级缓存
- RecyclerView在设计的时候讲上述5个缓存对象分为了3级。每次创建ViewHolder的时候，会按照优先级依次查询缓存创建ViewHolder。每次讲ViewHolder缓存到Recycler缓存的时候，也会按照优先级依次缓存进去。三级缓存分别是：
    - 一级缓存：返回布局和内容都都有效的ViewHolder
        - 按照position或者id进行匹配
        - 命中一级缓存无需onCreateViewHolder和onBindViewHolder
        - mAttachScrap在adapter.notifyXxx的时候用到
        - mChanedScarp在每次View绘制的时候用到，因为getViewHolderForPosition非调用多次，后面将
        - mCachedView：用来解决滑动抖动的情况，默认值为2
    - 二级缓存：返回View
        - 按照position和type进行匹配
        - 直接返回View
        - 需要自己继承ViewCacheExtension实现
        - 位置固定，内容不发生改变的情况，比如说Header如果内容固定，就可以使用
    - 三级缓存：返回布局有效，内容无效的ViewHolder
        - 按照type进行匹配，每个type缓存值默认=5
        - layout是有效的，但是内容是无效的
        - 多个RecycleView可共享,可用于多个RecyclerView的优化
- 图解
    - ![image](https://upload-images.jianshu.io/upload_images/4432347-2b217ad3312ae857.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)



### 05.屏幕滑动时分析
- 如图所示
    - ![image](https://upload-images.jianshu.io/upload_images/4432347-af9eb27fa7749837.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
- 实例解释：
    - 由于ViewCacheExtension在实际使用的时候较少用到，因此本例中忽略二级缓存。mChangedScrap和mAttchScrap是RecyclerView内部控制的缓存，本例暂时忽略。
    - 图片解释：
        - RecyclerView包含三部分：已经出屏幕，在屏幕里面，即将进入屏幕，我们滑动的方向是向上
        - RecyclerView包含三种Type：1，2，3。屏幕里面的都是Type=3
        - 红色的线代表已经出屏幕的ViewHolder与Recycler的交互情况
        - 绿色的线代表，即将进入屏幕的ViewHolder进入屏幕时候，ViewHolder与Recycler的交互情况
    - 出屏幕时候的情况
        - 当ViewHolder（position=0，type=1）出屏幕的时候，由于mCacheViews是空的，那么就直接放在mCacheViews里面，ViewHolder在mCacheViews里面布局和内容都是有效的，因此可以直接复用。
        ViewHolder（position=1，type=2）同步骤1
        - 当ViewHolder（position=2，type=1）出屏幕的时候由于一级缓存mCacheViews已经满了，因此将其放入RecyclerPool（type=1）的缓存池里面。此时ViewHolder的内容会被标记为无效，当其复用的时候需要再次通过Adapter.bindViewHolder来绑定内容。
        ViewHolder（position=3，type=2）同步骤3
    - 进屏幕时候的情况
        - 当ViewHolder（position=3-10，type=3）进入屏幕绘制的时候，由于Recycler的mCacheViews里面找不到position匹配的View，同时RecyclerPool里面找不到type匹配的View，因此，其只能通过adapter.createViewHolder来创建ViewHolder，然后通过adapter.bindViewHolder来绑定内容。
        - 当ViewHolder（position=11，type=1）进入屏幕的时候，发现ReccylerPool里面能找到type=1的缓存，因此直接从ReccylerPool里面取来使用。由于内容是无效的，因此还需要调用bindViewHolder来绑定布局。同时ViewHolder（position=4，type=3）需要出屏幕，其直接进入RecyclerPool（type=3）的缓存池中
        - ViewHolder（position=12，type=2）同步骤6
    - 屏幕往下拉ViewHolder（position=1）进入屏幕的情况
        - 由于mCacheView里面的有position=1的ViewHolder与之匹配，直接返回。由于内容是有效的，因此无需再次绑定内容
        - ViewHolder（position=0）同步骤8
























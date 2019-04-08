#### 优化逻辑目录介绍
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





### 04.瀑布流图片错乱问题解决



### 05.关于点击事件放在哪里优化
- 关于rv设置item条目点击事件有两种方式：1.在onCreateViewHolder中写；2.在onBindViewHolder中写；3.在ViewHolder中写。那么究竟是哪一种好呢？
    - 1.在onCreateViewHolder中写
        ```
        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            final View view = LayoutInflater.from(mContext).inflate(R.layout.item_me_gv_grid, parent, false);
            final MyViewHolder holder = new MyViewHolder(view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onItemClick(view, holder.getLayoutPosition());
                    }
                }
            });
            return holder;
        }
        ```
    - 2.在onBindViewHolder中写
        ```
        @Override
        public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onItemClick(holder.itemView, holder.getAdapterPosition());
                    }
                }
            });
        }
        ```
    - 3.在ViewHolder中写
        ```
        class MyViewHolder extends RecyclerView.ViewHolder {
            MyViewHolder(final View itemView) {
                super(itemView);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (listener != null) {
                            listener.onItemClick(itemView, getAdapterPosition());
                        }
                    }
                });
            }
        }
        ```
- onBindViewHolder() 中频繁创建新的 onClickListener 实例没有必要，建议实际开发中应该在 onCreateViewHolder() 中每次为新建的 View 设置一次就行。



### 08.拖拽排序与滑动删除优化
- 拖拽效果优化
    - 在item被拖拽或侧滑时修改背景色，当动作结束后将背景色恢复回来，而ItemTouchHelper.Callback中正好有对应这两个状态的方法，分别是：onSelectedChanged()、clearView()。那么优化处理其实可以放到这两个方法中处理。
    - 左右滑动使item透明度变浅且缩小该如何实现呢？让item执行了两种属性动画而已，在ItemTouchHelper.Callback中有一个方法可以拿到item被拖拽或滑动时的位移变化，那就是onChildDraw()方法，在该方法中设置item渐变和缩放属性动画。
    - 出现问题，按照上面做法会出现删除后有空白item留出来，那么为什么会出现这种情况呢？并不是多出了两条空白数据，它们是正常的数据，只是看不到了，这是因为RecyclerView条目（itemView）覆用导致的，前面在onChildDraw()方法中对itemView设置了透明和缩小，而一个列表中固定只有几个itemView而已，当那两个透明缩小的itemView被再次使用时，之前设置的透明度和高度比例已经是0，所以就出现了这种情况，解决方法也很简单，只要在item被移除后，将itemView的透明度和高度比例设置回来即可。



### 10.状态切换交互优化




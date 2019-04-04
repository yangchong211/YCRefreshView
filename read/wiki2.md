#### 优化逻辑目录介绍
- 01.接口分离优化
- 02.去除淡黄色警告
- 03.SparseArray替代HashMap
- 04.瀑布流图片错乱问题解决
- 05.关于点击事件放在哪里优化






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












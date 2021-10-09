package org.yczbj.ycrefreshview.staggered;


import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.yczbj.ycrefreshview.R;
import org.yczbj.ycrefreshview.data.PersonData;
import org.yczbj.ycrefreshviewlib.inter.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;


public class StageredLoadMoreAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private Context mContext;
    private OnItemClickListener listener;
    /**
     * 第一种ViewType，正常的item
     */
    private int normalType = 0;
    /**
     * 第二种ViewType，底部的提示View
     */
    private int footType = 1;
    /**
     * 变量，是否有更多数据
     */
    private boolean hasMore;
    /**
     * 变量，是否隐藏了底部的提示
     */
    private boolean fadeTips = false;
    /**
     * 获取主线程的Handler
     */
    private Handler mHandler = new Handler(Looper.getMainLooper());



    public StageredLoadMoreAdapter(Context context, boolean hasMore) {
        this.mContext = context;
        this.hasMore = hasMore;
    }

    private List<PersonData> data = new ArrayList<>();

    public void setData(List<PersonData> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public void setOnClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    /**
     * 暴露接口，改变fadeTips的方法
     */
    public boolean isFadeTips() {
        return fadeTips;
    }

    /**
     * 暴露接口，更新数据源，并修改hasMore的值，如果有增加数据，hasMore为true，否则为false
     */
    public void updateList(List<PersonData> newDatas, boolean hasMore) {
        int size = data.size();
        // 在原有的数据之上增加新数据
        if (newDatas != null) {
            data.addAll(newDatas);
            this.hasMore = hasMore;
            notifyItemRangeInserted(size,newDatas.size());
        }
    }

    /**
     * 自定义方法，获取列表中数据源的最后一个位置，比getItemCount少1，因为不计上footView
     */
    public int getRealLastPosition() {
        return data.size();
    }


    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
        if (layoutParams != null && layoutParams instanceof StaggeredGridLayoutManager.LayoutParams) {
            StaggeredGridLayoutManager.LayoutParams params = (StaggeredGridLayoutManager.LayoutParams) layoutParams;
            int position = holder.getLayoutPosition();
            //如果是上拉加载更多类型，则设置setFullSpan为true，那么它就会占一行
            if (getItemViewType(position) == footType) {
                params.setFullSpan(true);
            }
        }
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // 根据返回的ViewType，绑定不同的布局文件，这里只有两种
        if (viewType == normalType) {
            return new MyViewHolder(LayoutInflater.from(mContext)
                    .inflate(R.layout.item_image, parent,false));
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




    class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView iv_news_image;

        MyViewHolder(final View itemView) {
            super(itemView);
            iv_news_image = itemView.findViewById(R.id.iv_news_image);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onItemClick(getAdapterPosition());
                    }
                }
            });
        }
    }


    class FootHolder extends RecyclerView.ViewHolder {

        private TextView tv_more;

        FootHolder(View itemView) {
            super(itemView);
            tv_more = itemView.findViewById(R.id.tv_more);
        }
    }


    private void setBindViewHolder(MyViewHolder holder, PersonData person, int position) {
        ViewGroup.LayoutParams params = holder.iv_news_image.getLayoutParams();
        int setHeight = position%5;
        //计算View的高度
        int height = 300;
        switch (setHeight){
            case 0:
                height = 500;
                break;
            case 1:
                height = 750;
                break;
            case 2:
                height = 880;
                break;
            case 3:
                height = 360;
                break;
            case 4:
                height = 660;
                break;
            default:
                break;
        }
        params.height = height;
        holder.iv_news_image.setLayoutParams(params);
        Glide.with(holder.iv_news_image.getContext())
                .load(person.getImage())
                .error(R.drawable.bg_small_tree_min)
                .placeholder(R.drawable.bg_small_tree_min)
                .into(holder.iv_news_image);
    }


    private void setFootBindViewHolder(final FootHolder holder, int position) {
        // 之所以要设置可见，是因为我在没有更多数据时会隐藏了这个footView
        holder.tv_more.setVisibility(View.VISIBLE);
        // 只有获取数据为空时，hasMore为false，所以当我们拉到底部时基本都会首先显示“正在加载更多...”
        if (hasMore) {
            // 不隐藏footView提示
            fadeTips = false;
            if (data.size() > 0) {
                // 如果查询数据发现增加之后，就显示正在加载更多
                holder.tv_more.setText("逗比，正在加载更多...");
            }
        } else {
            if (data.size() > 0) {
                // 如果查询数据发现并没有增加时，就显示没有更多数据了
                holder.tv_more.setText("逗比，没有更多数据了");

                // 然后通过延时加载模拟网络请求的时间，在500ms后执行
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // 隐藏提示条
                        holder.tv_more.setVisibility(View.GONE);
                        // 将fadeTips设置true
                        fadeTips = true;
                        // hasMore设为true是为了让再次拉到底时，会先显示正在加载更多
                        hasMore = true;
                    }
                }, 500);
            }
        }
    }


}

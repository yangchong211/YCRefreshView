package org.yczbj.ycrefreshview.select;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.yc.selectviewlib.SelectRecyclerViewAdapter;

import org.yczbj.ycrefreshview.R;

import java.util.List;


/**
 * <pre>
 *     @author yangchong
 *     blog  : https://github.com/yangchong211
 *     time  : 2016/03/22
 *     desc  : 关注点页面适配器
 *     revise:
 * </pre>
 */
public class SelectFollowAdapter extends SelectRecyclerViewAdapter<SelectFollowAdapter.MyViewHolder> {

    public Activity activity;
    public List<SelectPoint> data;

    public SelectFollowAdapter(Activity activity, List<SelectPoint> data) {
        this.activity = activity;
        this.data = data;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.tag_select_follow, parent, false);
        return new MyViewHolder(view , mItemClickListener);
    }


    @SuppressLint("MissingSuperCall")
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        if(data!=null && data.size()>0){
            holder.tvName.setText(data.get(position).getName());
            if(isIndexSelected(position)){
                holder.tvName.setBackgroundResource(R.drawable.shape_btn_color_bg_press);
                holder.tvName.setTextColor(activity.getResources().getColor(R.color.colorAccent));
            }else {
                holder.tvName.setBackgroundResource(R.drawable.shape_btn_color_bg);
                holder.tvName.setTextColor(activity.getResources().getColor(R.color.color_666666));
            }
        }
    }


    @Override
    public int getItemCount() {
        return data!=null ? data.size() : 0;
    }


    class MyViewHolder extends  RecyclerView.ViewHolder{

        private  OnListItemClickListener listener;
        TextView tvName;
        MyViewHolder(View itemView , OnListItemClickListener mItemClickListener) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            this.listener = mItemClickListener;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(v,getAdapterPosition());
                }
            });
        }

    }

    private OnListItemClickListener mItemClickListener;
    public void setOnItemClickListener(OnListItemClickListener listener) {
        this.mItemClickListener = listener;
    }

    public interface OnListItemClickListener {

        void onItemClick(View view, int position);

    }
}

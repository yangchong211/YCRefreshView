package org.yczbj.ycrefreshview.type;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
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


public class TypeAdapter extends RecyclerView.Adapter<TypeAdapter.MyViewHolder> {


    private Context mContext;
    private OnItemClickListener listener;

    public TypeAdapter(Context context) {
        this.mContext = context;
    }

    private List<PersonData> data = new ArrayList<>();

    public void setData(List<PersonData> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public List<PersonData> getData() {
        return data;
    }

    public void setOnClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_news, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        PersonData person = data.get(position);
        if (person.getName()==null || person.getName().length()==0){
            holder.tv_content.setText("小杨逗比"+position);
        }else {
            holder.tv_title.setText(person.getName());
        }
        if (person.getSign()==null || person.getSign().length()==0){
            holder.tv_content.setText("这个是内容"+position);
        }else {
            holder.tv_content.setText(person.getSign());
        }


        Glide.with(holder.iv_news_image.getContext())
                .load(person.getImage())
                .error(R.drawable.bg_small_tree_min)
                .placeholder(R.drawable.bg_small_tree_min)
                .into(holder.iv_news_image);
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_title;
        private ImageView iv_news_image;
        private TextView tv_content;

        MyViewHolder(final View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_title);
            iv_news_image = itemView.findViewById(R.id.iv_news_image);
            tv_content = itemView.findViewById(R.id.tv_content);
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

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }


}

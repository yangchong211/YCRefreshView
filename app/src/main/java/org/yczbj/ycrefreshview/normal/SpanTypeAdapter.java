package org.yczbj.ycrefreshview.normal;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
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


public class SpanTypeAdapter extends RecyclerView.Adapter<SpanTypeAdapter.MyViewHolder> {


    private Context mContext;
    private OnItemClickListener listener;

    public SpanTypeAdapter(Context context) {
        this.mContext = context;
    }

    private List<SpanModel> data = new ArrayList<>();

    public void setData(List<SpanModel> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public List<SpanModel> getData() {
        return data;
    }

    public void setOnClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_news_content, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        SpanModel person = data.get(position);
        if (person.getName()==null || person.getName().length()==0){
            holder.tv_content.setText("小杨逗比"+position);
        }else {
            holder.tv_title.setText(person.getName());
        }
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_title;
        private TextView tv_content;

        MyViewHolder(final View itemView) {
            super(itemView);
            itemView.setBackgroundResource(R.color.colorAccent);
            tv_title = itemView.findViewById(R.id.tv_title);
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

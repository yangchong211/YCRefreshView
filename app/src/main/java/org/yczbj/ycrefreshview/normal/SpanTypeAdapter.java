package org.yczbj.ycrefreshview.normal;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.yczbj.ycrefreshview.R;
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
            holder.tvContent.setText("小杨逗比"+position);
        }else {
            holder.tvTitle.setText(person.getName());
        }
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        private SparseArray<View> viewSparseArray;
        private TextView tvTitle;
        private TextView tvContent;

        MyViewHolder(final View itemView) {
            super(itemView);
            if(viewSparseArray==null){
                viewSparseArray = new SparseArray<>();
            }
            itemView.setBackgroundResource(R.color.colorAccent);
            tvTitle = (TextView) viewSparseArray.get(R.id.tv_title);
            tvContent = (TextView) viewSparseArray.get(R.id.tv_content);
            if (tvTitle == null) {
                tvTitle = itemView.findViewById(R.id.tv_title);
                viewSparseArray.put(R.id.tv_title, tvTitle);
            }
            if (tvContent == null) {
                tvContent = itemView.findViewById(R.id.tv_content);
                viewSparseArray.put(R.id.tv_content, tvContent);
            }
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

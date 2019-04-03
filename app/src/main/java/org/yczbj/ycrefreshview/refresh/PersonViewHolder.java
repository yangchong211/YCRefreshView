package org.yczbj.ycrefreshview.refresh;

import android.util.Log;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;


import org.yczbj.ycrefreshview.R;
import org.yczbj.ycrefreshview.data.PersonData;
import org.yczbj.ycrefreshviewlib.holder.BaseViewHolder;


public class PersonViewHolder extends BaseViewHolder<PersonData> {

    private TextView tv_title;
    private ImageView iv_news_image;
    private TextView tv_content;
    private Button btn_del;
    private Button btn_top;


    public PersonViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_news);
        iv_news_image = getView(R.id.iv_news_image);
        tv_title = getView(R.id.tv_title);
        tv_content = getView(R.id.tv_content);
        btn_del = getView(R.id.btn_del);
        btn_top = getView(R.id.btn_top);
    }

    @Override
    public void setData(final PersonData person){
        Log.i("ViewHolder","position"+getDataPosition());
        tv_title.setText(person.getName());
        tv_content.setText(person.getSign());
        Glide.with(getContext())
                .load(person.getImage())
                .error(R.drawable.bg_small_tree_min)
                .into(iv_news_image);
    }
}

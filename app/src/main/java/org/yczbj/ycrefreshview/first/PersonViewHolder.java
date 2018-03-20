package org.yczbj.ycrefreshview.first;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;


import org.yczbj.ycrefreshview.R;
import org.yczbj.ycrefreshview.other.Person;
import org.yczbj.ycrefreshviewlib.swipeMenu.OnSwipeMenuListener;
import org.yczbj.ycrefreshviewlib.viewHolder.BaseViewHolder;

import jp.wasabeef.glide.transformations.CropCircleTransformation;



public class PersonViewHolder extends BaseViewHolder<Person> {

    private TextView tv_title;
    private ImageView iv_news_image;
    private TextView tv_content;


    public PersonViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_news);
        iv_news_image = getView(R.id.iv_news_image);
        tv_title = getView(R.id.tv_title);
        tv_content = getView(R.id.tv_content);
    }

    @Override
    public void setData(final Person person){
        Log.i("ViewHolder","position"+getDataPosition());
        tv_title.setText(person.getName());
        tv_content.setText(person.getSign());
        Glide.with(getContext())
                .load(person.getFace())
                .error(R.drawable.bg_small_tree_min)
                .placeholder(R.drawable.default_image)
                .bitmapTransform(new CropCircleTransformation(getContext()))
                .into(iv_news_image);
    }
}

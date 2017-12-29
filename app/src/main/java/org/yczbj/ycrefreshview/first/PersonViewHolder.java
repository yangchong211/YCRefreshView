package org.yczbj.ycrefreshview.first;

import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;


import org.yczbj.ycrefreshview.R;
import org.yczbj.ycrefreshview.other.Person;
import org.yczbj.ycrefreshviewlib.viewHolder.BaseViewHolder;

import jp.wasabeef.glide.transformations.CropCircleTransformation;



public class PersonViewHolder extends BaseViewHolder<Person> {
    private TextView mTv_name;
    private ImageView mImg_face;
    private TextView mTv_sign;


    public PersonViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_person);
        mTv_name = getView(R.id.person_name);
        mTv_sign = getView(R.id.person_sign);
        mImg_face = getView(R.id.person_face);
    }

    @Override
    public void setData(final Person person){
        Log.i("ViewHolder","position"+getDataPosition());
        mTv_name.setText(person.getName());
        mTv_sign.setText(person.getSign());
        Glide.with(getContext())
                .load(person.getFace())
                .placeholder(R.drawable.default_image)
                .bitmapTransform(new CropCircleTransformation(getContext()))
                .into(mImg_face);
    }
}

package org.yczbj.ycrefreshview.first;

import android.content.Context;
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
import org.yczbj.ycrefreshviewlib.adapter.RecyclerArrayAdapter;

import jp.wasabeef.glide.transformations.CropCircleTransformation;


/**
 * Created by Mr.Jude on 2015/7/18.
 */
public class PersonAdapter extends RecyclerArrayAdapter<Person> {
    public PersonAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new PersonViewHolder(parent);
    }

    private OnSwipeMenuListener listener;
    public void setOnSwipeMenuListener(OnSwipeMenuListener listener) {
        this.listener = listener;
    }

    public class PersonViewHolder extends BaseViewHolder<Person> {

        private TextView mTv_name;
        private ImageView mImg_face;
        private TextView mTv_sign;
        private Button btn_del;
        private Button btn_top;

        public PersonViewHolder(ViewGroup parent) {
            super(parent, R.layout.item_person);
            mTv_name = getView(R.id.person_name);
            mTv_sign = getView(R.id.person_sign);
            mImg_face = getView(R.id.person_face);
            btn_del = getView(R.id.btn_del);
            btn_top = getView(R.id.btn_top);
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

            View.OnClickListener clickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (v.getId()){
                        case R.id.btn_del:
                            if (null != listener) {
                                listener.toDelete(getAdapterPosition());
                            }
                            break;
                        case R.id.btn_top:
                            if (null != listener) {
                                listener.toTop(getAdapterPosition());
                            }
                            break;
                    }
                }
            };

            btn_del.setOnClickListener(clickListener);
            btn_top.setOnClickListener(clickListener);
        }
    }


}

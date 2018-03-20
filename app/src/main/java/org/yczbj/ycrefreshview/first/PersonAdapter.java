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

        private TextView tv_title;
        private ImageView iv_news_image;
        private TextView tv_content;
        private Button btn_del;
        private Button btn_top;


        PersonViewHolder(ViewGroup parent) {
            super(parent, R.layout.item_news);
            iv_news_image = getView(R.id.iv_news_image);
            tv_title = getView(R.id.tv_title);
            tv_content = getView(R.id.tv_content);
            btn_del = getView(R.id.btn_del);
            btn_top = getView(R.id.btn_top);
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

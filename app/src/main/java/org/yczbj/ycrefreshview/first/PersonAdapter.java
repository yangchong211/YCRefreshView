package org.yczbj.ycrefreshview.first;

import android.content.Context;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.yczbj.ycrefreshview.R;
import org.yczbj.ycrefreshview.data.PersonData;
import org.yczbj.ycrefreshviewlib.holder.BaseViewHolder;
import org.yczbj.ycrefreshviewlib.adapter.RecyclerArrayAdapter;


public class PersonAdapter extends RecyclerArrayAdapter<PersonData> {

    public PersonAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new PersonViewHolder(parent);
    }

    public class PersonViewHolder extends BaseViewHolder<PersonData> {

        private TextView tv_title;
        private ImageView iv_news_image;
        private TextView tv_content;


        PersonViewHolder(ViewGroup parent) {
            super(parent, R.layout.item_news);
            iv_news_image = getView(R.id.iv_news_image);
            tv_title = getView(R.id.tv_title);
            tv_content = getView(R.id.tv_content);

            addOnClickListener(R.id.iv_news_image);
            addOnClickListener(R.id.tv_title);
        }

        @Override
        public void setData(final PersonData person){
            Log.i("ViewHolder","position"+getDataPosition());
            tv_title.setText(person.getName());
            tv_content.setText(person.getSign());
            Glide.with(getContext())
                    .load(person.getImage())
                    .error(R.drawable.bg_small_tree_min)
                    .placeholder(R.drawable.bg_small_tree_min)
                    .into(iv_news_image);

        }
    }


}

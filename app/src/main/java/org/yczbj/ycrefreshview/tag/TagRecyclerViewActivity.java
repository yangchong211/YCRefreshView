package org.yczbj.ycrefreshview.tag;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.yczbj.ycrefreshview.R;
import org.yczbj.ycrefreshview.app.SysUtils;
import org.yczbj.ycrefreshview.data.AppUtils;
import org.yczbj.ycrefreshview.data.DataProvider;
import org.yczbj.ycrefreshview.data.PersonData;
import org.yczbj.ycrefreshview.refresh.PersonAdapter;
import org.yczbj.ycrefreshviewlib.adapter.RecyclerArrayAdapter;
import org.yczbj.ycrefreshviewlib.holder.BaseViewHolder;
import org.yczbj.ycrefreshviewlib.inter.OnErrorListener;
import org.yczbj.ycrefreshviewlib.inter.OnItemLongClickListener;
import org.yczbj.ycrefreshviewlib.inter.OnLoadMoreListener;
import org.yczbj.ycrefreshviewlib.inter.OnNoMoreListener;
import org.yczbj.ycrefreshviewlib.item.RecycleViewItemLine;

/**
 * @author yc
 */
public class TagRecyclerViewActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);
        initRecyclerView();
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        //画笔用来计算文字的宽度
        final Paint mSearchHistoryPaint = new Paint();
        mSearchHistoryPaint.setTextSize(SysUtils.Dp2Px(this,12));
        final int width = SysUtils.getScreenWidth(this);
        final int mPaddingSize = SysUtils.Dp2Px(this,40);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, width);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                //就是这里 需要测量文字的宽度，加上左右内边距
                int textWidth = (int) mSearchHistoryPaint.measureText(DataProvider.getTag().get(position))
                        + mPaddingSize;
                //如果文字的宽度超过屏幕的宽度，那么我们就设置为屏幕宽度
                return textWidth > width ? width : textWidth;
            }
        });
        recyclerView.setLayoutManager(gridLayoutManager);
        PersonAdapter adapter = new PersonAdapter(this);
        recyclerView.setAdapter(adapter);
        adapter.addAll(DataProvider.getTag());
    }

    public class PersonAdapter extends RecyclerArrayAdapter<String> {

        PersonAdapter(Context context) {
            super(context);
        }

        @Override
        public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
            return new PersonViewHolder(parent);
        }

        public class PersonViewHolder extends BaseViewHolder<String> {

            private TextView tv;

            PersonViewHolder(ViewGroup parent) {
                super(parent, R.layout.item_tag);
                tv = getView(R.id.tv);
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void setData(final String string){
                tv.setText(string);
            }
        }


    }


}

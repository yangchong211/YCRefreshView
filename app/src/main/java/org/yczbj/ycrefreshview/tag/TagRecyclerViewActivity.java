package org.yczbj.ycrefreshview.tag;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.ViewGroup;
import android.widget.TextView;

import org.yczbj.ycrefreshview.R;
import org.yczbj.ycrefreshview.app.SysUtils;
import org.yczbj.ycrefreshview.data.DataProvider;
import org.yczbj.ycrefreshviewlib.adapter.RecyclerArrayAdapter;
import org.yczbj.ycrefreshviewlib.holder.BaseViewHolder;

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

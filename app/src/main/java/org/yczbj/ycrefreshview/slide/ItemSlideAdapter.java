package org.yczbj.ycrefreshview.slide;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.yc.cn.slideview.SlideHelper;
import com.yc.cn.slideview.SlideViewHolder;

import org.yczbj.ycrefreshview.R;

import java.util.List;

/**
 * ================================================
 * 作    者：杨充
 * 版    本：1.0
 * 创建日期：2017/7/3
 * 描    述：我的项目页面 适配器adapter
 * 修订历史：
 * ================================================
 */
public class ItemSlideAdapter extends RecyclerView.Adapter {

    private Activity activity;
    private SlideHelper mISlideHelper = new SlideHelper();
    private List<YCBean> mData;
    private OnDeleteClickListener mDeleteListener;

    ItemSlideAdapter(Activity activity, OnDeleteClickListener mDeleteListener) {
        this.activity = activity;
        this.mDeleteListener = mDeleteListener;
    }

    public void setData(List<YCBean> mData) {
        this.mData = mData;
        notifyDataSetChanged();
    }

    void slideOpen() {
        mISlideHelper.slideOpen();
    }

    void slideClose() {
        mISlideHelper.slideClose();
    }

    private HhItemClickListener mItemClickListener;
    void setOnItemClickListener(HhItemClickListener listener) {
        this.mItemClickListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_slide_view, parent, false);
        OneSlideViewHolder oneSlideViewHolder = new OneSlideViewHolder(inflate, mItemClickListener, mDeleteListener);
        mISlideHelper.add(oneSlideViewHolder);
        return oneSlideViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((OneSlideViewHolder) holder).bind(position);
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    private class OneSlideViewHolder extends SlideViewHolder implements View.OnClickListener {

        private OnDeleteClickListener deleteListener;
        private HhItemClickListener mListener;

        private RelativeLayout rl_item;
        private FrameLayout fl_menu;
        private TextView item_tv_delete, tv_go_edit, tv_project_title;

        private OneSlideViewHolder(View view, final HhItemClickListener mItemClickListener, OnDeleteClickListener mDeleteListener) {
            super(view);
            this.mListener = mItemClickListener;
            this.deleteListener = mDeleteListener;

            rl_item = (RelativeLayout) view.findViewById(R.id.rl_item);
            fl_menu = (FrameLayout) view.findViewById(R.id.fl_menu);
            item_tv_delete = (TextView) view.findViewById(R.id.item_tv_delete);
            tv_go_edit = (TextView) view.findViewById(R.id.tv_go_edit);
            tv_project_title = (TextView) view.findViewById(R.id.tv_project_title);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListener != null) {
                        mListener.onItemClick(view, getAdapterPosition());
                    }
                }
            });

            item_tv_delete.setOnClickListener(this);
            tv_go_edit.setOnClickListener(this);
            fl_menu.setVisibility(View.GONE);
        }

        @Override
        public void doAnimationSet(int offset, float fraction) {
            rl_item.scrollTo(offset, 0);
        }

        @Override
        public void onBindSlideClose(int state) {
            fl_menu.setVisibility(View.VISIBLE);
        }

        @Override
        public void doAnimationSetOpen(int state) {
            fl_menu.setVisibility(View.VISIBLE);
        }

        void bind(int position) {
            setOffset(70);
            onBindSlide(rl_item);
            tv_project_title.setText(mData.get(position).getName());
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.item_tv_delete:
                    if (deleteListener != null) {
                        deleteListener.onEditDeleteClick(view, item_tv_delete, getAdapterPosition());
                    }
                    break;
                case R.id.tv_go_edit:

                    break;
                default:
                    break;
            }
        }
    }
}

package org.yczbj.ycrefreshviewlib.item;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import org.yczbj.ycrefreshviewlib.adapter.RecyclerArrayAdapter;


/**
 * <pre>
 *     @author 杨充
 *     blog  : https://github.com/yangchong211
 *     time  : 2017/5/2
 *     desc  : list条目的分割线
 *     revise:
 * </pre>
 */
public class DividerViewItemLine extends RecyclerView.ItemDecoration{

    private ColorDrawable mColorDrawable;
    private int mHeight;
    private int mPaddingLeft;
    private int mPaddingRight;
    private boolean mDrawLastItem = true;
    private boolean mDrawHeaderFooter = false;

    public DividerViewItemLine(int color, int height) {
        this.mColorDrawable = new ColorDrawable(color);
        this.mHeight = height;
    }

    public DividerViewItemLine(int color, int height, int paddingLeft, int paddingRight) {
        this.mColorDrawable = new ColorDrawable(color);
        this.mHeight = height;
        this.mPaddingLeft = paddingLeft;
        this.mPaddingRight = paddingRight;
    }

    public void setDrawLastItem(boolean mDrawLastItem) {
        this.mDrawLastItem = mDrawLastItem;
    }

    public void setDrawHeaderFooter(boolean mDrawHeaderFooter) {
        this.mDrawHeaderFooter = mDrawHeaderFooter;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view,
                               @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view);
        int orientation = 0;
        int headerCount = 0,footerCount = 0;
        if (parent.getAdapter()==null){
            return;
        }
        if (parent.getAdapter() instanceof RecyclerArrayAdapter){
            headerCount = ((RecyclerArrayAdapter) parent.getAdapter()).getHeaderCount();
            footerCount = ((RecyclerArrayAdapter) parent.getAdapter()).getFooterCount();
        }

        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof StaggeredGridLayoutManager){
            orientation = ((StaggeredGridLayoutManager) layoutManager).getOrientation();
        }else if (layoutManager instanceof GridLayoutManager){
            orientation = ((GridLayoutManager) layoutManager).getOrientation();
        }else if (layoutManager instanceof LinearLayoutManager){
            orientation = ((LinearLayoutManager) layoutManager).getOrientation();
        }
        int itemCount = parent.getAdapter().getItemCount();
        if (position>=headerCount && position<itemCount-footerCount||mDrawHeaderFooter){
            if (orientation == OrientationHelper.VERTICAL){
                outRect.bottom = mHeight;
            }else {
                outRect.right = mHeight;
            }
        }
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if (parent.getAdapter() == null){
            return;
        }
        int orientation = 0;
        int headerCount = 0, footerCount = 0 , dataCount;
        if (parent.getAdapter() instanceof RecyclerArrayAdapter){
            headerCount = ((RecyclerArrayAdapter) parent.getAdapter()).getHeaderCount();
            footerCount = ((RecyclerArrayAdapter) parent.getAdapter()).getFooterCount();
            dataCount = ((RecyclerArrayAdapter) parent.getAdapter()).getCount();
        }else {
            dataCount = parent.getAdapter().getItemCount();
        }
        int dataStartPosition = headerCount;
        int dataEndPosition = headerCount+dataCount;

        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof StaggeredGridLayoutManager){
            orientation = ((StaggeredGridLayoutManager) layoutManager).getOrientation();
        }else if (layoutManager instanceof GridLayoutManager){
            orientation = ((GridLayoutManager) layoutManager).getOrientation();
        }else if (layoutManager instanceof LinearLayoutManager){
            orientation = ((LinearLayoutManager) layoutManager).getOrientation();
        }
        int start,end;
        if (orientation == OrientationHelper.VERTICAL){
            start = parent.getPaddingLeft() + mPaddingLeft;
            end = parent.getWidth() - parent.getPaddingRight() - mPaddingRight;
        }else {
            start = parent.getPaddingTop() + mPaddingLeft;
            end = parent.getHeight() - parent.getPaddingBottom() - mPaddingRight;
        }

        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            int position = parent.getChildAdapterPosition(child);
            //数据项除了最后一项 数据项最后一项
            //header&footer且可绘制
            if (position>=dataStartPosition&&position<dataEndPosition-1
                    ||(position == dataEndPosition-1&&mDrawLastItem)
                    ||(!(position>=dataStartPosition&&position<dataEndPosition)&&mDrawHeaderFooter)
                    ){

                if (orientation == OrientationHelper.VERTICAL){
                    RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
                    int top = child.getBottom() + params.bottomMargin;
                    int bottom = top + mHeight;
                    mColorDrawable.setBounds(start,top,end,bottom);
                    mColorDrawable.draw(c);
                }else {
                    RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
                    int left = child.getRight() + params.rightMargin;
                    int right = left + mHeight;
                    mColorDrawable.setBounds(left,start,right,end);
                    mColorDrawable.draw(c);
                }
            }
        }
    }


}
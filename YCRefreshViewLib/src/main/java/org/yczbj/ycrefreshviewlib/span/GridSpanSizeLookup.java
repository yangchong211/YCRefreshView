package org.yczbj.ycrefreshviewlib.span;

import android.support.v7.widget.GridLayoutManager;

import org.yczbj.ycrefreshviewlib.inter.InterItemView;

import java.util.ArrayList;

/**
 * <pre>
 *     @author 杨充
 *     blog  : https://github.com/yangchong211
 *     time  : 2017/5/2
 *     desc  : 自定义SpanSizeLookup
 *     revise:
 * </pre>
 */
public class GridSpanSizeLookup extends GridLayoutManager.SpanSizeLookup{

    private int mMaxCount;
    private ArrayList<InterItemView> headers;
    private ArrayList<InterItemView> footers;
    /**
     * 这个是所有数据的size
     */
    private int mSize;


    public GridSpanSizeLookup(int maxCount, ArrayList<InterItemView> headers,
                       ArrayList<InterItemView> footers, int size){
        this.mMaxCount = maxCount;
        this.headers = headers;
        this.footers = footers;
        this.mSize = size;
    }

    /**
     * 该方法的返回值就是指定position所占的列数
     * @param position                      指定索引
     * @return                              列数
     */
    @Override
    public int getSpanSize(int position) {
        //如果有headerView，则
        if (headers.size()!=0){
            if (position<headers.size()) {
                return mMaxCount;
            }
        }
        //如果有footerView，则
        if (footers.size()!=0) {
            int i = position - headers.size() - mSize;
            if (i >= 0) {
                return mMaxCount;
            }
        }
        return 1;
    }

}

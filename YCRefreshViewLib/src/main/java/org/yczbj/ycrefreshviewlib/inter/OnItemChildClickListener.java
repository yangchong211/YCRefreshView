package org.yczbj.ycrefreshviewlib.inter;

import android.view.View;


/**
 * <pre>
 *     @author 杨充
 *     blog  : https://github.com/yangchong211
 *     time  : 2016/4/28
 *     desc  : item中孩子点击监听接口
 *     revise:
 * </pre>
 */
public interface OnItemChildClickListener {

    /**
     * item中孩子点击监听接口
     * @param view                  view
     * @param position              position索引
     */
    void onItemChildClick(View view, int position);
}

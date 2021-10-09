package org.yczbj.ycrefreshview.slide;

import android.view.View;
import android.widget.TextView;

/**
 * ================================================
 * 作    者：杨充
 * 版    本：1.0
 * 创建日期：2017/7/3
 * 描    述：item侧滑后，点击删除接口
 * 修订历史：
 * ================================================
 */
public interface OnDeleteClickListener {
    void onEditDeleteClick(View view, TextView textView, int position);
}

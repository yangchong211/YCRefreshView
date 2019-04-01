package org.yczbj.ycrefreshviewlib.inter;

import android.view.View;
import android.view.ViewGroup;
/**
 * <pre>
 *     @author yangchong
 *     blog  : https://blog.csdn.net/m0_37700275/article/details/80863685
 *     time  : 2017/4/22
 *     desc  :
 *     revise: 支持多种状态切换；支持上拉加载更多，下拉刷新；支持添加头部或底部view
 * </pre>
 */
public interface InterItemView {
    /**
     * 创建view
     * @param parent            parent
     * @return                  view
     */
    View onCreateView(ViewGroup parent);

    /**
     * 绑定view
     * @param headerView        headerView
     */
    void onBindView(View headerView);
}

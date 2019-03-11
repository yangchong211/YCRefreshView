package org.yczbj.ycrefreshviewlib.inter;

import android.view.View;
import android.view.ViewGroup;

public interface ItemView {

    View onCreateView(ViewGroup parent);
    void onBindView(View headerView);
}

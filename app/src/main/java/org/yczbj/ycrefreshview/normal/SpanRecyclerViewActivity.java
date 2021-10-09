package org.yczbj.ycrefreshview.normal;

import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.yczbj.ycrefreshview.R;
import org.yczbj.ycrefreshview.data.AppUtils;
import org.yczbj.ycrefreshviewlib.item.SpaceViewItemLine;

import java.util.ArrayList;

/**
 * @author yc
 */
public class SpanRecyclerViewActivity extends AppCompatActivity {


    private RecyclerView recyclerView;
    private SpanTypeAdapter adapter;
    private Handler handler = new Handler();
    private ArrayList<SpanModel> mDataList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);
        initData();
        initRecyclerView();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (handler!=null){
            handler.removeCallbacksAndMessages(null);
            handler = null;
        }
    }


    private void initData() {
        mDataList = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            SpanModel model = new SpanModel();
            model.setName(i + "");
            if (i == 0) {
                model.setType(1);
            } else if (i < 7) {
                model.setType(2);
            } else if (i>=8 && i<11){
                model.setType(3);
            } else if (i>=13 && i<16){
                model.setType(4);
            } else {
                model.setType(1);
            }
            mDataList.add(model);
        }
    }

    /**
     * 先是定义了一个6列的网格布局，然后通过GridLayoutManager.SpanSizeLookup这个类来动态的指定某个item应该占多少列
     */
    private void initRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView);
        GridLayoutManager manager = new GridLayoutManager(this, 6);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                SpanModel model = mDataList.get(position);
                if (model.getType() == 1) {
                    return 6;
                } else if(model.getType() == 2){
                    return 3;
                }else if (model.getType() == 3){
                    return 2;
                }else if (model.getType() == 4){
                    return 2;
                } else {
                    return 1;
                }
            }
        });
        recyclerView.setLayoutManager(manager);
        SpaceViewItemLine itemDecoration = new SpaceViewItemLine(
                (int) AppUtils.convertDpToPixel(8,this));
        itemDecoration.setPaddingEdgeSide(true);
        itemDecoration.setPaddingStart(true);
        recyclerView.addItemDecoration(itemDecoration);
        adapter = new SpanTypeAdapter(this);
        recyclerView.setAdapter(adapter);
        adapter.setData(mDataList);
    }



}

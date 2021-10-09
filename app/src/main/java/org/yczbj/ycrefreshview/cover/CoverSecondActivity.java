package org.yczbj.ycrefreshview.cover;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.Toast;


import com.ycbjie.adapter.AbsGroupAdapter;
import com.ycbjie.adapter.GroupLayoutManager;
import com.ycbjie.adapter.GroupViewHolder;
import com.ycbjie.adapter.OnChildClickListener;
import com.ycbjie.adapter.OnFooterClickListener;
import com.ycbjie.adapter.OnHeaderClickListener;

import org.yczbj.ycrefreshview.R;

import java.util.ArrayList;
import java.util.List;

public class CoverSecondActivity extends AppCompatActivity {

    private GroupedAdapter mAdapter;
    private List<GroupEntity> list = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_recycler_view);

        RecyclerView mRecyclerView = findViewById(R.id.recycler_view);
        mAdapter = new GroupedAdapter(this, list);
        mAdapter.setOnHeaderClickListener(new OnHeaderClickListener() {
            @Override
            public void onHeaderClick(AbsGroupAdapter adapter, GroupViewHolder holder,
                                      int groupPosition) {
                Toast.makeText(CoverSecondActivity.this,
                        "组头：groupPosition = " + groupPosition, Toast.LENGTH_LONG).show();
            }
        });
        mAdapter.setOnFooterClickListener(new OnFooterClickListener() {
            @Override
            public void onFooterClick(AbsGroupAdapter adapter, GroupViewHolder holder,
                                      int groupPosition) {
                Toast.makeText(CoverSecondActivity.this,
                        "组尾：groupPosition = " + groupPosition, Toast.LENGTH_LONG).show();
                GroupEntity groupEntity = list.get(groupPosition);
                //设置footer点击后不可见状态
                groupEntity.setFooter("");
                ArrayList<ChildEntity> children = groupEntity.getChildren();
                int size = children.size();
                for (int j = 0; j < 10; j++) {
                    children.add(new ChildEntity("逗比"));
                }
                mAdapter.notifyChildRangeInserted(groupPosition,size,10);
            }
        });
        mAdapter.setOnChildClickListener(new OnChildClickListener() {
            @Override
            public void onChildClick(AbsGroupAdapter adapter, GroupViewHolder holder,
                                     int groupPosition, int childPosition) {
                Toast.makeText(CoverSecondActivity.this,"子项：groupPosition = " + groupPosition
                        + ", childPosition = " + childPosition, Toast.LENGTH_LONG).show();
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        //直接使用GroupGridLayoutManager实现子项的Grid效果
        GroupLayoutManager gridLayoutManager = new GroupLayoutManager(this, 3, mAdapter){
            //重写这个方法 改变子项的SpanSize。
            //这个跟重写SpanSizeLookup的getSpanSize方法的使用是一样的。
            @Override
            public int getChildSpanSize(int groupPosition, int childPosition) {
                return super.getChildSpanSize(groupPosition, childPosition);
            }
        };
        mRecyclerView.setLayoutManager(gridLayoutManager);

        getData();
    }



    private void getData() {
        ArrayList<GroupEntity> groups = getGroups(10, 8);
        list.addAll(groups);
        mAdapter.notifyDataSetChanged();
    }


    /**
     * 获取组列表数据
     *
     * @param groupCount    组数量
     * @param childrenCount 每个组里的子项数量
     * @return
     */
    public static ArrayList<GroupEntity> getGroups(int groupCount, int childrenCount) {
        ArrayList<GroupEntity> groups = new ArrayList<>();
        for (int i = 0; i < groupCount; i++) {
            ArrayList<ChildEntity> children = new ArrayList<>();
            for (int j = 0; j < childrenCount; j++) {
                children.add(new ChildEntity("第" + (i + 1) + "组第" + (j + 1) + "项"));
            }
            groups.add(new GroupEntity("第" + (i + 1) + "组头部",
                    "第" + (i + 1) + "组尾部", children));
        }
        return groups;
    }
}

package org.yczbj.ycrefreshview.three;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuItem;

import com.yc.cn.ycbannerlib.first.util.SizeUtil;

import org.yczbj.ycrefreshview.other.DataProvider;
import org.yczbj.ycrefreshview.R;
import org.yczbj.ycrefreshview.other.Person;
import org.yczbj.ycrefreshview.first.PersonAdapter;
import org.yczbj.ycrefreshviewlib.YCRefreshView;
import org.yczbj.ycrefreshviewlib.item.DividerViewItemLine;
import org.yczbj.ycrefreshviewlib.swipeMenu.OnSwipeMenuListener;

import java.util.List;
import java.util.Random;


public class ThirdInsertActivity extends AppCompatActivity {

    private YCRefreshView recyclerView;
    private PersonAdapter adapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refresh_view);
        recyclerView = (YCRefreshView) findViewById(R.id.recyclerView);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        DividerViewItemLine itemDecoration = new DividerViewItemLine(Color.GRAY, SizeUtil.dip2px(this, 0.5f), SizeUtil.dip2px(this, 72), 0);
        itemDecoration.setDrawLastItem(false);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setAdapterWithProgress(adapter = new PersonAdapter(this));
        adapter.setOnSwipeMenuListener(new OnSwipeMenuListener() {
            //删除功能
            @Override
            public void toDelete(int position) {
                if(adapter.getAllData().size()>position && position>-1){
                    adapter.getAllData().remove(position);
                    adapter.notifyItemRemoved(position);//推荐用这个
                }
            }

            //置顶功能
            @Override
            public void toTop(int position) {
                //先移除那个位置的数据，然后将其添加到索引为0的位置，然后刷新数据
                if (position > 0 && adapter.getAllData().size()>position) {
                    Person person = adapter.getAllData().get(position);
                    adapter.getAllData().remove(person);
                    adapter.notifyItemInserted(0);
                    adapter.getAllData().add(0, person);
                    adapter.notifyItemRemoved(position + 1);
                    if (linearLayoutManager.findFirstVisibleItemPosition() == 0) {
                        recyclerView.scrollToPosition(0);
                    }
                }
            }
        });

        List<Person> persons = DataProvider.getPersonList(0);
        adapter.addAll(persons.subList(0, 3));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_insert, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Random random = new Random();
        int len = adapter.getCount();
        if (len > 0) {
            int pos = random.nextInt(len);
//        int pos = 0;
            List<Person> persons = DataProvider.getPersonList(0);
            Person data = persons.get(random.nextInt(persons.size()));
            switch (item.getItemId()) {
                case R.id.ic_add:
                    adapter.insert(data, pos);
                    break;
                case R.id.ic_remove:
                    adapter.remove(pos);
                    break;
                case R.id.ic_refresh:
                    adapter.update(data, pos);
                    break;
            }
        }
        return true;
    }
}

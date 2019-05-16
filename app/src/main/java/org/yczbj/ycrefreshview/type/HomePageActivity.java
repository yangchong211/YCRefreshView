/*
Copyright 2017 yangchong211（github.com/yangchong211）

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

package org.yczbj.ycrefreshview.type;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import org.yczbj.ycrefreshview.R;

import java.util.ArrayList;


public class HomePageActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private HomePageAdapter adapter;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);
        initRecyclerView();
        initData();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (handler!=null){
            handler.removeCallbacksAndMessages(null);
            handler = null;
        }
    }

    private void initRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new HomePageAdapter(this);
        recyclerView.setAdapter(adapter);
    }


    private void initData() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ArrayList<HomePageEntry> list = new ArrayList<>();
                //第一条是banner
                list.add(new HomePageEntry(0));
                //第二组
                list.add(new HomePageEntry(3));
                list.add(new HomePageEntry(4));
                list.add(new HomePageEntry(4));
                list.add(new HomePageEntry(4));
                //第三组
                list.add(new HomePageEntry(1));
                //第四组
                list.add(new HomePageEntry(3));
                list.add(new HomePageEntry(2));
                list.add(new HomePageEntry(2));
                list.add(new HomePageEntry(2));
                list.add(new HomePageEntry(2));
                list.add(new HomePageEntry(2));
                list.add(new HomePageEntry(2));
                //第五组
                //第六组
                list.add(new HomePageEntry(3));
                list.add(new HomePageEntry(4));
                list.add(new HomePageEntry(4));
                list.add(new HomePageEntry(4));
                //第七组
                list.add(new HomePageEntry(3));
                list.add(new HomePageEntry(2));
                list.add(new HomePageEntry(2));
                list.add(new HomePageEntry(2));
                list.add(new HomePageEntry(2));
                //第八组
                //第九组
                list.add(new HomePageEntry(3));
                list.add(new HomePageEntry(4));
                list.add(new HomePageEntry(4));
                list.add(new HomePageEntry(4));
                adapter.setData(list);
            }
        }, 50);
    }




}

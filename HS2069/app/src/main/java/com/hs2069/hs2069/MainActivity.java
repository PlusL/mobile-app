package com.hs2069.hs2069;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.GetCallback;
import com.avos.avoscloud.GetDataCallback;
import com.avos.avoscloud.SaveCallback;
import com.hs2069.hs2069.Adapter.MainAdapter;
import com.hs2069.hs2069.Data.MainItem;
import com.hs2069.hs2069.fragments.MainTabFragment1;
import com.hs2069.hs2069.fragments.MainTabFragment2;
import com.hs2069.hs2069.fragments.PersonInfo1Fragment;
import com.hs2069.hs2069.fragments.SearchRecordFragment;
import com.hs2069.hs2069.fragments.SearchResultFragment;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    android.support.v7.app.ActionBar actionBar = null;
    MainTabFragment1 mMainTabFragment1 = new MainTabFragment1();
    MainTabFragment2 mMainTabFragment2 = new MainTabFragment2();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AVOSCloud.initialize(this, getString(R.string.app_id), getString(R.string.app_key));
        /*
        if(findViewById(R.id.main_tab_fragment1) != null){
            if(savedInstanceState != null) {
                return;
            }
            MainTabFragment1 mMainTabFragment1 = new MainTabFragment1();
            MainTabFragment2 mMainTabFragment2 = new MainTabFragment2();
            android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.main_tab_fragment1, mMainTabFragment1).commit();
            manager.beginTransaction().replace(R.id.main_tab_fragment2, mMainTabFragment2).commit();


        }
        */

        //下载图像
        AVQuery<AVObject> query = new AVQuery<>("item");
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (e == null) {
                    List<AVObject> mList = list;
                    for (int i = 0; i < mList.size(); i++) {
                        final String id = mList.get(i).getObjectId();
                        AVFile file = new AVFile("dog" + id + ".jpg", mList.get(i).getString("src"), new HashMap<String, Object>());
                        final int ii = i;
                        //save image
                        file.getDataInBackground(new GetDataCallback() {
                            @Override
                            public void done(byte[] bytes, AVException e) {
                                if (e == null) {
                                    Log.w("download image:" + ii, "success");
                                    try {
                                        FileOutputStream fout = openFileOutput("dog" + id + ".jpg", Context.MODE_PRIVATE);
                                        fout.write(bytes);
                                        fout.close();
                                    } catch (Exception e1) {
                                        e1.printStackTrace();
                                    }
                                } else {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                } else {
                    e.printStackTrace();
                }
            }
        });
        /*
        android.support.v4.app.Fragment fragment_main = new MainTabFragment1();
        fragment_main = (MainTabFragment1) Fragment.instantiate(this, MainTabFragment1.class.getName());
        android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.container, fragment_main).commit();
        */

        //设置不同的tab
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(android.support.v7.app.ActionBar.NAVIGATION_MODE_TABS);
        android.support.v4.app.Fragment fragment1 = new MainTabFragment1();
        android.support.v4.app.Fragment fragment2 = new MainTabFragment2();
        android.support.v7.app.ActionBar.Tab tab1 = actionBar.newTab().setText("tab1");
        tab1.setTabListener(new MyTabsListener(fragment1, this));
        actionBar.addTab(tab1);
        android.support.v7.app.ActionBar.Tab tab2 = actionBar.newTab().setText("tab2");
        tab2.setTabListener(new MyTabsListener(fragment2, this));
        actionBar.addTab(tab2);
        /*
        final ArrayList<MainItem> dataList = new ArrayList<MainItem>();
        AVQuery<AVObject> query1 = new AVQuery<>("item");
        dataList.clear();
        final MainItem mMainItem = new MainItem();
        query1.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (e == null) {
                    List<AVObject> mList = list;
                    cnt = list.size();
                    for (int i = 0; i < mList.size(); i++) {
                        try {
                            FileInputStream fis = openFileInput("dog" + i + ".jpg");
                            Bitmap bitmap = BitmapFactory.decodeStream(fis);
                            mMainItem.setTitle(mList.get(i).getString("title"));
                            //MainItem mMainItem = new MainItem(mList.get(i).getString("title"), mList.get(i).getString("content"), mList.get(i).getString("price"), bitmap);
                            dataList.add(mMainItem);
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                    }
                } else {
                    e.printStackTrace();
                }
            }
        });
        ListView lv = (ListView)findViewById(R.id.activity_main_lv);
        MainAdapter mAdapter = new MainAdapter(dataList);
        lv.setAdapter(mAdapter);
        */
        /*
        //上传数据
        final AVFile file = new AVFile("test.jpg", "https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=2058484024,1849749645&fm=21&gp=0.jpg", new HashMap<String, Object>());
        final AVObject item = new AVObject("item");
        file.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    Log.w("upload:", "success");
                    item.put("src", file.getUrl());
                    item.put("title", "萌萌哒松狮");
                    item.put("content", "妻子走了，看到它心里总是会想起过去的甜蜜，豆豆很乖，希望有爱的人，能够好好对他。他喜欢出去玩，喜欢跑，不要把他老关在家里。他不挑食，但也不要对他太坏了.希望同城上门面议，我不要钱，只希望豆豆好好的。");
                    item.put("price", "1");
                    item.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(AVException e) {
                            if (e == null) {
                                final String id = item.getObjectId();
                                AVQuery<AVObject> query = new AVQuery<AVObject>("item");
                                query.getInBackground(id, new GetCallback<AVObject>() {
                                    @Override
                                    public void done(AVObject avObject, AVException e) {
                                        if (e == null) {
                                            Log.w("src", " " + avObject.getString("src"));
                                        } else {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                            } else {
                                e.printStackTrace();
                            }
                        }
                    });
                } else {
                    e.printStackTrace();
                }
            }
        });
        */
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.action_search:
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_person:
                if(AVUser.getCurrentUser() == null){
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                }else{
                    startActivity(new Intent(MainActivity.this, PersonActivity.class));
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    protected class MyTabsListener implements android.support.v7.app.ActionBar.TabListener{
        private android.support.v4.app.Fragment fragment;
        private AppCompatActivity mActivity;
        FragmentTransaction fmt;

        public MyTabsListener(android.support.v4.app.Fragment fragment, AppCompatActivity activity){
            this.fragment = fragment;
            this.mActivity = activity;
        }

        @Override
        public void onTabSelected(android.support.v7.app.ActionBar.Tab tab, android.support.v4.app.FragmentTransaction ft) {
            android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.container, fragment).commit();
        }

        @Override
        public void onTabUnselected(android.support.v7.app.ActionBar.Tab tab, android.support.v4.app.FragmentTransaction ft) {
            android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().remove(fragment).commit();
        }

        @Override
        public void onTabReselected(android.support.v7.app.ActionBar.Tab tab, android.support.v4.app.FragmentTransaction ft) {

        }
    }

}

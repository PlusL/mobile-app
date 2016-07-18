package com.hs2069.hs2069;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.hs2069.hs2069.Adapter.MainAdapter;
import com.hs2069.hs2069.Data.MainItem;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by edward2414 on 7/17/2016.
 */
public class FavoriteActivity extends AppCompatActivity {
    private ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(R.layout.actionbar_favorite);
        actionBar.getCustomView().findViewById(R.id.actionbar_favorite_action_left).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        actionBar.getCustomView().findViewById(R.id.actionbar_favorite_action_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FavoriteActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });
        //TODO:test
        lv = (ListView)findViewById(R.id.activity_favorite_lv);
        AVQuery<AVObject> query = new AVQuery<>("favorite");
        final ArrayList<MainItem> dataList = new ArrayList<MainItem>();
        final ArrayList<String> idList = new ArrayList<String>();
        dataList.clear();
        idList.clear();
        query.whereEqualTo("buyer", AVUser.getCurrentUser().getObjectId().toString());
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (e == null) {
                    for (int i = 0; i < list.size(); i++) {
                        final int ii = i;
                        AVQuery<AVObject> query2 = new AVQuery<>("item");
                        query2.whereEqualTo("objectId", list.get(i).getString("item").toString());
                        query2.findInBackground(new FindCallback<AVObject>() {
                            @Override
                            public void done(List<AVObject> list, AVException e) {
                                List<AVObject> mList = list;
                                Bitmap bitmap;
                                try {
                                    FileInputStream fis = openFileInput("dog" + mList.get(0).getObjectId() + ".jpg");
                                    bitmap = BitmapFactory.decodeStream(fis);
                                    MainItem mMainItem = new MainItem(mList.get(0).getString("title"), mList.get(0).getString("content"), mList.get(0).getString("price"), bitmap);
                                    dataList.add(mMainItem);
                                    idList.add(mList.get(0).getObjectId());
                                } catch (Exception e2) {
                                    e2.printStackTrace();
                                }
                                lv.setAdapter(new MainAdapter(dataList));
                            }
                        });
                    }
                } else {
                    e.printStackTrace();
                }
            }
        });
    }
}

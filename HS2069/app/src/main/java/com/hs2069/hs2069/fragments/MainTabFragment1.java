package com.hs2069.hs2069.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.GetDataCallback;
import com.hs2069.hs2069.Adapter.MainAdapter;
import com.hs2069.hs2069.Adapter.PersonInfoAdapter1;
import com.hs2069.hs2069.BlankActivity;
import com.hs2069.hs2069.Data.MainItem;
import com.hs2069.hs2069.ItemActivity;
import com.hs2069.hs2069.R;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by edward2414 on 7/6/2016.
 */
public class MainTabFragment1 extends android.support.v4.app.Fragment {
    private static final String[] strs = new String[]{"one", "two", "three", "four", "five","six","seven","eight","nine","ten","eleven","n12","n13"};
    private ListView lv;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_main_tab1, container, false);
        lv = (ListView)view.findViewById(R.id.fragment_main_tab1_lv);
        AVQuery<AVObject> query = new AVQuery<>("item");
        final ArrayList<MainItem> dataList = new ArrayList<MainItem>();
        final ArrayList<String> idList = new ArrayList<String>();
        dataList.clear();
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (e == null) {
                    List<AVObject> mList = list;
                    for (int i = 0; i < mList.size(); i++) {
                        final int ii = i;
                        /*
                        AVFile file = new AVFile("dog" + i + ".jpg", mList.get(i).getString("src"), new HashMap<String, Object>());
                        //save image
                        file.getDataInBackground(new GetDataCallback() {
                            @Override
                            public void done(byte[] bytes, AVException e) {
                                if (e == null) {
                                    Log.w("download image:" + ii, "success");
                                    try {
                                        FileOutputStream fout = getActivity().openFileOutput("dog" + ii + ".jpg", Context.MODE_PRIVATE);
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
                        */
                        Bitmap bitmap;
                        try {
                            FileInputStream fis = getActivity().openFileInput("dog" + mList.get(i).getObjectId() + ".jpg");
                            bitmap = BitmapFactory.decodeStream(fis);
                            MainItem mMainItem = new MainItem(mList.get(i).getString("title"), mList.get(i).getString("content"), mList.get(i).getString("price"), bitmap);
                            dataList.add(mMainItem);
                            idList.add(mList.get(i).getObjectId());
                        } catch (Exception e2) {
                            e2.printStackTrace();
                        }
                    }
                    lv.setAdapter(new MainAdapter(dataList));
                } else {
                    e.printStackTrace();
                }
            }
        });
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), ItemActivity.class);
                intent.putExtra("id", idList.get(position));
                startActivity(intent);
            }
        });
        return view;
    }
}

package com.hs2069.hs2069.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.GetDataCallback;
import com.hs2069.hs2069.Adapter.MainAdapter;
import com.hs2069.hs2069.Adapter.SearchResultAdapter;
import com.hs2069.hs2069.Data.MainItem;
import com.hs2069.hs2069.ItemActivity;
import com.hs2069.hs2069.R;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by edward2414 on 7/8/2016.
 */
public class SearchResultFragment extends android.support.v4.app.Fragment {

    private ListView lv;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_search_result, container, false);
        final ArrayList<String> idList = new ArrayList<String>();
        idList.clear();

        AVQuery<AVObject> query = new AVQuery<>("item");
        final ArrayList<MainItem> dataList = new ArrayList<MainItem>();
        dataList.clear();
        Bundle data = getArguments();
        query.whereContains("title", data.getString("content"));
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (e == null) {
                    Log.w("search result", list.size() + "");
                    List<AVObject> mList = list;
                    for (int i = 0; i < mList.size(); i++) {
                        final int ii = i;
                        final AVObject mAVObject = mList.get(i);
                        AVFile file = new AVFile("dog_result" + ii + ".jpg", mList.get(i).getString("src"), new HashMap<String, Object>());
                        //save image
                        file.getDataInBackground(new GetDataCallback() {
                            @Override
                            public void done(byte[] bytes, AVException e) {
                                if (e == null) {
                                    Log.w("download image:" + ii, "success");
                                    try {
                                        FileOutputStream fout = getActivity().openFileOutput("dog_result" + ii + ".jpg", Context.MODE_PRIVATE);
                                        fout.write(bytes);
                                        fout.close();
                                    } catch (Exception e1) {
                                        e1.printStackTrace();
                                    }

                                    Bitmap bitmap;
                                    try {
                                        FileInputStream fis = getActivity().openFileInput("dog_result" + ii + ".jpg");
                                        bitmap = BitmapFactory.decodeStream(fis);
                                        MainItem mMainItem = new MainItem(mAVObject.getString("title"), mAVObject.getString("content"), mAVObject.getString("price"), bitmap);
                                        dataList.add(mMainItem);
                                        idList.add(mAVObject.getObjectId());
                                    } catch (Exception e2) {
                                        e2.printStackTrace();
                                    }
                                    lv = (ListView) view.findViewById(R.id.fragment_search_result_lv);
                                    lv.setAdapter(new MainAdapter(dataList));
                                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                            Intent intent = new Intent(getActivity(), ItemActivity.class);
                                            intent.putExtra("id", idList.get(position));
                                            startActivity(intent);
                                        }
                                    });

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
        return view;
    }
}


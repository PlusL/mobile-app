package com.hs2069.hs2069.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hs2069.hs2069.Adapter.SearchRecordAdapter;
import com.hs2069.hs2069.R;
import com.hs2069.hs2069.SearchActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by edward2414 on 7/7/2016.
 */
public class SearchRecordFragment extends android.support.v4.app.Fragment {
    ArrayList<String> dataList = new ArrayList<String>();
    ArrayList<String> dataList2 = new ArrayList<String>();
    private ListView lv;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_search_record, container, false);
        /*
        byte[] s = new byte[5];
        try {
            FileInputStream inputStream = getActivity().openFileInput("SearchRecord.txt");
            int len = -1;
            while((len = inputStream.read(s)) != -1){
                dataList.add(new String(s));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        */
        //加载搜索记录
        int cnt = 0;
        FileInputStream inputStream = null;
        try{
            inputStream = getActivity().openFileInput("SearchRecord.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            while(br.ready()) {
                if(cnt % 2 == 0)dataList.add(new String(br.readLine()));
                else br.readLine();
                cnt++;
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try{
                inputStream.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        int size = dataList.size();
        for(int i = 1; i <= size; i++){
            dataList2.add(dataList.get(size-i));
        }
        lv = (ListView)view.findViewById(R.id.fragment_search_record_lv);
        lv.setAdapter(new SearchRecordAdapter(dataList2));
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //final android.support.v4.app.Fragment fragment_search_result = new SearchResultFragment();
                //android.support.v4.app.FragmentTransaction fmt = getActivity().getSupportFragmentManager().beginTransaction();
                //fmt.add(R.id.search_container, fragment_search_result).commit();
                android.support.v4.app.Fragment fragment = new SearchResultFragment();
                Bundle data = new Bundle();
                data.putString("content", dataList2.get(position));
                fragment.setArguments(data);
                android.support.v4.app.FragmentManager manager = getActivity().getSupportFragmentManager();
                manager.beginTransaction().replace(R.id.search_container, fragment).commit();
            }
        });
        ImageView iv = (ImageView)view.findViewById(R.id.fragment_search_record_iv);
        //删除搜索记录
        iv.setOnClickListener(new View.OnClickListener() {
            FileOutputStream outputStream = null;
            @Override
            public void onClick(View v) {
                try{
                    outputStream = getActivity().openFileOutput("SearchRecord.txt", Context.MODE_PRIVATE);
                    outputStream.write("".getBytes());
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    try{
                        outputStream.close();
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
                android.support.v4.app.Fragment fragment_search_record = new SearchRecordFragment();
                android.support.v4.app.FragmentManager manager = getActivity().getSupportFragmentManager();
                manager.beginTransaction().replace(R.id.search_container, fragment_search_record).commit();
                //Toast.makeText(getActivity(), "删除搜索记录已被按下", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
}

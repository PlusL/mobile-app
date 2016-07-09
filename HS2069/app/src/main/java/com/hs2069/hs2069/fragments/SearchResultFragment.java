package com.hs2069.hs2069.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.hs2069.hs2069.Adapter.SearchResultAdapter;
import com.hs2069.hs2069.R;

import java.util.ArrayList;

/**
 * Created by edward2414 on 7/8/2016.
 */
public class SearchResultFragment extends android.support.v4.app.Fragment {
    private static final String[] strs = new String[15];
    ArrayList<String> dataList = new ArrayList<String>();
    private ListView lv;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_result, container, false);
        for(int i = 0; i < 15; i++)
        {
            strs[i] = "搜索结果" + i;
            dataList.add(strs[i]);
        }
        lv = (ListView)view.findViewById(R.id.fragment_search_result_lv);
        lv.setAdapter(new SearchResultAdapter(dataList));
        return view;
    }
}


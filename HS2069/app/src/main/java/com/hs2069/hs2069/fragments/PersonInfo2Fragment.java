package com.hs2069.hs2069.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.hs2069.hs2069.Adapter.PersonInfoAdapter1;
import com.hs2069.hs2069.R;

import java.util.ArrayList;

/**
 * Created by edward2414 on 7/10/2016.
 */
public class PersonInfo2Fragment extends android.support.v4.app.Fragment {
    private static final String[] strs = new String[]{"支持与反馈"};
    ArrayList<String> dataList = new ArrayList<String>();
    private ListView lv;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_person_info2, container, false);
        for(int i = 0; i < 1; i++)
        {
            dataList.add(strs[i]);
        }
        lv = (ListView)view.findViewById(R.id.fragment_person_info2_lv);
        lv.setAdapter(new PersonInfoAdapter1(dataList));
        return view;
    }
}

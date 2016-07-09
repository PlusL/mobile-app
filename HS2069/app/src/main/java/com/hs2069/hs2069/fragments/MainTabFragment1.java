package com.hs2069.hs2069.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.hs2069.hs2069.R;

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
        //                    lv.setAdapter(new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_list_item_1, strs));
        lv.setAdapter(new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_list_item_checked, strs));
        lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        return view;
    }
}

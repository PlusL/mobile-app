package com.example.edward2414.actionbartest.fragments;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.TextView;

/**
 * Created by edward2414 on 7/6/2016.
 */
public class MainTabFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        Context context = this.getActivity();
        TextView tv = new TextView(context);
        Bundle bd = this.getArguments();
        int tabs = bd.getInt("key");
        tv.setLayoutParams(new ActionBar.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT));
        tv.setText("actionbar tab:" + tabs);
        return tv;
    }

}

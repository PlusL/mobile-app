package com.hs2069.hs2069;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hs2069.hs2069.fragments.MainTabFragment1;
import com.hs2069.hs2069.fragments.MainTabFragment2;
import com.hs2069.hs2069.fragments.SearchRecordFragment;
import com.hs2069.hs2069.fragments.SearchResultFragment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * Created by edward2414 on 7/5/2016.
 */
public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        //final ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(R.layout.actionbar_search);
        actionBar.getCustomView().findViewById(R.id.actionbar_search_action_left).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        final android.support.v4.app.Fragment fragment_search_record = new SearchRecordFragment();
        final android.support.v4.app.Fragment fragment_search_result = new SearchResultFragment();
        android.support.v4.app.FragmentTransaction fmt = getSupportFragmentManager().beginTransaction();
        fmt.add(R.id.search_container, fragment_search_record).commit();
        //setContentView(R.layout.fragment_main_tab);
        final EditText mEditText = (EditText) findViewById(R.id.actionbar_search_edittext);
        mEditText.setFocusable(true);
        mEditText.setFocusableInTouchMode(true);
        mEditText.requestFocus();
        mEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                //储存搜索记录
                String filename = "SearchRecord.txt";
                File file = new File(getFilesDir(), filename);
                String filecontent = mEditText.getText().toString();
                FileOutputStream outputStream = null;
                try {
                    outputStream = openFileOutput(filename, Context.MODE_APPEND);
                    outputStream.write((filecontent + "\n").getBytes("UTF-8"));
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        outputStream.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                //加载搜索结果fragment，设置输入法为隐藏
                android.support.v4.app.Fragment fragment_search_result = new SearchResultFragment();
                android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
                manager.beginTransaction().replace(R.id.search_container, fragment_search_result).commit();
                EditText mEditText = (EditText) findViewById(R.id.actionbar_search_edittext);
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
                /*
                byte[] s = new byte[80];
                try {
                    FileInputStream inputStream = openFileInput("SearchRecord.txt");
                    inputStream.read(s);
                    Toast.makeText(SearchActivity.this, new String(s), Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                */
                return false;
            }
        });

        //设置清楚搜索记录
        actionBar.getCustomView().findViewById(R.id.actionbar_search_action_discard).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText mEditText = (EditText) findViewById(R.id.actionbar_search_edittext);
                mEditText.setText("");
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                mEditText.setFocusable(true);
                mEditText.setFocusableInTouchMode(true);
                mEditText.requestFocus();
                android.support.v4.app.Fragment fragment_search_record = new SearchRecordFragment();
                android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
                manager.beginTransaction().replace(R.id.search_container, fragment_search_record).commit();
            }
        });
    }

}

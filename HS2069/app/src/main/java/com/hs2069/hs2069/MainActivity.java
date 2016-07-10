package com.hs2069.hs2069;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.hs2069.hs2069.fragments.MainTabFragment1;
import com.hs2069.hs2069.fragments.MainTabFragment2;

public class MainActivity extends AppCompatActivity {

    android.support.v7.app.ActionBar actionBar = null;
    MainTabFragment1 mMainTabFragment1 = new MainTabFragment1();
    MainTabFragment2 mMainTabFragment2 = new MainTabFragment2();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
                Intent intent2 = new Intent(MainActivity.this, PersonActivity.class);
                startActivity(intent2);
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
              android.support.v4.app.FragmentTransaction fmt = mActivity.getSupportFragmentManager().beginTransaction();
              fmt.add(R.id.container, fragment).commit();
        }

        @Override
        public void onTabUnselected(android.support.v7.app.ActionBar.Tab tab, android.support.v4.app.FragmentTransaction ft) {
            android.support.v4.app.FragmentTransaction fmt = mActivity.getSupportFragmentManager().beginTransaction();
            fmt.remove(fragment).commit();
        }

        @Override
        public void onTabReselected(android.support.v7.app.ActionBar.Tab tab, android.support.v4.app.FragmentTransaction ft) {

        }
    }

}

package com.hs2069.hs2069;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.hs2069.hs2069.fragments.ModifyFragment;

/**
 * Created by edward2414 on 7/11/2016.
 */
public class ModifyActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify);
        android.support.v4.app.Fragment fragment_modify = new ModifyFragment();
        android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.modify_container, fragment_modify).commit();
    }
}

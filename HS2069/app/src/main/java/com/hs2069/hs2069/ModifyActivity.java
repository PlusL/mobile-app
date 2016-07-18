package com.hs2069.hs2069;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.avos.avoscloud.AVUser;
import com.hs2069.hs2069.fragments.ModifyFragment;

/**
 * Created by edward2414 on 7/11/2016.
 */
public class ModifyActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(R.layout.actionbar_modify);
        actionBar.getCustomView().findViewById(R.id.actionbar_modify_action_left).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        actionBar.getCustomView().findViewById(R.id.actionbar_modify_action_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ModifyActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });
        android.support.v4.app.Fragment fragment_modify = new ModifyFragment();
        android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.modify_container, fragment_modify).commit();
    }
}

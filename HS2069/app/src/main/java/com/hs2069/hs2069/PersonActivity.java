package com.hs2069.hs2069;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.hs2069.hs2069.R;
import com.hs2069.hs2069.fragments.PersonInfo1Fragment;
import com.hs2069.hs2069.fragments.PersonInfo2Fragment;
import com.hs2069.hs2069.fragments.SearchRecordFragment;

/**
 * Created by edward2414 on 7/9/2016.
 */
public class PersonActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(R.layout.actionbar_person);
        actionBar.getCustomView().findViewById(R.id.actionbar_person_action_left).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        actionBar.getCustomView().findViewById(R.id.actionbar_person_action_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PersonActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });
        android.support.v4.app.Fragment fragment_person_info1 = new PersonInfo1Fragment();
        android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.person_container, fragment_person_info1).commit();
        android.support.v4.app.Fragment fragment_person_info2 = new PersonInfo2Fragment();
        android.support.v4.app.FragmentManager manager2 = getSupportFragmentManager();
        manager2.beginTransaction().replace(R.id.person_container2, fragment_person_info2).commit();
        findViewById(R.id.activity_person_iv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PersonActivity.this, "退出登录已被按下", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

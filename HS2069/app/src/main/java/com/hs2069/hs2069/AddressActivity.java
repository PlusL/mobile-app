package com.hs2069.hs2069;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

/**
 * Created by edward2414 on 7/16/2016.
 */
public class AddressActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(R.layout.actionbar_address);
        actionBar.getCustomView().findViewById(R.id.actionbar_address_action_left).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        actionBar.getCustomView().findViewById(R.id.actionbar_address_action_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddressActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });
        TextView tv0 = (TextView)findViewById(R.id.activity_address_tv);
        FileInputStream inputStream = null;
        try{
            inputStream = openFileInput("Address.txt");
            if(inputStream != null) {
                BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
                while (br.ready()) {
                    tv0.setText(br.readLine());
                }
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
        findViewById(R.id.activity_address_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AddressActivity.this);
                builder.setTitle("提示");
                builder.setMessage("确认修改地址？");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() { //设置确定按钮
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss(); //关闭dialog
                        EditText et = (EditText)findViewById(R.id.activity_address_et);
                        String address = et.getText().toString();
                        TextView tv = (TextView)findViewById(R.id.activity_address_tv);
                        tv.setText(address);
                        FileOutputStream outputStream = null;
                        try {
                            outputStream = openFileOutput("Address.txt", Context.MODE_PRIVATE);
                            outputStream.write(address.getBytes());
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            try {
                                outputStream.close();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        Toast.makeText(AddressActivity.this, "地址已修改", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() { //设置取消按钮
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.create().show();
            }
        });
    }
}

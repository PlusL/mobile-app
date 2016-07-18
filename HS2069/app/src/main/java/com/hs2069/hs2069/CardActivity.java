package com.hs2069.hs2069;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVUser;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

/**
 * Created by edward2414 on 7/17/2016.
 */
public class CardActivity extends AppCompatActivity {
    public static CardActivity mCardActivity;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(R.layout.actionbar_card);
        actionBar.getCustomView().findViewById(R.id.actionbar_card_action_left).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        actionBar.getCustomView().findViewById(R.id.actionbar_card_action_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CardActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });
        mCardActivity = this;
        TextView tv0 = (TextView)findViewById(R.id.activity_card_tv);
        FileInputStream inputStream = null;
        try{
            inputStream = openFileInput("Card.txt");
            if(inputStream != null) {
                BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
                while (br.ready()) {
                    tv0.setText("当前卡号： " + br.readLine());
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
        findViewById(R.id.activity_card_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CardActivity.this);
                builder.setTitle("请输入交易密码:");
                final EditText et = new EditText(CardActivity.this);
                et.setInputType(0x12);
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                et.setLayoutParams(layoutParams);
                builder.setView(et);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() { //设置确定按钮
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss(); //关闭dialog
                        String password = et.getText().toString();
                        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                        StrictMode.setThreadPolicy(policy);
                        try {
                            String url = "http://10.214.11.157:8088/bank.php";
                            AsyncHttpClient client = new AsyncHttpClient();
                            RequestParams params = new RequestParams();
                            params.put("apiname", "bindAccount");
                            EditText et_name = (EditText)findViewById(R.id.activity_card_et_name);
                            params.put("userName", et_name.getText().toString());
                            EditText et_number = (EditText)findViewById(R.id.activity_card_et);
                            params.put("accountID", et_number.getText().toString());
                            params.put("accountPasswd", password);
                            params.put("userID", AVUser.getCurrentUser().getObjectId().toString());
                            client.post(url, params, new MyJson2());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
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

class MyJson2 extends JsonHttpResponseHandler {

    @Override
    public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONObject response) {
        super.onSuccess(statusCode, headers, response);
        try {
            Log.w("http success", response.getString("info"));
            switch (response.getString("code")){
                case "-3":{
                    Toast.makeText(CardActivity.mCardActivity, "code -3: 用户名或账号错误", Toast.LENGTH_SHORT).show();
                    break;
                }
                case "2":{
                    Toast.makeText(CardActivity.mCardActivity, "绑定成功", Toast.LENGTH_SHORT).show();
                    OutputStream outputStream = null;
                    try {
                        outputStream = CardActivity.mCardActivity.openFileOutput("Card.txt", Context.MODE_PRIVATE);
                        outputStream.write(response.getString("accountID").getBytes());
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            outputStream.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                }
                case "3":{
                    Toast.makeText(CardActivity.mCardActivity, "更改绑定成功", Toast.LENGTH_SHORT).show();
                    OutputStream outputStream = null;
                    try {
                        outputStream = CardActivity.mCardActivity.openFileOutput("Card.txt", Context.MODE_APPEND);
                        outputStream.write(response.getString("accountID").getBytes());
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            outputStream.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                }
                case "-1":{
                    Toast.makeText(CardActivity.mCardActivity, "code -1: 密码错误", Toast.LENGTH_SHORT).show();
                    break;
                }
                default:break;
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onFailure(int statusCode,  cz.msebera.android.httpclient.Header[] headers, String responseBody, Throwable e){
        super.onFailure(statusCode, headers, responseBody, e);
        Toast.makeText(CardActivity.mCardActivity, "Unknow Error!", Toast.LENGTH_SHORT).show();
        Log.w("http failure", responseBody);
    }
}

package com.hs2069.hs2069;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.os.Bundle;
import android.os.Message;
import android.os.StrictMode;
import android.preference.PreferenceActivity;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.AsyncHttpResponseHandler;
import com.avos.avoscloud.GetCallback;
import com.avos.avoscloud.GetDataCallback;
import com.avos.avoscloud.okhttp.internal.framed.Header;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;


import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Handler;

/**
 * Created by edward2414 on 7/16/2016.
 */
public class PaymentActivity extends AppCompatActivity {
    public static PaymentActivity mPaymentActivity;
    String money = new String();
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(R.layout.actionbar_payment);
        actionBar.getCustomView().findViewById(R.id.actionbar_payment_action_left).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        actionBar.getCustomView().findViewById(R.id.actionbar_payment_action_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PaymentActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });
        mPaymentActivity = this;
        TextView tv = (TextView)findViewById(R.id.activity_payment_tv_address);
        FileInputStream inputStream = null;
        try{
            inputStream = openFileInput("Address.txt");
            if(inputStream != null) {
                BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
                while (br.ready()) {
                    tv.setText(br.readLine());
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
        findViewById(R.id.activity_payment_tv_address).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PaymentActivity.this, AddressActivity.class));
            }
        });
        Intent intent = getIntent();
        final String id = intent.getStringExtra("id");
        AVQuery<AVObject> query = new AVQuery<>("item");
        query.whereEqualTo("objectId", id);
        query.getFirstInBackground(new GetCallback<AVObject>() {
            @Override
            public void done(AVObject avObject, AVException e) {
                if (e == null) {
                    Log.w("get payment item info", "success");
                    TextView price = (TextView) findViewById(R.id.activity_payment_price);
                    money = avObject.getString("price");
                    price.setText("CNY " + avObject.getString("price"));
                    TextView title = (TextView) findViewById(R.id.activity_payment_title);
                    title.setText(avObject.getString("title"));
                    TextView content = (TextView) findViewById(R.id.activity_payment_content);
                    content.setText(avObject.getString("content"));
                    TextView tv2 = (TextView) findViewById(R.id.activity_payment_tv_price);
                    tv2.setText("CNY " + money);
                    Bitmap bitmap;
                    try {
                        FileInputStream fis = openFileInput("item" + id + ".jpg");
                        bitmap = BitmapFactory.decodeStream(fis);
                        ImageView iv = (ImageView) findViewById(R.id.activity_payment_iv);
                        iv.setImageBitmap(bitmap);
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                } else {
                    e.printStackTrace();
                }
            }
        });
        findViewById(R.id.activity_payment_buy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(AVUser.getCurrentUser() == null){
                    startActivity(new Intent(PaymentActivity.this, LoginActivity.class));
                }else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(PaymentActivity.this);
                    builder.setTitle("请输入交易密码:");
                    final EditText et = new EditText(PaymentActivity.this);
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
                                String url = "http://10.214.11.99:8088/bank.php";
                                AsyncHttpClient client = new AsyncHttpClient();
                                RequestParams params = new RequestParams();
                                params.put("apiname", "confirmPasswd");
                                //params.put("buyerID", AVUser.getCurrentUser().getObjectId().toString());
                                params.put("buyerID", "000000000000000000000003");
                                params.put("sellerID", "000000000000000000000001");
                                params.put("accountPasswd", password);
                                params.put("sumMoney", money);
                                client.post(url, params, new MyJson());
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
            }
        });
    }


}


class MyJson extends JsonHttpResponseHandler {

    @Override
    public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONObject response) {
        super.onSuccess(statusCode, headers, response);
        try {
            Log.w("http success", response.getString("info"));
            switch (response.getString("code")){
                case "-2":{
                    Toast.makeText(PaymentActivity.mPaymentActivity, "code -2: 余额不足", Toast.LENGTH_SHORT).show();
                    break;
                }
                case "1":{
                    Toast.makeText(PaymentActivity.mPaymentActivity, "交易成功", Toast.LENGTH_SHORT).show();
                    Log.w("sumMoney", response.getString("sumMoney"));
                    Log.w("sellerMailbox", response.getString("sellerMailbox"));
                    break;
                }
                case "-5":{
                    Toast.makeText(PaymentActivity.mPaymentActivity, "code -5: 郝帅的锅", Toast.LENGTH_SHORT).show();
                    break;
                }
                case "-4":{
                    Toast.makeText(PaymentActivity.mPaymentActivity, "code -4: 交易金额不能为0或者负", Toast.LENGTH_SHORT).show();
                    break;
                }
                case "-1":{
                    Toast.makeText(PaymentActivity.mPaymentActivity, "code -1: 密码错误", Toast.LENGTH_SHORT).show();
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
        Toast.makeText(PaymentActivity.mPaymentActivity, "Unknow Error!", Toast.LENGTH_SHORT).show();
        Log.w("http failure", responseBody);
    }
}

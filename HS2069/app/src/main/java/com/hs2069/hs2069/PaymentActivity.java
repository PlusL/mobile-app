package com.hs2069.hs2069;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Message;
import android.os.StrictMode;
import android.preference.PreferenceActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AsyncHttpResponseHandler;
import com.avos.avoscloud.GetCallback;
import com.avos.avoscloud.GetDataCallback;
import com.avos.avoscloud.okhttp.internal.framed.Header;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileOutputStream;
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
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
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
                    price.setText("CNY " + avObject.getString("price"));
                    TextView title = (TextView) findViewById(R.id.activity_payment_title);
                    title.setText(avObject.getString("title"));
                    TextView content = (TextView) findViewById(R.id.activity_payment_content);
                    content.setText(avObject.getString("content"));
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
                /*
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
                try {
                    String url = "http://10.214.11.99:8088/bank.php";
                    AsyncHttpClient client = new AsyncHttpClient();
                    RequestParams params = new RequestParams();
                    params.put("apiname", "confirOrder");
                    params.put("buyerID", "00000000001");
                    params.put("sellerID", "00000000002");
                    params.put("sumMoney", 5);
                    client.post(url, params, new MyJson());


                } catch (Exception e) {
                    e.printStackTrace();
                }
                */
            }
        });
    }


}


class MyJson extends JsonHttpResponseHandler {
    @Override
    public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONObject response) {
        super.onSuccess(statusCode, headers, response);
        Log.w("http", "success");
    }
}


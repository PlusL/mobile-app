package com.hs2069.hs2069;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.GetCallback;
import com.avos.avoscloud.GetDataCallback;
import com.hs2069.hs2069.Data.MainItem;

import org.w3c.dom.Text;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;

/**
 * Created by edward2414 on 7/10/2016.
 */
public class ItemActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
        Intent intent = getIntent();
        final String id = intent.getStringExtra("id");
        AVQuery<AVObject> query = new AVQuery<>("item");
        query.whereEqualTo("objectId", id);
        query.getFirstInBackground(new GetCallback<AVObject>() {
            @Override
            public void done(AVObject avObject, AVException e) {
                if (e == null) {
                    Log.w("get item info", "success");
                    TextView price = (TextView) findViewById(R.id.activity_item_price);
                    price.setText("CNY " + avObject.getString("price"));
                    TextView title = (TextView) findViewById(R.id.activity_item_title);
                    title.setText(avObject.getString("title"));
                    TextView content = (TextView) findViewById(R.id.activit_item_content);
                    content.setText(avObject.getString("content"));
                    AVFile file = new AVFile("item" + id + ".jpg", avObject.getString("src"), new HashMap<String, Object>());
                    //save image
                    file.getDataInBackground(new GetDataCallback() {
                        @Override
                        public void done(byte[] bytes, AVException e) {
                            if (e == null) {
                                Log.w("download item image:" + id, "success");
                                try {
                                    FileOutputStream fout = openFileOutput("item" + id + ".jpg", Context.MODE_PRIVATE);
                                    fout.write(bytes);
                                    fout.close();
                                } catch (Exception e1) {
                                    e1.printStackTrace();
                                }
                            } else {
                                e.printStackTrace();
                            }
                        }
                    });
                    Bitmap bitmap;
                    try {
                        FileInputStream fis = openFileInput("item" + id + ".jpg");
                        bitmap = BitmapFactory.decodeStream(fis);
                        ImageView iv = (ImageView) findViewById(R.id.activity_item_iv1);
                        iv.setImageBitmap(bitmap);
                        ImageView iv2 = (ImageView) findViewById(R.id.activity_item_iv2);
                        iv2.setImageBitmap(bitmap);
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                } else {
                    e.printStackTrace();
                }
            }
        });
        TextView tv = (TextView)findViewById(R.id.activity_item_buy);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(ItemActivity.this, PaymentActivity.class);
                intent2.putExtra("id", id);
                startActivity(intent2);
            }
        });
    }
}

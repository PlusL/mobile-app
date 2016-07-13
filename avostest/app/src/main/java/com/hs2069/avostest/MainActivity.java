package com.hs2069.avostest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;
import com.avos.avoscloud.RequestMobileCodeCallback;
import com.avos.avoscloud.SaveCallback;
import com.avos.avoscloud.SignUpCallback;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText et_account = (EditText)findViewById(R.id.et_account);
                String acount = String.valueOf(et_account.getText());
                EditText et_password = (EditText)findViewById(R.id.et_password);
                String password = String.valueOf(et_password.getText());
                AVUser.logInInBackground(acount, password, new LogInCallback<AVUser>() {
                    @Override
                    public void done(AVUser avUser, AVException e) {
                        if (e == null) {
                            Intent intent = new Intent(MainActivity.this, AfterLoginActivity.class);
                            startActivity(intent);
                        }else{
                            Toast.makeText(MainActivity.this, "登陆失败", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
        findViewById(R.id.btn_register).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
        // 测试 SDK 是否正常工作的代码
        AVOSCloud.initialize(this, "K0azWb0067vg2P46h1waNCtg-gzGzoHsz", "am6l8j6JEYHSpKVTM112qwNl");
        AVObject testObject = new AVObject("TestObject");
        /*
        testObject.put("name", "Hello World!");
        testObject.put("priority", 1);
        testObject.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    Log.d("saved", "success!");
                }
            }
        });

        AVOSCloud.requestSMSCodeInBackground("18868102556", new RequestMobileCodeCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    Log.d("sms", "success");
                } else {
                    // 失败的原因可能有多种，常见的是用户名已经存在。
                    e.printStackTrace();
                }
            }
        });

        AVUser user = new AVUser();// 新建 AVUser 对象实例
        user.setUsername("edward");// 设置用户名
        user.setPassword("123456");// 设置密码
        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    // 注册成功
                    Log.d("test", "success");
                } else {
                    // 失败的原因可能有多种，常见的是用户名已经存在。
                    e.printStackTrace();
                }
            }
        });

        AVUser.logInInBackground("edward", "123456", new LogInCallback<AVUser>() {
            @Override
            public void done(AVUser avUser, AVException e) {
                Toast.makeText(MainActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
            }
        });
        */
    }
}

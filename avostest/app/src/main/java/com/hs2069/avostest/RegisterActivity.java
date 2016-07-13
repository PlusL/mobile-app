package com.hs2069.avostest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVMobilePhoneVerifyCallback;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.LogInCallback;
import com.avos.avoscloud.RequestMobileCodeCallback;
import com.avos.avoscloud.SignUpCallback;

import java.util.List;

/**
 * Created by edward2414 on 7/12/2016.
 */
public class RegisterActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Button btnCode = (Button)findViewById(R.id.btn_sent);
        btnCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText etPhone = (EditText)findViewById(R.id.et_phone);
                String phone = String.valueOf(etPhone.getText());
                if(phone.isEmpty()){
                    Toast.makeText(RegisterActivity.this, "请输入手机号", Toast.LENGTH_SHORT).show();
                }else{
                    AVOSCloud.requestSMSCodeInBackground(phone, new RequestMobileCodeCallback() {
                        @Override
                        public void done(AVException e) {
                            if (e == null) {
                                Log.d("sms", "success");
                            } else {
                                // 失败的原因可能有多种，常见的是用户名已经存在。
                                Toast.makeText(RegisterActivity.this, "验证码发送错误", Toast.LENGTH_SHORT).show();
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
        });
        Button btnReg = (Button)findViewById(R.id.btn_register2);
        btnReg.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                EditText etPhone = (EditText)findViewById(R.id.et_phone);
                final String phone = String.valueOf(etPhone.getText());
                EditText etPassword = (EditText)findViewById(R.id.et_password2);
                final String password = String.valueOf(etPassword.getText());
                AVQuery<AVUser> query = AVQuery.getQuery(AVUser.class);
                query.whereEqualTo("username", phone);
                query.findInBackground(new FindCallback<AVUser>() {
                    @Override
                    public void done(List<AVUser> list, AVException e) {
                        if(e == null){
                            if(list.size() != 0){
                                Toast.makeText(RegisterActivity.this, "手机号已被注册", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            //Toast.makeText(RegisterActivity.this, "手机号未被注册", Toast.LENGTH_SHORT).show();
                            EditText etCode = (EditText)findViewById(R.id.et_code);
                            String code = String.valueOf(etCode.getText());
                            AVOSCloud.verifyCodeInBackground(code, phone, new AVMobilePhoneVerifyCallback() {
                                @Override
                                public void done(AVException e) {
                                    if (e == null) {
                                        // 验证成功
                                        Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                                        //TODO:上传注册的账号密码
                                        AVUser avUser = new AVUser();
                                        avUser.setUsername(phone);
                                        avUser.setPassword(password);
                                        avUser.signUpInBackground(new SignUpCallback() {
                                            @Override
                                            public void done(AVException e) {
                                                if(e == null){
                                                    startActivity(new Intent(RegisterActivity.this, AfterLoginActivity.class));
                                                }else{
                                                    Toast.makeText(RegisterActivity.this, "账号密码注册失败", Toast.LENGTH_SHORT).show();
                                                    e.printStackTrace();
                                                }
                                            }
                                        });
                                    } else {
                                        Toast.makeText(RegisterActivity.this, "注册验证失败", Toast.LENGTH_SHORT).show();
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }else{
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }
}

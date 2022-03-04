package com.example.zing_android;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private final static String TAG = "console";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onButtonLogin(View view) {

        EditText et_acc = findViewById(R.id.et_accout);
        EditText et_pw = findViewById(R.id.et_password);

        String acc = et_acc.getText().toString();
        String pw = et_pw.getText().toString();

        boolean acc_eq = acc.equals("123456");
        boolean pw_eq = pw.equals("123456");

//        if(!acc_eq || !pw_eq) {
//            Toast.makeText(this, "账号或密码错误, 请重新输入！", Toast.LENGTH_LONG).show();
//            return;
//        }

        Toast.makeText(this, "登录成功", Toast.LENGTH_LONG).show();

        Intent intent = new Intent(this, SecondActivity.class);
        startActivity(intent);
    }
}
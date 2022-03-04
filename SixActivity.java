package com.example.zing_android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

public class SixActivity extends AppCompatActivity {


    private static final String TAG = "SixActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_six);
    }

    public void onButtonEvent(View view) {
        switch (view.getId()) {
            case R.id.btn_get:


                Person user = new Person("zing0", "男", 18);
                Person user1 = new Person("zing1", "男", 28);
                Person user2 = new Person("zing2", "男", 38);

                Common.getInstance().addUser(user);
                Common.getInstance().addUser(user1);
                ArrayList<Person> allUser1 = Common.getInstance().getUserList();
                Common.getInstance().addUser(user2);
                ArrayList<Person> allUser2 = Common.getInstance().getUserList();
                break;
            case R.id.btn_getUser:

                Person userData = Common.getInstance().getUserByName("zing0");

                Common.getInstance().removeUserByName("zing2");

                ArrayList<Person> allUse = Common.getInstance().getUserList();

                break;
        }


    }


}




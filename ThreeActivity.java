package com.example.zing_android;

import android.Manifest;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Locale;

public class ThreeActivity extends AppCompatActivity {

    private static final String TAG = "threeActivity";
    private static final int ACTIVITY_GET_CAMERA_IAMGE = 1;
    private static final int REQUEST_CODE = 1;
    private ImageView imgView = null;
    private Context selfContext;


    //创建通知管理器
    private NotificationManager notificationManager;
    //通知渠道的id
    private String channelId="music";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_three);

        imgView = findViewById(R.id.im_three_camera);

        /**
         * 接收上一activity传递过来的参数
         */
        Intent it = getIntent();
        //单个参数
//        String v = it.getStringExtra("k");
//        TextView ed = findViewById(R.id.textView4);
//        ed.setText(v);

        //多个参数
        Bundle bundle = it.getExtras();
        int num = bundle.getInt("num");
        String str1 = bundle.getString("str");
        String str2 = bundle.getString("str2");

        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append("num"+num);
        strBuilder.append("str1"+str1);
        strBuilder.append("str2"+str2);
        Log.e(TAG, strBuilder.toString());

        selfContext = this;


        //获取通知管理器对象
        notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        //Android8.0以上的适配
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O) {
            //创建通知渠道实例（这三个参数是必须要有的）
            NotificationChannel channel = new NotificationChannel(channelId,"音乐消息",NotificationManager.IMPORTANCE_HIGH);
            //创建通知渠道的通知管理器
            NotificationManager manager =(NotificationManager)getSystemService(NotificationManager.class);
            //将实例交给管理器
            manager.createNotificationChannel(channel);
        }


    }

    public void onButtonEvent(View view) {
//        File currentImageFile = null;
        switch (view.getId()) {
            case R.id.btn_three_one:    //跳转网页
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http://www.baidu.com"));
                startActivity(intent);
                break;
            case R.id.btn_three_two:    //拨打电话
                Intent it_phone = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:10086"));
                startActivity(it_phone);
                break;
            case R.id.btn_three_three:  //发宋短信
                Intent it_sms = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:10086"));
                it_sms.putExtra("sms_body", "hello ....");
                startActivity(it_sms);
                break;
            case R.id.btn_three_four:   //拍照存储

                /**
                 * 若是显示无法连接到相机 则检查模拟器是否设置相机为模拟场景 而不是webcham0
                 */
                if (ContextCompat.checkSelfPermission(ThreeActivity.this, Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(ThreeActivity.this,new String[]{Manifest.permission.CAMERA},1);

                }
                else {
                    Log.e("console", "调用相机");
                    Intent it_camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(it_camera, Activity.DEFAULT_KEYS_DIALER);
                }

                break;
            case R.id.btn_three_five:   //打开设置
                Intent it_set = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
                startActivityForResult(it_set, 0);
                break;

            case R.id.btn_three_six:


                Intent resultItent = new Intent(selfContext, MainActivity.class);
                resultItent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                resultItent.putExtra("what", 5);

                PendingIntent resultpengingItent = PendingIntent.getActivity(selfContext, 5, resultItent, PendingIntent.FLAG_UPDATE_CURRENT);

                //创建notification对象，设置相应属性
                Notification notification = new NotificationCompat.Builder(this,channelId)
                        .setContentTitle("这是标题")
                        .setContentText("这是内容")
                        .setWhen(System.currentTimeMillis())
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.pause))
                        .setSmallIcon(R.drawable.lift)
                        .setAutoCancel(true)
                        .setContentIntent(resultpengingItent)
                        .build();


                //调用管理器的notify方法
                notificationManager.notify(1,notification);
                break;
            case R.id.btn_three_seven:

                Intent intent2 = new Intent(selfContext, FourActivity.class);
                startActivity(intent2);;
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK) {
            Bundle bundle = data.getExtras();
            Bitmap bitMap = (Bitmap)bundle.get("data");
            //显示在imgview组件上
            imgView.setImageBitmap(bitMap);

            /**
             * 保存到手机相册
             * */
            String sdStatus = Environment.getExternalStorageState();
            if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
                Log.i("TestFile",
                        "SD card is not avaiable/writeable right now.");
                return;
            }
            String name = new DateFormat().format("yyyyMMdd_hhmmss",Calendar.getInstance(Locale.CHINA)) + ".jpg";
            Toast.makeText(this, name, Toast.LENGTH_LONG).show();
            Bitmap bitmap = (Bitmap) bundle.get("data");// 获取相机返回的数据，并转换为Bitmap图片格式

            FileOutputStream b = null;
            //???????????????????????????????为什么不能直接保存在系统相册位置呢？？？？？？？？？？？？
            File file = new File("/sdcard/myImage/");
            file.mkdirs();// 创建文件夹
            String fileName = "/sdcard/myImage/"+name;

            try {
                b = new FileOutputStream(fileName);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } finally {
                try {
                    b.flush();
                    b.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
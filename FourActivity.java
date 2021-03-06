package com.example.zing_android;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class FourActivity extends AppCompatActivity {

    private static  String TAG = "fourActivity";
    private ClipboardManager clipboard;
    Context selfContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_four);

        Log.e(TAG, " create");
        selfContext = this;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.e(TAG, " onDestroy");
    }

    public void onButtonEvent( View view) {

        switch (view.getId()) {
            case R.id.btn_four_one: //http
                this.httpGet();
                break;
            case R.id.btn_four_two://copy
                this.setClipData();
                break;
            case R.id.btn_four_three://paste

                String clipData = this.getClipData();

                TextView texView = findViewById(R.id.textView_paste);
                texView.setText(clipData);
                break;
            case R.id.btn_screen_h:
                setScreenToH();
                break;
            case R.id.btn_screen_v:
                setScreenToV();
                break;

            case R.id.btn_getCnn:
                int state = getAPNType(selfContext);
                Log.e(TAG, String.valueOf(state));
                break;
            case R.id.btn_toFive:

                Intent intent = new Intent(selfContext, FiveActivtity.class);
                startActivity(intent);
                break;
        }
    }

    public static int getAPNType(Context context) {
        //???????????????
        int netType = 0;
        //????????????????????????????????????
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        //??????NetworkInfo??????
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        //NetworkInfo???????????? ?????????????????????
        if (networkInfo == null) {
            return netType;
        }
        //?????? NetworkInfo??????????????? ????????????networkInfo?????????
        int nType = networkInfo.getType();
        if (nType == ConnectivityManager.TYPE_WIFI) {
            //WIFI
            netType = 1;
        } else if (nType == ConnectivityManager.TYPE_MOBILE) {
            int nSubType = networkInfo.getSubtype();
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            //3G   ?????????3G???UMTS???HSDPA ?????????3G???EVDO
            if (nSubType == TelephonyManager.NETWORK_TYPE_LTE
                    && !telephonyManager.isNetworkRoaming()) {
                netType = 4;
            } else if (nSubType == TelephonyManager.NETWORK_TYPE_UMTS
                    || nSubType == TelephonyManager.NETWORK_TYPE_HSDPA
                    || nSubType == TelephonyManager.NETWORK_TYPE_EVDO_0
                    && !telephonyManager.isNetworkRoaming()) {
                netType = 3;
                //2G ??????????????????2G???GPRS???EGDE????????????2G???CDMA
            } else if (nSubType == TelephonyManager.NETWORK_TYPE_GPRS
                    || nSubType == TelephonyManager.NETWORK_TYPE_EDGE
                    || nSubType == TelephonyManager.NETWORK_TYPE_CDMA
                    && !telephonyManager.isNetworkRoaming()) {
                netType = 2;
            } else {
                netType = 2;
            }
        }
        return netType;
    }

    /**
     * ??????????????? ?????????androidmanifest??????????????????activity ????????????android:configChanges="orientation|keyboardHidden|screenSize
     * ??????activity???????????????????????????
     */
    public void setScreenToV() {
        int tmp = getRequestedOrientation();
        Log.e(TAG, "??????????????????" + tmp);

        if(getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            Log.e(TAG, "??????????????????");
            FourActivity.this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    public void setScreenToH() {
        int tmp = getRequestedOrientation();
        Log.e(TAG, "?????????????????????" + tmp);
        if(getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            Log.e(TAG, "????????????");
            FourActivity.this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }

    }



    //?????????????????????
    public void setClipData() {

        EditText edText = findViewById(R.id.four_et_copy);

        String copyData = String.valueOf(edText.getText());
        clipboard = (ClipboardManager)
                getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("smiple_text", copyData);
        clipboard.setPrimaryClip(clip);
    }

    //???????????????
    public String getClipData() {
        if(!clipboard.hasPrimaryClip()) {
            return null;
        }
        ClipData data = clipboard.getPrimaryClip();
        ClipData.Item item = data.getItemAt(0);
        String content = item.getText().toString();
        return content;
    }

    public void httpGet() {

        /**
         * api28??????????????????xml/newwork_security_config.xml?????? ???????????????
         * ?????????androidmanifest  apliction ??? ???  android:networkSecurityConfig="@xml/network_security_config"
         *         android:usesCleartextTraffic="true"
         *
         *    ????????????????????????
         */
        new Thread(new Runnable() {
            @Override
            public void run() {

                //??????okhttp ???????????????????????????????????????????????????
//
//                OkHttpClient clien  = new OkHttpClient();
//                Request request = new Request.Builder().url("https://www.csdn.net").build();
//                try {
//                    Response response = clien.newCall(request).execute();
//                    if(response.isSuccessful()) {
//                        Log.e("sssss", response.body().string());
//                    /**
//                     * ???????????????????????????????????????????????? ??????????????????????????????????????????
//                     */
//                    showHttpResponse(response.body().string());
//                    }
//                } catch (IOException e) {
//                    Log.e("ssss", " ????????????");
//                }

                //??????????????????
                String httpUrl = "https://forum.cocos.org/t/104654.json?track_visit=true&forceLoad=true";
                String resultData = "";
                URL url = null;
                Log.e(TAG, "111111");
                try {
                    url = new URL(httpUrl);
                } catch (MalformedURLException e) {

                    Log.e("http", e.getMessage());
                }

                if(url != null) {
                    Log.e(TAG, "22222222222");
                    try {
                        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
                        connection.setRequestMethod("GET");
                        connection.setConnectTimeout(5000);
                        //????????????????????????????????????
                        connection.setReadTimeout(5000);
                        InputStream in = connection.getInputStream();

                        InputStreamReader reader  = new InputStreamReader(in);
                        BufferedReader buffer = new BufferedReader(reader);

                        String inputLine = null;
                        Log.e(TAG, "33333333");
                        while (((inputLine = buffer.readLine()) != null)) {
                            resultData += inputLine+"\n";
                        }
                        in.close ();
                        connection.disconnect();
                        if (resultData == null) {
                            resultData =  "Sorry,the content is null";
                        }

                    } catch (IOException e) {
                        Log.e(TAG, "444444444444");
                        e.printStackTrace();
                    }
                }

                Log.e(TAG, "55555555555");
                Log.e(TAG, resultData);

                /**
                 * ???????????????????????????????????????????????? ??????????????????????????????????????????
                 */
                showHttpResponse(resultData);

            }
        }).start();

    }

    public void showHttpResponse(final String resultData) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                TextView texView = findViewById(R.id.tv_http_response);
                texView.setText(resultData);

            }
        });
    }




}
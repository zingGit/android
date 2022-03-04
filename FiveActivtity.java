package com.example.zing_android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.EditText;

public class FiveActivtity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_five);
    }

    public void onButtonEvent(View view) {

        switch( view.getId()) {
            case R.id.btn_share:
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_TEXT, "测试分享文字");
                shareIntent.setType("text/plain");

                Intent sendIntent = Intent.createChooser(shareIntent, null);
                startActivity(sendIntent);
                break;
            case R.id.btn_openWebView:
                String url;
                EditText etUrl = findViewById(R.id.et_openUrl);
                url = etUrl.getText().toString();
                WebView webview = findViewById(R.id.webview);
                webview.loadUrl(url);
                break;
            case R.id.btn_to_six:
                Intent intent = new Intent(this, SixActivity.class);
                startActivity(intent);
                break;
        }
    }
}
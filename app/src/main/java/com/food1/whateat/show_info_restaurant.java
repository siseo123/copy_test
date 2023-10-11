package com.food1.whateat;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

public class show_info_restaurant extends AppCompatActivity {
    final String PACKAGE_NAME = "net.daum.android.map";

    ImageButton goto_kakaoMap;
    ImageButton back_button;
    WebView w_view;

    String lat;
    String lng;
    double my_pos;
    double mx_pos;

    String url_link;

    String source;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_info_restaurant);
        goto_kakaoMap = findViewById(R.id.goto_kakaomap);
        back_button = findViewById(R.id.back_button);
        w_view = findViewById(R.id.web_view);

        Intent getData = getIntent();
        url_link = getData.getStringExtra("url");

        lat = getData.getStringExtra("lat");
        lng = getData.getStringExtra("lng");
        my_pos = getData.getDoubleExtra("my_pos",0);
        mx_pos = getData.getDoubleExtra("mx_pos",0);

        w_view.getSettings().setAllowContentAccess(true);
        w_view.getSettings().setJavaScriptEnabled(true);
        w_view.setWebViewClient(new WebViewClient());
        w_view.loadUrl(url_link);

        goto_kakaoMap.setOnClickListener(v -> installedKakaoMap(v));
        back_button.setOnClickListener(v -> {
            finish();
        });
    }

    public void installedKakaoMap(View v)
    {
        Intent intent = getPackageManager().getLaunchIntentForPackage(PACKAGE_NAME);
        String my_pos = String.valueOf(this.my_pos);
        String mx_pos = String.valueOf(this.mx_pos);
        Uri uri = Uri.parse("kakaomap://route?sp="+my_pos+","+mx_pos+"&ep="+lat+","+lng+"&by=FOOT");
        Uri uri2 = Uri.parse("market://launch?id=net.daum.android.map");
        if(intent == null){
            Toast.makeText(getApplicationContext(),"카카오 맵을 설치해주세요.",Toast.LENGTH_LONG).show();
            Intent it = new Intent(Intent.ACTION_VIEW,uri2);
            startActivity(it);
        }
        else
        {
            Intent it = new Intent(Intent.ACTION_VIEW,uri);
            startActivity(it);
        }
    }
}
package com.food1.whateat;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

public class show_info_restaurant extends AppCompatActivity {
    final String PACKAGE_NAME = "net.daum.android.map";
    public static WebView w_view;

    ImageButton goto_kakaoMap;
    ImageButton back_button;

    String lat;
    String lng;
    double my_pos;
    double mx_pos;

    String url_link;

    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_info_restaurant);
        goto_kakaoMap = findViewById(R.id.goto_kakaomap);
        back_button = findViewById(R.id.back_button);
        w_view = findViewById(R.id.web_view);

        Intent getData = getIntent();
        dialog = new ProgressDialog(this);
        url_link = getData.getStringExtra("url");

        lat = getData.getStringExtra("lat");
        lng = getData.getStringExtra("lng");
        my_pos = getData.getDoubleExtra("my_pos",0);
        mx_pos = getData.getDoubleExtra("mx_pos",0);
        w_view.setBackgroundColor(Color.TRANSPARENT);
        back_button.setBackgroundColor(Color.TRANSPARENT);
        goto_kakaoMap.setBackgroundColor(Color.TRANSPARENT);
        w_view.getSettings().setAllowContentAccess(true);
        w_view.getSettings().setJavaScriptEnabled(true);


        w_view.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if( url.startsWith("http:") || url.startsWith("https:") ) {
                    return false;
                }

                // Otherwise allow the OS to handle things like tel, mailto, etc.
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity( intent );
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon)
            {
                super.onPageStarted(view, url, favicon);
                dialog.setMessage("불러오는 중...");
                dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                dialog.show();
            }
        });
        w_view.loadUrl(url_link);
        w_view.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress)
            {
                super.onProgressChanged(view,newProgress);
                if(newProgress >= 100)
                    dialog.dismiss();
            }
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result)
            {
                result.confirm();
                return super.onJsAlert(view, url, message, result);
            }
        });


        goto_kakaoMap.setOnClickListener(v -> installedKakaoMap(v));
        back_button.setOnClickListener(v -> {
            finish();
        });



    }

    @Override
    public void onBackPressed(){
        if(w_view.canGoBack()){
            w_view.goBack();
        }else{
            super.onBackPressed();
        }
    }


    public void installedKakaoMap(View v)
    {
        Intent intent = getPackageManager().getLaunchIntentForPackage(PACKAGE_NAME);
        String my_pos = String.valueOf(this.my_pos);
        String mx_pos = String.valueOf(this.mx_pos);
        Uri uri = Uri.parse("kakaomap://route?sp="+my_pos+","+mx_pos+"&ep="+lat+","+lng+"&by=FOOT");

        if(intent == null){
            //Toast.makeText(getApplicationContext(),"카카오 맵을 설치해주세요.",Toast.LENGTH_LONG).show();
            showMessage();
        }
        else
        {
            Intent it = new Intent(Intent.ACTION_VIEW,uri);
            startActivity(it);
        }
    }

    public void showMessage(){
        Uri uri2 = Uri.parse("market://launch?id=net.daum.android.map");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("안내");
        builder.setMessage("\"카카오맵\"을 설치 하시겠습니까?");
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent it = new Intent(Intent.ACTION_VIEW,uri2);
                startActivity(it);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
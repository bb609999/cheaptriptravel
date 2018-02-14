package com.example.cheaptriptravel.util;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.cheaptriptravel.R;

public class RouteWebViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_web_view);

        Intent intent = getIntent();
        String address = intent.getStringExtra("address");

        WebView webView = (WebView) findViewById(R.id.route_web_view);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(address);
    }
}

package com.mca.alisha.e_medicare;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class ClinicActivity extends AppCompatActivity {
    WebView map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clinic);
        map=findViewById(R.id.clinic);
        map.setWebViewClient(new WebViewClient());
        map.getSettings().setJavaScriptEnabled(true);
        map.loadUrl("https://www.google.com/maps/search/hospitals/@23.0376388,72.5468922,12z/data=!3m1!4b1");

    }
}

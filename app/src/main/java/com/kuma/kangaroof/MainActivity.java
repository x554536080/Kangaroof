package com.kuma.kangaroof;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    WebView webView;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webView = findViewById(R.id.main_web_view);
        button = findViewById(R.id.button2);
        button.setOnClickListener(view -> {
            Intent i = new Intent();
            i.setClass(MainActivity.this, MainActivity2.class);
            startActivity(i);
        });

        webView.loadUrl("file:///android_asset/main_h5.html");

    }
}
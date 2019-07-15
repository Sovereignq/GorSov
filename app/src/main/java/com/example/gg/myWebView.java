package com.example.gg;

import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

public class myWebView extends AppCompatActivity {
    WebView web;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_web_view);
        getSupportActionBar().hide();
        web = findViewById(R.id.webBrowser);
        WebSettings webSettings = web.getSettings();
        webSettings.setJavaScriptEnabled(true);
        web.setWebViewClient(new Browser());
        web.loadUrl("https://google.com");
    }

   private class Browser extends WebViewClient {


       @Override
       public boolean shouldOverrideUrlLoading
               (WebView view, String url) {
           System.out.println(url);
           return !url.contains(Config.URL);

       }
   }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if(web.canGoBack())
                web.goBack();
            else
                return super.onKeyDown(keyCode, event);
        }
        else if((keyCode == KeyEvent.KEYCODE_HOME))
        {
            finish();
            return true;
        }

        return true;
    }
}

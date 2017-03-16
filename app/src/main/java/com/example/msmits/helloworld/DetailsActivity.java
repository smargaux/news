package com.example.msmits.helloworld;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebView;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        News news = getIntent().getExtras().getParcelable("news");;
        String html=news.getHtml();
        String title=news.getTitle();
        setTitle(title);

        Log.i("html code",html);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
        {
            WebView.setWebContentsDebuggingEnabled(true);
        }
        WebView webView=(WebView) findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadData(html, "text/html; charset=utf-8", "utf-8");
        //webView.loadUrl("file:///android_asset/article.html");
    }

}

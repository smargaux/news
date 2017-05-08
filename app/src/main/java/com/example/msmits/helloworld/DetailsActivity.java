package com.example.msmits.helloworld;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Post post = getIntent().getExtras().getParcelable("post");
        String content=post.content;
        String title=post.title;
        setTitle(title);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
        {
            WebView.setWebContentsDebuggingEnabled(true);
        }
        WebView webView=(WebView) findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        StringBuilder  html_content=new  StringBuilder();
        // On charge le contenu avec la feuille de style pour améliorer l'affichage de l'article
        html_content.append("<HTML><HEAD><LINK href=\"style.css\" type=\"text/css\" rel=\"stylesheet\"/></HEAD><body>");
        html_content.append(content);
        html_content.append("</body></HTML>");
        webView.loadDataWithBaseURL("file:///android_asset/", html_content .toString(), "text/html; charset=utf-8", "utf-8", null);
        //webView.loadUrl(url);
    }

}

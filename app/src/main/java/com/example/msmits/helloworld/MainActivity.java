package com.example.msmits.helloworld;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements OnListItemClickListener{
    private ArrayList<News> list_news = new ArrayList<News>();
    private int position;
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // On vérifie si l'appareil est un téléphone ou une tablette
        if(getResources().getBoolean(R.bool.isTablet)){
            setContentView(R.layout.activity_main_tablet);

            News news1=new News("Premier Article","Super j'ai créer mon premier article","News","23 novembre 2016",3,false,"<html><head><title>Premier Article</title></head><body><h1>Hello 1 </h1></body></html>");
            list_news.add(news1);
            News news2=new News("Deuxième Article","Super j'ai créer mon deuxième article","News","15 novembre 2016",12,true,"<html><head><title>Deuxième Article</title></head><body><h1>Hello 2 </h1></body></html>");
            list_news.add(news2);
            News news3=new News("Troisème Article","Super j'ai créer mon troisième article","News","13 novembre 2016",12,true,"<html><head><title>Troisème Article</title></head><body><h1>Hello 3 </h1></body></html>");
            list_news.add(news3);
            News news4=new News("Troisème Article","Super j'ai créer mon troisième article","Tutos","13 novembre 2016",3,false,"<html><head><title>Troisème Article</title></head><body><h1>Hello 3 </h1></body></html>");
            list_news.add(news4);
            LinearLayoutManager layoutManager =new LinearLayoutManager(this);
            RecyclerView rView = (RecyclerView) findViewById(R.id.recycleListView);
            rView.setLayoutManager(layoutManager);
            rView.setAdapter(new myAdapter(list_news,this));
            webView =(WebView) findViewById(R.id.webview_fragment);

            // Paramètrage de la webview
            webView.getSettings().setJavaScriptEnabled(true);
            // Par défault on affiche la première vue
            webView.loadData(news1.getHtml(), "text/html; charset=utf-8", "utf-8");

        }else{
            setContentView(R.layout.linear_layout);
            ViewPager viewPager= (ViewPager)findViewById(R.id.pager);
            ArrayList <String> categories= new ArrayList<String>();
            categories.add("News");
            categories.add("Tutos");

            viewPager.setAdapter(new myPagerAdapter(getSupportFragmentManager(),this,categories));
        }



        //LinearLayoutManager layoutManager=new LinearLayoutManager(this);

        //setContentView(R.layout.activity_main);
        //setContentView(webview);
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
        {
            WebView.setWebContentsDebuggingEnabled(true);
        }
        WebView webView=(WebView) findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new MyJSInterface(), "MyName");
        webView.loadData("", "text/html", null);
        webView.loadUrl("file:///android_asset/article.html");
        //String appName = this.getResources().getString(R.string.app_name);
        //Toast.makeText(this,appName,Toast.LENGTH_LONG).show();
        //Log.i(appName," est le nom de l'application");
        */
        // Inflate the layout for this fragment
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
    @Override
    protected void onRestoreInstanceState(Bundle
                                                  savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * react to the user tapping/selecting an options menu item
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.preferences:
                //Toast.makeText(this, "ADD!", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(this, PreferencesActivity.class);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public void onHeaderClicked(int position) {

        // On enregistre la position
        if(getResources().getBoolean(R.bool.isTablet)){
            String html=list_news.get(position).getHtml();
            webView =(WebView) findViewById(R.id.webview_fragment);
            webView.loadData(html,"text/html; charset=utf-8", "utf-8");
        }

    }
    @Override
    public void onItemClicked(int position) {

    }
}

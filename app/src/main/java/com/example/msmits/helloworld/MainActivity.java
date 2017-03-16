package com.example.msmits.helloworld;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class MainActivity extends AppCompatActivity implements OnListItemClickListener{
    private ArrayList<News> list_news = new ArrayList<News>();
    private ArrayList<Category> categories_list=new ArrayList<Category>();
    private ArrayList<Post> last_posts=new ArrayList<Post>();
    private OnListItemClickListener listener;
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
            final RecyclerView rView = (RecyclerView) findViewById(R.id.recycleListView);
            rView.setLayoutManager(layoutManager);
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://www.goglasses.fr/")
                    .addConverterFactory(
                            JacksonConverterFactory.create())
                    .build();
            API api = retrofit.create(API.class);
            Call<ResponseLastPosts> call = api.getLastPosts();
            call.enqueue(new Callback<ResponseLastPosts>() {
                @Override
                public void onResponse(Call<ResponseLastPosts> call, Response<ResponseLastPosts> response) {
                    ResponseLastPosts reponseLastPosts= response.body();

                    last_posts=reponseLastPosts.posts;

                    rView.setAdapter(new myAdapter(last_posts,listener));
                }

                @Override
                public void onFailure(Call<ResponseLastPosts> call, Throwable t) {

                }
            });
            webView =(WebView) findViewById(R.id.webview_fragment);

            // Paramètrage de la webview
            webView.getSettings().setJavaScriptEnabled(true);
            // Par défault on affiche la première vue
            webView.loadData(news1.getHtml(), "text/html; charset=utf-8", "utf-8");

        }else{
            setContentView(R.layout.linear_layout);
            final ViewPager  viewPager= (ViewPager)findViewById(R.id.pager);

            // On récupère les catégories
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://www.goglasses.fr/")
                    .addConverterFactory(
                            JacksonConverterFactory.create())
                    .build();
            API api = retrofit.create(API.class);
            Call<ReponseCategory> call = api.getCategories();
            call.enqueue(new Callback<ReponseCategory>() {
                             @Override
                             public void onResponse(Call<ReponseCategory> call, Response<ReponseCategory> response) {
                                 ReponseCategory reponseCategory = response.body();

                                 categories_list=reponseCategory.categories;

                                 viewPager.setAdapter(new myPagerAdapter(getSupportFragmentManager(),getApplicationContext(),categories_list));


                             }

                             @Override
                             public void onFailure(Call<ReponseCategory> call, Throwable t) {

                                 Log.i("Failure",t.toString());

                             }
                         });

            /*ArrayList <String> categories= new ArrayList<String>();
            categories.add("News");
            categories.add("Tutos");*/
            Log.i("Categories list 2 ",categories_list.toString());

        }

        // On récupère les préferences
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        String notification = pref.getString("pref_notification",getResources().getString(R.string.notif_title));
        Intent notificationIntent = new Intent(getApplicationContext(), MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

        Log.i("Notification ",notification);

        // On crée la notification
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.bookmark_plus_outline)
                        .setContentTitle(getResources().getString(R.string.app_name))
                        .setContentText(notification)
                        .setContentIntent(contentIntent);



        // Notification manager
        NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify((int)System.currentTimeMillis(), builder.build());
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

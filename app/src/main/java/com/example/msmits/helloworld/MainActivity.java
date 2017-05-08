package com.example.msmits.helloworld;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
import java.util.Arrays;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class MainActivity extends AppCompatActivity implements OnListItemClickListener{
    private ArrayList<News> list_news = new ArrayList<News>();
    private ArrayList<Category> categories_list=new ArrayList<Category>();
    private ArrayList<Category> categories_chosen=new ArrayList<Category>();

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

                    // On récupère les catégories enregistrées dans les préférences
                    SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                    Set<String> categories = pref.getStringSet("pref_categories",null);

                    // On enregistre dans la BDD  les catégories issues des paramètres
                    SQLiteDatabase db=DataBaseHelper.getInstance(
                            getApplicationContext()).getWritableDatabase();
                    //On vérifie si des catégories ont déjà été enregistrées dans la bdd
                    String[] columns=new String[]{"SLUG"};
                    Cursor cursor = db.query("categories",columns,null,null,null,null,null);
                    int nb_categories=cursor.getCount();
                    for (Category category:categories_list) {
                        //On ajoute les catégories uniquement si il n'y en a aucune dans la base de données
                        if(categories!=null){
                            Log.i("Current category",category.slug);
                            Log.i("Is in array ",String.valueOf(categories.contains(category.slug)));
                            if(categories.contains(category.slug)){
                                categories_chosen.add(category);
                                ContentValues category_values= new ContentValues();
                                category_values.put("ID",category.id);
                                category_values.put("SLUG", category.slug);
                                category_values.put("TITLE", category.title);
                                category_values.put("DESCRIPTION", category.description);
                                category_values.put("PARENT", category.parent);
                                category_values.put("POST_COUNT", category.post_count);

                                db.insert("categories",null,category_values);
                            }
                        }else{
                            if(cursor!=null && nb_categories > 0) {
                                if (cursor.moveToFirst()) {
                                    do {
                                        // Si les catégories choisies sont dans la base de données
                                        if (categories.contains(cursor.getString(0))) {
                                            Category current_category = new Category();
                                            current_category.slug = cursor.getString(0);
                                            current_category.title = cursor.getString(1);
                                            current_category.description = cursor.getString(2);
                                            current_category.parent = cursor.getInt(3);
                                            current_category.post_count = cursor.getInt(4);
                                            categories_chosen.add(current_category);
                                        }
                                    } while (cursor.moveToNext());
                                }
                            }
                        }

                    }



                    cursor.close();
                    db.close();
                    viewPager.setAdapter(new myPagerAdapter(getSupportFragmentManager(),getApplicationContext(),categories_chosen));


                }

                @Override
                public void onFailure(Call<ReponseCategory> call, Throwable t) {

                    Log.i("Failure",t.toString());

                }
            });


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

                                 // On récupère les catégories enregistrées dans les préférences
                                 SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                                 Set<String> categories = pref.getStringSet("pref_categories",null);

                                 // On enregistre dans la BDD  les catégories issues des paramètres
                                 SQLiteDatabase db=DataBaseHelper.getInstance(
                                         getApplicationContext()).getWritableDatabase();
                                 //On vérifie si des catégories ont déjà été enregistrées dans la bdd
                                 String[] columns=new String[]{"SLUG"};
                                 Cursor cursor = db.query("categories",columns,null,null,null,null,null);
                                 int nb_categories=cursor.getCount();
                                 for (Category category:categories_list) {
                                     //On ajoute les catégories uniquement si il n'y en a aucune dans la base de données
                                     if(categories!=null){
                                         Log.i("Current category",category.slug);
                                         Log.i("Is in array ",String.valueOf(categories.contains(category.slug)));
                                         if(categories.contains(category.slug)){
                                             categories_chosen.add(category);
                                             ContentValues category_values= new ContentValues();
                                             category_values.put("ID",category.id);
                                             category_values.put("SLUG", category.slug);
                                             category_values.put("TITLE", category.title);
                                             category_values.put("DESCRIPTION", category.description);
                                             category_values.put("PARENT", category.parent);
                                             category_values.put("POST_COUNT", category.post_count);

                                             db.insert("categories",null,category_values);
                                         }
                                     }else{
                                         if(cursor!=null && nb_categories > 0) {
                                             if (cursor.moveToFirst()) {
                                                 do {
                                                     // Si les catégories choisies sont dans la base de données
                                                     if (categories.contains(cursor.getString(0))) {
                                                         Category current_category = new Category();
                                                         current_category.slug = cursor.getString(0);
                                                         current_category.title = cursor.getString(1);
                                                         current_category.description = cursor.getString(2);
                                                         current_category.parent = cursor.getInt(3);
                                                         current_category.post_count = cursor.getInt(4);
                                                         categories_chosen.add(current_category);
                                                     }
                                                 } while (cursor.moveToNext());
                                             }
                                         }
                                     }

                                 }



                                 cursor.close();
                                 db.close();
                                 viewPager.setAdapter(new myPagerAdapter(getSupportFragmentManager(),getApplicationContext(),categories_chosen));


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

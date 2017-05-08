package com.example.msmits.helloworld;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.lang.reflect.Array;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Created by msmits on 15/03/2017.
 */

public class LastNewsFragment extends android.support.v4.app.Fragment implements OnListItemClickListener{
    private ArrayList<News> list_news = new ArrayList<News>();
    private int position;
    private OnListItemClickListener listener;
    ProgressBar loader;
     RecyclerView rView;
    private ArrayList<Post> last_posts= new ArrayList<Post>();
    public  static LastNewsFragment lastNewsFragmentInstance() {
        LastNewsFragment fragment = new LastNewsFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {

        return inflater.inflate(R.layout.fragment_news, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        GridLayoutManager layoutManager =new GridLayoutManager(getActivity().getApplicationContext(),getResources().getInteger(R.integer.columns));
        rView = (RecyclerView) view.findViewById(R.id.recycleListView);
        rView.setLayoutManager(layoutManager);
        rView.setHasFixedSize(true);


        loader=(ProgressBar) getView().findViewById(R.id.loader);
        ConnectivityManager cm =
                (ConnectivityManager)getContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        // Si il y a un réseau on utilise l'api pour charger les posts
        if(isConnected){
            // On récupère les derniers posts
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
                    SQLiteDatabase db=DataBaseHelper.getInstance(
                            getContext()).getWritableDatabase();
                    last_posts=reponseLastPosts.posts;
                    // On ajoute les posts à la base de données
                    for(Post post:last_posts){
                        ContentValues post_values= new ContentValues();
                        post_values.put("ID",post.id);
                        post_values.put("SLUG", post.slug);
                        post_values.put("TITLE", post.title);
                        post_values.put("TITLE_PLAIN", post.title_plain);
                        post_values.put("DESCRIPTION", post.content);
                        post_values.put("DATE", post.date);
                        post_values.put("MODIFIED", post.modified);

                        post_values.put("COMMENTS_COUNT", post.comments_count);
                        int update_post=db.update("posts",post_values,"id=?",new String[]{String.valueOf(post.id)});
                        // Si le post n'existe pas dans la base de données, on l'ajoute
                        if(update_post==0){
                            db.insert("posts",null,post_values);

                        }
                    }
                    rView.setAdapter(new myAdapter(last_posts,LastNewsFragment.this));
                    loader.setVisibility(View.GONE);
                    rView.setVisibility(View.VISIBLE);

                }

                @Override
                public void onFailure(Call<ResponseLastPosts> call, Throwable t) {
                    Log.i("Failure lasts posts",t.toString());

                }
            });
        }else{
            // On récupère les posts enregistrés dans la base de données
            SQLiteDatabase db=DataBaseHelper.getInstance(
                    getContext()).getWritableDatabase();
            Cursor cursor=db.query("posts",null,null,null,null,null,"date","10");
            if(cursor!=null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        Post post=new Post();
                        post.id=cursor.getInt(0);
                        post.slug=cursor.getString(1);
                        post.title=cursor.getString(2);
                        post.title_plain=cursor.getString(3);
                        post.content=cursor.getString(4);
                        post.date=cursor.getString(5);
                        post.modified=cursor.getString(6);
                        post.comments_count=cursor.getInt(7);
                        last_posts.add(post);
                    } while (cursor.moveToNext());
                }
            }
            cursor.close();
            rView.setAdapter(new myAdapter(last_posts,LastNewsFragment.this));
            loader.setVisibility(View.GONE);
            rView.setVisibility(View.VISIBLE);
        }







    }


    @Override
    public void onHeaderClicked(int position) {

        Intent intent = new Intent(getActivity().getApplicationContext(), DetailsActivity.class);
        intent.putExtra("post", last_posts.get(position));
        startActivity(intent);

    }
    @Override
    public void onItemClicked(int position) {
        // On enregistre la position
        this.position=position;
        Intent intent = new Intent(getActivity().getApplicationContext(), DetailsActivity.class);
        intent.putExtra("post", last_posts.get(position));

        startActivity(intent);
    }
}

package com.example.msmits.helloworld;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Created by Margaux on 08/05/2017.
 */

public class FavoritesNewsFragment extends android.support.v4.app.Fragment implements OnListItemClickListener {
    private ArrayList<News> list_news = new ArrayList<News>();
    private int position;
    private ArrayList<Post>fave_posts=new ArrayList<>();

    private OnListItemClickListener listener;
    ProgressBar loader;
    RecyclerView rView;


    public static FavoritesNewsFragment favoritesNewsFragmentInstance() {
        FavoritesNewsFragment fragment = new FavoritesNewsFragment();
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
           // On enregistre dans la BDD  les catégories issues des paramètres
        SQLiteDatabase db=DataBaseHelper.getInstance(
                getActivity().getApplicationContext()).getWritableDatabase();
        String[] columns=new String[]{"FAVORITE"};
        Cursor cursor = db.query("posts",null,"FAVORITE=?",new String[] {"1"},null,null,null);
        Log.i("Fave posts",String.valueOf(cursor.getCount()));
        if(cursor!=null && cursor.getCount()>0) {
            if (cursor.moveToFirst()) {
                do {
                    Post fave_post = new Post();
                    Log.i("1",cursor.getString(0));
                    Log.i("2",cursor.getString(2));
                 //   Log.i("3",cursor.getString(3));
                    Log.i("4",cursor.getString(4));
                 //   Log.i("5",cursor.getString(5));
//                    Log.i("6",cursor.getString(6));
//                    Log.i("7",cursor.getString(7));
                    fave_post.slug = cursor.getString(1);
                    fave_post.title = cursor.getString(2);
                    fave_post.title_plain = cursor.getString(3);
                    fave_post.date = cursor.getString(4);
                    fave_post.modified = cursor.getString(5);
                    Author author = new Author();
                    author.id = cursor.getInt(6);
                    fave_post.author = author;
                    fave_post.comments_count = cursor.getInt(6);
                    fave_posts.add(new Post());

                } while (cursor.moveToNext());
            }

        }
        cursor.close();
        rView.setAdapter(new myAdapter(fave_posts,FavoritesNewsFragment.this));
        loader.setVisibility(View.GONE);
        rView.setVisibility(View.VISIBLE);

    }


    @Override
    public void onHeaderClicked(int position) {

        Intent intent = new Intent(getActivity().getApplicationContext(), DetailsActivity.class);
        intent.putExtra("post", fave_posts.get(position));
        startActivity(intent);

    }
    @Override
    public void onItemClicked(int position) {
        // On enregistre la position
        this.position=position;
        Intent intent = new Intent(getActivity().getApplicationContext(), DetailsActivity.class);
        intent.putExtra("post", fave_posts.get(position));

        startActivity(intent);
    }

}
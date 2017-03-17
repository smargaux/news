package com.example.msmits.helloworld;

import android.content.Intent;
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
        // On récupère les catégories
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
                Log.i("Lasts Posts",reponseLastPosts.toString());

                rView.setAdapter(new myAdapter(last_posts,LastNewsFragment.this));
                loader.setVisibility(View.GONE);
                rView.setVisibility(View.VISIBLE);

            }

            @Override
            public void onFailure(Call<ResponseLastPosts> call, Throwable t) {
                Log.i("Failure lasts posts",t.toString());

            }
        });






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

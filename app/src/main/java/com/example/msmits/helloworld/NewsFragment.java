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


public class NewsFragment
extends android.support.v4.app.Fragment implements OnListItemClickListener{
    private ArrayList<News> list_news = new ArrayList<News>();
    private int position;
    private Bundle bundle;
    private ArrayList<Post> category_posts = new ArrayList<Post>();
    private OnListItemClickListener listener;
    RecyclerView rView;
    ProgressBar loader;

    public  static NewsFragment newInstance(Category category) {
        NewsFragment fragment = new NewsFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("category", category.id);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news, container, false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        GridLayoutManager layoutManager =new GridLayoutManager(getActivity().getApplicationContext(),getResources().getInteger(R.integer.columns));

       rView = (RecyclerView) getView().findViewById(R.id.recycleListView);
        rView.setLayoutManager(layoutManager);
        //rView.setVisibility(View.GONE);
        loader=(ProgressBar) getView().findViewById(R.id.loader);
//        loader.setVisibility(View.VISIBLE);
        bundle=getArguments();
        int currentCategory=bundle.getInt("category");

        // On récupère les catégories
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.goglasses.fr/")
                .addConverterFactory(
                        JacksonConverterFactory.create())
                .build();
        API api = retrofit.create(API.class);
        Call<ResponsePostsByCategory> call = api.getPostsByCategory(currentCategory);
        call.enqueue(new Callback<ResponsePostsByCategory>() {
            @Override
            public void onResponse(Call<ResponsePostsByCategory> call, Response<ResponsePostsByCategory> response) {
                ResponsePostsByCategory reponseCategory = response.body();
                category_posts=reponseCategory.posts;

                rView.setAdapter(new myAdapter(category_posts,NewsFragment.this));
                if(loader.getVisibility()==View.VISIBLE){
                    loader.setVisibility(View.GONE);
                    rView.setVisibility(View.VISIBLE);


                }
            }

            @Override
            public void onFailure(Call<ResponsePostsByCategory> call, Throwable t) {
                Log.i("Failure posts by category",t.toString());

            }
        });




    }


    @Override
    public void onHeaderClicked(int position) {

        Intent intent = new Intent(getActivity().getApplicationContext(), DetailsActivity.class);
        intent.putExtra("post", category_posts.get(position));
        startActivity(intent);

    }
    @Override
    public void onItemClicked(int position) {
        // On enregistre la position
        this.position=position;
        Intent intent = new Intent(getActivity().getApplicationContext(), DetailsActivity.class);
        intent.putExtra("post", category_posts.get(position));

        startActivity(intent);
    }
}

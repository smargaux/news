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

import java.util.ArrayList;


public class NewsFragment
extends android.support.v4.app.Fragment implements OnListItemClickListener{
    private ArrayList<News> list_news = new ArrayList<News>();
    private ArrayList<News> category_news = new ArrayList<News>();
    private int position;
    private Bundle bundle;
    String category;
    public  static NewsFragment newInstance(String category) {
        NewsFragment fragment = new NewsFragment();
        Bundle bundle = new Bundle();
        bundle.putString("category", category);
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
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        GridLayoutManager layoutManager =new GridLayoutManager(getActivity().getApplicationContext(),getResources().getInteger(R.integer.columns));

        RecyclerView rView = (RecyclerView) getView().findViewById(R.id.recycleListView);
        rView.setLayoutManager(layoutManager);
        bundle=getArguments();
        String currentCategory=bundle.getString("category");
        News news1=new News("Premier Article","Super j'ai créer mon premier article","News","23 novembre 2016",3,false,"<html><head><title>Premier Article</title></head><body><h1>Hello 1 </h1></body></html>");
        list_news.add(news1);
        News news2=new News("Deuxième Article","Super j'ai créer mon deuxième article","News","15 novembre 2016",12,true,"<html><head><title>Deuxième Article</title></head><body><h1>Hello 2 </h1></body></html>");
        list_news.add(news2);
        News news3=new News("Troisème Article","Super j'ai créer mon troisième article","News","13 novembre 2016",12,true,"<html><head><title>Troisème Article</title></head><body><h1>Hello 3 </h1></body></html>");
        list_news.add(news3);
        News news4=new News("Troisème Article","Super j'ai créer mon troisième article","Tutos","13 novembre 2016",3,false,"<html><head><title>Troisème Article</title></head><body><h1>Hello 3 </h1></body></html>");
        list_news.add(news4);
        category_news.clear();
        for (int i = 0; i < list_news.size(); i++) {
            category=list_news.get(i).getCategory().toString();
            if(category.equalsIgnoreCase(currentCategory)){
                category_news.add(list_news.get(i));
            }else{
            }
        }
        Log.i("News news",category_news.toString());
        rView.setAdapter(new myAdapter(category_news,this));



    }


    @Override
    public void onHeaderClicked(int position) {

        Intent intent = new Intent(getActivity().getApplicationContext(), DetailsActivity.class);
        intent.putExtra("news", list_news.get(position));

        startActivity(intent);

    }
    @Override
    public void onItemClicked(int position) {
        // On enregistre la position
        this.position=position;
        Intent intent = new Intent(getActivity().getApplicationContext(), DetailsActivity.class);
        intent.putExtra("news", list_news.get(position));

        startActivity(intent);
    }
}

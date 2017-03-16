package com.example.msmits.helloworld;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

import static com.example.msmits.helloworld.LastNewsFragment.lastNewsFragmentInstance;
import static com.example.msmits.helloworld.NewsFragment.newInstance;

/**
 * Created by msmits on 15/03/2017.
 */

public class myPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<Category> categories;
    Context context;
    public myPagerAdapter(FragmentManager fm,Context nContext, ArrayList<Category> categories) {
        super(fm);
        this.categories=categories;
        this.context = nContext;

    }
    @Override
    public int getCount() {
        return this.categories.size()+1;
    }
    @Override
    public Fragment getItem(int position) {
        if(position==0){
            return lastNewsFragmentInstance();
        }else{
            return newInstance(categories.get(position-1).id);

        }

    }
    @Override
    public String getPageTitle(int position) {
        if(position==0){
            return context.getString(R.string.last_news);
        }
        return this.categories.get(position-1).title;
    }


}

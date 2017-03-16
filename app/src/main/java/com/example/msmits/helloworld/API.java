package com.example.msmits.helloworld;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by msmits on 16/03/2017.
 */

public interface API {
        @GET("?json=get_category_index")
        Call<ReponseCategory> getCategories();

        @GET("?json=get_recent_posts")
        Call<ResponseLastPosts> getLastPosts();

        @GET("?json=get_category_posts")
        Call<ResponsePostsByCategory> getPostsByCategory(@Query("id") int id);

        @GET("?json=get_category_index&id={id}")
        Call<List<Category>> getCategoryById(@Path("id") int id);

}

package com.example.msmits.helloworld;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;

/**
 * Created by msmits on 16/03/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseLastPosts {
    public String status;
    public int count;
    public int count_total;
    public int pages;
    public ArrayList<Post> posts;

}

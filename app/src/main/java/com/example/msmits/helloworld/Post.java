package com.example.msmits.helloworld;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by msmits on 16/03/2017.
 */

public class Post {
    public int id;
    public String type;
    public String slug;
    public String url;
    public String title;
    public String title_plain;
    public String content;
    public String excerpt;
    public String date;
    public String modified;
    public ArrayList<Category> categories;
    public Array tags;
    public Author author;
    public Array comments;
    public Array attachments;
    public int comments_count;
    public String comment_status;
    public String thumbnail;
    public Object custom_fields;
    public Array taxonomy;

}

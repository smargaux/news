package com.example.msmits.helloworld;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by msmits on 16/03/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Post implements Parcelable {
    public int id;
    public String status;
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
    public ArrayList<com.example.msmits.helloworld.Tag> tags;
    public Author author;
    public ArrayList<Comment> comments;
    public ArrayList<Attachment> attachments;
    public int comments_count;
    public String comment_status;
    public String thumbnail;
    public Object custom_fields;
    public Array taxonomy;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.status);
        dest.writeString(this.type);
        dest.writeString(this.slug);
        dest.writeString(this.url);
        dest.writeString(this.title);
        dest.writeString(this.title_plain);
        dest.writeString(this.content);
        dest.writeString(this.excerpt);
        dest.writeString(this.date);
        dest.writeString(this.modified);
        dest.writeList(this.categories);
        dest.writeList(this.tags);
        dest.writeParcelable(this.author, flags);
        dest.writeList(this.comments);
        dest.writeList(this.attachments);
        dest.writeInt(this.comments_count);
        dest.writeString(this.comment_status);
        dest.writeString(this.thumbnail);
        //dest.writeParcelable(this.custom_fields, flags);
        //dest.writeParcelable(this.taxonomy, flags);
    }

    public Post() {
    }

    protected Post(Parcel in) {
        this.id = in.readInt();
        this.status = in.readString();
        this.type = in.readString();
        this.slug = in.readString();
        this.url = in.readString();
        this.title = in.readString();
        this.title_plain = in.readString();
        this.content = in.readString();
        this.excerpt = in.readString();
        this.date = in.readString();
        this.modified = in.readString();
        this.categories = new ArrayList<Category>();
        in.readList(this.categories, Category.class.getClassLoader());
        this.tags = new ArrayList<Tag>();
        in.readList(this.tags, Tag.class.getClassLoader());
        this.author = in.readParcelable(Author.class.getClassLoader());
        this.comments = new ArrayList<Comment>();
        in.readList(this.comments, Comment.class.getClassLoader());
        this.attachments = new ArrayList<Attachment>();
        in.readList(this.attachments, Attachment.class.getClassLoader());
        this.comments_count = in.readInt();
        this.comment_status = in.readString();
        this.thumbnail = in.readString();
        this.custom_fields = in.readParcelable(Object.class.getClassLoader());
        this.taxonomy = in.readParcelable(Array.class.getClassLoader());
    }

    public static final Parcelable.Creator<Post> CREATOR = new Parcelable.Creator<Post>() {
        @Override
        public Post createFromParcel(Parcel source) {
            return new Post(source);
        }

        @Override
        public Post[] newArray(int size) {
            return new Post[size];
        }
    };
}

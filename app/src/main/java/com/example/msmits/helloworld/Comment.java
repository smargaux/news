package com.example.msmits.helloworld;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by msmits on 17/03/2017.
 */

public class Comment implements Parcelable {
    public int id;
    public String name;
    public String url;
    public String date;
    public String content;
    public int parent;
    public Author author;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.url);
        dest.writeString(this.date);
        dest.writeString(this.content);
        dest.writeInt(this.parent);
        dest.writeParcelable(this.author, flags);
    }

    public Comment() {
    }

    protected Comment(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.url = in.readString();
        this.date = in.readString();
        this.content = in.readString();
        this.parent = in.readInt();
        this.author = in.readParcelable(Author.class.getClassLoader());
    }

    public static final Parcelable.Creator<Comment> CREATOR = new Parcelable.Creator<Comment>() {
        @Override
        public Comment createFromParcel(Parcel source) {
            return new Comment(source);
        }

        @Override
        public Comment[] newArray(int size) {
            return new Comment[size];
        }
    };
}

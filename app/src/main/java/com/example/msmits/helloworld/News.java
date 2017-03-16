package com.example.msmits.helloworld;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by msmits on 14/03/2017.
 */

public class News implements Parcelable {
    private String title;
    private String description;
    private String category;
    private String date;
    private String html;
    private int comments;
    private boolean highlight;
    public News(String title, String description,String category,String date,int comments, boolean highlight, String html){
        this.title=title;
        this.description=description;
        this.category=category;
        this.date=date;
        this.comments=comments;
        this.highlight=highlight;
        this.html=html;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.description);
        dest.writeString(this.category);
        dest.writeString(this.date);
        dest.writeString(this.html);
        dest.writeInt(this.comments);
        dest.writeByte(this.highlight ? (byte) 1 : (byte) 0);
    }

    protected News(Parcel in) {
        this.title = in.readString();
        this.description = in.readString();
        this.category = in.readString();
        this.date = in.readString();
        this.html = in.readString();
        this.comments = in.readInt();
        this.highlight = in.readByte() != 0;
    }

    public static final Parcelable.Creator<News> CREATOR = new Parcelable.Creator<News>() {
        @Override
        public News createFromParcel(Parcel source) {
            return new News(source);
        }

        @Override
        public News[] newArray(int size) {
            return new News[size];
        }
    };
    public String getHtml(){
        return this.html;
    }
    public String getTitle(){
        return this.title;
    }
    public String getDescription(){
        return this.description;
    }
    public String getCategory(){
        return this.category;
    }
    public String getDate(){
        return this.date;
    }
    public int getComments(){
        return this.comments;
    }
    public boolean getHighlight(){
        return this.highlight;
    }
}

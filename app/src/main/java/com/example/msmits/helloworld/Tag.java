package com.example.msmits.helloworld;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by msmits on 17/03/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)

public class Tag implements Parcelable {
    public int id;
    public String slug;
    public String title;
    public String description;
    public int post_count;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.slug);
        dest.writeString(this.title);
        dest.writeString(this.description);
        dest.writeInt(this.post_count);
    }

    public Tag() {
    }

    protected Tag(Parcel in) {
        this.id = in.readInt();
        this.slug = in.readString();
        this.title = in.readString();
        this.description = in.readString();
        this.post_count = in.readInt();
    }

    public static final Parcelable.Creator<Tag> CREATOR = new Parcelable.Creator<Tag>() {
        @Override
        public Tag createFromParcel(Parcel source) {
            return new Tag(source);
        }

        @Override
        public Tag[] newArray(int size) {
            return new Tag[size];
        }
    };
}

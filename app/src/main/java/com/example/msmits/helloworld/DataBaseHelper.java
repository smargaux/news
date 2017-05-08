package com.example.msmits.helloworld;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by msmits on 17/03/2017.
 */

public class DataBaseHelper extends SQLiteOpenHelper{
    public final String DB_NAME = "goglasses.db";
    private final int DB_VERSION = 1;
    private final String CREATE_TABLE_CATEGORIES =
            "CREATE TABLE categories (" +
                    "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "SLUG VARCHAR(255),"+
                    "TITLE VARCHAR(255),"+
                    "DESCRIPTION VARCHAR(255),"+
                    "PARENT int,"+
                    "POST_COUNT int);";
    private final String CREATE_TABLE_POSTS =
            "CREATE TABLE posts (" +
                    "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "SLUG VARCHAR(255),"+
                    "TITLE VARCHAR(255),"+
                    "TITLE_PLAIN VARCHAR(255),"+
                    "DESCRIPTION VARCHAR(255),"+
                    "DATE VARCHAR(255),"+
                    "MODIFIED VARCHAR(255),"+
                    "CONTENT LONG_TEXT,"+
                    "AUTHOR_ID INT,"+
                    "COMMENTS_COUNT INT,"+
                    "FAVORITE BOOLEAN DEFAULT 0, "+
                    "PARENT int);";
    private final String CREATE_TABLE_AUTHOR =
            "CREATE TABLE authors (" +
                    "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "NAME VARCHAR(255),"+
                    "FIRST_NAME VARCHAR(255),"+
                    "LAST_NAME  VARCHAR(255),"+
                    "NICKNAME VARCHAR(255));";
    private final String CREATE_TABLE_CATEGORY_POST=
            "CREATE TABLE category_post (" +
                    "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "ID_POST INT,"+
                    "ID_CATEGORY INT);";

    private static DataBaseHelper sInstance;

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_CATEGORIES);
        db.execSQL(CREATE_TABLE_POSTS);
        db.execSQL(CREATE_TABLE_CATEGORY_POST);
        db.execSQL(CREATE_TABLE_AUTHOR);

    }

    public DataBaseHelper(Context context, String DB_NAME, SQLiteDatabase.CursorFactory factory, int DB_VERSION) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public static DataBaseHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new DataBaseHelper(context,"goglasses.db",null,1);
        }
        return sInstance;
    }
}

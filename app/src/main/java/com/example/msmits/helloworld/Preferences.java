package com.example.msmits.helloworld;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class Preferences extends PreferenceFragment  {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.preferences);
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        PreferenceManager.setDefaultValues(getContext(), R.xml.preferences, false);

        Preference notification=(Preference)findPreference("pref_notification");
        Preference categories= (Preference)findPreference("pref_categories");
        /*notification.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {

                Intent notificationIntent = new Intent(getActivity(), MainActivity.class);
                PendingIntent contentIntent = PendingIntent.getActivity(getActivity(), 0, notificationIntent, 0);

                Log.i("Notification ",preference.toString());

                // On crée la notification
                NotificationCompat.Builder builder =
                        new NotificationCompat.Builder(getActivity())
                                .setSmallIcon(R.drawable.bookmark_plus_outline)
                                .setContentTitle(getResources().getString(R.string.app_name))
                                .setContentText("Notification")
                                .setContentIntent(contentIntent);



                // Notification manager
                NotificationManager notificationManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.notify((int)System.currentTimeMillis(), builder.build());
                return false;
            }
        });*/
        notification.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                Intent notificationIntent = new Intent(getActivity(), MainActivity.class);
                PendingIntent contentIntent = PendingIntent.getActivity(getActivity(), 0, notificationIntent, 0);

                Log.i("Notification ",preference.toString());

                // On crée la notification
                NotificationCompat.Builder builder =
                        new NotificationCompat.Builder(getActivity())
                                .setSmallIcon(R.drawable.bookmark_plus_outline)
                                .setContentTitle(getResources().getString(R.string.app_name))
                                .setContentText("Notification")
                                .setContentIntent(contentIntent);



                // Notification manager
                NotificationManager notificationManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.notify((int)System.currentTimeMillis(), builder.build());
                return false;            }
        });


        categories.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener(){
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                SQLiteDatabase db=DataBaseHelper.getInstance(
                        getActivity().getApplicationContext()).getWritableDatabase();
                //On vérifie si des catégories ont déjà été enregistrées dans la bdd
                String[] columns=new String[]{"SLUG"};
                Cursor cursor = db.query("categories",columns,null,null,null,null,null);
                String[] nouvelles_categories=newValue.toString().split(",");
                List<String> list_categories = new ArrayList<String>();

                if(cursor!=null && cursor.getCount()>0){
                    if (cursor.moveToFirst()) {
                        do {
                            list_categories.add(cursor.getString(0));
                            } while (cursor.moveToNext());
                        }
                }
                for(String category:list_categories){
                    ContentValues category_values= new ContentValues();
                    category_values.put("SLUG", category);
                    db.insert("categories",null,category_values);
                }

                return true;
            }
        });
    }


}
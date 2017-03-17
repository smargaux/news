package com.example.msmits.helloworld;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class Preferences extends PreferenceFragment  {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.preferences);
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        Preference notification=(Preference)findPreference("pref_notification");
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
    }


}
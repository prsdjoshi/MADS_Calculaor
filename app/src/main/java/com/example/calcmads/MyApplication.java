package com.example.calcmads;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.calcmads.data.model.HistoryModel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MyApplication extends Application {
    public static ArrayList<HistoryModel> historyModelArrayList;
    public static SharedPreferences sharedPreferences;

    @Override
    public void onCreate() {
        super.onCreate();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        historyModelArrayList = new ArrayList<>();
    }
}

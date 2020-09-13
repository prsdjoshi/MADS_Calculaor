package com.example.calcmads;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import androidx.annotation.NonNull;

import com.example.calcmads.data.model.HistoryModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyApplication extends Application {
    public static ArrayList<HistoryModel> historyModelArrayList;
    public static SharedPreferences sharedPreferences;
    private DatabaseReference mDatabase;
    private DatabaseReference loginEndPoint;
    public static String getFirebaseUsername = "", getFirebasePassword = "";

    @Override
    public void onCreate() {
        super.onCreate();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        historyModelArrayList = new ArrayList<>();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        getLoginData();
    }

    public void getLoginData() {
        loginEndPoint = mDatabase.child("login");
        loginEndPoint.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                getFirebaseUsername = dataSnapshot.child("username").getValue().toString();
                getFirebasePassword = dataSnapshot.child("password").getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}

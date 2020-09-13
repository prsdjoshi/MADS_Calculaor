package com.example.calcmads.data;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.calcmads.data.model.LoggedInUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    private DatabaseReference mDatabase;
    String getUsername = "";
    String getPassword = "";
    private DatabaseReference loginEndPoint;

    public Result<LoggedInUser> login(String username, String password) {

        try {
            // TODO: handle loggedInUser authentication
            mDatabase = FirebaseDatabase.getInstance().getReference();
            loginEndPoint = mDatabase.child("login");
            loginEndPoint.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    getUsername = dataSnapshot.child("username").getValue().toString();
                    getPassword = dataSnapshot.child("password").getValue().toString();

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            if (username.equalsIgnoreCase(getUsername) && password.equalsIgnoreCase(getPassword)) {
                LoggedInUser fakeUser =
                        new LoggedInUser(
                                java.util.UUID.randomUUID().toString(),
                                getUsername);
                return new Result.Success<>(fakeUser);
            } else {
                return new Result.Error(new Exception("Error logging in"));
            }

        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    public void logout() {
        // TODO: revoke authentication
    }
}
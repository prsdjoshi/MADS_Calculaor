package com.example.calcmads.data;

import android.os.HandlerThread;
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
import java.util.logging.Handler;

import static com.example.calcmads.MyApplication.getFirebasePassword;
import static com.example.calcmads.MyApplication.getFirebaseUsername;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    public Result<LoggedInUser> login(String username, String password) {

        try {
            // TODO: handle loggedInUser authentication

            if (username.equalsIgnoreCase(getFirebaseUsername) && password.equalsIgnoreCase(getFirebasePassword)) {
                LoggedInUser fakeUser =
                        new LoggedInUser(
                                java.util.UUID.randomUUID().toString(),
                                getFirebaseUsername);
                return new Result.Success<>(fakeUser);
            } else {
                return new Result.Error(new Exception("Error logging in"));
            }

        } catch (Exception e) {
            e.printStackTrace();
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    public void logout() {
        // TODO: revoke authentication
    }
}
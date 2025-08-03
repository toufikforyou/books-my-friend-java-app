package com.sopnolikhi.booksmyfriend.Design.Ui.Activities.Authentication;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;

import com.sopnolikhi.booksmyfriend.databinding.ActivityAuthUserBinding;

public class AuthUserActivity extends AppCompatActivity {

    ActivityAuthUserBinding authUserBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        authUserBinding = ActivityAuthUserBinding.inflate(getLayoutInflater());
        //setContentView(R.layout.activity_auth_user);
        setContentView(authUserBinding.getRoot());

    }
}
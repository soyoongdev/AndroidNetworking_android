package com.example.androidnetworking.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.androidnetworking.R;
import com.example.androidnetworking.ui.fragment.CheckEmailFragment;
import com.example.androidnetworking.ui.fragment.LoginFragment;

public class LoginManagerActivity extends AppCompatActivity{
    String TAG = "LoginManagerActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_manager);

        LoginFragment loginFragment = new LoginFragment();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.frame_login, loginFragment)
                    .commit();
        }
    }

}
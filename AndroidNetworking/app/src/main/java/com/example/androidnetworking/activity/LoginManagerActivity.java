package com.example.androidnetworking.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.ActivityOptions;
import android.os.Bundle;

import com.example.androidnetworking.R;
import com.example.androidnetworking.ui.fragment.LoginFragment;

public class LoginManagerActivity extends AppCompatActivity {
    FragmentManager fm = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_manager);

        FragmentTransaction ft = fm.beginTransaction();
        LoginFragment loginFragment = new LoginFragment();
        ft.add(R.id.frame_login, loginFragment)
                .commit();
    }
}
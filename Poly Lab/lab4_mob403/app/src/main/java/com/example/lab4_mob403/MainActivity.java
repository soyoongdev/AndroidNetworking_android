package com.example.lab4_mob403;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.lab4_mob403.Fragment.LoginFragment;

public class MainActivity extends AppCompatActivity {
    FragmentManager fm = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Add
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.fragment_frame, new LoginFragment())
                .commit();
    }
}
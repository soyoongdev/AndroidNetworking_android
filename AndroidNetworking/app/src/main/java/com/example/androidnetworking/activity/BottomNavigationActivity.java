package com.example.androidnetworking.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.androidnetworking.R;
import com.example.androidnetworking.ui.fragment.HeartFragment;
import com.example.androidnetworking.ui.fragment.HomeFragment;
import com.example.androidnetworking.ui.fragment.NotificationFragment;
import com.example.androidnetworking.ui.fragment.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class BottomNavigationActivity extends AppCompatActivity {
    BottomNavigationView navigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navigation);

        loadFragment(new HomeFragment());

        navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected( MenuItem menuItem) {
                int id = menuItem.getItemId();
                if(id == R.id.nav_home) {
                    loadFragment(new HomeFragment());
                    return true;
                } else if(id == R.id.nav_heart) {
                    loadFragment(new HeartFragment());
                    return true;
                } else if(id == R.id.nav_noti) {
                    loadFragment(new NotificationFragment());
                    return true;
                } else if(id == R.id.nav_profile) {
                    loadFragment(new ProfileFragment());
                    return true;
                }
                return true;
            }
        });

    }

    public void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.commit();
    }
}
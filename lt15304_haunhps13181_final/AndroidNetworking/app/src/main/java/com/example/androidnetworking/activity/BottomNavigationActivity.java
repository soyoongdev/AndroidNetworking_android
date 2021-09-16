package com.example.androidnetworking.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import android.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.androidnetworking.R;
import com.example.androidnetworking.ui.fragment.HeartFragment;
import com.example.androidnetworking.ui.fragment.HomeFragment;
import com.example.androidnetworking.ui.fragment.NotificationFragment;
import com.example.androidnetworking.ui.fragment.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;


public class BottomNavigationActivity extends AppCompatActivity {
    BottomNavigationView navigation;

    public BottomNavigationActivity() {

    }


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
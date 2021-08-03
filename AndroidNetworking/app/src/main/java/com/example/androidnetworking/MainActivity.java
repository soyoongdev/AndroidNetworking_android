package com.example.androidnetworking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.androidnetworking.R;
import com.example.androidnetworking.activity.LoginManagerActivity;
import com.example.androidnetworking.model.User;
import com.example.androidnetworking.server_client.SharedPrefManager;
import com.example.androidnetworking.ui.fragment.LoginFragment;

public class MainActivity extends AppCompatActivity {
    TextView tvMain;
    Button btnLogout;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (SharedPrefManager.getInstance(MainActivity.this).isLoggedIn()) {
            User user = SharedPrefManager.getInstance(MainActivity.this).getUser();

            tvMain.setText(
                    "id: " + String.valueOf(user.getId()) + "\n" +
                            "username: " + user.getUsername() + "\n" +
                            "email: " + user.getEmail() + "\n" +
                            "date and time: " + user.getDatetime()
            );
        } else {
            startActivity(new Intent(MainActivity.this, LoginManagerActivity.class));
            finish();
        }

        tvMain = findViewById(R.id.tvMain);
        btnLogout = findViewById(R.id.btnLogout);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPrefManager.getInstance(getApplicationContext()).logout();
            }
        });

    }


}
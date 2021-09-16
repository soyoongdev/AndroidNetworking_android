package com.example.lab3_mob403;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.lab3_mob403.bai1.bai1;
import com.example.lab3_mob403.bai2.JsonParserToListViewActivity;
import com.example.lab3_mob403.bai2_2.Bai2Activity;
import com.example.lab3_mob403.bai3.bai3;
import com.example.lab3_mob403.bai4.bai4;

public class MainActivity extends AppCompatActivity {
    Button btnBai1, btnBai2, btnBai2_2, btnBai3, btnBai4;
    public static String ip = "192.168.1.5";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnBai1 = findViewById(R.id.btnBai1);
        btnBai2 = findViewById(R.id.btnBai2_1);
        btnBai2_2 = findViewById(R.id.btnBai2_2);
        btnBai3 = findViewById(R.id.btnBai3);
        btnBai4 = findViewById(R.id.btnBai4);

        btnBai1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, bai1.class));
            }
        });

        btnBai2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, JsonParserToListViewActivity.class));
            }
        });
        btnBai2_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Bai2Activity.class));
            }
        });

        btnBai3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, bai3.class));
            }
        });

        btnBai4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, bai4.class));
            }
        });
    }
}
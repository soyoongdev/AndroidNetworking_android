package com.example.lab2_mob403.bai2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.lab2_mob403.R;
import com.example.lab2_mob403.bai3.bai3;

public class bai2 extends AppCompatActivity {
    private Button btnSubMit, btnNextBai3;
    private TextView tvResult;
    private EditText edtWidth, edtLength;
    private String LINK_SERVER;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bai2);

        LINK_SERVER = "http://172.20.10.7:8080/php/rectangle_POST.php";
        btnSubMit = findViewById(R.id.btnSubMit2);
        btnNextBai3 = findViewById(R.id.btnBai3);
        tvResult = findViewById(R.id.tvResult2);
        edtWidth = findViewById(R.id.edtWidth);
        edtLength = findViewById(R.id.edtLength);

        btnSubMit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BackgroundTask_POST bgTask = new BackgroundTask_POST(bai2.this, LINK_SERVER, edtWidth.getText().toString(), edtLength.getText().toString(), tvResult);
                bgTask.execute();
            }
        });


        btnNextBai3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(bai2.this, bai3.class));
            }
        });

    }
}
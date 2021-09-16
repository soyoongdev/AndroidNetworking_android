package com.example.lab2_mob403.bai3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.lab2_mob403.R;
import com.example.lab2_mob403.bai2.BackgroundTask_POST;
import com.example.lab2_mob403.bai2.bai2;
import com.example.lab2_mob403.bai4.ui.LoginActivity;

public class bai3 extends AppCompatActivity {
    private Button btnSubMit, btnNextBai4;
    private TextView tvResult;
    private EditText edtCanh;
    private String LINK_SERVER;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bai3);

        LINK_SERVER = "http://172.20.10.7:8080/php/canh_POST.php";
        btnSubMit = findViewById(R.id.btnSubmit3);
        btnNextBai4 = findViewById(R.id.btnBai4);
        tvResult = findViewById(R.id.tvResult3);
        edtCanh = findViewById(R.id.edtCanh);

        btnSubMit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                BackgroundTask_POST2 bg = new BackgroundTask_POST2(bai3.this, LINK_SERVER, edtCanh.getText().toString(), tvResult);
//                bg.execute();
                tvResult.setText("123");
            }
        });

        btnNextBai4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(bai3.this, LoginActivity.class));
            }
        });
    }
}
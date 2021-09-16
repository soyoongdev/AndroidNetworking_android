package com.example.lab2_mob403.bai4.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.lab2_mob403.R;

public class LoginActivity extends AppCompatActivity {
    private EditText edtUsername, edtPassword;
    private String LINK_SERVER;
    private Button btnSubmit;
    private ProgressBar progressBar;
    private TextView tvResult4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        LINK_SERVER = "http://172.20.10.7:8080/php/login.php";
        edtUsername = (EditText) findViewById(R.id.username);
        edtPassword = (EditText) findViewById(R.id.password);
        btnSubmit = (Button) findViewById(R.id.login);
        tvResult4 = (TextView) findViewById(R.id.tvResult4);
        progressBar = (ProgressBar) findViewById(R.id.loading);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BackgroundTask_POST3 bg = new BackgroundTask_POST3(LoginActivity.this, LINK_SERVER, edtUsername.getText().toString(), edtPassword.getText().toString(), tvResult4);
                bg.execute();
            }
        });
    }
}
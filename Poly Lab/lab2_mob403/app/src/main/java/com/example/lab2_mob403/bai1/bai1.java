package com.example.lab2_mob403.bai1;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.lab2_mob403.R;
import com.example.lab2_mob403.bai2.bai2;

public class bai1 extends AppCompatActivity {
    private Button btnSubMit, btnNextBai2;
    private TextView tvResult;
    private EditText edtName, edtScore;
    private String LINK_SERVER;
    String ipAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bai1);

        try {
            WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
            ipAddress = Formatter.formatIpAddress(wifiManager.getConnectionInfo().getIpAddress());

        } catch (Exception ex) {
            Log.e("IP Address", ex.toString());
        }

        LINK_SERVER = "http://172.20.10.7:8080/php/studient_get.php";
        btnSubMit = findViewById(R.id.btnSubMit);
        btnNextBai2 = findViewById(R.id.btnBai2);
        tvResult = findViewById(R.id.tvResult);
        edtName = findViewById(R.id.edtName);
        edtScore = findViewById(R.id.edtScore);


        btnSubMit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BackgroundTask_GET bgTask = new BackgroundTask_GET(bai1.this, LINK_SERVER, edtName.getText().toString(), edtScore.getText().toString(), tvResult);
                bgTask.execute();
            }
        });

        btnNextBai2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(bai1.this, bai2.class));
            }
        });
    }

}
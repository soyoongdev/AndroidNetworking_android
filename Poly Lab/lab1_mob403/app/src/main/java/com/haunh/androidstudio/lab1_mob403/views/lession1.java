package com.haunh.androidstudio.lab1_mob403.views;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.haunh.androidstudio.lab1_mob403.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class lession1 extends Activity {
    private Button btnLoadWithThread, btnNextLess2;
    private ImageView imgView;
    private TextView txtView;
    private String str1 = "https://ap.poly.edu.vn/images/logo.png";
    private Bitmap bitmap = null;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lession1_activity);

        btnLoadWithThread = (Button) findViewById(R.id.btnLoad);
        btnNextLess2 = (Button) findViewById(R.id.btnNextLess2);
        imgView = (ImageView) findViewById(R.id.imgView);
        txtView = (TextView) findViewById(R.id.txtView);
        progressBar = (ProgressBar) findViewById(R.id.grogress);

        progressBar = new ProgressBar(this);

        btnLoadWithThread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Thread myThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(View.VISIBLE);
                        bitmap = loadImageUsingThread(str1);
                        imgView.post(new Runnable() {
                            @Override
                            public void run() {
                                txtView.setText("Image loaded!");
                                imgView.setImageBitmap(bitmap);
                                if (txtView.getText().toString().equals("Image Loaded!"))  {
                                    progressBar.setVisibility(View.GONE);
                                }else {
                                    progressBar.setVisibility(View.VISIBLE);
                                }
                            }
                        });
                    }
                });
                myThread.start();
            }
        });

        btnNextLess2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(lession1.this, lession2.class);
                startActivity(intent);
            }
        });


    }

    private Bitmap loadImageUsingThread(String link) {
        URL url;
        Bitmap bm = null;
        try {
            url = new URL(link);
            bm = BitmapFactory.decodeStream(url.openConnection().getInputStream());
        }catch (IOException e) {
            Log.i("Error: " , e.getMessage());
        }
        return bm;
    }

}
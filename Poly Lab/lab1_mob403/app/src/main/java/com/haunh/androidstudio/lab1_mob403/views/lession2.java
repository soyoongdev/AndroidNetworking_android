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

public class lession2 extends Activity {
    private Button btnLoad, btn3;
    private ImageView imgView2;
    private TextView txtView2;
    private String str2 = "https://images.fpt.shop/unsafe/fit-in/585x390/filters:quality(90):fill(white)/fptshop.com.vn/Uploads/Originals/2020/9/19/637361530855765347_Apple%20Watch%20SE%20GPS%20Cellular%2044mm%20den%201.png";
    private Bitmap bitmap = null;
    private ProgressBar progressBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lession2_activity);

        btnLoad = (Button) findViewById(R.id.btnLoad2);
        btn3 = findViewById(R.id.btn3);
        imgView2 = (ImageView) findViewById(R.id.imgView2);
        txtView2 = (TextView) findViewById(R.id.txtView2);
        progressBar = (ProgressBar) findViewById(R.id.grogress2);

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(lession2.this, lession3.class);
                startActivity(intent);
            }
        });

        btnLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar = new ProgressBar(lession2.this);
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(View.VISIBLE);
                        bitmap = downloadBitmap(str2);
                        Message msg = handler.obtainMessage();
                        Bundle bundle = new Bundle();
                        String threadMess = "Image downloaded!";
                        bundle.putString("message", threadMess);
                        msg.setData(bundle);
                        handler.sendMessage(msg);
                    }
                };
                Thread thread = new Thread(runnable);
                thread.start();
            }
        });

    }


    private Bitmap downloadBitmap (String link) {
        try {
            URL url = new URL(link);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            InputStream inp = connection.getInputStream();
            bitmap = BitmapFactory.decodeStream(inp);
            return bitmap;
        }catch (IOException e) {
            Log.i("Error: " , e.getMessage());
        }
        return null;
    }

    private Handler handler = new Handler() {
        public void handleMessage(Message mess) {
            super.handleMessage(mess);
            Bundle bundle = mess.getData();
            String getMess = bundle.getString("message");
            txtView2.setText(getMess);
            imgView2.setImageBitmap(bitmap);
        }
    };

}
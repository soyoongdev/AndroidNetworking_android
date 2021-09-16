package com.haunh.androidstudio.lab1_mob403.views;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.haunh.androidstudio.lab1_mob403.R;

public class lession3 extends Activity implements OnClickListener, Listener {
    private Button btnLoad, btn4;
    private ImageView imgView3;
    private TextView txtView3;
    private String str3 = "https://images.fpt.shop/unsafe/fit-in/585x390/filters:quality(90):fill(white)/fptshop.com.vn/Uploads/Originals/2020/9/19/637361530855765347_Apple%20Watch%20SE%20GPS%20Cellular%2044mm%20den%201.png";
    private Bitmap bitmap = null;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lession3);

        btnLoad = findViewById(R.id.btnLoad3);
        btn4 = findViewById(R.id.btn4);
        imgView3 = (ImageView) findViewById(R.id.imgView3);
        txtView3 = (TextView) findViewById(R.id.txtView3);
        progressBar = (ProgressBar) findViewById(R.id.grogress3);

        btnLoad.setOnClickListener(this);

        btn4.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(lession3.this, lession4.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnLoad3:
                new loadImageWithAsyncTask( this, this).execute(str3);
                break;
        }
    }

    @Override
    public void onImageLoaded(Bitmap bitmap) {
        imgView3.setImageBitmap(bitmap);
        txtView3.setText("Image downloaded!");
    }

    @Override
    public void onError() {
        txtView3.setText("Error load image!");
    }

}
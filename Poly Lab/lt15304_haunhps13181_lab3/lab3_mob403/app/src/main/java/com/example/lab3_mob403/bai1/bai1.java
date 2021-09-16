package com.example.lab3_mob403.bai1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.example.lab3_mob403.R;

public class bai1 extends AppCompatActivity {
    ListView lv_contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bai1);
        lv_contact = findViewById(R.id.lv_contact);

        GetContacts getContacts = new GetContacts(bai1.this, lv_contact);
        getContacts.execute();
    }
}
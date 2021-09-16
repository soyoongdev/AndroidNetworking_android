package com.example.lab3_mob403.bai2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.ListView;

import com.example.lab3_mob403.R;

public class JsonParserToListViewActivity extends AppCompatActivity {
    Context context;
    ListView listViewBai1;
    GetAPI getAPI;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json_parser_to_list_view);
        listViewBai1 = (ListView) findViewById(R.id.listViewBai1) ;

        getAPI = new GetAPI(JsonParserToListViewActivity.this, listViewBai1);
        getAPI.execute();

    }
}
package com.example.lab3_mob403.bai4;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab3_mob403.Model.Contact;
import com.example.lab3_mob403.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class bai4 extends AppCompatActivity {
    private String TAG = bai4.class.getSimpleName();
    View parentView;
    private ListView listView;
    private ArrayList<Contact> contactList;
    private MyContactAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bai4);
        initViews();
    }

    private void initViews() {
        contactList = new ArrayList<>();
        listView = (ListView) findViewById(R.id.lv_b4);
        parentView = findViewById(R.id.parentLayout);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Snackbar.make(parentView, contactList.get(i).getName() + " => " + contactList.get(i).getPhone().getHome(), Snackbar.LENGTH_LONG).show();
            }
        });

        Toast toast = Toast.makeText(getApplicationContext(), "Loading..", Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0); toast.show();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabB4);
        assert fab != null ;

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (InternetConnection.checkConnection(getApplicationContext())) {
                    final ProgressDialog progressDialog = new ProgressDialog(bai4.this);
                    progressDialog.setTitle("Title");
                    progressDialog.setMessage("Message");

                    ApiService api = RetrofitClient.getApiService();
                    Call<ContactList> call = api.getMyJSON();

                    call.enqueue(new Callback<ContactList>() {
                        @Override
                        public void onResponse(Call<ContactList> call, Response<ContactList> response) {
                            //Dismiss Dialog
                            progressDialog.dismiss();
                            if(response.isSuccessful()) { // Got Successfully
                                contactList = response.body().getContacts();
                                // Binding that List to Adapter
                                adapter = new MyContactAdapter(bai4.this, contactList);
                                listView.setAdapter(adapter);
                            } else { Snackbar.make(parentView,
                                    "Some thing went wrong!", Snackbar.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ContactList> call, Throwable t) {
                            progressDialog.dismiss();
                        }
                    });
                } else {
                    Snackbar.make(parentView,
                            "Internet connection not available", Snackbar.LENGTH_LONG).show();
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.title1) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
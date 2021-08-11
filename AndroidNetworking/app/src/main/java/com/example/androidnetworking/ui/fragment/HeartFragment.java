package com.example.androidnetworking.ui.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.androidnetworking.R;
import com.example.androidnetworking.adapter.CategoryAdapter;
import com.example.androidnetworking.model.Category;
import com.example.androidnetworking.model.Product;
import com.example.androidnetworking.model.ServerResponse;
import com.example.androidnetworking.server_client.Constants;
import com.example.androidnetworking.server_client.VolleySingleton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HeartFragment extends Fragment {
    String TAG = "HeartFragment";
    private RecyclerView rcvCat;
    ArrayList<Category> catLists;
    CategoryAdapter categoryAdapter;
    private Dialog dialog;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view  = inflater.inflate(R.layout.fragment_heart, container, false);
        loadCat();
        initView(view);
        return view;
    }

    private void initView(View view) {
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fabLoadCat);
        rcvCat = (RecyclerView) view.findViewById(R.id.rcv_cat);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addStory();
            }
        });
    }
    private void loadCat() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.URL_GET_ALL_CAT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));
                            boolean resultError = jsonObject.getBoolean("error");
                            String message = jsonObject.getString("message");
                            ServerResponse serverResponse = new ServerResponse(resultError, message);
                            JSONObject objProduct = jsonObject.getJSONObject("category");
                            JSONArray jsonArrayPro = objProduct.getJSONArray("categories");

                            if (serverResponse.getState() == false) {
                                catLists = new ArrayList<>();
                                // Get item from product
                                for (int i = 0; i < jsonArrayPro.length(); i++) {
                                    String resultArray = jsonArrayPro.getString(i);
                                    JSONObject jobItem = new JSONObject(resultArray);
                                    String id = jobItem.getString("id_type");
                                    String name = jobItem.getString("name_type");
                                    String created_at = jobItem.getString("created_at");
                                    String updated_at = jobItem.getString("updated_at");
                                    Category cat = new Category(id, name, created_at, updated_at);
                                    catLists.add(cat);
                                }
                                categoryAdapter = new CategoryAdapter(getActivity(), catLists);
                                rcvCat.setLayoutManager(new LinearLayoutManager(getContext()));
                                rcvCat.setAdapter(categoryAdapter);
                                categoryAdapter.notifyDataSetChanged();

                            }
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                            Log.e(TAG, e.getMessage());
                            Snackbar.make(getView(), e.getMessage(), Snackbar.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "Result error: " + error.getMessage());

                    }
                });

        VolleySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);
    }


    public void addStory() {
        dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.alertdialog_category_layout);
        dialog.setCancelable(false);
        Window window = dialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        if (dialog != null && dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        }
        TextView titleAlert = dialog.findViewById(R.id.tvTitleDialogCat);
        titleAlert.setText(R.string.add_cat_title);
        EditText name = dialog.findViewById(R.id.edtNameAddCat);
        Button btnAdd = dialog.findViewById(R.id.btnSubmitAddCat);
        Button btnCancel = dialog.findViewById(R.id.btnCancelCat);
        btnAdd.setText(R.string.add_cat_title);

        btnAdd.setOnClickListener(x -> {
            if(!name.getText().toString().isEmpty()){
                addCat(name.getText().toString());
                dialog.dismiss();
            }else{
                Toast.makeText(getContext(),
                        "Can not add category!",
                        Toast.LENGTH_SHORT).show();
            }
        });

        btnCancel.setOnClickListener(v -> {
            dialog.dismiss();
        });
        dialog.show();
    }

    private void addCat(String name_type) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_INSERT_CAT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e(TAG, "StringRequest onResponse: " + response);
                        try {
                            JSONObject jsonObject = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));
                            ServerResponse serverResponse = new ServerResponse(jsonObject.getBoolean("error"), jsonObject.getString("message"));

                            if (serverResponse.getState() == false) {
                                Toast.makeText(getContext(), serverResponse.getMessage(), Toast.LENGTH_SHORT).show();
                                loadCat();
                                dialog.dismiss();
                            } else {
                                Toast.makeText(getActivity(), "Failed!", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e(TAG, e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "StringRequest onErrorResponse: " + error.getMessage());
                        Snackbar.make(getView(), error.getMessage(), Snackbar.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("name_type", name_type);
                return params;
            }
        };

        VolleySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);
    }
}
package com.example.androidnetworking.ui.fragment;

import android.app.AlertDialog;
import android.app.appsearch.SetSchemaResponse;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.androidnetworking.R;
import com.example.androidnetworking.adapter.ProductAdapter;
import com.example.androidnetworking.model.Product;
import com.example.androidnetworking.model.ServerResponse;
import com.example.androidnetworking.model.User;
import com.example.androidnetworking.server_client.Constants;
import com.example.androidnetworking.server_client.SharedPrefManager;
import com.example.androidnetworking.server_client.VolleySingleton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HomeFragment extends Fragment {
    String TAG = "HomeFragment";
    private FloatingActionButton fab;
    private RecyclerView rcvPro;
    ArrayList<Product> productsLists;
    ProductAdapter productAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view  = inflater.inflate(R.layout.fragment_home, container, false);
        loadData();
        initView(view);
        return view;
    }

    private void initView(View view) {
        fab = (FloatingActionButton) view.findViewById(R.id.fabLoadPro);
        rcvPro = (RecyclerView) view.findViewById(R.id.rcv_product);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayAlertDialog2();
            }
        });

    }

    private void loadData() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_GET_ALL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));
                            Log.i(TAG, "Response: " + jsonObject);
                            boolean resultError = jsonObject.getBoolean("error");
                            String message = jsonObject.getString("message");
                            ServerResponse serverResponse = new ServerResponse(resultError, message);
                            Log.i(TAG, "Server response state: " + serverResponse.getState());
                            Log.i(TAG, "Server response message: " + serverResponse.getMessage());
                            JSONObject objProduct = jsonObject.getJSONObject("product");
                            Log.i(TAG, "Server response1 product: " + objProduct);
                            JSONArray jsonArrayPro = objProduct.getJSONArray("products");


                            if (serverResponse.getState() == false) {
                                productsLists = new ArrayList<>();
                                // Get item from product
                                for (int i = 0; i < jsonArrayPro.length(); i++) {
                                    String resultArray = jsonArrayPro.getString(i);
                                    JSONObject jobItem = new JSONObject(resultArray);
                                    String id = jobItem.getString("id");
                                    String name = jobItem.getString("name");
                                    String price = jobItem.getString("price");
                                    String created_at = jobItem.getString("created_at");
                                    String updated_at = jobItem.getString("updated_at");
                                    Product product = new Product(Integer.valueOf(id), name, price, created_at, updated_at);
                                    productsLists.add(product);
                                    Log.i(TAG, "Result Product All: " + resultArray);
                                }
                                productAdapter = new ProductAdapter(getActivity().getBaseContext(), productsLists);
                                rcvPro.setLayoutManager(new LinearLayoutManager(getContext()));
                                rcvPro.setAdapter(productAdapter);
                                productAdapter.notifyDataSetChanged();

                                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();

                            }
                        } catch (JSONException e) {
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

    public void displayAlertDialog2() {
        

    }

    public void displayAlertDialog() {
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.alert_insert_product_layout, null);
        final EditText edtName = (EditText) alertLayout.findViewById(R.id.edtNameIn);
        final EditText edtPrice = (EditText) alertLayout.findViewById(R.id.edtPriceIn);
        final Button btnAdd = (Button) alertLayout.findViewById(R.id.btnSubmitAdd);
        final Button btnCancel = (Button) alertLayout.findViewById(R.id.btnCancel);

        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setView(alertLayout);
        alert.setCancelable(false);


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Add button clicked", Toast.LENGTH_SHORT).show();

            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Add button cancel", Toast.LENGTH_SHORT).show();
            }
        });



        AlertDialog dialog = alert.create();
        dialog.show();
    }

}
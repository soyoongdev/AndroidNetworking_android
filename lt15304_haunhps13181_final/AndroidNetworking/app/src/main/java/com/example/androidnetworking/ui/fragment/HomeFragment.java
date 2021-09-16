package com.example.androidnetworking.ui.fragment;

import android.app.Dialog;
import android.app.SearchManager;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.androidnetworking.R;
import com.example.androidnetworking.adapter.ProductAdapter;
import com.example.androidnetworking.model.Category;
import com.example.androidnetworking.model.Product;
import com.example.androidnetworking.model.ServerResponse;
import com.example.androidnetworking.server_client.Constants;
import com.example.androidnetworking.server_client.VolleySingleton;
import com.example.androidnetworking.ui.FilterCustomerSearch;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

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
    ArrayList<Product> productsLists = new ArrayList<>();
    ArrayList<Category> catLists = new ArrayList<>();
    ProductAdapter productAdapter;
    Dialog dialog;
    String type = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view  = inflater.inflate(R.layout.fragment_home, container, false);
        initView(view);
        listCatData();
        loadProductData();
        return view;
    }

    private void initView(View view) {
        fab = (FloatingActionButton) view.findViewById(R.id.fabLoadPro);
        rcvPro = (RecyclerView) view.findViewById(R.id.rcv_product);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addStory();
            }
        });

    }


    public void addStory() {
        dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.alertdialog_product_layout);
        dialog.setCancelable(false);
        Window window = dialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        if (dialog != null && dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        }
        TextView titleAlert = dialog.findViewById(R.id.tvTitleDialogPro);
        titleAlert.setText(R.string.add_pro);
        EditText name = dialog.findViewById(R.id.edtNameAdd);
        EditText price = dialog.findViewById(R.id.edtPriceAdd);
        Spinner spinner = dialog.findViewById(R.id.spinner_category);
        Button btnAdd = dialog.findViewById(R.id.btnSubmitAdd);
        Button btnCancel = dialog.findViewById(R.id.btnCancel);
        btnAdd.setText(R.string.add);
        ArrayAdapter<Category> adapter =
                new ArrayAdapter<Category>(getContext(), android.R.layout.simple_spinner_dropdown_item, catLists);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        final int[] select = new int[1];
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.i("select","" + i);
                type = catLists.get(i).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                type = catLists.get(0).getId();
            }
        });

        btnAdd.setOnClickListener(x -> {
            String name_ = name.getText().toString().trim();
            String price_ = price.getText().toString().trim();

            String type_ = catLists.get(select[0]).getId();
            Product story = new Product(name_, price_, type_);
            addProduct(story);
        });

        btnCancel.setOnClickListener(v -> {
            dialog.dismiss();
        });
        dialog.show();
    }

    private void addProduct(Product product) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_INSERT_PRODUCT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e(TAG, "StringRequest onResponse: " + response);
                        try {
                            JSONObject jsonObject = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));
                            ServerResponse serverResponse = new ServerResponse(jsonObject.getBoolean("error"), jsonObject.getString("message"));

                            if (serverResponse.getState() == false) {
                                Toast.makeText(getContext(), serverResponse.getMessage(), Toast.LENGTH_SHORT).show();
                                loadProductData();
                                dialog.dismiss();
                            } else {
                                Toast.makeText(getActivity(), "Failed!", Toast.LENGTH_SHORT).show();
                            }

                        } catch (Exception e) {
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
                params.put("name", product.getName());
                params.put("price", product.getPrice());
                params.put("id_type", product.getId_type());
                return params;
            }
        };

        VolleySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);
    }

    private void loadProductData() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_GET_ALL_PRODUCT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));
                            boolean resultError = jsonObject.getBoolean("error");
                            String message = jsonObject.getString("message");
                            ServerResponse serverResponse = new ServerResponse(resultError, message);
                            JSONObject objProduct = jsonObject.getJSONObject("product");
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
                                    String id_type = jobItem.getString("id_type");
                                    Product product = new Product(id, name, price, created_at, updated_at, id_type);
                                    productsLists.add(product);
                                }
                                productAdapter = new ProductAdapter(getActivity(), productsLists);
                                rcvPro.setLayoutManager(new LinearLayoutManager(getContext()));
                                rcvPro.setAdapter(productAdapter);
                                productAdapter.notifyDataSetChanged();

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


    private void listCatData() {
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

}
package com.example.androidnetworking.adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.androidnetworking.R;
import com.example.androidnetworking.model.Category;
import com.example.androidnetworking.model.Product;
import com.example.androidnetworking.model.ServerResponse;
import com.example.androidnetworking.server_client.Constants;
import com.example.androidnetworking.server_client.VolleySingleton;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder> {
    String TAG = "ProductAdapter";
    private Context context;
    public ArrayList<Product> productList = new ArrayList<>();
    public ArrayList<Category> catLists = new ArrayList<>();
    public Product product;
    public Category cat;
    public Dialog dialog;
    public String selectedNameSpinner = "";
    public String getIdTypeCatList = null;

    public ProductAdapter(Context context, ArrayList<Product> productList) {
        this.context = context;
        this.productList = productList;

    }

    // Create layout view
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product_home, parent, false);
        return new MyViewHolder(itemView);
    }


    // For set data
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        product = productList.get(position);
        final int pos = position;
        holder.tvName.setText(product.getName());
        holder.tvPrice.setText(product.getPrice());
        holder.tvCreated.setText("Created at: " + product.getCreated_at());
        holder.tvUpdated.setText("Updated at: " + product.getUpdated_at());
        holder.tvNameType.setText(product.getId_type());
        getAllCat();
        int[] images = new int[]{R.drawable.a1, R.drawable.a2, R.drawable.a3, R.drawable.a4, R.drawable.a5, R.drawable.a6, R.drawable.a7, R.drawable.a8, R.drawable.a9};
        //Get the imageview
        int imageID = (int) (Math.random() * images.length);
        // Set display image
        holder.imgView.setImageResource(images[imageID]);
        getTypeList(holder, product);

        holder.imgMoreAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetMenu(pos);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (productList == null){
            return 0;
        }
        return productList.size();
    }


    // Create view element item card view
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvName, tvPrice, tvCreated, tvUpdated, tvNameType;
        private final ImageView imgView, imgMoreAction;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvNamePro);
            tvPrice = itemView.findViewById(R.id.tvPricePro);
            imgView = itemView.findViewById(R.id.imgPro);
            imgMoreAction = itemView.findViewById(R.id.imgMoreAction);
            tvCreated = itemView.findViewById(R.id.tvCreatedAPo);
            tvUpdated = itemView.findViewById(R.id.tvUpdatedAtPro);
            tvNameType = itemView.findViewById(R.id.tvNameTypePro);

        }

    }


    private void bottomSheetMenu(int position) {
        product = productList.get(position);

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
                context, R.style.BottomSheetDialogTheme
        );

        View bottomSheetView = LayoutInflater.from(context).inflate(
                R.layout.bottomsheet_pro_dialog,
                bottomSheetDialog.findViewById(R.id.bottomSheetContainer)
        );
        bottomSheetView.findViewById(R.id.btnSuaPro).setOnClickListener(view ->
        {
            bottomSheetDialog.dismiss();
            dialog = new Dialog(context);
            dialog.getWindow().getAttributes().windowAnimations = R.style.up_down;
            dialog.setContentView(R.layout.alertdialog_product_layout);
            dialog.setCancelable(false);
            Window window = dialog.getWindow();
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            if (dialog != null && dialog.getWindow() != null) {
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
            }
            TextView titleAlert = dialog.findViewById(R.id.tvTitleDialogPro);
            titleAlert.setText(R.string.title_alert_update);
            EditText name = dialog.findViewById(R.id.edtNameAdd);
            EditText price = dialog.findViewById(R.id.edtPriceAdd);
            Spinner spinner = dialog.findViewById(R.id.spinner_category);
            Button btnAdd = dialog.findViewById(R.id.btnSubmitAdd);
            Button btnCancel = dialog.findViewById(R.id.btnCancel);
            btnAdd.setText(R.string.update);
            name.setText(product.getName());
            price.setText(product.getPrice());

            ArrayAdapter<Category> adapter =
                    new ArrayAdapter<Category>(context, android.R.layout.simple_spinner_dropdown_item, catLists);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);


            for (int i = 0; i < catLists.size(); i++) {
                if (String.valueOf(product.getId_type()).contains(String.valueOf(catLists.get(i).getId()))) {
                    selectedNameSpinner = catLists.get(i).toString();
                    spinner.setSelection(i);
                    adapter.notifyDataSetChanged();
                }

            }

            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    // On selecting a spinner item
                    Category cat_ = (Category) parent.getItemAtPosition(position);
                    getIdTypeCatList = cat_.getId();
                    Toast.makeText(context, cat_.getId(), Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }

            });

            btnAdd.setOnClickListener(x -> {

                Product story = new Product(product.getId(), name.getText().toString(), price.getText().toString(), getIdTypeCatList);
                update(story);
                dialog.dismiss();
            });

            btnCancel.setOnClickListener(v -> {
                dialog.dismiss();
            });
            dialog.show();
        });
        bottomSheetView.findViewById(R.id.btnDeleteCat).setOnClickListener(view ->
        {
            bottomSheetDialog.dismiss();
            Log.i("id", productList.get(position).getId());
            delete(productList.get(position).getId());
        });
        bottomSheetView.findViewById(R.id.btnDismissBottomSheetPro).setOnClickListener(view -> bottomSheetDialog.dismiss());
        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetDialog.show();
    }

    private void update(Product story) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_UPDATE_PRODUCT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        Log.e(TAG, "StringRequest onResponse: " +
//                                story.getId() + "\n" +
//                                story.getName() + "\n" +
//                                story.getPrice() + "\n" +
//                                story.getId_type());
                        try {
                            JSONObject jsonObject = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));
                            Log.i(TAG, "Response: " + jsonObject);
                            ServerResponse serverResponse = new ServerResponse(jsonObject.getBoolean("error"), jsonObject.getString("message"));

                            if (serverResponse.getState() == false) {
                                Toast.makeText(context, serverResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, serverResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                            notifyDataChangePro();
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
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", story.getId());
                params.put("name", story.getName());
                params.put("price", story.getPrice());
                params.put("id_type", story.getId_type());
                return params;
            }
        };

        VolleySingleton.getInstance(context).addToRequestQueue(stringRequest);
    }

    private void delete(String id) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_DELETE_PRODUCT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));
                            ServerResponse serverResponse = new ServerResponse(jsonObject.getBoolean("error"), jsonObject.getString("message"));
                            notifyDataChangePro();
                            if (serverResponse.getState() == false) {
                                Toast.makeText(context, serverResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, serverResponse.getMessage(), Toast.LENGTH_SHORT).show();
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
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", id);
                return params;
            }
        };

        VolleySingleton.getInstance(context).addToRequestQueue(stringRequest);
    }

    private void addProduct(Product product) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_INSERT_PRODUCT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));
                            ServerResponse serverResponse = new ServerResponse(jsonObject.getBoolean("error"), jsonObject.getString("message"));
                            if (serverResponse.getState() == false) {
                                Toast.makeText(context, serverResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, serverResponse.getMessage(), Toast.LENGTH_SHORT).show();
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

        VolleySingleton.getInstance(context).addToRequestQueue(stringRequest);
    }

    private void getAllCat() {
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
                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.e(TAG, e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "Result error: " + error.getMessage());

                    }
                });

        VolleySingleton.getInstance(context).addToRequestQueue(stringRequest);
    }

    private void getTypeList(MyViewHolder holder, Product product) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.URL_GET_ALL_CAT,
                response -> {
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
                                Log.i(TAG, "Result Category All: " + resultArray);
                            }

                            for (int z = 0; z < catLists.size(); z++) {
                                if (catLists.get(z).equals(product.getId_type())) {
                                    holder.tvNameType.setText("Type: " + catLists.get(z).getName());
                                }
                            }

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    Toast.makeText(context, "Không thể lấy các category!", Toast.LENGTH_LONG).show();

                });

        Volley.newRequestQueue(context).add(stringRequest);
    }

    private void notifyDataChangePro() {
        productList.clear();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.URL_GET_ALL_PRODUCT,
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
                                // Get item from product
                                for (int i = 0; i < jsonArrayPro.length(); i++) {
                                    String resultArray = jsonArrayPro.getString(i);
                                    JSONObject jobItem = new JSONObject(resultArray);
                                    String id = jobItem.getString("id");
                                    String name = jobItem.getString("name");
                                    String price = jobItem.getString("price");
                                    String created_at = jobItem.getString("created_at");
                                    String updated_at = jobItem.getString("updated_at");
                                    Product product = new Product(id, name, price, created_at, updated_at);
                                    productList.add(product);
                                }
                                notifyDataSetChanged();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "Result error: " + error.getMessage());

                    }
                });

        VolleySingleton.getInstance(context).addToRequestQueue(stringRequest);
    }

}


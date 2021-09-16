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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.androidnetworking.R;
import com.example.androidnetworking.model.Category;
import com.example.androidnetworking.model.ServerResponse;
import com.example.androidnetworking.server_client.Constants;
import com.example.androidnetworking.server_client.VolleySingleton;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {
    String TAG = "CategoryAdapter";
    private final Context context;
    private ArrayList<Category> catList;
    public Dialog dialog;
    private Category cat;

    public CategoryAdapter(Context context, ArrayList<Category> catList) {
        this.context = context;
        this.catList = catList;
    }

    // Create layout view
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category_heart, parent,false);
        return new MyViewHolder(itemView);
    }


    // For set data
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final int pos = position;
        Category categories = catList.get(pos);
        holder.tvName.setText(categories.getName());
        holder.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetMenu(pos);
            }
        });
    }

    @Override
    public int getItemCount() {
        return catList.size();
    }

    // Create view element item card view
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvName;
        private final ImageView imgEdit;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvNameCat);
            imgEdit = itemView.findViewById(R.id.imgEditCat);
        }

    }

    private void bottomSheetMenu(int position) {
        cat = new Category();
        cat = catList.get(position);

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
                context, R.style.BottomSheetDialogTheme
        );

        View bottomSheetView = LayoutInflater.from(context).inflate(
                R.layout.bottomsheet_cat_dialog,
                bottomSheetDialog.findViewById(R.id.bottomSheetContainerCat)
        );
        bottomSheetView.findViewById(R.id.btnSuaCat).setOnClickListener(view ->
        {
            bottomSheetDialog.dismiss();
            dialog = new Dialog(context);
            dialog.getWindow().getAttributes().windowAnimations = R.style.up_down;
            dialog.setContentView(R.layout.alertdialog_category_layout);
            dialog.setCancelable(false);
            Window window = dialog.getWindow();
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            if (dialog != null && dialog.getWindow() != null) {
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
            }
            TextView titleAlert = dialog.findViewById(R.id.tvTitleDialogCat);
            titleAlert.setText(R.string.title_alert_update);
            EditText name = dialog.findViewById(R.id.edtNameAddCat);
            Button btnAdd = dialog.findViewById(R.id.btnSubmitAddCat);
            Button btnCancel = dialog.findViewById(R.id.btnCancelCat);
            btnAdd.setText(R.string.update);
            name.setText(cat.getName());

            btnAdd.setOnClickListener(x -> {

                Category story = new Category(cat.getId(), name.getText().toString());
                update(story);
                dialog.dismiss();
            });

            btnCancel.setOnClickListener(v -> {
                dialog.dismiss();
            });
            dialog.show();
        });

        bottomSheetView.findViewById(R.id.btnDismissBottomSheetCat).setOnClickListener(view -> bottomSheetDialog.dismiss());
        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetDialog.show();
    }

    private void update(Category story) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_UPDATE_CAT,
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
                            notifyDataChangeCat();
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
                params.put("id_type", story.getId());
                params.put("name_type", story.getName());
                return params;
            }
        };

        VolleySingleton.getInstance(context).addToRequestQueue(stringRequest);
    }

    private void notifyDataChangeCat() {
        catList.clear();
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
                                catList = new ArrayList<>();
                                // Get item from product
                                for (int i = 0; i < jsonArrayPro.length(); i++) {
                                    String resultArray = jsonArrayPro.getString(i);
                                    JSONObject jobItem = new JSONObject(resultArray);
                                    String id = jobItem.getString("id_type");
                                    String name = jobItem.getString("name_type");
                                    String created_at = jobItem.getString("created_at");
                                    String updated_at = jobItem.getString("updated_at");
                                    Category cat = new Category(id, name, created_at, updated_at);
                                    catList.add(cat);
                                }

                            }
                            notifyDataSetChanged();
                        }
                        catch (JSONException e) {
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
}


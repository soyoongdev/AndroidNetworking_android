package com.example.lab3_mob403.bai2;

import static com.example.lab3_mob403.MainActivity.ip;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;

import com.example.lab3_mob403.Adapter.ProductAdpater;
import com.example.lab3_mob403.Model.ProductModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class GetAPI extends AsyncTask<Void, Void, Void> {
    private String TAG = GetAPI.class.getSimpleName();
    public static String url = "http://"+ip+":8080/php/contacts/productJson.json";
    ArrayList<ProductModel> productList;
    private ListView listView;
    Context context;
    ProductAdpater productAdpater;
    private ProgressDialog dialog;

    public GetAPI(Context context, ListView listView){
        this.context = context;
        this.listView = listView;
        productList = new ArrayList<>();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog = new ProgressDialog(context);
        dialog.setMessage("Please wait...");
        dialog.setCancelable(false);
        dialog.show();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        HttpHandlerIn handler = new HttpHandlerIn();
        String jsonStr = handler.makeServiceCall(url);
        Log.d(TAG, "Response from url: " + jsonStr);
        if (jsonStr != null) {
            try{
                JSONObject jsonObject = new JSONObject(jsonStr);
                JSONArray jsonArray = jsonObject.getJSONArray("products");
                for (int i = 0; i < jsonArray.length(); i++){
                    JSONObject c = jsonArray.getJSONObject(i);

                    String id = c.getString("id");
                    String type = c.getString("type");
                    String title = c.getString("title");
                    String price = c.getString("price");
                    String imageUrl = c.getString("image");

                    ProductModel productModel = new ProductModel(id, type, title, price, imageUrl);
                    productList.add(productModel);
                }

            }catch (Exception e){
                Log.e(TAG, "Async task error: " + e.getMessage());
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void unused) {
        super.onPostExecute(unused);
        if(dialog.isShowing()){
            dialog.dismiss();
        }
        productAdpater = new ProductAdpater(context, productList);
        Log.i(TAG, "Log data: " + productAdpater.getCount());
        listView.setAdapter(productAdpater);
    }
}

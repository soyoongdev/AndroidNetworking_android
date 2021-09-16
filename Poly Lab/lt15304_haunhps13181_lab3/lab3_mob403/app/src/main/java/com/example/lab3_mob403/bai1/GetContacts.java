package com.example.lab3_mob403.bai1;

import static com.example.lab3_mob403.MainActivity.ip;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;

import com.example.lab3_mob403.Adapter.ContactAdapter;
import com.example.lab3_mob403.Adapter.ProductAdpater;
import com.example.lab3_mob403.Model.Contact;
import com.example.lab3_mob403.Model.ProductModel;
import com.example.lab3_mob403.bai2.GetAPI;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class GetContacts extends AsyncTask<Void, Void, Void> {
    private String TAG = GetContacts.class.getSimpleName();
    public static String url = "http://"+ip+":8080/php/contacts/json_data.json";

    ArrayList<Contact> contactList;
    private ListView listViewBai1;
    Context context;
    ContactAdapter contactAdpater;
    private ProgressDialog dialog;

    public GetContacts(Context context, ListView listViewBai1){
        this.context = context;
        this.listViewBai1 = listViewBai1;
        contactList = new ArrayList<>();
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
        HttpHandler handler = new HttpHandler();
        String jsonStr = handler.makeServiceCall(url);
        Log.d(TAG, "Response from url: " + jsonStr);
        if (jsonStr != null) {
            try{
                JSONObject jsonObject = new JSONObject(jsonStr);
                JSONArray jsonArray = jsonObject.getJSONArray("contacts");
                for (int i = 0; i < jsonArray.length(); i++){
                    JSONObject c = jsonArray.getJSONObject(i);
                    String id = c.getString("id");
                    String name = c.getString("name");
                    String email = c.getString("email");
                    String address = c.getString("address");
                    String gender = c.getString("gender");
                    String profile_pic = c.getString("profile_pic");

                    JSONObject phone = c.getJSONObject("phone");

                    String mobile = phone.getString("mobile");
                    String home = phone.getString("home");
                    String office = phone.getString("office");

                    Contact contact = new Contact(id, name, email, address, gender);
                    contactList.add(contact);
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
        contactAdpater = new ContactAdapter(context, contactList);
        Log.i(TAG, "Log data: " + contactAdpater.getCount());
        listViewBai1.setAdapter(contactAdpater);
    }
}

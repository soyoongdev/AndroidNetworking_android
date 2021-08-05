package com.example.lab3_mob403.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lab3_mob403.Model.Contact;
import com.example.lab3_mob403.Model.ProductModel;
import com.example.lab3_mob403.R;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class ContactAdapter extends BaseAdapter {
    Context context;
    ArrayList<Contact> contactList;
    private Bitmap bitmap = null;

    public ContactAdapter(Context context, ArrayList<Contact> contactList) {
        this.context = context;
        this.contactList = contactList;
    }

    @Override
    public int getCount() {
        return contactList.size();
    }

    @Override
    public Object getItem(int i) {
        return contactList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public static class ViewHolder {
        TextView tvName, tvEmail, tvPhone;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        ContactAdapter.ViewHolder viewHolder;

        if (view == null){
            viewHolder = new ContactAdapter.ViewHolder();
            view = inflater.inflate(R.layout.item_list, null);
            viewHolder.tvName = (TextView) view.findViewById(R.id.tvName);
            viewHolder.tvEmail = (TextView) view.findViewById(R.id.tvEmail);
            viewHolder.tvPhone = (TextView) view.findViewById(R.id.tvPhone);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ContactAdapter.ViewHolder) view.getTag();
        }
        Contact contact = contactList.get(i);
        viewHolder.tvName.setText(contact.getName());
        viewHolder.tvEmail.setText(contact.getEmail());

        return view;
    }

}

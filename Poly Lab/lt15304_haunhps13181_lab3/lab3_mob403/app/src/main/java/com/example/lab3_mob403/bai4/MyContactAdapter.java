package com.example.lab3_mob403.bai4;

import android.content.Context;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab3_mob403.Model.AndroidVersion;
import com.example.lab3_mob403.Model.Contact;
import com.example.lab3_mob403.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MyContactAdapter extends ArrayAdapter<Contact> {
    List<Contact> contactList;
    Context context;
    private LayoutInflater mInflater;

    // Constructors
    public MyContactAdapter(Context context, List<Contact> objects) {
        super(context, 0, objects);
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        contactList = objects;
    }

    @Override
    public Contact getItem(int position) {
        return contactList.get(position);
    }

    private static class ViewHolder {
        public final RelativeLayout rootView;
        public final ImageView imageView;
        public final TextView textViewName;
        public final TextView textViewEmail;

        private ViewHolder(RelativeLayout rootView, ImageView imageView, TextView textViewName, TextView textViewEmail) {
            this.rootView = rootView;
            this.imageView = imageView;
            this.textViewName = textViewName;
            this.textViewEmail = textViewEmail;
        }

        public static ViewHolder create(RelativeLayout rootView) {
            ImageView imageView = (ImageView)
                    rootView.findViewById(R.id.imgB4);
            TextView textViewName = (TextView)
                    rootView.findViewById(R.id.tvNameB4);
            TextView textViewEmail = (TextView)
                    rootView.findViewById(R.id.tvEmailB4);
            return new ViewHolder(rootView, imageView, textViewName, textViewEmail);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder vh;
        if (convertView == null) {

            View view = mInflater.inflate(R.layout.item_list_b4, parent, false);
                    vh = ViewHolder.create((RelativeLayout) view);
            view.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        Contact item = getItem(position);
        vh.textViewName.setText(item.getName());
        vh.textViewEmail.setText(item.getEmail());
        Picasso.with(context).load(item.getProfile_pic()).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).into(vh.imageView);
        return vh.rootView;
    }
}

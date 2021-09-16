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

import com.example.lab3_mob403.Model.ProductModel;
import com.example.lab3_mob403.R;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class ProductAdpater extends BaseAdapter {
    Context context;
    ArrayList<ProductModel> productList;
    private Bitmap bitmap = null;

    public ProductAdpater(Context context, ArrayList<ProductModel> productList) {
        this.context = context;
        this.productList = productList;
    }

    @Override
    public int getCount() {
        return productList.size();
    }

    @Override
    public Object getItem(int i) {
        return productList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public static class ViewHolder {
        TextView tvTitle, tvPrice;
        ImageView imgView;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        ViewHolder viewHolder;

        if (view == null){
            viewHolder = new ViewHolder();
            view = inflater.inflate(R.layout.activity_card_view_products, null);
            viewHolder.tvTitle = (TextView) view.findViewById(R.id.tvTitleCard);
            viewHolder.tvPrice = (TextView) view.findViewById(R.id.tvPriceCard);
            viewHolder.imgView = (ImageView) view.findViewById(R.id.imgViewCard);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }
        ProductModel product = productList.get(i);
        viewHolder.tvTitle.setText(product.getTitle());
        viewHolder.tvPrice.setText(product.getPrice());
        Log.i("TAG Adapter", "Log data: " + product.getImage());

        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                bitmap = loadImageUsingThread(product.getImage());
                viewHolder.imgView.post(new Runnable() {
                    @Override
                    public void run() {
                        viewHolder.imgView.setImageBitmap(bitmap);
                    }
                });
            }
        });
        thread.start();

        return view;
    }

    private Bitmap loadImageUsingThread(String link) {
        URL url;
        Bitmap bm = null;
        try {
            url = new URL(link);
            bm = BitmapFactory.decodeStream(url.openConnection().getInputStream());
        }catch (IOException e) {
            Log.i("Error: " , e.getMessage());
        }
        return bm;
    }
}

package com.example.androidnetworking.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidnetworking.R;
import com.example.androidnetworking.model.Product;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<Product> productList;

    public ProductAdapter(Context context, ArrayList<Product> productList) {
        this.context = context;
        this.productList = productList;
    }

    // Create layout view
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product_home, parent,false);
        return new MyViewHolder(itemView);
    }


    // For set data
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.tvName.setText(product.getName());
        holder.tvPrice.setText(product.getPrice());

        int[] images = new int[] {R.drawable.a1, R.drawable.a2, R.drawable.a3};
        //Get the imageview
        int imageID = (int) (Math.random() * images.length);
        // Set display image
        holder.imgView.setImageResource(images[imageID]);

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    // Create view element item card view
    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvName, tvPrice;
        private ImageView imgView, imgEdit, imgDelete;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvNamePro);
            tvPrice = itemView.findViewById(R.id.tvPricePro);
            imgView = itemView.findViewById(R.id.imgPro);
            imgEdit = itemView.findViewById(R.id.imgEditPro);
            imgDelete = itemView.findViewById(R.id.imgRemovePro);

            imgEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

            imgDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }

    }
}


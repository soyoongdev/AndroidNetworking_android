package com.example.androidnetworking.ui;

import android.widget.Filter;

import com.example.androidnetworking.adapter.ProductAdapter;
import com.example.androidnetworking.model.Product;

import java.util.ArrayList;

public class FilterCustomerSearch extends Filter {
    private final ProductAdapter mAdapter;
    ArrayList<Product> productLists;
    ArrayList<Product> filteredLists;

    public FilterCustomerSearch(ProductAdapter mAdapter, ArrayList<Product> productLists) {
        this.mAdapter = mAdapter;
        this.productLists = productLists;
        filteredLists = new ArrayList<>();
    }


    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        filteredLists.clear();
        final FilterResults results = new FilterResults();

        if (constraint.length() == 0) {
            filteredLists.addAll(productLists);
        } else {
            final String filterPattern = constraint.toString().toLowerCase().trim();

            for (final Product product : productLists) {
                if (product.getName().contains(constraint)) {
                    filteredLists.add(product);
                }
                else if (product.getPrice().contains(constraint))
                {
                    filteredLists.add(product);
                }
            }
        }
        results.values = filteredLists;
        results.count = filteredLists.size();
        return results;
    }

    @Override
    protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
        mAdapter.productList.clear();
        mAdapter.productList.addAll((ArrayList<Product>) filterResults.values);
        mAdapter.notifyDataSetChanged();
    }
}

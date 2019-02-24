package com.amazonk.android;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.LinkedList;

public class ProductAdapter extends
        RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private final LinkedList<String> mProductList;
    private LayoutInflater mInflater;

    ProductAdapter(Context context, LinkedList<String> productList) {
        mInflater = LayoutInflater.from(context);
        this.mProductList = productList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View mItemView = mInflater.inflate(R.layout.product_item, viewGroup, false);
        return new ProductViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder productViewHolder, int i) {
        String mCurrent = mProductList.get(i);
        productViewHolder.productItemView.setText(mCurrent);
        productViewHolder.productCountView.setText("1");
    }

    @Override
    public int getItemCount() {
        return mProductList.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {
        final TextView productItemView;
        final TextView productCountView;
        final ProductAdapter mAdapter;

        ProductViewHolder(View itemView, ProductAdapter adapter) {
            super(itemView);
            productItemView = itemView.findViewById(R.id.product_name);
            productCountView = itemView.findViewById(R.id.product_count);
            this.mAdapter = adapter;
        }
    }
}

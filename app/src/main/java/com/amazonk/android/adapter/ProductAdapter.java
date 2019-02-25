package com.amazonk.android.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amazonk.android.R;
import com.amazonk.android.model.Cart;

import java.util.LinkedList;

public class ProductAdapter extends
        RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private final LinkedList<Cart.Product> mProductList;
    private LayoutInflater mInflater;

    public ProductAdapter(Context context, LinkedList<Cart.Product> productList) {
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
        productViewHolder.productItemView.setText(
                mProductList.get(i).getNamaProduk()
        );
        productViewHolder.productCountView.setText(
                Integer.toString(mProductList.get(i).getJumlah())
        );
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

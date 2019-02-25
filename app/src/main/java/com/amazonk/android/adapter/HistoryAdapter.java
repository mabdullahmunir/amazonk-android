package com.amazonk.android.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amazonk.android.R;
import com.amazonk.android.model.Histories;

import java.util.LinkedList;

public class HistoryAdapter extends
        RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {

    private final LinkedList<Histories.History> mHistoryList;
    private LayoutInflater mInflater;

    public HistoryAdapter(Context context, LinkedList<Histories.History> historyList) {
        mInflater = LayoutInflater.from(context);
        this.mHistoryList = historyList;
    }

    @NonNull
    @Override
    public HistoryAdapter.HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View mItemView = mInflater.inflate(R.layout.history_item, viewGroup, false);
        return new HistoryAdapter.HistoryViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryAdapter.HistoryViewHolder productViewHolder, int i) {
        int totalHarga = 0;
        for (Histories.Product product : mHistoryList.get(i).getListProduk()) {
            totalHarga += product.getTotalHarga();
        }
        productViewHolder.totalPriceView.setText(
                Integer.toString(totalHarga)
        );
        productViewHolder.dateView.setText(
                mHistoryList.get(i).getTanggal()
        );
    }

    @Override
    public int getItemCount() {
        return mHistoryList.size();
    }

    public class HistoryViewHolder extends RecyclerView.ViewHolder {
        final TextView totalPriceView;
        final TextView dateView;
        final HistoryAdapter mAdapter;

        public HistoryViewHolder(@NonNull View itemView, HistoryAdapter adapter) {
            super(itemView);
            this.totalPriceView = itemView.findViewById(R.id.total_harga_view);
            this.dateView = itemView.findViewById(R.id.tanggal_view);
            this.mAdapter = adapter;
        }
    }
}

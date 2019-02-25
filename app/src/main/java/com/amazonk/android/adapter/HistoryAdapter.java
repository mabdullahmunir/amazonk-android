package com.amazonk.android.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amazonk.android.R;
import com.amazonk.android.model.Histories;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.LinkedList;

public class HistoryAdapter extends
        RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>{

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
        final HistoryViewHolder mViewHolder = new HistoryViewHolder(mItemView, this);
        mItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Log.d("hehe", String.valueOf(mViewHolder.getLayoutPosition()));
                final int pos = mViewHolder.getLayoutPosition();
                String userEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();
                FirebaseFirestore
                        .getInstance()
                        .collection("histories")
                        .document(userEmail)
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        Histories histories = documentSnapshot.toObject(Histories.class);
                        StringBuilder sb = new StringBuilder();

                        Histories.History history = histories.getListHistory().get(pos);

                        sb.append(history.getTanggal()).append("\n");

                        for (Histories.Product product : history.getListProduk()) {
                            sb.append(product.getNamaBarang())
                                    .append("\t")
                                    .append(product.getJumlahBarang())
                                    .append("\t")
                                    .append(product.getTotalHarga())
                                    .append("\n");
                        }

                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_SEND);
                        intent.putExtra(Intent.EXTRA_TEXT, sb.toString());
                        intent.setType("text/plain");
                        v.getContext().startActivity(intent);
                    }
                });
            }
        });
        return mViewHolder;
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

    public class HistoryViewHolder extends ViewHolder {
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

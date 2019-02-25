package com.amazonk.android.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amazonk.android.R;
import com.amazonk.android.model.Vouchers;

public class VoucherAdapter extends RecyclerView.Adapter<VoucherAdapter.VoucherViewHolder> {

    private final Vouchers mVouchers;
    private LayoutInflater mInflater;

    public VoucherAdapter(Context context, Vouchers vouchers) {
        mInflater = LayoutInflater.from(context);
        this.mVouchers = vouchers;
    }

    @NonNull
    @Override
    public VoucherViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View mItemView = mInflater.inflate(R.layout.voucher_listview, viewGroup, false);
        return new VoucherViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull VoucherViewHolder voucherViewHolder, int i) {
        voucherViewHolder.kodeVoucherView.setText(
                mVouchers.getVoucherList().get(i).getKodeVoucher()
        );
        voucherViewHolder.potonganView.setText(
                Integer.toString(mVouchers.getVoucherList().get(i).getPotongan())
        );
    }

    @Override
    public int getItemCount() {
        return mVouchers.getVoucherList().size();
    }

    class VoucherViewHolder extends RecyclerView.ViewHolder {
        final TextView kodeVoucherView;
        final TextView potonganView;
        final VoucherAdapter mAdapter;

        VoucherViewHolder(View itemView, VoucherAdapter adapter) {
            super(itemView);
            kodeVoucherView = itemView.findViewById(R.id.kodeVoucher);
            potonganView = itemView.findViewById(R.id.potongan);
            this.mAdapter = adapter;
        }
    }
}
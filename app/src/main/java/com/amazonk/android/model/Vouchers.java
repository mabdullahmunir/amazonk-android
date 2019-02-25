package com.amazonk.android.model;

import java.util.ArrayList;
import java.util.List;

public class Vouchers {
    private ArrayList<Voucher> voucherList;

    public Vouchers() {
        this.voucherList = new ArrayList<>();
    }

    public Vouchers(ArrayList<Voucher> voucherList) {
        this.voucherList = voucherList;
    }

    public List<Voucher> getVoucherList() {
        return voucherList;
    }

    public static class Voucher {
        private String kodeVoucher;
        private int potongan;

        public Voucher() {
        }

        public Voucher(String kodeVoucher, int potongan) {
            this.kodeVoucher = kodeVoucher;
            this.potongan = potongan;
        }

        public String getKodeVoucher() {
            return kodeVoucher;
        }

        public int getPotongan() {
            return potongan;
        }
    }
}

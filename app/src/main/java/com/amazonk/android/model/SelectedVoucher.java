package com.amazonk.android.model;

public class SelectedVoucher {
    private static int potongan = 0;

    public static int getPotongan() {
        return potongan;
    }

    public static void setPotongan(int newPotongan) {
        potongan = newPotongan;
    }
}

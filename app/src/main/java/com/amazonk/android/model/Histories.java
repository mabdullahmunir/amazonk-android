package com.amazonk.android.model;

import java.util.ArrayList;

public class Histories {
    private ArrayList<History> listHistory;

    public Histories() {
    }

    public ArrayList<History> getListHistory() {
        return listHistory;
    }

    public class History {
        private ArrayList<Cart.Product> listProduk;
        private String tanggal;

        public History() {
        }

        public ArrayList<Cart.Product> getListProduk() {
            return listProduk;
        }

        public String getTanggal() {
            return tanggal;
        }
    }
}

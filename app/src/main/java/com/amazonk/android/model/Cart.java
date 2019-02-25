package com.amazonk.android.model;

import java.util.ArrayList;

public class Cart {
    private ArrayList<Product> productList;

    public Cart() {
    }

    public Cart(ArrayList<Product> productList) {
        this.productList = productList;
    }

    public ArrayList<Product> getProductList() {
        return productList;
    }

    public static class Product {
        private String namaProduk;
        private int jumlah;

        public Product() {
        }

        public Product(String namaProduk, int jumlah) {
            this.namaProduk = namaProduk;
            this.jumlah = jumlah;
        }

        public String getNamaProduk() {
            return namaProduk;
        }

        public int getJumlah() {
            return jumlah;
        }
    }
}

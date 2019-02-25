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
        private String idProduk;
        private int jumlah;
        private String namaProduk;

        public Product() {
        }

        public Product(String idProduk, int jumlah) {
            this.idProduk = idProduk;
            this.jumlah = jumlah;
        }

        public String getIdProduk() {
            return idProduk;
        }

        public int getJumlah() {
            return jumlah;
        }

        public String getNamaProduk() {
            return namaProduk;
        }

        public void setNamaProduk(String namaProduk) {
            this.namaProduk = namaProduk;
        }
    }
}

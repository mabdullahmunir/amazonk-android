package com.amazonk.android.model;

import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

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

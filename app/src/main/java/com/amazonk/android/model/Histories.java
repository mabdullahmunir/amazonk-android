package com.amazonk.android.model;

import java.util.ArrayList;

public class Histories {
    private ArrayList<History> listHistory;

    public Histories() {
    }

    public Histories(ArrayList<History> listHistory) {
        this.listHistory = listHistory;
    }

    public ArrayList<History> getListHistory() {
        return listHistory;
    }

    public static class History {
        private ArrayList<Product> listProduk;
        private String tanggal;

        public History() {
        }

        public History(ArrayList<Product> listProduk, String tanggal) {
            this.listProduk = listProduk;
            this.tanggal = tanggal;
        }

        public ArrayList<Product> getListProduk() {
            return listProduk;
        }

        public String getTanggal() {
            return tanggal;
        }
    }

    public static class Product {
        private String namaBarang;
        private Integer totalHarga;
        private Integer jumlahBarang;

        public Product() {
        }

        public Product(String namaBarang, Integer totalHarga, Integer jumlahBarang) {
            this.namaBarang = namaBarang;
            this.totalHarga = totalHarga;
            this.jumlahBarang = jumlahBarang;
        }

        public String getNamaBarang() {
            return namaBarang;
        }

        public Integer getTotalHarga() {
            return totalHarga;
        }

        public Integer getJumlahBarang() {
            return jumlahBarang;
        }
    }
}

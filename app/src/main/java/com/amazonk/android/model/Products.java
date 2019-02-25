package com.amazonk.android.model;

public class Products {
    private String namaProduk;
    private Integer harga;

    public Products() {
    }

    public Products(String namaProduk, Integer harga) {
        this.namaProduk = namaProduk;
        this.harga = harga;
    }

    public String getNamaProduk() {
        return namaProduk;
    }

    public Integer getHarga() {
        return harga;
    }
}

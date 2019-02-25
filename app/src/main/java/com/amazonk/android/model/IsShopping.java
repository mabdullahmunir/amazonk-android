package com.amazonk.android.model;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

public class IsShopping {
    private static boolean isShopping = false;
    private static String userMail = FirebaseAuth.getInstance().getCurrentUser().getEmail();

    // Called by ScanActivity
    public static void startShopping() {
        isShopping = true;
    }

    // Called by LocationService to empty the cart and start paying
    public static void stopShopping() {
        isShopping = false;
        FirebaseFirestore
                .getInstance()
                .collection("cart")
                .document(userMail)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Cart cart = documentSnapshot.toObject(Cart.class);

                ArrayList<Histories.Product> hProduct = new ArrayList<>();

                for (Cart.Product product : cart.getProductList()) {
                    Task<DocumentSnapshot> t1 = FirebaseFirestore.getInstance().collection("products").document(product.getIdProduk()).get();

                    try {
                        DocumentSnapshot dc = Tasks.await(t1);
                        Products products = dc.toObject(Products.class);
                        Histories.Product hp = new Histories.Product(products.getNamaProduk(), product.getJumlah(), product.getJumlah()*products.getHarga());
                        hProduct.add(hp);
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                Task<DocumentSnapshot> t = FirebaseFirestore.getInstance().collection("histories").document(userMail).get();

                try {
                    DocumentSnapshot dc = Tasks.await(t);
                    Histories histories = dc.toObject(Histories.class);
                    Histories.History history = new Histories.History(hProduct, new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date()));
                    histories.getListHistory().add(history);
                    FirebaseFirestore.getInstance().collection("histories").document(userMail).set(histories);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static boolean status() {
        return isShopping;
    }
}

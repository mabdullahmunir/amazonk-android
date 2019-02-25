package com.amazonk.android;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.amazonk.android.model.Cart;
import com.amazonk.android.model.Products;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.LinkedList;

import javax.annotation.Nullable;

public class MainFragment extends Fragment {

    private static final String TAG = MainFragment.class.getSimpleName();
    private Button mScanButton;
    private TextView mHargaView;

    //Recycler
    private RecyclerView mRecyclerView;
    private ProductAdapter mAdapter;
    final private LinkedList<Cart.Product> mProductList = new LinkedList<>();
    private DocumentReference mCartDoc;
    private int total;

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, null);

        String userMail = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        mHargaView = view.findViewById(R.id.harga_view);
        mScanButton = view.findViewById(R.id.scan_button);
        mScanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapter.notifyDataSetChanged();
                Intent intent = new Intent(getContext(), ScanActivity.class);
                startActivity(intent);
            }
        });

        mCartDoc = FirebaseFirestore.getInstance().collection("cart").document(userMail);
        mCartDoc.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w(TAG, "listen:error", e);
                    return;
                }

                total = 0;
                Cart cart = null;

                try {
                    cart = documentSnapshot.toObject(Cart.class);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }

                if (cart != null) {
                    mProductList.clear();
                    for (final Cart.Product product : cart.getProductList()) {
                        FirebaseFirestore
                                .getInstance()
                                .collection("products")
                                .document(product.getIdProduk())
                                .get()
                                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                    @Override
                                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                                        Products products = documentSnapshot.toObject(Products.class);
                                        product.setNamaProduk(products.getNamaProduk());
                                        mProductList.add(product);
                                        total += products.getHarga() * product.getJumlah();
                                        mAdapter.notifyDataSetChanged();
                                        mHargaView.setText("Rp. " + total);
                                    }
                                });
                    }
                 }
            }
        });

        // Recycler View
        // Get a handle to the RecyclerView.
        mRecyclerView = view.findViewById(R.id.product_view);
        // Create an adapter and supply the data to be displayed.
        mAdapter = new ProductAdapter(getContext(), mProductList);
        // Connect the adapter with the RecyclerView.
        mRecyclerView.setAdapter(mAdapter);
        // Give the RecyclerView a default layout manager.
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }
}

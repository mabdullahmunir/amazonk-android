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

import com.amazonk.android.model.Cart;
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

    //Recycler
    private RecyclerView mRecyclerView;
    private ProductAdapter mAdapter;
    final private LinkedList<Cart.Product> mProductList = new LinkedList<>();
    private DocumentReference mCartDoc;

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

        mScanButton = view.findViewById(R.id.scan_button);
        mScanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ScanActivity.class);
                startActivity(intent);
            }
        });

        mCartDoc = FirebaseFirestore.getInstance().collection("cart").document("hehe");
        mCartDoc.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w(TAG, "listen:error", e);
                    return;
                }
                Cart cart = documentSnapshot.toObject(Cart.class);
                mProductList.clear();
                mProductList.addAll(cart.getProductList());
                mAdapter.notifyDataSetChanged();
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

package com.amazonk.android.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amazonk.android.R;
import com.amazonk.android.adapter.HistoryAdapter;
import com.amazonk.android.model.Cart;
import com.amazonk.android.model.Histories;
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

public class HistoryFragment extends Fragment {
    private static final String TAG = HistoryFragment.class.getSimpleName();
    private RecyclerView mRecyclerView;
    private HistoryAdapter mAdapter;
    final private LinkedList<Histories.History> mHistoryList = new LinkedList<>();
    private DocumentReference mHistoriesDoc;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, null);

        String userMail = FirebaseAuth.getInstance().getCurrentUser().getEmail();

        mHistoriesDoc = FirebaseFirestore.getInstance().collection("histories").document(userMail);
        mHistoriesDoc.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w(TAG, "listen:error", e);
                    return;
                }

                Histories histories = null;

                try {
                    histories = documentSnapshot.toObject(Histories.class);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }

                if (histories != null) {
                    mHistoryList.clear();
                    mHistoryList.addAll(histories.getListHistory());
                    mAdapter.notifyDataSetChanged();
                }
            }
        });


        // Recycler View
        // Get a handle to the RecyclerView.
        mRecyclerView = view.findViewById(R.id.history_view);
        // Create an adapter and supply the data to be displayed.
        mAdapter = new HistoryAdapter(getContext(), mHistoryList);
        // Connect the adapter with the RecyclerView.
        mRecyclerView.setAdapter(mAdapter);
        // Give the RecyclerView a default layout manager.
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }
}

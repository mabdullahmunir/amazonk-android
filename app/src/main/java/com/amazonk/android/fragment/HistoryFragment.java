package com.amazonk.android.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amazonk.android.R;
import com.amazonk.android.adapter.HistoryAdapter;
import com.amazonk.android.adapter.ProductAdapter;
import com.amazonk.android.model.Histories;
import com.google.firebase.auth.FirebaseAuth;

import java.util.LinkedList;

public class HistoryFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private HistoryAdapter mAdapter;
    final private LinkedList<Histories.History> mHistoryList = new LinkedList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, null);

        String userMail = FirebaseAuth.getInstance().getCurrentUser().getEmail();

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

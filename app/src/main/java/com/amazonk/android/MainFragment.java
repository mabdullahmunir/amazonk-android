package com.amazonk.android;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.LinkedList;

public class MainFragment extends Fragment {

    private Button mScanButton;

    //Recycler
    private RecyclerView mRecyclerView;
    private ProductAdapter mAdapter;

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

        final LinkedList<String> mWordList = new LinkedList<>();
        int mCount = 0;

        for (int i = 0; i < 20; i++) {
            mWordList.addLast("Word " + mCount++);
        }

        // Recycler View
        // Get a handle to the RecyclerView.
        mRecyclerView = view.findViewById(R.id.product_view);
        // Create an adapter and supply the data to be displayed.
        mAdapter = new ProductAdapter(getContext(), mWordList);
        // Connect the adapter with the RecyclerView.
        mRecyclerView.setAdapter(mAdapter);
        // Give the RecyclerView a default layout manager.
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }
}

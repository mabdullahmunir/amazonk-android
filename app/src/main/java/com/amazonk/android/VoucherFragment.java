package com.amazonk.android;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class VoucherFragment extends Fragment {
    private ListView mVoucherListView;
    private DocumentReference mVouchersDoc;
    private Map<String, Object> voucherMap;
    private String[] voucherKeyArray = new String[] {"No voucher available"};
    private Object[] voucherObjectArray;
    private List<String[]> voucherArrayArrayList;
    private List<String> voucherCodeArrayList;


    public VoucherFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mVouchersDoc = FirebaseFirestore.getInstance()
                .collection("vouchers")
                .document("senapatidiwangkara@gmail.com");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_voucher, container, false);

        mVouchersDoc.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d("Firehose", "DocumentSnapshot data: " + document.getData());
                        voucherKeyArray = document.getData().keySet().toArray(new String[] {});
//                        Log.d("Firehose", "" + voucherMap.keySet().toArray(new String[] {})[0]);
//                        voucherObjectArray = voucherMap.values().toArray();
//                        for (Object v : voucherObjectArray) {
//                            voucherArrayArrayList.add((String[]) v);
//                        }
//                        for (String[] v : voucherArrayArrayList) {
//                            voucherCodeArrayList.add(v[0]);
//                        }
                    } else {
                        Log.d("Firehose", "No such document");
                    }
                } else {
                    Log.d("Firehose", "get failed with ", task.getException());
                }
            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.voucher_listview, voucherKeyArray);
        mVoucherListView = view.findViewById(R.id.voucher_list);
        mVoucherListView.setAdapter(adapter);

        return view;
    }

}

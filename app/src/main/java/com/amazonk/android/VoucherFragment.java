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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

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
    private static final String TAG = VoucherFragment.class.getSimpleName();
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

//        mVouchersDoc.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                if (task.isSuccessful()) {
//                    DocumentSnapshot document = task.getResult();
//                    if (document.exists()) {
//                        Log.w("Firehose", "DocumentSnapshot data: " + document.getData());
//                        voucherKeyArray = document.getData().keySet().toArray(new String[] {});
////                        Log.d("Firehose", "" + voucherMap.keySet().toArray(new String[] {})[0]);
////                        voucherObjectArray = voucherMap.values().toArray();
////                        for (Object v : voucherObjectArray) {
////                            voucherArrayArrayList.add((String[]) v);
////                        }
////                        for (String[] v : voucherArrayArrayList) {
////                            voucherCodeArrayList.add(v[0]);
////                        }
//                    } else {
//                        Log.w("Firehose", "No such document");
//                    }
//                } else {
//                    Log.w("Firehose", "get failed with ", task.getException());
//                }
//            }
//        });

        mVouchersDoc.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w(TAG, "listen:error", e);
                    return;
                }
                Log.w(TAG, "new data : " + documentSnapshot.getData());
            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.voucher_listview, voucherKeyArray);
        mVoucherListView = view.findViewById(R.id.voucher_list);
        mVoucherListView.setAdapter(adapter);

        return view;
    }

}

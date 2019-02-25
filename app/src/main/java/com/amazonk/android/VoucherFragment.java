package com.amazonk.android;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.amazonk.android.model.Vouchers;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.gson.Gson;

import java.util.ArrayList;
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

//        Vouchers.Voucher v = new Vouchers.Voucher("hehe", 50000);
//        Vouchers.Voucher v1 = new Vouchers.Voucher("hehe", 50000);
//        Vouchers.Voucher v2 = new Vouchers.Voucher("hehe", 50000);
//
//        ArrayList<Vouchers.Voucher> lv = new ArrayList<>();
//        lv.add(v);
//        lv.add(v1);
//        lv.add(v2);
//
//        Vouchers vv = new Vouchers(lv);

//        FirebaseFirestore.getInstance().collection("vouchers").document("test").set(vv);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_voucher, container, false);

        mVouchersDoc.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w(TAG, "listen:error", e);
                    return;
                }
                Log.w(TAG, "new data : " + documentSnapshot.getData());
//                Vouchers test = new Gson().fromJson(documentSnapshot.getData().toString(), Vouchers.class);
//                Log.w("Firehose", "Yeet: " + test.getVoucherList());
                Vouchers vvv = documentSnapshot.toObject(Vouchers.class);
                Log.w(TAG, vvv.getVoucherList().get(0).getKodeVoucher());
            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.voucher_listview, voucherKeyArray);
        mVoucherListView = view.findViewById(R.id.voucher_list);
        mVoucherListView.setAdapter(adapter);

        return view;
    }

}

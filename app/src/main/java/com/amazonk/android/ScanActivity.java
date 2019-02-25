package com.amazonk.android;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.NfcF;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class ScanActivity extends AppCompatActivity {

    private NfcAdapter mNfcAdapter;
    private PendingIntent mPendingIntent;
    private IntentFilter[] mIntentFilters;
    private String[][] mNFCTechLists;
    private TextView mTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        // TextView
        mTextView = findViewById(R.id.textView);

        // NFC setup
        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);

        if (mNfcAdapter != null)
            mTextView.setText("Read an NFC tag");
        else
            mTextView.setText("This phone is not NFC enabled.");

        // create an intent with tag data and deliver to this activity
        mPendingIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);

        // set an intent filter for all MIME data
        IntentFilter tagIntent = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);

        try {
            mIntentFilters = new IntentFilter[] { tagIntent };
        } catch (Exception e) {
            Log.e("TagDispatch", e.toString());
        }

        mNFCTechLists = new String[][] { new String[] { NfcF.class.getName() } };
    }


    @Override
    public void onResume() {
        super.onResume();

        if (mNfcAdapter != null)
            mNfcAdapter.enableForegroundDispatch(this, mPendingIntent, mIntentFilters, mNFCTechLists);
    }

    @Override
    public void onPause() {
        super.onPause();

        if (mNfcAdapter != null)
            mNfcAdapter.disableForegroundDispatch(this);
    }

    @Override
    public void onNewIntent(Intent intent) {
        String action = intent.getAction();
        if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(action)) {
            Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            mTextView.setText(bin2hex(tag.getId())); // id-nya RFID
            String idBarang = bin2hex(tag.getId());

            try {
                URL url = new URL("https://amazonk-shelf-manager.herokuapp.com/open-shelf");
                HttpURLConnection http = (HttpURLConnection) url.openConnection();
                http.setRequestMethod("POST");
                http.setRequestProperty("Content-Type", "application/json");
                http.setRequestProperty("Accept", "application/json");
                http.setDoOutput(true);

                String jsonPayload = "{\"idBarang\":\"" + idBarang + "\"}";
                byte[] out = jsonPayload.getBytes(StandardCharsets.UTF_8);
                int length = out.length;

                http.setFixedLengthStreamingMode(length);
                http.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                http.connect();

                OutputStream os = http.getOutputStream();
                os.write(out);

                BufferedReader in = new BufferedReader(new InputStreamReader(http.getInputStream()));
                String inputLine;
                while ((inputLine = in.readLine()) != null) Log.d("Scan", inputLine);
                in.close();
                os.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    static String bin2hex(byte[] data) {
        return String.format("%0" + (data.length * 2) + "X", new BigInteger(1,data));
    }
}

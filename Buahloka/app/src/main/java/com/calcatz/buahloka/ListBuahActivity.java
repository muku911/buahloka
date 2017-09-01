package com.calcatz.buahloka;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ListBuahActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_buah);

        Bundle bundle;
        bundle = getIntent().getExtras();
        
        String put;

        put = bundle.getString("jenisbuah");

        TextView testto = (TextView)findViewById(R.id.test);
        testto.setText(put + " " + bundle.getString("pilihanprovinsi"));
    }
}

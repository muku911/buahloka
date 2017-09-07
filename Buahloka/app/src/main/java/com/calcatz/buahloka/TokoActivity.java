package com.calcatz.buahloka;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by Fauzan on 31/08/2017.
 */

public class TokoActivity extends AppCompatActivity{

    Button btnreview;
    Button btnlist;
    Button btnpesanan;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toko);

        btnreview = (Button) findViewById(R.id.btnReview);
        btnlist = (Button) findViewById(R.id.btnList);
        btnpesanan = (Button) findViewById(R.id.btnPesanan);

        btnlist.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View view) {
                                           Intent i = new Intent(getApplicationContext(), ListJualanActivity.class);
                                           startActivity(i);
                                       }
                                   });

        btnreview.setOnClickListener(new View.OnClickListener(){
                                         @Override
                                         public void onClick(View view) {
                                             Intent i = new Intent(getApplicationContext(), TokoReview.class);
                                             startActivity(i);
                                         }
                                     });

        btnpesanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Pesanan.class);
                startActivity(i);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

    }


}

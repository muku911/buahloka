package com.calcatz.buahloka;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

public class DetailKebunActivity extends AppCompatActivity {

    //UI
    private TextView tx_judulKebun, tx_harga, tx_tersedia, tx_promo, tx_isiDiPesan;
    private RatingBar rating;
    private ImageView img_logoBuah;
    private Button btn_buyItem;

    private ImageView img_doc1, img_doc2, img_doc3;
    private TextView
            tx_isiLokasiKebun, tx_isiTanggalPanenLast ,tx_isiNextPanen,
            tx_isiPanjangAVG, tx_isiLebarAVG, tx_isiBeratAVG,
            tx_isiLastTestWarna, tx_isiCodeWarna, tx_viewWarna,
            tx_isiLastTestKematangan, tx_isiKadarAvgGasEtilen, tx_isiPersenKematangan,
            tx_isiLastTestKandungan, tx_isiKandungan, tx_isiStatusKandungan,
            tx_isiKadaluarsa,
            tx_isiManfaat;


    //String
    private String pilihanKebun, pilihanBuah;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_kebun);

        headerView();
        init();

    }

    private void headerView() {
        //LoadBundle
        Bundle bundle;
        bundle = getIntent().getExtras();
        pilihanKebun = bundle.getString("jenisbuah");
        pilihanBuah = bundle.getString("pilihanBuah");
    }

    private void init() {
        img_logoBuah = (ImageView)findViewById(R.id.img_logoBuah);
        rating =  (RatingBar)findViewById(R.id.ratingBar);
        btn_buyItem = (Button)findViewById(R.id.btn_buyItem);

        tx_judulKebun = (TextView)findViewById(R.id.tx_judulKebun);
        tx_harga = (TextView)findViewById(R.id.tx_harga);
        tx_tersedia = (TextView)findViewById(R.id.tx_tersedia);
        tx_promo = (TextView)findViewById(R.id.tx_promo);
        tx_isiDiPesan = (TextView)findViewById(R.id.tx_isiDiPesan);

        img_doc1 = (ImageView)findViewById(R.id.img_doc1);
        img_doc2 = (ImageView)findViewById(R.id.img_doc2);
        img_doc3 = (ImageView)findViewById(R.id.img_doc3);

        tx_isiLokasiKebun = (TextView)findViewById(R.id.tx_isiLokasiKebun);
        tx_isiTanggalPanenLast = (TextView)findViewById(R.id.tx_isiTanggalPanenLast);
        tx_isiNextPanen = (TextView)findViewById(R.id.tx_isiNextPanen);

        tx_isiPanjangAVG = (TextView)findViewById(R.id.tx_isiPanjangAVG);
        tx_isiLebarAVG = (TextView)findViewById(R.id.tx_isiLebarAVG);
        tx_isiBeratAVG = (TextView)findViewById(R.id.tx_isiBeratAVG);

        tx_isiLastTestWarna = (TextView)findViewById(R.id.tx_isiLastTestWarna);
        tx_isiCodeWarna = (TextView)findViewById(R.id.tx_isiCodeWarna);
        tx_viewWarna = (TextView)findViewById(R.id.tx_viewWarna);

        tx_isiLastTestKematangan = (TextView)findViewById(R.id.tx_isiLastTestKematangan);
        tx_isiKadarAvgGasEtilen = (TextView)findViewById(R.id.tx_isiDiPesan);
        tx_isiPersenKematangan = (TextView)findViewById(R.id.tx_isiPersenKematangan);

        tx_isiLastTestKandungan = (TextView)findViewById(R.id.tx_isiLastTestKandungan);
        tx_isiKandungan = (TextView)findViewById(R.id.tx_isiKandungan);
        tx_isiStatusKandungan = (TextView)findViewById(R.id.tx_isiStatusKandungan);

        tx_isiKadaluarsa = (TextView)findViewById(R.id.tx_isiKadaluarsa);

        tx_isiManfaat = (TextView)findViewById(R.id.tx_isiManfaat);
    }
}

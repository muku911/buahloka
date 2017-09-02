package com.calcatz.buahloka;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class DetailKebunActivity extends AppCompatActivity {

    //UI
    private TextView tx_judulKebun, tx_harga, tx_tersedia, tx_promo, tx_isiDiPesan;
    private RatingBar rating;
    private ImageView img_logoBuah;
    private Button btn_buyItem, btn_review;

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

    //Data
    private Rating ratingThis;
    private List<Rating>ratings = new ArrayList<Rating>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_kebun);

        headerView();
        init();
        //Reset
        ratings.clear();
        //set
        setValueOffline();
        //valuereset();
        ratingAVG();



    }

    private void ratingAVG() {
        float b = 0;
        for (int a = 0 ; a < ratings.size() ; a++ ){
            b = b + Float.parseFloat(ratings.get(a).getRating());
        }
        float c = b/5;
        rating.setRating(c);

        int sum = ratings.size();
        tx_isiDiPesan.setText(""+sum+"x");
    }

    private void setValueOffline() {
        ratingThis = new Rating("4");
        ratings.add(ratingThis);

        ratingThis = new Rating("1");
        ratings.add(ratingThis);

        ratingThis = new Rating("3");
        ratings.add(ratingThis);

        ratingThis = new Rating("5");
        ratings.add(ratingThis);
    }

    private void valuereset() {
        rating.setRating(0);
        tx_harga.setText("Rp. "+" /Kg");
        tx_tersedia.setText(" ");
        tx_promo.setText("Rp. "+" /Kg Pemesanan > " + " ");
        tx_isiDiPesan.setText(" ");
        tx_judulKebun.setText("Bualoka");
        img_logoBuah.setImageResource(R.drawable.logo_bualoka);


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
        btn_review = (Button)findViewById(R.id.btn_review);

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

class Rating {
    private String rating;

    public Rating(String rating) {
        this.rating = rating;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}

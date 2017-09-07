package com.calcatz.buahloka;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.RatingBar;
import android.widget.TextView;

import org.w3c.dom.Text;

public class DataKebun extends AppCompatActivity {



    private Kebun kebun;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_kebun);


        TextView txLokasiKebun = (TextView)findViewById(R.id.txLokasiKebun);
        TextView txTglPanen = (TextView)findViewById(R.id.txTglPanen);
        TextView txNextPanen = (TextView) findViewById(R.id.txNextPanen);

        String lokasi = Kebun.getLokasi();
        String tglPanen = Kebun.getTglPanen();
        String nextPanen = Kebun.getNextPanen();

        Kebun kebun = new Kebun(lokasi,tglPanen, nextPanen );

        Log.d("LOGUOU", lokasi);

        txLokasiKebun.setText(lokasi);
        txTglPanen.setText(tglPanen);
        txNextPanen.setText(nextPanen);
    }

}

class Kebun {
   private String lokasi, tglPanen, nextPanen;

    public Kebun() {
    }
    public Kebun(String lokasi, String tglPanen, String nextPanen) {
        this.lokasi = lokasi;
        this.tglPanen = tglPanen;
        this.nextPanen = nextPanen;
    }

    public static String getLokasi() {

        return "ALamat: Jl Sukolilo Surabaya";
        //return lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    public static String getTglPanen() {

        return "Tanggal Terakhir Panen: 25 Januari 2016";
        //return tglPanen;
    }

    public void setTglPanen(String tglPanen) {
        this.tglPanen = tglPanen;
    }

    public static String getNextPanen() {

        return "Tanggal Panen Selanjutnya; 10 Agustus 2016";
        //return nextPanen;
    }

    public void setNextPanen(String nextPanen) {
        this.nextPanen = nextPanen;
    }
}
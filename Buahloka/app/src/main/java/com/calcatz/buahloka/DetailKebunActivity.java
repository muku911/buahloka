package com.calcatz.buahloka;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;

public class DetailKebunActivity extends AppCompatActivity {
    //Firebase
    private FirebaseDatabase mydatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseDetail = mydatabase.getReference();
    private DatabaseReference databaseRating = mydatabase.getReference();

    //Data
    private DetailData detailData;
    private Keranjang keranjang;


    private Rating ratting;
    private List<Rating> ratingList = new ArrayList<Rating>();

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
    private String pilihanKebun, pilihanBuah, idKebun;
    private String ratingAVG, id_barang;

    //Data

    //private List<Rating>ratings = new ArrayList<Rating>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_kebun);

        init();
        headerView();

        //set


        //firewbase
        databaseDetail.child("Barang").child(idKebun).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                detailData = dataSnapshot.getValue(DetailData.class);

                editData();



                databaseRating.child("Toko").child(detailData.getId_toko()).child("Comment").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (ratingList != null)
                            ratingList.clear();
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                            ratting = snapshot.getValue(Rating.class);
                            String temp = snapshot.child("id_barang").getValue(String.class);
                            if (temp.equals(detailData.getId())){
                                id_barang = temp;

                                ratingList.add(ratting);
                            }
                        }
                        ratingAVG();
                        btn_review.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Bundle bundle = new Bundle();
                                bundle.putString("pilihanKebun", pilihanKebun);
                                bundle.putString("pilihanBuah", pilihanBuah);
                                bundle.putString("idKebun", idKebun);
                                bundle.putString("ratingAVG", ratingAVG);
                                bundle.putString("id_barang", id_barang);
                                bundle.putString("id_toko", detailData.getId_toko());
                                bundle.putString("alamat", detailData.getAlamat());
                                Intent gotoListBuah = new Intent(DetailKebunActivity.this, ReviewActivity.class);
                                gotoListBuah.putExtras(bundle);
                                startActivity(gotoListBuah);
                            }
                        });
                        btn_buyItem.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                String idkutmj = UUID.randomUUID().toString();
                                keranjang = new Keranjang(idkutmj, detailData.getId(),1, detailData.getHarga_kilo());
                                databaseDetail.child("User").child("ujnYGeECSfcpQtjmltHM3AdhrBL2").child("Keranjang").child("Item").child(idkutmj).setValue(keranjang);
                                Toast.makeText(DetailKebunActivity.this, "Telah dipindah kan ke Keranjang Anda", Toast.LENGTH_LONG).show();

                            }
                        });
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }

            private void editData() {
                tx_harga.setText(""+detailData.getHarga_kilo()+"/Kg");
                tx_tersedia.setText(""+detailData.getKetersediaan()+" Ton");
                if (detailData.getPromo() != null)
                    tx_promo.setText(detailData.getPromo());
                else tx_promo.setVisibility(View.GONE);

                tx_isiLokasiKebun.setText(detailData.getAlamat());
                tx_isiTanggalPanenLast.setText(detailData.getTgl_panen());
                tx_isiNextPanen.setText(detailData.getTgl_panen());

                tx_isiPanjangAVG.setText(""+detailData.getAvrg_panjang()+" cm");
                tx_isiLebarAVG.setText(""+detailData.getAvrg_lebar()+" cm");
                tx_isiBeratAVG.setText(""+detailData.getAvrg_berat()+" Kg");

                tx_isiLastTestWarna.setText(detailData.getTgl_uji());
                tx_isiCodeWarna.setText("C="+detailData.getWarna_c()+"  M="+detailData.getWarna_m()+"  Y="+detailData.getWarna_y()+"  K="+detailData.getWarna_k());

                tx_isiLastTestKematangan.setText(detailData.getTgl_uji());
                tx_isiKadarAvgGasEtilen.setText(""+detailData.getKematangan_buah()+" ppm/buah");
                tx_isiPersenKematangan.setText(detailData.getKematangan_buah()+"%");

                tx_isiLastTestKandungan.setText(detailData.getTgl_uji());
                tx_isiKandungan.setText(detailData.getBod_recorded()+" mg/L");
                tx_isiStatusKandungan.setText(detailData.getKandungan_bakteri());

                tx_isiKadaluarsa.setText(detailData.getKadaluarsa());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }

    private void ratingAVG() {
        float b = 0;
        int sum = ratingList.size();
        for (int a = 0 ; a < ratingList.size() ; a++ ){
            b = b + ratingList.get(a).getRating();
        }
        float c = b/sum;
        ratingAVG = "" + c;
        rating.setRating(c);
        tx_isiDiPesan.setText(""+sum+"x");
    }

    private void valuereset() {
        rating.setRating(0);
        tx_harga.setText("Rp. "+" /Kg");
        tx_tersedia.setText(" ");
        tx_promo.setText("Rp. "+" /Kg Pemesanan > " + " ");
        tx_isiDiPesan.setText(" ");
        tx_judulKebun.setText("Bualoka");


    }

    private void headerView() {
        //LoadBundle
        Bundle bundlee;
        bundlee = getIntent().getExtras();
        pilihanKebun = bundlee.getString("pilihankebun");
        pilihanBuah = bundlee.getString("pilihanBuah");
        idKebun = bundlee.getString("idkebun");


        tx_judulKebun.setText(pilihanKebun);
        img_logoBuah.setImageResource(R.drawable.logo_bualoka);
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


class Keranjang{
    String id, id_barang;
    long quantity, harga;

    public Keranjang() {
    }

    public Keranjang(String id, String id_barang, long quantity, long harga) {
        this.id = id;
        this.id_barang = id_barang;
        this.quantity = quantity;
        this.harga = harga;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId_barang() {
        return id_barang;
    }

    public void setId_barang(String id_barang) {
        this.id_barang = id_barang;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public long getHarga() {
        return harga;
    }

    public void setHarga(long harga) {
        this.harga = harga;
    }
}

class Rating{
    long rating;

    public Rating() {
    }

    public Rating(long rating) {
        this.rating = rating;
    }

    public long getRating() {
        return rating;
    }

    public void setRating(long rating) {
        this.rating = rating;
    }
}

class DetailData {
    private String alamat, aman_konsumsi, bahan_kimia, city, id, id_kebun, id_toko, kadaluarsa, kandungan_bakteri,
                    promo, province, subdistric, tgl_panen, tgl_uji;
    private long avrg_berat, avrg_lebar, avrg_panjang, avrg_rating, bod_recorded, harga_kilo, id_buah, jumlah_pembeli,
                    kematangan_buah, ketersediaan, warna_c, warna_k, warna_m, warna_y;

    public DetailData() {
    }

    public DetailData(String alamat, String aman_konsumsi, String bahan_kimia, String city, String id, String id_kebun, String id_toko, String kadaluarsa, String kandungan_bakteri, String promo, String province, String subdistric, String tgl_panen, String tgl_uji, long avrg_berat, long avrg_lebar, long avrg_panjang, long avrg_rating, long bod_recorded, long harga_kilo, long id_buah, long jumlah_pembeli, long kematangan_buah, long ketersediaan, long warna_c, long warna_k, long warna_m, long warna_y) {
        this.alamat = alamat;
        this.aman_konsumsi = aman_konsumsi;
        this.bahan_kimia = bahan_kimia;
        this.city = city;
        this.id = id;
        this.id_kebun = id_kebun;
        this.id_toko = id_toko;
        this.kadaluarsa = kadaluarsa;
        this.kandungan_bakteri = kandungan_bakteri;
        this.promo = promo;
        this.province = province;
        this.subdistric = subdistric;
        this.tgl_panen = tgl_panen;
        this.tgl_uji = tgl_uji;
        this.avrg_berat = avrg_berat;
        this.avrg_lebar = avrg_lebar;
        this.avrg_panjang = avrg_panjang;
        this.avrg_rating = avrg_rating;
        this.bod_recorded = bod_recorded;
        this.harga_kilo = harga_kilo;
        this.id_buah = id_buah;
        this.jumlah_pembeli = jumlah_pembeli;
        this.kematangan_buah = kematangan_buah;
        this.ketersediaan = ketersediaan;
        this.warna_c = warna_c;
        this.warna_k = warna_k;
        this.warna_m = warna_m;
        this.warna_y = warna_y;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getAman_konsumsi() {
        return aman_konsumsi;
    }

    public void setAman_konsumsi(String aman_konsumsi) {
        this.aman_konsumsi = aman_konsumsi;
    }

    public String getBahan_kimia() {
        return bahan_kimia;
    }

    public void setBahan_kimia(String bahan_kimia) {
        this.bahan_kimia = bahan_kimia;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId_kebun() {
        return id_kebun;
    }

    public void setId_kebun(String id_kebun) {
        this.id_kebun = id_kebun;
    }

    public String getId_toko() {
        return id_toko;
    }

    public void setId_toko(String id_toko) {
        this.id_toko = id_toko;
    }

    public String getKadaluarsa() {
        return kadaluarsa;
    }

    public void setKadaluarsa(String kadaluarsa) {
        this.kadaluarsa = kadaluarsa;
    }

    public String getKandungan_bakteri() {
        return kandungan_bakteri;
    }

    public void setKandungan_bakteri(String kandungan_bakteri) {
        this.kandungan_bakteri = kandungan_bakteri;
    }

    public String getPromo() {
        return promo;
    }

    public void setPromo(String promo) {
        this.promo = promo;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getSubdistric() {
        return subdistric;
    }

    public void setSubdistric(String subdistric) {
        this.subdistric = subdistric;
    }

    public String getTgl_panen() {
        return tgl_panen;
    }

    public void setTgl_panen(String tgl_panen) {
        this.tgl_panen = tgl_panen;
    }

    public String getTgl_uji() {
        return tgl_uji;
    }

    public void setTgl_uji(String tgl_uji) {
        this.tgl_uji = tgl_uji;
    }

    public long getAvrg_berat() {
        return avrg_berat;
    }

    public void setAvrg_berat(long avrg_berat) {
        this.avrg_berat = avrg_berat;
    }

    public long getAvrg_lebar() {
        return avrg_lebar;
    }

    public void setAvrg_lebar(long avrg_lebar) {
        this.avrg_lebar = avrg_lebar;
    }

    public long getAvrg_panjang() {
        return avrg_panjang;
    }

    public void setAvrg_panjang(long avrg_panjang) {
        this.avrg_panjang = avrg_panjang;
    }

    public long getAvrg_rating() {
        return avrg_rating;
    }

    public void setAvrg_rating(long avrg_rating) {
        this.avrg_rating = avrg_rating;
    }

    public long getBod_recorded() {
        return bod_recorded;
    }

    public void setBod_recorded(long bod_recorded) {
        this.bod_recorded = bod_recorded;
    }

    public long getHarga_kilo() {
        return harga_kilo;
    }

    public void setHarga_kilo(long harga_kilo) {
        this.harga_kilo = harga_kilo;
    }

    public long getId_buah() {
        return id_buah;
    }

    public void setId_buah(long id_buah) {
        this.id_buah = id_buah;
    }

    public long getJumlah_pembeli() {
        return jumlah_pembeli;
    }

    public void setJumlah_pembeli(long jumlah_pembeli) {
        this.jumlah_pembeli = jumlah_pembeli;
    }

    public long getKematangan_buah() {
        return kematangan_buah;
    }

    public void setKematangan_buah(long kematangan_buah) {
        this.kematangan_buah = kematangan_buah;
    }

    public long getKetersediaan() {
        return ketersediaan;
    }

    public void setKetersediaan(long ketersediaan) {
        this.ketersediaan = ketersediaan;
    }

    public long getWarna_c() {
        return warna_c;
    }

    public void setWarna_c(long warna_c) {
        this.warna_c = warna_c;
    }

    public long getWarna_k() {
        return warna_k;
    }

    public void setWarna_k(long warna_k) {
        this.warna_k = warna_k;
    }

    public long getWarna_m() {
        return warna_m;
    }

    public void setWarna_m(long warna_m) {
        this.warna_m = warna_m;
    }

    public long getWarna_y() {
        return warna_y;
    }

    public void setWarna_y(long warna_y) {
        this.warna_y = warna_y;
    }
}
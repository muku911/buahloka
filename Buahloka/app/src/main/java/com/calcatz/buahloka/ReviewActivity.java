package com.calcatz.buahloka;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.PropertyName;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ReviewActivity extends AppCompatActivity {
    //Firebase
    private FirebaseDatabase mydatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseRating = mydatabase.getReference();
    private DatabaseReference databaseUser = mydatabase.getReference();

    //String
    private String pilihanKebun, pilihanBuah, idKebun, ratingAVG, id_kebun, id_toko, id_barang, alamat;
    private String namaPereview, komentarPereview;
    private long ratingPereview;

    //Data
    private Pereview pereview;
    private List<Pereview> pereviewList = new ArrayList<Pereview>();

    private PereviewViewAdapter adapter;

    //UI
    private RatingBar ratingBar;
    private TextView tx_nama, tx_isiAlamat, tx_isiPhone;
    private ListView lv_jualan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        //inisialisasi
        ratingBar = (RatingBar)findViewById(R.id.ratingBar);
        tx_nama = (TextView)findViewById(R.id.tx_nama);
        tx_isiAlamat = (TextView)findViewById(R.id.tx_isiAlamat);
        tx_isiPhone = (TextView)findViewById(R.id.tx_isiPhone);
        lv_jualan = (ListView)findViewById(R.id.lv_listJualan);

        Bundle bundle;
        bundle = getIntent().getExtras();
        pilihanKebun = bundle.getString("pilihanKebun");
        pilihanBuah = bundle.getString("pilihanBuah");
        idKebun = bundle.getString("idKebun");
        ratingAVG = bundle.getString("ratingAVG");
        id_kebun = bundle.getString("id_barang");//
        id_toko = bundle.getString("id_toko");
        id_barang = bundle.getString("id_barang");//
        alamat = bundle.getString("alamat");

        ratingBar.setRating(Float.parseFloat(ratingAVG));
        tx_nama.setText(pilihanKebun);
        tx_isiAlamat.setText(alamat);
        tx_isiPhone.setText("081012345678");


        databaseRating.child("Toko").child(id_toko).child("Comment").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (pereviewList != null){
                    pereviewList.clear();
                }
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    String temp = snapshot.child("id_barang").getValue(String.class);

                    if (temp.equals(id_barang)){
                        String simpenan = snapshot.child("id_user").getValue(String.class);
                        final String simpenan1 = snapshot.child("komentar").getValue(String.class);
                        final long simpenan2 = snapshot.child("rating").getValue(long.class);
                        databaseUser.child("User").child(simpenan).child("Profile").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                namaPereview = dataSnapshot.child("name").getValue(String.class);
                                komentarPereview = simpenan1;
                                ratingPereview = simpenan2;
                                pereview = new Pereview(namaPereview,komentarPereview,ratingPereview);
                                pereviewList.add(pereview);

                               // Toast.makeText(ReviewActivity.this, "Welcomes ", Toast.LENGTH_SHORT).show();



                                adapter = new PereviewViewAdapter(ReviewActivity.this,pereviewList);
                                lv_jualan.setAdapter(adapter);

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
}

class Pereview {
    private String name, comment;
    private  long  rating;

    public Pereview() {
    }

    public Pereview(String name, String comment, long rating) {
        this.name = name;
        this.comment = comment;
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public long getRating() {
        return rating;
    }

    public void setRating(long rating) {
        this.rating = rating;
    }
}

class PereviewViewAdapter extends BaseAdapter{

    private LayoutInflater inflater;
    private Activity activity;
    private List<Pereview> pereviewList;

    public PereviewViewAdapter(Activity activity, List<Pereview> pereviewList) {
        this.activity = activity;
        this.pereviewList = pereviewList;
    }

    @Override
    public int getCount() {
        return pereviewList.size();
    }

    @Override
    public Object getItem(int i) {
        return pereviewList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        inflater  = (LayoutInflater)activity.getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.items_review, null);

        TextView  tx_nama = (TextView)itemView.findViewById(R.id.tx_nama);
        TextView tx_isiKomentar = (TextView)itemView.findViewById(R.id.tx_isiKomentar);
        RatingBar ratingBar_review = (RatingBar)itemView.findViewById(R.id.ratingBar_review);
        ImageView img_itemJualan = (ImageView)itemView.findViewById(R.id.img_itemJualan);

        ratingBar_review.setRating(pereviewList.get(i).getRating());
        tx_nama.setText(pereviewList.get(i).getName());
        tx_isiKomentar.setText(pereviewList.get(i).getComment());
        img_itemJualan.setImageResource(R.drawable.logo_bualoka);

        return itemView;
    }
}


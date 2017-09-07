package com.calcatz.buahloka;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class TokoReview extends AppCompatActivity {

    private ListView listReview;
    private Review review;
    private ArrayList<Review>reviewList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toko_review);
        listReview = (ListView) findViewById(R.id.listTokoReview);
        dataoffline();

        ListAdapter adapter = new ReviewListAdapter(this, R.layout.item_list_tokoreview,reviewList, this);
        listReview.setAdapter(adapter);

    }

    private void dataoffline(){
       Review data1 = new Review("Bapak", "ini komentar saya", 5);
       reviewList.add(data1);
        Review data2 = new Review("Ibu", "ini komentar saya", 3);
        reviewList.add(data2);
        Review data3 = new Review("Anak", "ini komentar saya", 3);
        reviewList.add(data3);
        Review data4 = new Review("Kakak", "ini komentar saya", 2);
        reviewList.add(data4);
        Review data5 = new Review("Adek", "ini komentar saya", 2);
        reviewList.add(data5);
    }
}

class Review{
    private String nama;
    private String komentar;
    private int rating;

    public Review() {
    }

    public Review(String nama, String komentar, int rating) {
        this.nama = nama;
        this.komentar = komentar;
        this.rating = rating;
    }

    public String getNama() {
        return nama;
    }

    public String getKomentar() {
        return komentar;
    }

    public int getRating() {
        return rating;
    }
}

class ReviewListAdapter extends ArrayAdapter<Review>{
    private Context mContext;
    ArrayAdapter<Review>review;
    int mResource;

    public ReviewListAdapter(Context context, int resource, ArrayList<Review> objects, Context mContext) {
        super(context, resource, objects);
        this.mContext = mContext;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String nama = getItem(position).getNama();
        String komentar = getItem(position).getKomentar();
        int rating = getItem(position).getRating();

        Review review = new Review(nama,komentar,rating);
        LayoutInflater inflater = LayoutInflater.from(mContext);

        convertView = inflater.inflate(R.layout.item_list_tokoreview, parent, false);

        TextView txNama = (TextView) convertView.findViewById(R.id.textNamaUser);
        TextView txKomentar = (TextView) convertView.findViewById(R.id.textKomentar);
        RatingBar rtToko = (RatingBar) convertView.findViewById(R.id.ratingBarUser);

        txNama.setText(nama);
        txKomentar.setText(komentar);
        rtToko.setRating(rating);

        return convertView;

    }
}
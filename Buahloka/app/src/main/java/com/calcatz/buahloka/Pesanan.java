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
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Pesanan extends AppCompatActivity {

    private ListView listPesanan;
    private Pesanans pesanan;
    private ArrayList<Pesanans> pesananList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesanan);
        listPesanan = (ListView) findViewById(R.id.listPesanan);
        dataoffline();

        ListAdapter adapter = new PesananListAdapter(this, R.layout.item_list_pesanan,pesananList, this);
        listPesanan.setAdapter(adapter);
    }

    private void dataoffline() {
        Pesanans data1 = new Pesanans("Bapak", "Jalan alamat", "10 Kg", "Matang", "10000");
        pesananList.add(data1);
        Pesanans data2 = new Pesanans("Ibu", "Jalan alamat", "10 Kg", "Matang", "10000");
        pesananList.add(data2);
        Pesanans data3 = new Pesanans("Adek", "Jalan alamat", "10 Kg", "Matang", "10000");
        pesananList.add(data3);
        Pesanans data4 = new Pesanans("KAka", "Jalan alamat", "10 Kg", "Matang", "10000");
        pesananList.add(data4);
        Pesanans data5 = new Pesanans("ROnaldi", "Jalan alamat", "10 Kg", "Matang", "10000");
        pesananList.add(data5);
        Pesanans data6 = new Pesanans("Bapak", "Jalan alamat", "10 Kg", "Matang", "10000");
        pesananList.add(data6);
        Pesanans data7 = new Pesanans("Bapak", "Jalan alamat", "10 Kg", "Matang", "10000");
        pesananList.add(data7);
        Pesanans data8 = new Pesanans("Bapak", "Jalan alamat", "10 Kg", "Matang", "10000");
        pesananList.add(data8);
        Pesanans data9 = new Pesanans("Bapak", "Jalan alamat", "10 Kg", "Matang", "10000");
        pesananList.add(data9);

    }
}

class Pesanans{
    private String pembeli, alamat, jumlahBeli, status, totalHarga;

    public Pesanans() {
    }

    public Pesanans(String pembeli, String alamat, String jumlahBeli, String status, String totalHarga) {
        this.pembeli = pembeli;
        this.alamat = alamat;
        this.jumlahBeli = jumlahBeli;
        this.status = status;
        this.totalHarga = totalHarga;
    }

    public String getPembeli() {
        return pembeli;
    }

    public String getAlamat() {
        return alamat;
    }

    public String getJumlahBeli() {
        return jumlahBeli;
    }

    public String getStatus() {
        return status;
    }

    public String getTotalHarga() {
        return totalHarga;
    }
}

class PesananListAdapter extends ArrayAdapter<Pesanans>{
    private Context mContext;
    int mResource;
    ArrayAdapter<Pesanans>pesanan;


    public PesananListAdapter(Context context, int resource, ArrayList<Pesanans> objects, Context mContext) {
        super(context, resource, objects);
        this.mContext = mContext;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String pembeli = getItem(position).getPembeli();
        String alamat = getItem(position).getAlamat();
        String jumlahBeli = getItem(position).getJumlahBeli();
        String status = getItem(position).getStatus();
        String totalHarga = getItem(position).getTotalHarga();

        Pesanans pesanan = new Pesanans(pembeli,alamat,jumlahBeli,status,totalHarga);
        LayoutInflater inflater = LayoutInflater.from(mContext);

        convertView = inflater.inflate(R.layout.item_list_pesanan, parent, false);

        TextView txPembeli = (TextView) convertView.findViewById(R.id.textPsnPembeli);
        TextView txAlamat = (TextView) convertView.findViewById(R.id.textPsnAlamat);
        TextView txJumlah = (TextView) convertView.findViewById(R.id.textPsnJumlah);
        TextView txStatus = (TextView) convertView.findViewById(R.id.textPsnStatus);
        TextView txHarga = (TextView) convertView.findViewById(R.id.textPsnHarga);

        txPembeli.setText(pembeli);
        txAlamat.setText(alamat);
        txJumlah.setText(jumlahBeli);
        txStatus.setText(status);
        txHarga.setText(totalHarga);

        return convertView;

    }
}

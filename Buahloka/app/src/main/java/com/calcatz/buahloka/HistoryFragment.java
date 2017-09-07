package com.calcatz.buahloka;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fauzan on 31/08/2017.
 */

public class HistoryFragment extends Fragment{

    public HistoryFragment() {
        // Required empty public constructor
    }

    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    FirebaseUser user = firebaseAuth.getCurrentUser();

    ListView lv_item_history;

    List<String> address_history = new ArrayList<>();
    List<String> city_history = new ArrayList<>();
    List<String> id_toko_history = new ArrayList<>();
    List<String> nama_penerima = new ArrayList<>();
    List<String> provinsi_history = new ArrayList<>();
    List<String> status_pengiriman = new ArrayList<>();
    List<Integer> harga_total = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        lv_item_history = view.findViewById(R.id.lv_history);

        setItem();

        return view;
    }


    public void setItem() {
        DatabaseReference dr_item = database.getReference();
        dr_item.child("User").child(user.getUid()).child("Transaksi").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot data : dataSnapshot.getChildren()){
                    String alamat = data.child("address_tujuan").getValue(String.class);
                    String kota = data.child("city").getValue(String.class);
                    String idToko = data.child("id_toko").getValue(String.class);
                    String nama = data.child("nama_penerima").getValue(String.class);
                    String provinsi = data.child("province").getValue(String.class);
                    String status = data.child("status_pengiriman").getValue(String.class);
                    int hartot = data.child("total_harga").getValue(Integer.class);

                    address_history.add(alamat);
                    city_history.add(kota);
                    id_toko_history.add(idToko);
                    nama_penerima.add(nama);
                    provinsi_history.add(provinsi);
                    status_pengiriman.add(status);
                    harga_total.add(hartot);
                }

                setLv_Adapter();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void setLv_Adapter(){
        HistoryAdapter historyAdapter = new HistoryAdapter(getActivity(),address_history,city_history,id_toko_history,nama_penerima,provinsi_history,status_pengiriman,harga_total);
        lv_item_history.setAdapter(historyAdapter);
    }
}

class HistoryAdapter extends BaseAdapter{

    Context context;
    List<String> alamat, kota, id_toko, nama_penerima, provinsi, status;
    List<Integer> harga;
    private LayoutInflater inflater;

    public HistoryAdapter(Context context,List<String> alamat, List<String> kota, List<String> id_toko, List<String> nama_penerima, List<String> provinsi, List<String> status, List<Integer> harga){
        this.context = context;
        this.alamat = alamat;
        this.kota = kota;
        this.id_toko = id_toko;
        this.nama_penerima = nama_penerima;
        this.provinsi = provinsi;
        this.status = status;
        this.harga = harga;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return alamat.size();
    }

    @Override
    public Object getItem(int i) {
        return alamat.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final int urut = i;
        View rootView = inflater.inflate(R.layout.history_list_row,viewGroup,false);

        TextView tv_nama_toko = rootView.findViewById(R.id.tv_nama_toko);
        TextView tv_nama_penerima = rootView.findViewById(R.id.tv_nama_penerima);
        TextView tv_alamat = rootView.findViewById(R.id.tv_alamat);
        TextView tv_kota = rootView.findViewById(R.id.tv_kota);
        TextView tv_provinsi = rootView.findViewById(R.id.tv_provinsi);
        TextView tv_status = rootView.findViewById(R.id.tv_status);
        TextView tv_harga = rootView.findViewById(R.id.tv_harga);

        tv_nama_penerima.setText(nama_penerima.get(i));
        tv_alamat.setText(alamat.get(i));
        tv_kota.setText(kota.get(i));
        tv_provinsi.setText(provinsi.get(i));
        tv_status.setText(status.get(i));
        tv_harga.setText("Rp. "+String.valueOf(harga.get(i)));
        return rootView;
    }
}


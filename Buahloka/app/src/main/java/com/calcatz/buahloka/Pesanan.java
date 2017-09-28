package com.calcatz.buahloka;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Pesanan extends AppCompatActivity {

    private ListView listPesanan;

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();

        ListView lv_pesanan;

        List<String> address_history = new ArrayList<>();
        List<String> city_history = new ArrayList<>();
        List<String> id_toko_history = new ArrayList<>();
        List<String> nama_penerima = new ArrayList<>();
        List<String> provinsi_history = new ArrayList<>();
        List<String> status_pengiriman = new ArrayList<>();
        List<Integer> harga_total = new ArrayList<>();
        List<Long> imgsource = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesanan);

        lv_pesanan = (ListView)findViewById(R.id.lv_pesanan);

            setItem();

    }


    public void setItem() {
        DatabaseReference dr_item = database.getReference();
        dr_item.child("Toko").child(user.getUid()+"toko").child("Transaksi").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(address_history!=null) address_history.clear();
                if(city_history!=null) city_history.clear();
                if(id_toko_history!=null) id_toko_history.clear();
                if(nama_penerima!=null) nama_penerima.clear();
                if(provinsi_history!=null) provinsi_history.clear();
                if(status_pengiriman!=null)status_pengiriman.clear();
                if(harga_total!=null) harga_total.clear();
                if(imgsource!=null) imgsource.clear();

                for (DataSnapshot data : dataSnapshot.getChildren()){
                    String alamat = data.child("address_tujuan").getValue(String.class);
                    String kota = data.child("city").getValue(String.class);
                    String idToko = data.child("id_toko").getValue(String.class);
                    String nama = data.child("nama_penerima").getValue(String.class);
                    String provinsi = data.child("province").getValue(String.class);
                    String status = data.child("status_pengiriman").getValue(String.class);
                    int hartot = data.child("total_harga").getValue(Integer.class);
                    long img = data.child("imgsource").getValue(Long.class);

                    address_history.add(alamat);
                    city_history.add(kota);
                    id_toko_history.add(idToko);
                    nama_penerima.add(nama);
                    provinsi_history.add(provinsi);
                    status_pengiriman.add(status);
                    harga_total.add(hartot);
                    imgsource.add(img);
                }

                setLv_Adapter();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void setLv_Adapter(){
        PesananAdapter pesananAdapter = new PesananAdapter(this,address_history,city_history,id_toko_history,nama_penerima,provinsi_history,status_pengiriman,harga_total,imgsource);
        lv_pesanan.setAdapter(pesananAdapter);
    }
}

class PesananAdapter extends BaseAdapter{

    Context context;
    List<String> alamat, kota, id_toko, nama_penerima, provinsi, status;
    List<Integer> harga;
    private LayoutInflater inflater;
    List<Long> imgsource;

    public PesananAdapter(Context context,List<String> alamat, List<String> kota, List<String> id_toko, List<String> nama_penerima, List<String> provinsi, List<String> status, List<Integer> harga, List<Long> img){
        this.context = context;
        this.alamat = alamat;
        this.kota = kota;
        this.id_toko = id_toko;
        this.nama_penerima = nama_penerima;
        this.provinsi = provinsi;
        this.status = status;
        this.harga = harga;
        this.imgsource = img;
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
    public View getView(final int i, View view, ViewGroup viewGroup) {
        final int urut = i;
        View rootView = inflater.inflate(R.layout.history_toko_list_row,viewGroup,false);

        ImageView iv_photo_buah = rootView.findViewById(R.id.iv_photo_barang);
        TextView tv_nama_toko = rootView.findViewById(R.id.tv_nama_toko);
        TextView tv_nama_penerima = rootView.findViewById(R.id.tv_nama_penerima);
        TextView tv_alamat = rootView.findViewById(R.id.tv_alamat);
        TextView tv_kota = rootView.findViewById(R.id.tv_kota);
        TextView tv_provinsi = rootView.findViewById(R.id.tv_provinsi);
        TextView tv_status = rootView.findViewById(R.id.tv_status);
        TextView tv_harga = rootView.findViewById(R.id.tv_harga);

        iv_photo_buah.setImageResource(imgsource.get(i).intValue());
        tv_nama_penerima.setText(nama_penerima.get(i));
        tv_alamat.setText(alamat.get(i));
        tv_kota.setText(kota.get(i));
        tv_provinsi.setText(provinsi.get(i));
        tv_status.setText(status.get(i));
        tv_harga.setText("Rp. "+String.valueOf(harga.get(i)));

        Button terima = (Button) rootView.findViewById(R.id.btn_terima);
        terima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                FirebaseUser user = firebaseAuth.getCurrentUser();
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference get_item = database.getReference();
                final String[] id = new String[1];
                get_item.child("Toko").child(user.getUid()+"toko").child("Transaksi").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference dr_terima = database.getReference();
                        for(DataSnapshot data : dataSnapshot.getChildren()) {
                            id[0] = data.getKey();
                            TransaksiUser transaksiUser = new TransaksiUser(null, null, null, null, null, null, 0, "Diproses oleh Penjual",0);
                            dr_terima.child("Toko").child(user.getUid() + "toko").child("Transaksi").child(id[0]).child("status_pengiriman").setValue(transaksiUser.status_pengiriman);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
        return rootView;
    }
}

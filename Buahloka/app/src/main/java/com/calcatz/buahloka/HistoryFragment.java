package com.calcatz.buahloka;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
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

    List<String> nama_barang = new ArrayList<>();
    List<String> nama_toko = new ArrayList<>();
    List<Integer> quantity = new ArrayList<>();
    List<Integer> harga_barang = new ArrayList<>();

    List<IdBarangbeli> id_barang = new ArrayList<IdBarangbeli>();
    List<Barangbeli> l_barang = new ArrayList<Barangbeli>();
    List<String> id_item = new ArrayList<>();

    List<String> address_tujuan = new ArrayList<>();
    List<Integer> ongkir = new ArrayList<>();
    List<String> no_resi = new ArrayList<>();
    List<String> status_pengiriman = new ArrayList<>();



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
                int hartot = 0;
                if(id_barang!=null){
                    id_barang.clear();
                }
                if(quantity!=null){
                    quantity.clear();
                }
                if(harga_barang!=null){
                    harga_barang.clear();
                }
                if(id_item!=null){
                    id_item.clear();
                }
                if(no_resi!=null){
                    no_resi.clear();
                }
                if(address_tujuan!=null){
                    address_tujuan.clear();
                }
                if(ongkir!=null){
                    ongkir.clear();
                }
                if(status_pengiriman!=null){
                    status_pengiriman.clear();
                }

                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    IdBarangbeli id = data.getValue(IdBarangbeli.class);

                    id_barang.add(id);
                }

                for (int i = 0; i < id_barang.size(); i++) {
                    int qty = id_barang.get(i).getQuantity();
                    int hrg = id_barang.get(i).getHarga();
                    String id = id_barang.get(i).getId();
                    hartot = hartot + hrg;

                    id_item.add(id);
                    quantity.add(qty);
                    harga_barang.add(hrg);


                }
                setBarang();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void setBarang() {
        DatabaseReference dr_barang = database.getReference();
        dr_barang.child("Barang").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(l_barang!=null){
                    l_barang.clear();
                }
                if(nama_barang!=null){
                    nama_barang.clear();
                }

                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    String id = data.getKey();
                    for (int i = 0; i < id_barang.size(); i++) {
                        if (id.equals(id_barang.get(i).getId_barang())) {
                            Barangbeli barang = data.getValue(Barangbeli.class);

                            l_barang.add(barang);
                        }
                    }
                }
                for (int i = 0; i < l_barang.size(); i++) {
                    String nama = l_barang.get(i).getName();

                    nama_barang.add(nama);
                }
                setToko();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void setToko(){
        DatabaseReference dr_toko = database.getReference();
        dr_toko.child("Toko").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(nama_toko!=null){
                    nama_toko.clear();
                }

                for (DataSnapshot data : dataSnapshot.getChildren()){
                    String id = data.getKey();
                    for (int i = 0; i<l_barang.size(); i++) {
                        if (id.equals(l_barang.get(i).getId_toko())) {
                            String toko_name = data.child("Profile").child("name").getValue(String.class);

                            nama_toko.add(toko_name);
                        }
                    }
                }

                setLv_Adapter();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void setLv_Adapter(){
        HistoryAdapter historyAdapter = new HistoryAdapter(getActivity(),id_item,nama_barang,nama_toko,quantity,harga_barang,address_tujuan,no_resi,ongkir,status_pengiriman);
        lv_item_history.setAdapter(historyAdapter);
    }
}

class Barangbeli{
    private String name, id_toko;

    public Barangbeli(){

    }

    public Barangbeli(String name, String id_toko){
        this.name = name;
        this.id_toko = id_toko;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getId_toko(){
        return id_toko;
    }

    public void setId_toko(String id){
        this.id_toko = id;
    }
}

class IdBarangbeli{
    private String id_barang,id_item;
    private int quantity, harga;

    public IdBarangbeli(){

    }

    public IdBarangbeli(String id_barang, int qty, int harga){
        this.id_barang = id_barang;
        this.quantity = qty;
        this.harga = harga;
    }

    public int getQuantity(){
        return quantity;
    }

    public void  setQuantity(int qty){
        this.quantity = qty;
    }

    public int getHarga(){
        return harga;
    }

    public void setHarga(int harga){
        this.harga = harga;
    }

    public String getId(){
        return id_item;
    }

    public void setId(String id_item){
        this.id_item = id_item;
    }

    public String getId_barang(){
        return id_barang;
    }

    public void setId_barang(String id_barang){
        this.id_barang = id_barang;
    }
}

class HistoryAdapter extends BaseAdapter{

    Context context;
    List<String> nama_barang,nama_toko,address_tujuan, no_resi, status_pengiriman;
    List<Integer> quantity,harga_barang,ongkir;
    List<String> id_item;
    LayoutInflater inflater;

    public HistoryAdapter(Context context,List<String> id_item, List<String> nama_barang, List<String> nama_toko, List<Integer> quantity, List<Integer> harga_barang, List<String> address_tujuan, List<String> no_resi,
                          List<Integer> ongkir, List<String> status_pengiriman){
        this.context = context;
        this.id_item = id_item;
        this.nama_barang = nama_barang;
        this.nama_toko = nama_toko;
        this.quantity = quantity;
        this.harga_barang = harga_barang;
        this.address_tujuan = address_tujuan;
        this.no_resi = no_resi;
        this.status_pengiriman = status_pengiriman;
        this.ongkir = ongkir;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return nama_barang.size();
    }

    @Override
    public Object getItem(int i) {
        return nama_barang.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final int urut = i;
        View rootView = inflater.inflate(R.layout.keranjang_list_row,viewGroup,false);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = firebaseAuth.getCurrentUser();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference dr_delete = database.getReference();

        TextView tv_nama_barang = rootView.findViewById(R.id.tv_nama_barang);
        TextView tv_nama_toko = rootView.findViewById(R.id.tv_nama_toko);
        TextView tv_quantity = rootView.findViewById(R.id.tv_quantity);
        TextView tv_harga_barang = rootView.findViewById(R.id.tv_harga_barang);
        TextView tv_tujuan = rootView.findViewById(R.id.tv_tujuan);
        TextView tv_ongkir = rootView.findViewById(R.id.tv_onkir);
        TextView tv_resi = rootView.findViewById(R.id.tv_resi);
        TextView tv_status = rootView.findViewById(R.id.tv_status);

        tv_nama_barang.setText(nama_barang.get(i));
        tv_nama_toko.setText(nama_toko.get(i));
        tv_quantity.setText(String.valueOf(quantity.get(i))+" Kg");
        tv_harga_barang.setText("Rp "+String.valueOf(harga_barang.get(i)));
        tv_ongkir.setText(ongkir.get(i));
        tv_resi.setText(no_resi.get(i));
        tv_status.setText(status_pengiriman.get(i));
        tv_tujuan.setText(address_tujuan.get(i));

        ImageView iv_delete = rootView.findViewById(R.id.iv_delete);
        iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dr_delete.child("User").child(user.getUid()).child("Transaksi").child(id_item.get(urut)).removeValue();
            }
        });
        return rootView;
    }
}

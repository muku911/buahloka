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

public class KeranjangFragment extends Fragment{

    public KeranjangFragment() {
        // Required empty public constructor
    }

    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    FirebaseUser user = firebaseAuth.getCurrentUser();

    ListView lv_item_keranjang;
    TextView tv_harga_total;

    List<String> nama_barang = new ArrayList<>();
    List<String> nama_toko = new ArrayList<>();
    List<Integer> quantity = new ArrayList<>();
    List<Integer> harga_barang = new ArrayList<>();

    List<IdBarang> id_barang = new ArrayList<IdBarang>();
    List<Barang> l_barang = new ArrayList<Barang>();
    List<String> id_item = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_keranjang, container, false);

        lv_item_keranjang = view.findViewById(R.id.lv_keranjang);

        tv_harga_total = view.findViewById(R.id.tv_harga_total);

        setItem();

        return view;
    }

    public void setItem() {
//        nama_barang.add(0,"Jeruk");
//        nama_barang.add(1,"Jambu");
//
//        nama_toko.add(0,"Toko Bunda");
//        nama_toko.add(1,"Toko Bapak");
//
//        quantity.add(0,5);
//        quantity.add(1,10);
//
//        harga_barang.add(0,100000);
//        harga_barang.add(1,200000);
        DatabaseReference dr_item = database.getReference();
        dr_item.child("User").child(user.getUid()).child("Keranjang").child("Item").addValueEventListener(new ValueEventListener() {
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

                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    IdBarang id = data.getValue(IdBarang.class);

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

                    tv_harga_total.setText("Rp. " + String.valueOf(hartot));
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
                            Barang barang = data.getValue(Barang.class);

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
        KeranjangAdapter keranjangAdapter = new KeranjangAdapter(getActivity(),id_item,nama_barang,nama_toko,quantity,harga_barang);
        lv_item_keranjang.setAdapter(keranjangAdapter);
    }
}

class Barang{
    private String name, id_toko;

    public Barang(){

    }

    public Barang(String name, String id_toko){
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

class IdBarang{
    private String id_barang,id_item;
    private int quantity, harga;

    public IdBarang(){

    }

    public IdBarang(String id_barang, int qty, int harga){
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

class KeranjangAdapter extends BaseAdapter{

    Context context;
    List<String> nama_barang,nama_toko;
    List<Integer> quantity,harga_barang;
    List<String> id_item;
    LayoutInflater inflater;

    public KeranjangAdapter(Context context,List<String> id_item, List<String> nama_barang, List<String> nama_toko, List<Integer> quantity, List<Integer> harga_barang){
        this.context = context;
        this.id_item = id_item;
        this.nama_barang = nama_barang;
        this.nama_toko = nama_toko;
        this.quantity = quantity;
        this.harga_barang = harga_barang;
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

        tv_nama_barang.setText(nama_barang.get(i));
        tv_nama_toko.setText(nama_toko.get(i));
        tv_quantity.setText(String.valueOf(quantity.get(i))+" Kg");
        tv_harga_barang.setText("Rp "+String.valueOf(harga_barang.get(i)));

        ImageView iv_delete = rootView.findViewById(R.id.iv_delete);
        iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dr_delete.child("User").child(user.getUid()).child("Keranjang").child("Item").child(id_item.get(urut)).removeValue();
            }
        });
        return rootView;
    }
}

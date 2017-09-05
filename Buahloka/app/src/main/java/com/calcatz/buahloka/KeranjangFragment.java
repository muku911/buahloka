package com.calcatz.buahloka;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
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

    ArrayList<String> nama_barang = new ArrayList<>();
    ArrayList<String> nama_toko = new ArrayList<>();
    ArrayList<Integer> quantity = new ArrayList<>();
    ArrayList<Integer> harga_barang = new ArrayList<>();

    ArrayList<String> id_barang = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_keranjang, container, false);

        lv_item_keranjang = view.findViewById(R.id.lv_keranjang);

        setValue();

        KeranjangAdapter keranjangAdapter = new KeranjangAdapter(getActivity(),nama_barang,nama_toko,quantity,harga_barang);
        lv_item_keranjang.setAdapter(keranjangAdapter);

        return view;
    }

    private void setValue(){
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
        DatabaseReference dr_nama_barang = database.getReference();
        dr_nama_barang.child("User").child(user.getUid()).child("Keranjang").child("Item").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()){
                    //id_barang.add();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}


class KeranjangAdapter extends BaseAdapter{

    Context context;
    ArrayList<String> nama_barang,nama_toko;
    ArrayList<Integer> quantity,harga_barang;
    LayoutInflater inflater;

    public KeranjangAdapter(Context context, ArrayList<String> nama_barang, ArrayList<String> nama_toko, ArrayList<Integer> quantity, ArrayList<Integer> harga_barang){
        this.context = context;
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
        View rootView = inflater.inflate(R.layout.keranjang_list_row,viewGroup,false);

        TextView tv_nama_barang = rootView.findViewById(R.id.tv_nama_barang);
        TextView tv_nama_toko = rootView.findViewById(R.id.tv_nama_toko);
        TextView tv_quantity = rootView.findViewById(R.id.tv_quantity);
        TextView tv_harga_barang = rootView.findViewById(R.id.tv_harga_barang);

        tv_nama_barang.setText(nama_barang.get(i));
        tv_nama_toko.setText(nama_toko.get(i));
        tv_quantity.setText(String.valueOf(quantity.get(i))+" Kg");
        tv_harga_barang.setText("Rp "+String.valueOf(harga_barang.get(i)));
        return rootView;
    }
}

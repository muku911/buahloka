package com.calcatz.buahloka;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
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

public class FavoritFragment extends Fragment{

    public FavoritFragment() {
        // Required empty public constructor
    }

    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    FirebaseUser user = firebaseAuth.getCurrentUser();

    ListView lv_item_favorit;

    List<String> nama_barang = new ArrayList<>();
    List<String> nama_toko = new ArrayList<>();
    List<Integer> harga_barang = new ArrayList<>();

    List<IdBarang> id_barang = new ArrayList<IdBarang>();
    List<Barang> l_barang = new ArrayList<Barang>();
    List<String> id_item = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorit, container, false);
        // Inflate the layout for this fragment

        lv_item_favorit = view.findViewById(R.id.lv_favorit);

        setItem();

        return view;
    }

    public void setItem() {
        DatabaseReference dr_item = database.getReference();
        dr_item.child("User").child(user.getUid()).child("Favorite").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(id_barang!=null){
                    id_barang.clear();
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
                    int hrg = id_barang.get(i).getHarga();
                    String id = id_barang.get(i).getId();

                    id_item.add(id);
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
        FavoriteAdapter favoriteAdapter = new FavoriteAdapter(getActivity(),id_item,nama_barang,nama_toko,harga_barang);
        lv_item_favorit.setAdapter(favoriteAdapter);
    }
}

class FavoriteAdapter extends BaseAdapter {

    Context context;
    List<String> nama_barang,nama_toko;
    List<Integer> harga_barang;
    List<String> id_item;
    LayoutInflater inflater;

    public FavoriteAdapter(Context context,List<String> id_item, List<String> nama_barang, List<String> nama_toko, List<Integer> harga_barang){
        this.context = context;
        this.id_item = id_item;
        this.nama_barang = nama_barang;
        this.nama_toko = nama_toko;
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
        View rootView = inflater.inflate(R.layout.favorit_list_row,viewGroup,false);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = firebaseAuth.getCurrentUser();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference dr_delete = database.getReference();

        TextView tv_nama_barang = rootView.findViewById(R.id.tv_nama_barang);
        TextView tv_nama_toko = rootView.findViewById(R.id.tv_nama_toko);
        TextView tv_harga_barang = rootView.findViewById(R.id.tv_harga_barang);

        tv_nama_barang.setText(nama_barang.get(i));
        tv_nama_toko.setText(nama_toko.get(i));
        tv_harga_barang.setText("Rp "+String.valueOf(harga_barang.get(i)));

        ImageView iv_delete = rootView.findViewById(R.id.iv_delete);
        iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dr_delete.child("User").child(user.getUid()).child("Favorite").child(id_item.get(urut)).removeValue();
            }
        });
        return rootView;
    }
}

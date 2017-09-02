package com.calcatz.buahloka;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

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

public class BerandaFragment extends Fragment{

    //firebase
    private FirebaseDatabase mydatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseProfinsi = mydatabase.getReference();
    private DatabaseReference databaseBuah = mydatabase.getReference();

    //UI
    private Spinner spinner_provinsi;
    private GridView gv_buah;

    //Data
    private NamaProvinsi namaProvinsi ;
    private NamaBuah namaBuah;

    private List<NamaProvinsi> nameprofinsi = new ArrayList<NamaProvinsi>();
    private List<NamaBuah> namebuah = new ArrayList<NamaBuah>();

    private NamabuahViewAdapter adapter;

    private List<String> daftarProfinsi = new ArrayList<String>();

    private String jenisBuah, pilihanProvinsi;


    public BerandaFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_beranda, container, false);

        //INIT UI
        spinner_provinsi = (Spinner)rootView.findViewById(R.id.spinner_provinsiAsal_beranda);
        gv_buah = (GridView)rootView.findViewById(R.id.gv_jenisBuah);

        //Click
        gv_buah.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                jenisBuah = namebuah.get(i).getName();
                pilihanProvinsi = spinner_provinsi.getSelectedItem().toString();
                Bundle bundle = new Bundle();
                bundle.putString("jenisbuah", jenisBuah);
                bundle.putString("pilihanprovinsi", pilihanProvinsi);
                Intent gotoListBuah = new Intent(getActivity(), ListBuahActivity.class);
                gotoListBuah.putExtras(bundle);
                startActivity(gotoListBuah);
            }
        });


        //Firebase
        databaseProfinsi.child("Region").child("Provinsi").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (nameprofinsi != null)
                    nameprofinsi.clear();
                if (daftarProfinsi != null)
                    daftarProfinsi.clear();

                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    namaProvinsi = snapshot.getValue(NamaProvinsi.class);
                    nameprofinsi.add(namaProvinsi);
                }
                for (int a = 0 ; a < nameprofinsi.size() ; a++){
                    String b = nameprofinsi.get(a).getName();
                    daftarProfinsi.add(b);
                }
                ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item,daftarProfinsi);
                spinner_provinsi.setAdapter(adapter2);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        databaseBuah.child("Buah").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (namebuah != null)
                    namebuah.clear();

                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    namaBuah = snapshot.getValue(NamaBuah.class);
                    namebuah.add(namaBuah);
                }
                adapter = new NamabuahViewAdapter( getContext() , namebuah);
                gv_buah.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        return rootView;
    }

}

class NamaProvinsi{
    private String name;

    public NamaProvinsi(String name) {
        this.name = name;
    }

    public NamaProvinsi() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

class NamaBuah{
    private String name;

    public NamaBuah() {
    }

    public NamaBuah(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

class NamabuahViewAdapter extends BaseAdapter{

    private Context mContext;

    private List<NamaBuah> namaBuah ;

    private ArrayAdapter<NamaBuah> arraylist;
    private LayoutInflater inflater;

    public NamabuahViewAdapter(Context mContext, List<NamaBuah> namaBuah) {
        this.mContext = mContext;
        this.namaBuah = namaBuah;
    }


    @Override
    public int getCount() {
        return namaBuah.size();
    }

    @Override
    public Object getItem(int i) {
        return namaBuah.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View itemView = view;


        if (view == null){
            inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            itemView = inflater.inflate(R.layout.items_beranda, null);
        }
        TextView jenisBuah = (TextView)itemView.findViewById(R.id.tv_itemBeranda);
        ImageView gambarBuah = (ImageView)itemView.findViewById(R.id.img_itemBeranda);


        jenisBuah.setText(namaBuah.get(i).getName());



        return itemView;
    }
}
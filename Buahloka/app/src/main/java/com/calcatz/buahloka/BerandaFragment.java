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
import android.widget.Toast;

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
    private TextView tx_provinsiAsal_beranda;

    //Data
    private NamaProvinsi namaProvinsi ;
    private NamaBuah namaBuah;

    private List<NamaProvinsi> nameprofinsi = new ArrayList<NamaProvinsi>();
    private List<NamaBuah> namebuah = new ArrayList<NamaBuah>();

    private NamabuahViewAdapter adapter;

    private List<String> daftarProfinsi = new ArrayList<String>();
    private List<String> daftarProfinsiID = new ArrayList<String>();

    private String jenisBuah, pilihanProvinsi, regional;


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
        tx_provinsiAsal_beranda = (TextView) rootView.findViewById(R.id.tx_provinsiAsal_beranda);

        //Click
        gv_buah.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if ( spinner_provinsi.getSelectedItemPosition() != 0){
                    jenisBuah = namebuah.get(i).getName();
                    pilihanProvinsi = spinner_provinsi.getSelectedItem().toString();
                    int tempRegional = spinner_provinsi.getSelectedItemPosition();
                    regional = daftarProfinsiID.get(tempRegional);
                    Bundle bundle = new Bundle();
                    String id = "";
                    for (int noob = 0; noob < namebuah.size() ; noob++){
                        String temp = namebuah.get(noob).getName();
                        if (temp.equals(jenisBuah)){
                            noob++;
                            id = ""+noob;
                        }
                    }
                    bundle.putString("id", id);
                    bundle.putString("jenisbuah", jenisBuah);
                    bundle.putString("pilihanprovinsi", pilihanProvinsi);
                    bundle.putString("regional", regional);
                    Intent gotoListBuah = new Intent(getActivity(), ListBuahActivity.class);
                    gotoListBuah.putExtras(bundle);
                    startActivity(gotoListBuah);
                }else Toast.makeText(getActivity(), "Pilih Provinsi", Toast.LENGTH_SHORT).show();
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

                    String id = snapshot.getKey();
                    daftarProfinsiID.add(id);

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

        if (namaBuah.get(i).getName().equals("Alpukat"))
            gambarBuah.setImageResource(R.drawable.avocado);
        else if (namaBuah.get(i).getName().equals("Anggur"))
            gambarBuah.setImageResource(R.drawable.grapes);
        else if (namaBuah.get(i).getName().equals("Apel"))
            gambarBuah.setImageResource(R.drawable.apple);
        else if (namaBuah.get(i).getName().equals("Belimbing"))
            gambarBuah.setImageResource(R.drawable.starfruit);
        else if (namaBuah.get(i).getName().equals("Bengkuang"))
            gambarBuah.setImageResource(R.drawable.bengkoang);
        else if (namaBuah.get(i).getName().equals("Blewah"))
            gambarBuah.setImageResource(R.drawable.cantaloupe);
        else if (namaBuah.get(i).getName().equals("Duku"))
            gambarBuah.setImageResource(R.drawable.duku);
        else if (namaBuah.get(i).getName().equals("Durian"))
            gambarBuah.setImageResource(R.drawable.durian);
        else if (namaBuah.get(i).getName().equals("Jambu"))
            gambarBuah.setImageResource(R.drawable.jambu);
        else if (namaBuah.get(i).getName().equals("Jeruk"))
            gambarBuah.setImageResource(R.drawable.img_jeruk);
        else if (namaBuah.get(i).getName().equals("Kelapa"))
            gambarBuah.setImageResource(R.drawable.coconut);
        else if (namaBuah.get(i).getName().equals("Kelengkeng"))
            gambarBuah.setImageResource(R.drawable.duku);
        else if (namaBuah.get(i).getName().equals("Kiwi"))
            gambarBuah.setImageResource(R.drawable.kiwi);
        else if (namaBuah.get(i).getName().equals("Kurma"))
            gambarBuah.setImageResource(R.drawable.kurma);
        else if (namaBuah.get(i).getName().equals("Lemon"))
            gambarBuah.setImageResource(R.drawable.lemon);
        else if (namaBuah.get(i).getName().equals("Leci"))
            gambarBuah.setImageResource(R.drawable.leci);
        else if (namaBuah.get(i).getName().equals("Labu"))
            gambarBuah.setImageResource(R.drawable.pumpkin);
        else if (namaBuah.get(i).getName().equals("Mangga"))
            gambarBuah.setImageResource(R.drawable.mangga);
        else if (namaBuah.get(i).getName().equals("Manggis"))
            gambarBuah.setImageResource(R.drawable.mangosteen);
        else if (namaBuah.get(i).getName().equals("Markisa"))
            gambarBuah.setImageResource(R.drawable.markisa);
        else if (namaBuah.get(i).getName().equals("Melon"))
            gambarBuah.setImageResource(R.drawable.melon);
        else if (namaBuah.get(i).getName().equals("Nanas"))
            gambarBuah.setImageResource(R.drawable.pineapple);
        else if (namaBuah.get(i).getName().equals("Nangka"))
            gambarBuah.setImageResource(R.drawable.nangka);
        else if (namaBuah.get(i).getName().equals("Naga"))
            gambarBuah.setImageResource(R.drawable.buahnaga);
        else if (namaBuah.get(i).getName().equals("Pepaya"))
            gambarBuah.setImageResource(R.drawable.papaya);
        else if (namaBuah.get(i).getName().equals("Pir"))
            gambarBuah.setImageResource(R.drawable.pear);
        else if (namaBuah.get(i).getName().equals("Pisang"))
            gambarBuah.setImageResource(R.drawable.banana);
        else if (namaBuah.get(i).getName().equals("Rambutan"))
            gambarBuah.setImageResource(R.drawable.rambutan);
        else if (namaBuah.get(i).getName().equals("Salak"))
            gambarBuah.setImageResource(R.drawable.salak);
        else if (namaBuah.get(i).getName().equals("Sawo"))
            gambarBuah.setImageResource(R.drawable.sawo);
        else if (namaBuah.get(i).getName().equals("Semangka"))
            gambarBuah.setImageResource(R.drawable.watermelon);
        else if (namaBuah.get(i).getName().equals("Sirsak"))
            gambarBuah.setImageResource(R.drawable.sirsak);
        else if (namaBuah.get(i).getName().equals("Srikaya"))
            gambarBuah.setImageResource(R.drawable.srikaya);
        else if (namaBuah.get(i).getName().equals("Strawberry"))
            gambarBuah.setImageResource(R.drawable.strawberry);
        else if (namaBuah.get(i).getName().equals("Tomat"))
            gambarBuah.setImageResource(R.drawable.tomato);
        jenisBuah.setText(namaBuah.get(i).getName());


        return itemView;
    }
}
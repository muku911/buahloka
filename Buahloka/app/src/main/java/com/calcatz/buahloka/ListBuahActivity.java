package com.calcatz.buahloka;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

public class ListBuahActivity extends AppCompatActivity {

    //Firebase
    private FirebaseDatabase mydatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseDaerah = mydatabase.getReference();
    private DatabaseReference databaseKebun = mydatabase.getReference();
    private DatabaseReference databaseProvinsi  = mydatabase.getReference();

    //UI
    private Spinner spinner_daerah, spinner_sortby;
    private ImageView img_logoBuah;
    private TextView tv_provinsiAsal,tx_tersedia;
    private GridView gv_daftarKebun;

    //Data
    private String pilihanBuah, pilihanProvinsi, regional, id, tempSpiner;
    private boolean find, show;

    private String namaKebun;
    private long hargaKilo;

    private DaftarKebun daftarKebun;
    private HargaBarang hargaBarang ;
    private DaftarDaerah daftarDaerah;

    private List<DaftarKebun> kebunList = new ArrayList<DaftarKebun>();
    private List<HargaBarang> hargaList = new ArrayList<HargaBarang>();
    private List<DaftarDaerah> namaDaerah = new ArrayList<DaftarDaerah>();
    private List<String> listdaftarDaerah = new ArrayList<String>();

    private DaftarKebunViewAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_buah);

        headerView();
        init();

        //test
        //tx_tersedia.setText(id);

        //Action
        tv_provinsiAsal.setText(pilihanProvinsi);

        switch (pilihanBuah){
            case "Jeruk":
                img_logoBuah.setImageResource(R.drawable.img_jeruk);
                break;
            case "Pisang":
                img_logoBuah.setImageResource(R.drawable.img_pisang);

                break;
            case "Apel":
                img_logoBuah.setImageResource(R.drawable.img_apel);
                break;
        }



        //Click
        gv_daftarKebun.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String pilihkebun = kebunList.get(i).getName();
                String pilihankKebun = hargaList.get(i).getId();
                //Toast.makeText(ListBuahActivity.this, "Welcome " + pilihkebun, Toast.LENGTH_SHORT).show();
                Bundle bundle = new Bundle();
                bundle.putString("pilihankebun", pilihkebun);
                bundle.putString("idkebun", pilihankKebun);
                bundle.putString("pilihanBuah", pilihanBuah);
                Intent gotoListBuah = new Intent(ListBuahActivity.this, DetailKebunActivity.class);
                gotoListBuah.putExtras(bundle);
                startActivity(gotoListBuah);
            }
        });

        //firebase Data regional

        databaseDaerah.child("Region").child("Provinsi").child(regional).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (namaDaerah != null)
                    namaDaerah.clear();
                if (listdaftarDaerah != null)
                    listdaftarDaerah.clear();


                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    String temp = snapshot.child("name").getValue(String.class);
                    if (temp != null)
                        listdaftarDaerah.add(temp);
                    //daftarDaerah = snapshot.child("name").getValue(DaftarDaerah.class);
                    //namaDaerah.add(daftarDaerah);

                }
                for (int a = 0; a < namaDaerah.size(); a++) {
                    String b = namaDaerah.get(a).getName();
                    listdaftarDaerah.add(b);
                }

                ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(ListBuahActivity.this, android.R.layout.simple_spinner_dropdown_item, listdaftarDaerah);
                spinner_daerah.setAdapter(adapter2);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //firebaseCheckIDbuah
        spinner_daerah.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                tempSpiner = adapterView.getSelectedItem().toString();

                show = false;

                if (kebunList != null)
                    kebunList.clear();
                if (hargaList != null)
                    hargaList.clear();

                adapter = new DaftarKebunViewAdapter(ListBuahActivity.this, kebunList, hargaList);
                gv_daftarKebun.setAdapter(adapter);

                databaseProvinsi.child("Barang").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                            String tempSubdic = snapshot.child("city").getValue(String.class);
                            if (tempSubdic.equals(tempSpiner)) show = true;
                        }

                        if (show) {

                            databaseDaerah.child("Barang").addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {



                                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                                        long temp = snapshot.child("id_buah").getValue(long.class);
                                        long idtoLong = Long.parseLong(id);

                                        if (temp == idtoLong) {
                                            hargaKilo = snapshot.child("harga_kilo").getValue(long.class);
                                            String idBarang = snapshot.child("id").getValue(String.class);
                                            hargaBarang = new HargaBarang(hargaKilo,idBarang);
                                            hargaList.add(hargaBarang);
                                            final String tempToko = snapshot.child("id_toko").getValue(String.class);
                                            String tempKebun = snapshot.child("id_kebun").getValue(String.class);
                                            databaseKebun.child("Toko").child(tempToko).child("Kebun").child(tempKebun).addValueEventListener(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(DataSnapshot dataSnapshot) {

                                                    namaKebun = dataSnapshot.child("name").getValue(String.class);


                                                    daftarKebun = new DaftarKebun(namaKebun);
                                                    kebunList.add(daftarKebun);
                                                    int sum = kebunList.size();
                                                    for (int a = 0; a < sum; a++) {
                                                        adapter = new DaftarKebunViewAdapter(ListBuahActivity.this, kebunList, hargaList);
                                                        gv_daftarKebun.setAdapter(adapter);

                                                    }
                                                }

                                                @Override
                                                public void onCancelled(DatabaseError databaseError) {

                                                }
                                            });

                                        }
                                    }

                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



    }

    private void init() {
        spinner_daerah = (Spinner)findViewById(R.id.spinnerDaerah);
        spinner_sortby = (Spinner)findViewById(R.id.spinnerSortby);
        tx_tersedia =  (TextView)findViewById(R.id.tx_tersedia);
        img_logoBuah = (ImageView)findViewById(R.id.img_logoBuah);

        tv_provinsiAsal = (TextView)findViewById(R.id.tx_provinsi);

        gv_daftarKebun = (GridView)findViewById(R.id.gv_data);
    }

    private void headerView() {
        //LoadBundle
        Bundle bundle;
        bundle = getIntent().getExtras();
        pilihanBuah = bundle.getString("jenisbuah");
        pilihanProvinsi = bundle.getString("pilihanprovinsi");
        regional  = bundle.getString("regional");
        id = bundle.getString("id");


    }
}

class HargaBarang{
    private long harga_kilo;
    private String id;

    public HargaBarang() {
    }

    public HargaBarang(long harga_kilo, String id) {
        this.harga_kilo = harga_kilo;
        this.id = id;
    }

    public long getHarga_kilo() {
        return harga_kilo;
    }

    public void setHarga_kilo(long harga_kilo) {
        this.harga_kilo = harga_kilo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

class DaftarKebun{
    String name;

    public DaftarKebun() {
    }

    public DaftarKebun(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

class DaftarKebunViewAdapter extends BaseAdapter {

    private Context mContext;
    private Activity activity;

    private List<DaftarKebun> kebunList;
    private List<HargaBarang> hargaList;


    private ArrayAdapter<DaftarKebun> arraylist;
    private LayoutInflater inflater;

    public DaftarKebunViewAdapter(Activity activity, List<DaftarKebun> kebunList, List<HargaBarang> hargaList) {
        this.activity = activity;
        this.kebunList = kebunList;
        this.hargaList = hargaList;
    }

    @Override
    public int getCount() {
        return kebunList.size();
    }

    @Override
    public Object getItem(int i) {
        return kebunList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        inflater  = (LayoutInflater)activity.getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.items_list_buah, null);

        TextView tx_nameShop = (TextView)itemView.findViewById(R.id.tx_nameShop);
        TextView tx_priceItem = (TextView)itemView.findViewById(R.id.tx_price);
        ImageView gambarBuah = (ImageView)itemView.findViewById(R.id.img_itemShop);


        tx_priceItem.setText("Rp. "+ hargaList.get(i).getHarga_kilo());

        tx_nameShop.setText(kebunList.get(i).getName());
        gambarBuah.setImageResource(R.drawable.img_apel);

        return itemView;
    }
}

class DaftarDaerah{
    private String name;

    public DaftarDaerah(String name) {
        this.name = name;
    }

    public DaftarDaerah() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
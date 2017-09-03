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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
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

    //UI
    private Spinner spinner_daerah, spinner_sortby;
    private ImageView img_logoBuah;
    private TextView tv_provinsiAsal,tx_tersedia;
    private GridView gv_daftarKebun;

    //Data
    private String pilihanBuah, pilihanProvinsi, regional;
    private boolean find;

    private DaftarKebun daftarKebun;
    private DaftarDaerah daftarDaerah;

    private List<DaftarKebun> kebunList = new ArrayList<DaftarKebun>();
    private List<DaftarDaerah> namaDaerah = new ArrayList<DaftarDaerah>();
    private List<String> listdaftarDaerah = new ArrayList<String>();

    private DaftarKebunViewAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_buah);

        headerView();
        init();
        dataoffline();

        //tx_tersedia.setText(regional);

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

        int sum = kebunList.size();

        for (int a = 0 ; a < sum ; a++){
            adapter = new DaftarKebunViewAdapter(ListBuahActivity.this,kebunList);
            gv_daftarKebun.setAdapter(adapter);

        }

        //Click
        gv_daftarKebun.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String pilihkebun = kebunList.get(i).getName();
                Bundle bundle = new Bundle();
                bundle.putString("pilihankebun", pilihkebun);
                bundle.putString("pilihanBuah", pilihanBuah);
                Intent gotoListBuah = new Intent(ListBuahActivity.this, DetailKebunActivity.class);
                gotoListBuah.putExtras(bundle);
                startActivity(gotoListBuah);
            }
        });


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


    }

    private void dataoffline() {
        //Toko
        daftarKebun = new DaftarKebun("Kebun Joko","50000");
        kebunList.add(daftarKebun);

        daftarKebun = new DaftarKebun("Kebun Joko","5000000");
        kebunList.add(daftarKebun);

        daftarKebun = new DaftarKebun("Kebun Joko","250000");
        kebunList.add(daftarKebun);

        daftarKebun = new DaftarKebun("Kebun Joko","5000");
        kebunList.add(daftarKebun);

        daftarKebun = new DaftarKebun("Kebun Joko","500000");
        kebunList.add(daftarKebun);

        daftarKebun = new DaftarKebun("Kebun Joko","59000");
        kebunList.add(daftarKebun);

        daftarKebun = new DaftarKebun("Kebun Joko","58000");
        kebunList.add(daftarKebun);

        daftarKebun = new DaftarKebun("Kebun Joko","58000");
        kebunList.add(daftarKebun);

        daftarKebun = new DaftarKebun("Kebun Joko","58000");
        kebunList.add(daftarKebun);

        daftarKebun = new DaftarKebun("Kebun Joko","58000");
        kebunList.add(daftarKebun);

        daftarKebun = new DaftarKebun("Kebun Joko","58000");
        kebunList.add(daftarKebun);

        daftarKebun = new DaftarKebun("Kebun Joko","58000");
        kebunList.add(daftarKebun);

        daftarKebun = new DaftarKebun("Kebun Joko","58000");
        kebunList.add(daftarKebun);

        daftarKebun = new DaftarKebun("Kebun Joko","58000");
        kebunList.add(daftarKebun);

        daftarKebun = new DaftarKebun("Kebun Joko","58000");
        kebunList.add(daftarKebun);

        daftarKebun = new DaftarKebun("Kebun Joko","58000");
        kebunList.add(daftarKebun);

        daftarKebun = new DaftarKebun("Kebun Joko","58000");
        kebunList.add(daftarKebun);

        daftarKebun = new DaftarKebun("Kebun Joko","58000");
        kebunList.add(daftarKebun);

        daftarKebun = new DaftarKebun("Kebun Joko","58000");
        kebunList.add(daftarKebun);

        daftarKebun = new DaftarKebun("Kebun Joko","58000");
        kebunList.add(daftarKebun);

        daftarKebun = new DaftarKebun("Kebun Joko","58000");
        kebunList.add(daftarKebun);

        daftarKebun = new DaftarKebun("Kebun Joko","58000");
        kebunList.add(daftarKebun);
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


    }
}



class DaftarKebun{
    String name, price;

    public DaftarKebun() {
    }

    public DaftarKebun(String name, String price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}

class DaftarKebunViewAdapter extends BaseAdapter {

    private Context mContext;
    private Activity activity;

    private List<DaftarKebun> kebunList;

    private ArrayAdapter<DaftarKebun> arraylist;
    private LayoutInflater inflater;

    public DaftarKebunViewAdapter(Activity activity, List<DaftarKebun> kebunList) {
        this.activity = activity;
        this.kebunList = kebunList;
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

        tx_nameShop.setText(kebunList.get(i).getName());
        tx_priceItem.setText("Rp. "+kebunList.get(i).getPrice());
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
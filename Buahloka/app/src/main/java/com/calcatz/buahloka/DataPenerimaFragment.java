package com.calcatz.buahloka;


import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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
import java.util.UUID;


/**
 * A simple {@link Fragment} subclass.
 */
public class DataPenerimaFragment extends Fragment {


    public DataPenerimaFragment() {
        // Required empty public constructor
    }

    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    FirebaseUser user = firebaseAuth.getCurrentUser();

    private NamaProvinsi namaProvinsi ;
    private DaftarDaerah daftarDaerah;

    private List<NamaProvinsi> nameprofinsi = new ArrayList<NamaProvinsi>();
    private List<String> nama_kota = new ArrayList<>();
    private List<String> daftarProfinsi = new ArrayList<String>();
    private List<String> id_province = new ArrayList<>();
    List<String> id_item = new ArrayList<>();
    List<IdBarang> id_barang = new ArrayList<IdBarang>();
    List<String> nama_barang = new ArrayList<>();
    List<String> id_toko = new ArrayList<>();
    List<Barang> l_barang = new ArrayList<Barang>();
    List<Integer> harga_barang = new ArrayList<>();
    List<Integer> quantity = new ArrayList<>();
    List<String> id_for_barang = new ArrayList<>();

    EditText et_nama_penerima, et_alamat_penerima;
    Spinner spnr_province,spnr_city;

    String province, city;
    int hartot;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_data_penerima, container, false);

        final Bundle bundle = this.getArguments();
        if(bundle!= null){
            hartot = bundle.getInt("harga");
        }

        et_nama_penerima = view.findViewById(R.id.et_nama_penerima);
        et_alamat_penerima = view.findViewById(R.id.et_alamat_tujuan);

        spnr_province = view.findViewById(R.id.spnr_province);
        spnr_city = view.findViewById(R.id.spnr_city);

        DatabaseReference databaseProfinsi = database.getReference();
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
                    id_province.add(id);
                }
                for (int a = 0 ; a < nameprofinsi.size() ; a++){
                    String b = nameprofinsi.get(a).getName();
                    daftarProfinsi.add(b);
                }
                ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item,daftarProfinsi);
                spnr_province.setAdapter(adapter2);
                spnr_province.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        getCity(id_province.get(i));
                        province = daftarProfinsi.get(i);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        DatabaseReference dr_item = database.getReference();
        dr_item.child("User").child(user.getUid()).child("Keranjang").child("Item").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(id_item!=null){
                    id_item.clear();
                }

                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    IdBarang idBarang = data.getValue(IdBarang.class);
                    String barang = idBarang.getId_barang();
                    String id = data.child("id").getValue(String.class);
                    int harga = idBarang.getHarga();
                    int qty = idBarang.getQuantity();

                    id_for_barang.add(barang);
                    quantity.add(qty);
                    harga_barang.add(harga);
                    id_barang.add(idBarang);
                    id_item.add(id);
                }

                setBarang();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

//        Button button = view.findViewById(R.id.btn_terima);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String alamaat = et_alamat_penerima.getText().toString();
//                String nama = et_nama_penerima.getText().toString();
//
//                List<String> id = new ArrayList<String>();
//                for (int i = 0; i<id_item.size(); i++){
//                    String idTransaksi = UUID.randomUUID().toString();
//                    id.add(idTransaksi);
//                    TransaksiUser transaksiUser = new TransaksiUser(idTransaksi,alamaat,city,id_toko.get(i),nama,province,harga_barang.get(i),"Dipesankan");
//                    IdBarang idBarang = new IdBarang(id_item.get(i),id_for_barang.get(i),quantity.get(i),harga_barang.get(i),);
//                    DatabaseReference dr_post_transaksi = database.getReference();
//                    dr_post_transaksi.child("User").child(user.getUid()).child("Transaksi").child(idTransaksi).setValue(transaksiUser);
//                    dr_post_transaksi.child("User").child(user.getUid()).child("Transaksi").child(idTransaksi).child(id_item.get(i)).setValue(idBarang);
//                    dr_post_transaksi.child("User").child(user.getUid()).child("Transaksi").child(idTransaksi).child(id_item.get(i)).removeValue();
//                }
//
//                for (int i = 0; i<id_item.size(); i++){
//                    DatabaseReference dr_post_transaksi = database.getReference();
//                    dr_post_transaksi.child("User").child(user.getUid()).child("Keranjang").child("Item").child(id_item.get(i)).removeValue();
//                }
//
//                BerandaFragment berandaFragment = new BerandaFragment();
//                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
//                fragmentTransaction.replace(R.id.fragment_view,berandaFragment);
//                fragmentTransaction.commit();
//            }
//        });

        return view;
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
                            String idToko = data.child("id_toko").getValue(String.class);

                            id_toko.add(idToko);
                            l_barang.add(barang);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void getCity(final String id_province){
        DatabaseReference dr_get_city = database.getReference();
        dr_get_city.child("Region").child("Provinsi").child(id_province).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(nama_kota!=null){
                    nama_kota.clear();
                }

                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    if(id_province.equals("0") || snapshot.getKey().equals("name")){
                        break;
                    }
                    String nama = snapshot.child("name").getValue(String.class);

                    nama_kota.add(nama);
                }

                ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item,nama_kota);
                spnr_city.setAdapter(adapter2);
                spnr_city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        city = nama_kota.get(i);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}

class TransaksiUser{
    List<String> id_item;
    String address_tujuan,city,id_toko,nama_penerima,province,status_pengiriman = "Dipesankan",id;
    int total_harga;
    long imgsource;

    public TransaksiUser(){

    }

    public TransaksiUser(String id, String address_tujuan, String city, String id_toko, String nama_penerima, String province, int total_harga, String status_pengiriman, long img){
        this.id = id;
        this.address_tujuan = address_tujuan;
        this.city = city;
        this.id_toko = id_toko;
        this.nama_penerima = nama_penerima;
        this.province = province;
        this.total_harga = total_harga;
        this.status_pengiriman = status_pengiriman;
        this.imgsource = img;
    }

    public String getAddress_tujuan(){
        return address_tujuan;
    }

    public String getCity(){
        return city;
    }

    public String getId_toko(){
        return id_toko;
    }

    public String getNama_penerima(){
        return nama_penerima;
    }

    public String getProvince(){
        return province;
    }

    public String getStatus_pengiriman(){
        return status_pengiriman;
    }

    public long getImgsource(){
        return imgsource;
    }

    public int getTotal_harga(){
        return total_harga;
    }

}
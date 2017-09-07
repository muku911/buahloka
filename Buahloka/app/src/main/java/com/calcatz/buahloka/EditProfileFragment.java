package com.calcatz.buahloka;


import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class EditProfileFragment extends Fragment {

    //firebase
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseUser user = firebaseAuth.getCurrentUser();
    private FirebaseDatabase mydatabase = FirebaseDatabase.getInstance();
    private DatabaseReference userDatabase = mydatabase.getReference();

    private NamaProvinsi namaProvinsi ;
    private List<NamaProvinsi> nameprofinsi = new ArrayList<NamaProvinsi>();
    private List<String> nama_kota = new ArrayList<>();
    private List<String> daftarProfinsi = new ArrayList<String>();
    private List<String> id_province = new ArrayList<>();

    //UI
    private EditText edtx_name, edtx_address, edtx_phone;
    Spinner spnr_provinsi, spnr_kota;
    private Button btn_save_editProfile;


    boolean datachange = true;
    //String
    private String name, address, phone, saldo, provinsi, kota;

    public EditProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_edit_profile, container, false);

        //Inisialisasi
        edtx_address = (EditText)rootView.findViewById(R.id.edt_address);
        edtx_phone = (EditText)rootView.findViewById(R.id.edt_phone);
        edtx_name = (EditText)rootView.findViewById(R.id.edt_fullname);
        btn_save_editProfile = (Button)rootView.findViewById(R.id.btn_save_profile);
        spnr_provinsi = (Spinner) rootView.findViewById(R.id.spnr_provinsi);
        spnr_kota = (Spinner) rootView.findViewById(R.id.spnr_kota);

        //Firebase
        if(datachange) {
            userDatabase.child("User").child(user.getUid()).child("Profile").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    UserProfile editProfileData = dataSnapshot.getValue(UserProfile.class);

                    if (editProfileData != null) {
                        edtx_name.setText(editProfileData.getName());
                        edtx_address.setText(editProfileData.getAddress());
                        edtx_phone.setText(editProfileData.getTelp());
                        provinsi = editProfileData.getProvince();
                        kota = editProfileData.getCity();

                        getProvince();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

        btn_save_editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean booleanName = true,
                        booleanAddress = true,
                        booleanPhone = true;

                boolean cancel = false;
                View focusView =  null;

                edtx_name.setError(null);
                edtx_address.setError(null);
                edtx_phone.setError(null);

                name = edtx_name.getText().toString();
                address = edtx_address.getText().toString();
                phone = edtx_phone.getText().toString();

                if (TextUtils.isEmpty(name)) {
                    edtx_name.setError("Isi Bagian Kosong Ini");
                    focusView = edtx_name;
                    cancel = true;
                    booleanName = false;
                }
                if (TextUtils.isEmpty(address)) {
                    edtx_address.setError("Isi Bagian Kosong Ini");
                    focusView = edtx_address;
                    cancel = true;
                    booleanAddress = false;
                }
                if (TextUtils.isEmpty(phone)) {
                    edtx_phone.setError("Isi Bagian Kosong Ini");
                    focusView = edtx_phone;
                    cancel = true;
                    booleanPhone = false;
                }

                if (booleanName && booleanAddress && booleanPhone){
                    UserProfile userProfile = new UserProfile(user.getUid(),name,phone,provinsi,kota,address);
                    //EditProfileData editedProfileData = new EditProfileData(name, address, phone, saldo);

                    userDatabase.child("User").child(user.getUid()).child("Profile").setValue(userProfile);
                    Toast.makeText(getActivity(), "Data Saved", Toast.LENGTH_SHORT).show();

                    datachange = false;
                    ProfileFragment profileFragment = new ProfileFragment();
                    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.add(R.id.fragment_view,profileFragment);
                    fragmentTransaction.commit();
                }
            }
        });

        // Inflate the layout for this fragment
        return rootView;
    }

    private void getProvince(){
        userDatabase.child("Region").child("Provinsi").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int posisiProvinsi = 0;
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

                    if(b.equals(provinsi)){
                        posisiProvinsi = a;
                        //getCity(b);
                    }
                }
                ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item,daftarProfinsi);
                spnr_provinsi.setAdapter(adapter2);
                spnr_provinsi.setSelection(posisiProvinsi);
                spnr_provinsi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        getCity(id_province.get(i));
                        provinsi = daftarProfinsi.get(i);
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

    private void getCity(final String id_province){
        DatabaseReference dr_get_city = mydatabase.getReference();
        dr_get_city.child("Region").child("Provinsi").child(id_province).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int urutan = 0,posisiKota=0;
                if(nama_kota!=null){
                    nama_kota.clear();
                }

                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    if(id_province.equals("0") || snapshot.getKey().equals("name")){
                        break;
                    }
                    String nama = snapshot.child("name").getValue(String.class);

                    if(nama.equals(kota)){
                        posisiKota=urutan;
                    }
                    nama_kota.add(nama);
                    urutan++;
                }

                ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item,nama_kota);
                spnr_kota.setAdapter(adapter2);
                spnr_kota.setSelection(posisiKota);
                spnr_kota.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        kota = nama_kota.get(i);
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

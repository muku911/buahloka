package com.calcatz.buahloka;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    public ProfileFragment() {
        // Required empty public constructor
    }

    String id, nama, telp, provinsi, kota, alamat;

    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseUser user = firebaseAuth.getCurrentUser();
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    TextView nama_user, telp_user, provinsi_user, kota_user, alamat_user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container,false);

        nama_user = (TextView) view.findViewById(R.id.tv_nama_user);
        telp_user = (TextView) view.findViewById(R.id.tv_telp_user);
        provinsi_user = (TextView) view.findViewById(R.id.tv_provinsi_user);
        kota_user = (TextView) view.findViewById(R.id.tv_kota_user);
        alamat_user =(TextView) view.findViewById(R.id.tv_alamat_user);

        DatabaseReference dr_user_profile = database.getReference();
        dr_user_profile.child("User").child(user.getUid()).child("Profile").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                id = dataSnapshot.child("id").getValue(String.class);
                nama = dataSnapshot.child("name").getValue(String.class);
                telp = dataSnapshot.child("telp").getValue(String.class);
                provinsi = dataSnapshot.child("province").getValue(String.class);
                kota = dataSnapshot.child("city").getValue(String.class);
                alamat = dataSnapshot.child("address").getValue(String.class);

                nama_user.setText(nama);
                telp_user.setText(telp);
                provinsi_user.setText(provinsi);
                kota_user.setText(kota);
                alamat_user.setText(alamat);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return view;
    }

}

class UserProfile{
    String id, name, telp, province, city, address;

    public UserProfile(){

    }

    public UserProfile(String id, String name, String telp, String provinsi, String city, String address){
        this.id = id;
        this.name = name;
        this.telp = telp;
        this.province = provinsi;
        this.city = city;
        this.address = address;
    }

    public String getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public String getTelp(){
        return telp;
    }

    public String getProvince(){
        return province;
    }

    public String getCity(){
        return city;
    }

    public String getAddress(){
        return address;
    }
}

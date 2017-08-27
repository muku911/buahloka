package com.calcatz.buahloka;


import android.os.Bundle;
import android.os.StrictMode;
import android.provider.Contacts;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class EditProfileFragment extends Fragment {

    //firebase
    private FirebaseDatabase mydatabase = FirebaseDatabase.getInstance();
    private DatabaseReference userDatabase = mydatabase.getReference();

    //UI
    private EditText edtx_name, edtx_address, edtx_phone;

    //String
    private String name, address, phone;

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

        //Firebase
        userDatabase.child("userData").child("putinvladimir").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                EditProfileData editProfileData = dataSnapshot.getValue(EditProfileData.class);

                if (editProfileData != null){
                    edtx_name.setText(editProfileData.getName());
                    edtx_address.setText(editProfileData.getAddress());
                    edtx_phone.setText(editProfileData.getPhone());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        // Inflate the layout for this fragment
        return rootView;
    }

    public void SaveEditProfile (View view){
        boolean booleanName = true,
                booleanAddress = true,
                booleanPhone = true;

        boolean cancel = false;
        View focusView =  null;

        edtx_name.setError(null);
        edtx_address.setError(null);
        edtx_phone.setError(null);

        name = edtx_name.getText().toString().trim();
        address = edtx_address.getText().toString().trim();
        phone = edtx_phone.getText().toString().trim();

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
            EditProfileData editProfileData = new EditProfileData(name, address, phone);

            userDatabase.child("userData").child("putinvladimir").setValue(editProfileData);
            Toast.makeText(getActivity(), "Saved", Toast.LENGTH_SHORT).show();
        }


    }

}

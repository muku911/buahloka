package com.calcatz.desanti1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class desanti1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desanti1);

        Spinner mySpinner = (Spinner)findViewById(R.id.spinner);

        ArrayAdapter<String> myAdapter1 = new ArrayAdapter<String>(desanti1.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.kelamin));
        myAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(myAdapter1);

        Spinner myAgama = (Spinner)findViewById(R.id.spinner2);

        ArrayAdapter<String> myAdapter2 = new ArrayAdapter<String>(desanti1.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.agama));
        myAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        myAgama.setAdapter(myAdapter2);

        Spinner myStatus = (Spinner)findViewById(R.id.spinner3);

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(desanti1.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.status));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        myStatus.setAdapter(myAdapter);

    }
}

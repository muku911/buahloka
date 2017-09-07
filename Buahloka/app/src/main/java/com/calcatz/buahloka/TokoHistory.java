package com.calcatz.buahloka;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class TokoHistory extends AppCompatActivity {

    private FirebaseDatabase mydatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseJualan = mydatabase.getReference();


    private ListView listHistory;
    private Histories histories;
    private ArrayList<Histories> historyList = new ArrayList<>();
    // private CustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toko_history);

        listHistory = (ListView) findViewById(R.id.listTokoHistory);

        dataoffline();

        ListAdapter adapter = new HistoryListAdapter(this, R.layout.item_list_history,historyList, this);
        listHistory.setAdapter(adapter);


        //Firebase
//        databaseJualan.child("Jualan").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                if (historyList != null)
//                    historyList.clear();
//
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                    histories = snapshot.getValue(Histories.class);
//                    historyList.add(histories);
//                }
//                adapter = new CustomAdapter(this, historyList);
//                listHistory.setAdapter(adapter);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });

    }

    private void dataoffline() {
        Histories data1 = new Histories("Bapak", "Jalan alamat", "10 Kg", "Matang", "10000");
        historyList.add(data1);
        Histories data2 = new Histories("Ibu", "Jalan alamat", "10 Kg", "Matang", "10000");
        historyList.add(data2);
        Histories data3 = new Histories("Anak", "Jalan alamat", "10 Kg", "Matang", "10000");
        historyList.add(data3);
        Histories data4 = new Histories("ADek", "Jalan alamat", "10 Kg", "Matang", "10000");
        historyList.add(data4);
        Histories data5 = new Histories("KAkak", "Jalan alamat", "10 Kg", "Matang", "10000");
        historyList.add(data5);
        Histories data6 = new Histories("Paman", "Jalan alamat", "10 Kg", "Matang", "10000");
        historyList.add(data6);
        Histories data7 = new Histories("Bapak", "Jalan alamat", "10 Kg", "Matang", "10000");
        historyList.add(data7);
        Histories data8 = new Histories("Bapak", "Jalan alamat", "10 Kg", "Matang", "10000");
        historyList.add(data8);
        Histories data9 = new Histories("Bapak", "Jalan alamat", "10 Kg", "Matang", "10000");
        historyList.add(data9);
        Histories data10 = new Histories("Bapak", "Jalan alamat", "10 Kg", "Matang", "10000");
        historyList.add(data10);
    }
}

class Histories{
    private String pembeli, alamat, jumlahBeli, status, totalHarga;

    public Histories() {
    }

    public Histories(String pembeli, String alamat, String jumlahBeli, String status, String totalHarga) {
        this.pembeli = pembeli;
        this.alamat = alamat;
        this.jumlahBeli = jumlahBeli;
        this.status = status;
        this.totalHarga = totalHarga;
    }

    public String getPembeli() {
        return pembeli;
    }

    public String getAlamat() {
        return alamat;
    }

    public String getJumlahBeli() {
        return jumlahBeli;
    }

    public String getStatus() {
        return status;
    }

    public String getTotalHarga() {
        return totalHarga;
    }
}


class HistoryListAdapter extends ArrayAdapter<Histories> {
    private Context mContext;
    int mResource;
    ArrayList<Histories> histories;

    public HistoryListAdapter(Context context, int resource, ArrayList<Histories> objects, Context mContext) {
        super(context, resource, objects);
        this.mContext = mContext;
        mResource = resource;
    }



    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String pembeli = getItem(position).getPembeli();
        String alamat = getItem(position).getAlamat();
        String jumlahBeli = getItem(position).getJumlahBeli();
        String status = getItem(position).getStatus();
        String totalHarga = getItem(position).getTotalHarga();

        Histories histories = new Histories(pembeli,alamat,jumlahBeli,status,totalHarga);

        LayoutInflater inflater = LayoutInflater.from(mContext);

        convertView = inflater.inflate(R.layout.item_list_history, parent, false);

        TextView txPembeli = (TextView) convertView.findViewById(R.id.textPembeli);
        TextView txAlamat = (TextView) convertView.findViewById(R.id.textAlamat);
        TextView txJumlah = (TextView) convertView.findViewById(R.id.textJumlah);
        TextView txStatus = (TextView) convertView.findViewById(R.id.textStatus);
        TextView txHarga = (TextView) convertView.findViewById(R.id.textHarga);

        txPembeli.setText(pembeli);
        txAlamat.setText(alamat);
        txJumlah.setText(jumlahBeli);
        txStatus.setText(status);
        txHarga.setText(totalHarga);

        return convertView;
    }
}

//class CustomAdapter extends BaseAdapter{
//
//
//    Context mContext;
//    List<Histories> histories;
//    LayoutInflater inflater;
//
//    public CustomAdapter(Context mContext, List<Histories> histories) {
//        this.mContext = mContext;
//        this.histories = histories;
//    }
//
//
//
//
//    @Override
//    public int getCount() {
//        return histories.size();
//    }
//
//    @Override
//    public Object getItem(int i) {
//        return histories.get(i);
//    }
//
//    @Override
//    public long getItemId(int i) {
//        return i;
//    }
//
//    @Override
//    public View getView(int i, View convertView, ViewGroup parent) {
//
//        View ItemView = convertView;
//        Log.d("LOGUOU", "qweqwe");
//         if (convertView==null)
//        {
//            inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            ItemView = inflater.inflate(R.layout.row_history,null);
//            Log.d("LOGUOU", "asdasdasda");
//        }
//
//        TextView txPembeli = (TextView) ItemView.findViewById(R.id.textPembeli);
//        TextView txAlamat = (TextView) ItemView.findViewById(R.id.textAlamat);
//        TextView txJumlah = (TextView) ItemView.findViewById(R.id.textJumlah);
//        TextView txStatus = (TextView) ItemView.findViewById(R.id.textStatus);
//        TextView txHarga = (TextView) ItemView.findViewById(R.id.textHarga);
//
//        txPembeli.setText(histories.get(i).getPembeli());
//        txAlamat.setText(histories.get(i).getAlamat());
//        txJumlah.setText(histories.get(i).getJumlahBeli());
//        txStatus.setText(histories.get(i).getStatus());
//        txHarga.setText(histories.get(i).getTotalHarga());
//        return ItemView;
//    }
//}

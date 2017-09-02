package com.calcatz.buahloka;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ListJualanActivity extends AppCompatActivity {

    //UI
    private ListView lv_listJualan;

    //Data
    private String pilihKebun, pilihBuah;

    private ListJualan listJualan;
    private List<ListJualan> jualanList = new ArrayList<ListJualan>();

    private ListJualanViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_jualan);

        //LoadBundle
        Bundle bundle;
        bundle = getIntent().getExtras();
        pilihKebun = bundle.getString("pilihankebun");
        pilihBuah = bundle.getString("pilihanBuah");

        //Inisialisasi
        lv_listJualan = (ListView)findViewById(R.id.lv_listJualan);

        dataoffline();

        //Tinker In Action
        int sum = jualanList.size();

        for (int a = 0 ; a < sum ; a++){
            adapter = new ListJualanViewAdapter(ListJualanActivity.this,jualanList);
            lv_listJualan.setAdapter(adapter);
        }
    }

    private void dataoffline() {
        listJualan = new ListJualan(pilihBuah, "Pisang Ambon", "10 Kg");
        jualanList.add(listJualan);

        listJualan = new ListJualan(pilihBuah, "Pisang Ambon", "5 Kg");
        jualanList.add(listJualan);

        listJualan = new ListJualan(pilihBuah, "Pisang Ambon", "4 Kg");
        jualanList.add(listJualan);

        listJualan = new ListJualan(pilihBuah, "Pisang Ambon", "1 Kg");
        jualanList.add(listJualan);

        listJualan = new ListJualan(pilihBuah, "Pisang Ambon", "100 Kg");
        jualanList.add(listJualan);

        listJualan = new ListJualan(pilihBuah, "Pisang Ambon", "14 Kg");
        jualanList.add(listJualan);
    }
}

class ListJualan {
    private String kategori, jenis, stok;

    public ListJualan() {
    }

    public ListJualan(String kategori, String jenis, String stok) {
        this.kategori = kategori;
        this.jenis = jenis;
        this.stok = stok;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public String getJenis() {
        return jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

    public String getStok() {
        return stok;
    }

    public void setStok(String stok) {
        this.stok = stok;
    }
}

class ListJualanViewAdapter extends BaseAdapter{

    private Context mContext;
    private Activity activity;

    private List<ListJualan> listJualen;

    private ArrayAdapter<ListJualan> arraylist;
    private LayoutInflater inflater;

    public ListJualanViewAdapter(Activity activity, List<ListJualan> listJualen) {
        this.activity = activity;
        this.listJualen = listJualen;
    }

    @Override
    public int getCount() {
        return listJualen.size();
    }

    @Override
    public Object getItem(int i) {
        return listJualen.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        inflater  = (LayoutInflater)activity.getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.items_list_jualan, null);

        TextView tx_kategori = (TextView)itemView.findViewById(R.id.tx_isiKategori);
        TextView tx_stok = (TextView)itemView.findViewById(R.id.tx_isiStokBuah);
        TextView tx_jenis = (TextView)itemView.findViewById(R.id.tx_isiJenisBuah);
        ImageView gambarBuah = (ImageView)itemView.findViewById(R.id.img_itemJualan);

        tx_kategori.setText(listJualen.get(i).getKategori());
        tx_stok.setText(listJualen.get(i).getStok());
        tx_jenis.setText(listJualen.get(i).getJenis());

        gambarBuah.setImageResource(R.drawable.logo_bualoka);

        return itemView;
    }
}

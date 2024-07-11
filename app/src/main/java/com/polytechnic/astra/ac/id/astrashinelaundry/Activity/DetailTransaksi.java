package com.polytechnic.astra.ac.id.astrashinelaundry.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.polytechnic.astra.ac.id.astrashinelaundry.Fragment.DetailTransaksiKurirFragment;
import com.polytechnic.astra.ac.id.astrashinelaundry.Fragment.PickUpKurirFragment;
import com.polytechnic.astra.ac.id.astrashinelaundry.R;

public class DetailTransaksi extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_detail_transaksi_kurir);

        if (savedInstanceState == null) {
            DetailTransaksiKurirFragment fragmentPickUpKurir = new DetailTransaksiKurirFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.DetailPickUpKurir, fragmentPickUpKurir)
                    .commit();
        }
    }
}
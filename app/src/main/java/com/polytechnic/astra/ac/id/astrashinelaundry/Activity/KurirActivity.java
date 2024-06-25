package com.polytechnic.astra.ac.id.astrashinelaundry.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.Toast;

import com.polytechnic.astra.ac.id.astrashinelaundry.Fragment.PickUpKurirFragment;
import com.polytechnic.astra.ac.id.astrashinelaundry.R;
import com.polytechnic.astra.ac.id.astrashinelaundry.ViewModel.TransaksiListViewModel;

public class KurirActivity extends AppCompatActivity {

    private TransaksiListViewModel mTransaksiListViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_kurir_pickup);


        if (savedInstanceState == null) {
            PickUpKurirFragment fragmentPickUpKurir = new PickUpKurirFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.PickUpKurir, fragmentPickUpKurir)
                    .commit();
        }
    }
}
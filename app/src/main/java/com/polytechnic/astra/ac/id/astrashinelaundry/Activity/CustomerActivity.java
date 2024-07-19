package com.polytechnic.astra.ac.id.astrashinelaundry.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.gson.Gson;
import com.polytechnic.astra.ac.id.astrashinelaundry.Fragment.ViewTransaksiFragment;
import com.polytechnic.astra.ac.id.astrashinelaundry.Model.UserModel;
import com.polytechnic.astra.ac.id.astrashinelaundry.R;
import com.polytechnic.astra.ac.id.astrashinelaundry.ViewModel.TransaksiListViewModel;

public class CustomerActivity extends AppCompatActivity {

    private TransaksiListViewModel mTransaksiListViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);

        SharedPreferences sharedPreferences = getSharedPreferences("loginSession", Context.MODE_PRIVATE);
        String userJson = sharedPreferences.getString("dataUser", null);

        // Gunakan Gson untuk mengonversi JSON string menjadi objek
        Gson gson = new Gson();
        UserModel user = gson.fromJson(userJson, UserModel.class);

        if (savedInstanceState == null) {
            ViewTransaksiFragment fragmentViewTransaksi = new ViewTransaksiFragment();

            // Buat Bundle dan tambahkan data
            Bundle bundle = new Bundle();
            bundle.putString("userJson", userJson);
            fragmentViewTransaksi.setArguments(bundle);

            // Tambahkan fragment ke activity
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container_customer, fragmentViewTransaksi)
                    .commit();
        }

    }
}
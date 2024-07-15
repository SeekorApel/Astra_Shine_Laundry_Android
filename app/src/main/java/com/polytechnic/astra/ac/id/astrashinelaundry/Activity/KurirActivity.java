package com.polytechnic.astra.ac.id.astrashinelaundry.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.google.gson.Gson;
import com.polytechnic.astra.ac.id.astrashinelaundry.Fragment.PickUpKurirFragment;
import com.polytechnic.astra.ac.id.astrashinelaundry.Model.UserModel;
import com.polytechnic.astra.ac.id.astrashinelaundry.R;
import com.polytechnic.astra.ac.id.astrashinelaundry.ViewModel.TransaksiListViewModel;

public class KurirActivity extends AppCompatActivity {

    private TransaksiListViewModel mTransaksiListViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kurir);

        SharedPreferences sharedPreferences = getSharedPreferences("loginSession", Context.MODE_PRIVATE);
        String userJson = sharedPreferences.getString("dataUser", null);

        // Gunakan Gson untuk mengonversi JSON string menjadi objek
        Gson gson = new Gson();
        UserModel user = gson.fromJson(userJson, UserModel.class);


        if (savedInstanceState == null) {
            PickUpKurirFragment fragmentPickUpKurir = new PickUpKurirFragment();

            Bundle bundle = new Bundle();
            bundle.putString("userJson", userJson);
            fragmentPickUpKurir.setArguments(bundle);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container_kurir, fragmentPickUpKurir)
                    .commit();
        }
    }
}
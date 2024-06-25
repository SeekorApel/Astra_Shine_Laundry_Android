package com.polytechnic.astra.ac.id.astrashinelaundry.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;

import com.polytechnic.astra.ac.id.astrashinelaundry.Fragment.LoginFragment;
import com.polytechnic.astra.ac.id.astrashinelaundry.Fragment.PengaturanFragment;
import android.os.Bundle;

import com.polytechnic.astra.ac.id.astrashinelaundry.R;

public class PengaturanActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengaturan);

        if (savedInstanceState == null) {
            PengaturanFragment fragmentPengaturan = new PengaturanFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_pengaturan, fragmentPengaturan)
                    .commit();
        }

    }

    private void logout(){
        SharedPreferences sharedPreferences = getSharedPreferences("loginSession", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

        Intent intent = new Intent(PengaturanActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
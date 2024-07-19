package com.polytechnic.astra.ac.id.astrashinelaundry.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.polytechnic.astra.ac.id.astrashinelaundry.Fragment.LoginFragment;
import com.polytechnic.astra.ac.id.astrashinelaundry.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Panggil Fragment Login Pada saat di jalankan
        if (savedInstanceState == null) {
            LoginFragment fragmentLogin = new LoginFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container_main, fragmentLogin)
                    .commit();
        }

        //Clear Field edittext email dan password
        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                Fragment loginFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container_main);
                if (loginFragment instanceof LoginFragment) {
                    ((LoginFragment) loginFragment).clearFields();
                }
            }
        });


    }

}
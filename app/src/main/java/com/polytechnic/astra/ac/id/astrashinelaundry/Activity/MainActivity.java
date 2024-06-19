package com.polytechnic.astra.ac.id.astrashinelaundry.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;

import com.polytechnic.astra.ac.id.astrashinelaundry.Fragment.LoginFragment;
import com.polytechnic.astra.ac.id.astrashinelaundry.R;

public class MainActivity extends AppCompatActivity {
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Panggil Fragment Login Pada saat di jalankan
        if (savedInstanceState == null) {
            LoginFragment fragmentLogin = new LoginFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_login, fragmentLogin)
                    .commit();
        }

        //Clear Field edittext email dan password
        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                Fragment loginFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_login);
                if (loginFragment instanceof LoginFragment) {
                    ((LoginFragment) loginFragment).clearFields();
                }
            }
        });


    }

}
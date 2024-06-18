package com.polytechnic.astra.ac.id.astrashinelaundry.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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
        // Check if the fragment container is available
        if (findViewById(R.id.fragment_login) != null) {
            if (savedInstanceState != null) {
                return;
            }
            // Create a new instance of LoginFragment
            LoginFragment loginFragment = new LoginFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_login, loginFragment).commit();
        }
    }

}
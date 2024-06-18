package com.polytechnic.astra.ac.id.astrashinelaundry.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.polytechnic.astra.ac.id.astrashinelaundry.Fragment.LoginFragment;
import com.polytechnic.astra.ac.id.astrashinelaundry.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Check if the fragment container is available
        if (findViewById(R.id.fragment_login) != null) {
            // If we're being restored from a previous state, then we don't need to do anything and should return or else we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }
            // Create a new instance of LoginFragment
            LoginFragment loginFragment = LoginFragment.newInstance("param1", "param2");
            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_login, loginFragment).commit();
        }
    }
}
package com.polytechnic.astra.ac.id.astrashinelaundry.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.polytechnic.astra.ac.id.astrashinelaundry.Fragment.LoginFragment;
import com.polytechnic.astra.ac.id.astrashinelaundry.Model.UserModel;
import com.polytechnic.astra.ac.id.astrashinelaundry.R;

public class TestCusActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_cus);

        // Ambil data dari Session
        SharedPreferences sharedPreferences = getSharedPreferences("loginSession", Context.MODE_PRIVATE);
        String userJson = sharedPreferences.getString("dataUser", null);

        // Gunakan Gson untuk mengonversi JSON string menjadi objek
        Gson gson = new Gson();
        UserModel user = gson.fromJson(userJson, UserModel.class);

        TextView txtInfo = findViewById(R.id.txt_info);
        txtInfo.setText("idUser" + user.getIdUser() + " namaUser " + user.getNamaUser() + " Role " + user.getRole());

        Button logoutButton = findViewById(R.id.btn_logout_customer);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Clear Session
                SharedPreferences sharedPreferences = getSharedPreferences("loginSession", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();

                Intent intent = new Intent(TestCusActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }
}
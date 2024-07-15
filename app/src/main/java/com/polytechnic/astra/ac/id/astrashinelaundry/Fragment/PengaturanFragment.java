package com.polytechnic.astra.ac.id.astrashinelaundry.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.polytechnic.astra.ac.id.astrashinelaundry.Activity.MainActivity;
import com.polytechnic.astra.ac.id.astrashinelaundry.R;

public class PengaturanFragment extends Fragment {

    private Button mBtnLogout, mBtnKembali;
    private Button mButtonLogout;

    private ConstraintLayout cl1, cl2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pengaturan,container,false);
        cl1 = view.findViewById(R.id.constraintLayoutPengaturanAkun);
        cl2 = view.findViewById(R.id.constraintLayoutPengaturanLokasi);

        cl1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToFragmentForgetPassword();
            }
        });

        cl2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToFragmentAlamat();
            }
        });

        mBtnLogout = view.findViewById(R.id.btn_get_location);
        mBtnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });

        mBtnKembali = view.findViewById(R.id.btn_kembali);
        mBtnKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToList();
            }
        });
        return view;
    }

    private void logout(){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("loginSession", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    private void navigateToFragmentAlamat(){
        AlamatFragment fragmentAlamat = new AlamatFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_view_transaksi, fragmentAlamat);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void navigateToFragmentForgetPassword(){
        PengaturanResetPasswordFragment fragmentForgetPassword = new PengaturanResetPasswordFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_view_transaksi, fragmentForgetPassword);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void navigateToList(){
        ViewTransaksiFragment fragmentListTransaksiCus = new ViewTransaksiFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_view_transaksi, fragmentListTransaksiCus);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
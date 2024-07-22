package com.polytechnic.astra.ac.id.astrashinelaundry.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.polytechnic.astra.ac.id.astrashinelaundry.API.Repository.UserRepository;
import com.polytechnic.astra.ac.id.astrashinelaundry.Model.UserModel;
import com.polytechnic.astra.ac.id.astrashinelaundry.R;
import com.polytechnic.astra.ac.id.astrashinelaundry.ViewModel.ForgetPasswordViewModel;

public class PengaturanResetPasswordFragment extends Fragment {

    private Button mBtnKembali, mBtnUbah;

    private EditText mEdtPasswordLama, mEdtPasswordBaru;

    private ForgetPasswordViewModel mForgetPasswordViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UserRepository.initialize(requireContext());
        mForgetPasswordViewModel = new ViewModelProvider(this).get(ForgetPasswordViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pengaturan_reset_password,container,false);
        mBtnKembali = view.findViewById(R.id.btn_kembali);
        mBtnUbah = view.findViewById(R.id.btn_ubah);
        mEdtPasswordLama = view.findViewById(R.id.edt_password_lama);
        mEdtPasswordBaru = view.findViewById(R.id.edt_password_baru);

        mBtnUbah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldPassword = mEdtPasswordLama.getText().toString().trim();
                String newPassword = mEdtPasswordBaru.getText().toString().trim();
                Integer idUser = getUserId();

                if (TextUtils.isEmpty(oldPassword)) {
                    mEdtPasswordLama.setError("Password lama wajib di isi");
                    mEdtPasswordLama.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(newPassword)) {
                    mEdtPasswordBaru.setError("Password baru wajib di isi");
                    mEdtPasswordBaru.requestFocus();
                    return;
                }

                mForgetPasswordViewModel.resetPasswordById(idUser , newPassword);

                mForgetPasswordViewModel.getSuccessResponse().observe(getViewLifecycleOwner(), message -> {
                    Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
                    navigateToFragment();
                });

                mForgetPasswordViewModel.getErrorResponse().observe(getViewLifecycleOwner(), error -> {
                    Toast.makeText(getContext(), error, Toast.LENGTH_LONG).show();
                });


            }
        });

        mBtnKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });

        return view;
    }

    private Integer getUserId(){
        Integer idUser = null;
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("loginSession", Context.MODE_PRIVATE);
        String userJson = sharedPreferences.getString("dataUser", null);
        Gson gson = new Gson();
        UserModel user = gson.fromJson(userJson, UserModel.class);
        idUser = user.getIdUser();

        return idUser;
    }

    //Lakukan Transaksi Perpindahan Fragment setelah aksi button di tekan
    private void navigateToFragment(){
        Fragment pengaturanFragment = new PengaturanFragment();
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container_customer, pengaturanFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
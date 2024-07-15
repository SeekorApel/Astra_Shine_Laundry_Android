package com.polytechnic.astra.ac.id.astrashinelaundry.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.polytechnic.astra.ac.id.astrashinelaundry.API.Repository.UserRepository;
import com.polytechnic.astra.ac.id.astrashinelaundry.Activity.MainActivity;
import com.polytechnic.astra.ac.id.astrashinelaundry.Model.UserModel;
import com.polytechnic.astra.ac.id.astrashinelaundry.R;
import com.polytechnic.astra.ac.id.astrashinelaundry.ViewModel.RegisterViewModel;

public class RegisterFragment extends Fragment {

    private EditText mEdtEmail, mEdtPassword, mEdtNama, mEdtNoTelephone;

    private Button mBtnDaftar, mBtnKembali;

    private RegisterViewModel mRegisterViewModel;

    public RegisterFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UserRepository.initialize(requireContext());
        mRegisterViewModel = new ViewModelProvider(this).get(RegisterViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register,container,false);
        mEdtEmail = view.findViewById(R.id.edt_title_alamat);
        mEdtPassword = view.findViewById(R.id.edt_password);
        mEdtNama = view.findViewById(R.id.edt_nama);
        mEdtNoTelephone = view.findViewById(R.id.edt_no_telp);
        mBtnDaftar = view.findViewById(R.id.btn_daftar);
        mBtnKembali = view.findViewById(R.id.btn_kembali);

        mBtnDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEdtEmail.getText().toString().trim();
                String password = mEdtPassword.getText().toString().trim();
                String nama = mEdtNama.getText().toString().trim();
                String noTelp = mEdtNoTelephone.getText().toString().trim();

                UserModel registerUser = new UserModel();
                registerUser.setEmail(email);
                registerUser.setPassword(password);
                registerUser.setNamaUser(nama);
                registerUser.setNoTelp(noTelp);

                if (TextUtils.isEmpty(email)) {
                    mEdtEmail.setError("Email wajib Di isi");
                    mEdtEmail.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    mEdtPassword.setError("Password wajib Di isi");
                    mEdtPassword.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(nama)) {
                    mEdtNama.setError("Nama wajib Di isi");
                    mEdtNama.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(noTelp)) {
                    mEdtNoTelephone.setError("No Telephone wajib Di isi");
                    mEdtNoTelephone.requestFocus();
                    return;
                }

                if (!isValidEmail(email)) {
                    mEdtEmail.setError("Format Email Tidak Valid abc@gmail.com");
                    mEdtEmail.requestFocus();
                    return;
                }

                mRegisterViewModel.registerUser(registerUser);

                mRegisterViewModel.getRegisterMessage().observe(getViewLifecycleOwner(), message -> {
                    Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
                    navigateToMainActivity();
                });

                mRegisterViewModel.getErrorMessage().observe(getViewLifecycleOwner(), error -> {
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

    private boolean isValidEmail(String email) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        return email.matches(emailPattern);
    }

    private void navigateToMainActivity(){
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
        getActivity().finish();
    }
}
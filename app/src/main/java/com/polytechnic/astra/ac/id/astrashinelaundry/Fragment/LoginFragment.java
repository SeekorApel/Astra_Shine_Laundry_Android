package com.polytechnic.astra.ac.id.astrashinelaundry.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.polytechnic.astra.ac.id.astrashinelaundry.API.Repository.UserRepository;
import com.polytechnic.astra.ac.id.astrashinelaundry.API.VO.UserVO;
import com.polytechnic.astra.ac.id.astrashinelaundry.Activity.KurirActivity;
import com.polytechnic.astra.ac.id.astrashinelaundry.Activity.PengaturanActivity;
import com.polytechnic.astra.ac.id.astrashinelaundry.Activity.TestCusActivity;
import com.polytechnic.astra.ac.id.astrashinelaundry.Model.UserModel;
import com.polytechnic.astra.ac.id.astrashinelaundry.R;
import com.polytechnic.astra.ac.id.astrashinelaundry.ViewModel.LoginViewModel;

public class LoginFragment extends Fragment {

    private TextView mTxtForgetPassword, mTxtSignIn1, mTextSignIn2;

    private EditText mEdtEmail, mEdtPassword;

    private Button mBtnLogin;

    private LoginViewModel mLoginViewModel;

    public LoginFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UserRepository.initialize(requireContext());
        mLoginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        mEdtEmail = view.findViewById(R.id.edt_email);
        mEdtPassword = view.findViewById(R.id.edt_password);


        mBtnLogin = view.findViewById(R.id.btn_logout);
        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEdtEmail.getText().toString().trim();
                String password = mEdtPassword.getText().toString().trim();


                if (TextUtils.isEmpty(email)) {
                    mEdtEmail.setError("Email wajib Di isi");
                    mEdtEmail.requestFocus();
                    return;
                }

//                if (!isValidEmail(email)) {
//                    mEdtEmail.setError("Format Email Tidak Valid abc@gmail.com");
//                    mEdtEmail.requestFocus();
//                    return;
//                }

                if (TextUtils.isEmpty(password)) {
                    mEdtPassword.setError("Password Wajib di isi");
                    mEdtPassword.requestFocus();
                    return;
                }

                mLoginViewModel.login(email, password);

                // Observe the login response
                observeLoginResponse();
            }
        });

        //Pindah Fragment Forget Password
        mTxtForgetPassword = view.findViewById(R.id.txt_lupa_password);
        mTxtForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ForgetPasswordFragment fragmentForgetPassword = new ForgetPasswordFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_login, fragmentForgetPassword);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        //Pindah Fragment Sign In 1
        mTxtSignIn1 = view.findViewById(R.id.txt_sign_up_1);
        mTxtSignIn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterFragment fragmentRegister = new RegisterFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_login, fragmentRegister);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        //Pindah Fragment Sign In 2
        mTextSignIn2 = view.findViewById(R.id.txt_sign_up_2);
        mTextSignIn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterFragment fragmentRegister = new RegisterFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_login, fragmentRegister);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        //Pengecekan Session Login
        checkSession();

        return view;
    }

    public void clearFields() {
        if (mEdtEmail != null && mEdtPassword != null) {
            mEdtEmail.setText("");
            mEdtPassword.setText("");
        }
    }

    // Regex Validasi Email
    private boolean isValidEmail(String email) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        return email.matches(emailPattern);
    }

    private void checkSession() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("loginSession", Context.MODE_PRIVATE);
        String userJson = sharedPreferences.getString("dataUser", null);
        if (userJson != null) {
            Gson gson = new Gson();
            UserModel userModel = gson.fromJson(userJson, UserModel.class);
            navigateToNewActivity(userModel.getRole());
        } else {
            // Tidak ada sesi
            Log.d("LoginFragment", "No session found");
        }
    }

    private void navigateToNewActivity (String role){
        if (role.equals("Customer")) {
            Intent intent = new Intent(getActivity(), PengaturanActivity.class);
            startActivity(intent);
            getActivity().finish();
        } else if (role.equals("Kurir")) {
            Intent intent = new Intent(getActivity(), KurirActivity.class);
            startActivity(intent);
            getActivity().finish();
        }
    }

    private void observeLoginResponse() {
        mLoginViewModel.getLoginResponse().observe(getViewLifecycleOwner(), new Observer<UserVO>() {
            @Override
            public void onChanged(UserVO userVO) {
                if (userVO != null) {
                    Integer idUser = userVO.getData().getIdUser();
                    String namaUser = userVO.getData().getNamaUser();
                    String noTelp = userVO.getData().getNoTelp();
                    String role = userVO.getData().getRole();
                    String status = userVO.getData().getStatus();
                    UserModel dataLogin = new UserModel(idUser, namaUser, noTelp, role, status);

                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences("loginSession", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    Gson gson = new Gson();
                    String userJson = gson.toJson(dataLogin);
                    editor.putString("dataUser", userJson);
                    editor.apply();

                    Toast.makeText(getActivity(), "Login Berhasil", Toast.LENGTH_SHORT).show();
                    navigateToNewActivity(role);
                } else {
                    Toast.makeText(getActivity(), "Email dan password Salah", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}
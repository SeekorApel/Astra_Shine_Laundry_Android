package com.polytechnic.astra.ac.id.astrashinelaundry.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.polytechnic.astra.ac.id.astrashinelaundry.R;

import org.w3c.dom.Text;

public class LoginFragment extends Fragment {

    private TextView mTxtForgetPassword, mTxtSignIn1, mTextSignIn2;

    private EditText mEdtEmail, mEdtPassword;

    private Button mBtnLogin;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        mEdtEmail = view.findViewById(R.id.edt_email);
        mEdtPassword = view.findViewById(R.id.edt_password);


        mBtnLogin = view.findViewById(R.id.btn_login);
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

                if (!isValidEmail(email)) {
                    mEdtEmail.setError("Format Email Tidak Valid abc@gmail.com");
                    mEdtEmail.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    mEdtPassword.setError("Password Wajib di isi");
                    mEdtPassword.requestFocus();
                    return;
                }

                Toast.makeText(getActivity(), "Login successful", Toast.LENGTH_SHORT).show();
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

        //Pindah Fragment Sign In
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

        //Pindah Fragment Sign In
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
}
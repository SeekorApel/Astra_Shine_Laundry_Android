package com.polytechnic.astra.ac.id.astrashinelaundry.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.polytechnic.astra.ac.id.astrashinelaundry.R;

public class ForgetPasswordFragment extends Fragment {

    private EditText mEdtEmail;
    private Button mBtnKembali, mBtnKirim;

    public ForgetPasswordFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forget_password, container, false);
        mBtnKirim = view.findViewById(R.id.btn_kirim);
        mBtnKembali = view.findViewById(R.id.btn_kembali);
        mEdtEmail = view.findViewById(R.id.edt_email);

        mBtnKirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEdtEmail.getText().toString().trim();

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
}
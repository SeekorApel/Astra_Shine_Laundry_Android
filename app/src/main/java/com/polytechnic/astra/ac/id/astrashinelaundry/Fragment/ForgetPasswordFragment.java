package com.polytechnic.astra.ac.id.astrashinelaundry.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.polytechnic.astra.ac.id.astrashinelaundry.API.Repository.UserRepository;
import com.polytechnic.astra.ac.id.astrashinelaundry.API.VO.UserVO;
import com.polytechnic.astra.ac.id.astrashinelaundry.Activity.MainActivity;
import com.polytechnic.astra.ac.id.astrashinelaundry.R;
import com.polytechnic.astra.ac.id.astrashinelaundry.ViewModel.ForgetPasswordViewModel;

public class ForgetPasswordFragment extends Fragment {

    private EditText mEdtEmail;
    private Button mBtnKembali, mBtnKirim;

    private ForgetPasswordViewModel mForgetPasswordViewModel;

    public ForgetPasswordFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UserRepository.initialize(requireContext());
        mForgetPasswordViewModel = new ViewModelProvider(this).get(ForgetPasswordViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forget_password, container, false);
        mBtnKirim = view.findViewById(R.id.btn_kirim);
        mBtnKembali = view.findViewById(R.id.btn_kembali);
        mEdtEmail = view.findViewById(R.id.edt_title_alamat);

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

                mForgetPasswordViewModel.resetPasswordByEmail(email);
                mForgetPasswordViewModel.getResponse().observe(getViewLifecycleOwner(), new Observer<UserVO>() {
                    @Override
                    public void onChanged(UserVO userVO) {
                        if(userVO.getStatus() == 200){
                            Toast.makeText(getContext(), userVO.getMessage(), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getActivity(), MainActivity.class);
                            startActivity(intent);
                            getActivity().finish();
                        }
                        else if(userVO.getStatus() == 500){
                            Toast.makeText(getContext(), userVO.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
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
}
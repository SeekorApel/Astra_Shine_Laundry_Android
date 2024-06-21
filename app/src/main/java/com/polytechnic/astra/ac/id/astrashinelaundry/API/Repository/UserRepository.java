package com.polytechnic.astra.ac.id.astrashinelaundry.API.Repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.polytechnic.astra.ac.id.astrashinelaundry.API.ApiUtils;
import com.polytechnic.astra.ac.id.astrashinelaundry.API.Service.UserService;
import com.polytechnic.astra.ac.id.astrashinelaundry.API.VO.LoginVO;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRepository {

    private static final String TAG = "UserRepository";
    private static UserRepository INSTANCE;
    private UserService mUserService;

    private UserRepository(Context context) {
        mUserService = ApiUtils.getUserService();
    }

    public static void initialize(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new UserRepository(context);
        }
    }

    public static UserRepository get() {
        return INSTANCE;
    }

    public MutableLiveData<LoginVO> getUserLogin(String email, String password) {
        Log.i(TAG, "getUserByEmailAndPassword() called");
        MutableLiveData<LoginVO> dataLogin = new MutableLiveData<>();

        Call<LoginVO> call = mUserService.getUserByEmailAndPassword(email, password);
        call.enqueue(new Callback<LoginVO>() {
            @Override
            public void onResponse(Call<LoginVO> call, Response<LoginVO> response) {
                dataLogin.setValue(response.body());
                Log.d(TAG, "getUserLogin.onResponse() called");
            }

            @Override
            public void onFailure(Call<LoginVO> call, Throwable throwable) {
                Log.e("Error API Call Login : ", throwable.getMessage());
            }
        });

        return dataLogin;
    }
}

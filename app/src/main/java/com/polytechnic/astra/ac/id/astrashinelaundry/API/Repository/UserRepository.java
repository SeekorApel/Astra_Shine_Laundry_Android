package com.polytechnic.astra.ac.id.astrashinelaundry.API.Repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.polytechnic.astra.ac.id.astrashinelaundry.API.ApiUtils;
import com.polytechnic.astra.ac.id.astrashinelaundry.API.Service.UserService;
import com.polytechnic.astra.ac.id.astrashinelaundry.API.VO.LoginVO;
import com.polytechnic.astra.ac.id.astrashinelaundry.API.VO.RegisterVO;

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
                Log.e("Error API Call : ", throwable.getMessage());
            }
        });

        return dataLogin;
    }

    public void resetPassword(String email, final ResetPasswordCallback callback) {
        Log.i(TAG, "resetPassword() called");
        Call<RegisterVO> call = mUserService.resetPasswordByEmail(email);
        call.enqueue(new Callback<RegisterVO>() {
            @Override
            public void onResponse(Call<RegisterVO> call, Response<RegisterVO> response) {
                Log.d(TAG, "resetPassword.onResponse() called");
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body().getMessage());
                } else {
                    callback.onError("Failed to reset password");
                }
            }

            @Override
            public void onFailure(Call<RegisterVO> call, Throwable throwable) {
                Log.e("Error API Call : ", throwable.getMessage());
                callback.onError(throwable.getMessage());
            }
        });
    }

    public interface ResetPasswordCallback {
        void onSuccess(String message);
        void onError(String error);
    }
}

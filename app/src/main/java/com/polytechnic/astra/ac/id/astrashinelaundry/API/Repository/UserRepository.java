package com.polytechnic.astra.ac.id.astrashinelaundry.API.Repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.polytechnic.astra.ac.id.astrashinelaundry.API.ApiUtils;
import com.polytechnic.astra.ac.id.astrashinelaundry.API.Service.UserService;
import com.polytechnic.astra.ac.id.astrashinelaundry.API.VO.UserVO;
import com.polytechnic.astra.ac.id.astrashinelaundry.API.VO.ForgetPasswordVO;
import com.polytechnic.astra.ac.id.astrashinelaundry.Model.UserModel;

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

    public MutableLiveData<UserVO> getUserLogin(String email, String password) {
        Log.i(TAG, "getUserByEmailAndPassword() called");
        MutableLiveData<UserVO> dataLogin = new MutableLiveData<>();

        Call<UserVO> call = mUserService.getUserByEmailAndPassword(email, password);
        call.enqueue(new Callback<UserVO>() {
            @Override
            public void onResponse(Call<UserVO> call, Response<UserVO> response) {
                dataLogin.setValue(response.body());
                Log.d(TAG, "getUserLogin.onResponse() called");
            }

            @Override
            public void onFailure(Call<UserVO> call, Throwable throwable) {
                Log.e("Error API Call : ", throwable.getMessage());
            }
        });

        return dataLogin;
    }

    public void resetPasswordByEmail(String email, final messageCallback callback) {
        Log.i(TAG, "resetPassword() called");
        Call<ForgetPasswordVO> call = mUserService.resetPasswordByEmail(email);
        call.enqueue(new Callback<ForgetPasswordVO>() {
            @Override
            public void onResponse(Call<ForgetPasswordVO> call, Response<ForgetPasswordVO> response) {
                Log.d(TAG, "resetPassword.onResponse() called");
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body().getMessage());
                } else {
                    callback.onError("Failed to reset password");
                }
            }

            @Override
            public void onFailure(Call<ForgetPasswordVO> call, Throwable throwable) {
                Log.e("Error API Call : ", throwable.getMessage());
                callback.onError(throwable.getMessage());
            }
        });
    }

    public void registerUser(UserModel user, final messageCallback callback){
        Log.i(TAG, "registerUser() called");
        Call<UserVO> call = mUserService.registerUser(user);
        call.enqueue(new Callback<UserVO>() {
            @Override
            public void onResponse(Call<UserVO> call, Response<UserVO> response) {
                Log.d(TAG, "registerUser.onResponse() called");
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body().getMessage());
                    Log.d(TAG, response.message());
                } else {
                    callback.onError("Registrasi Gagal");
                }
            }

            @Override
            public void onFailure(Call<UserVO> call, Throwable throwable) {
                Log.e("Error API Call : ", throwable.getMessage());
            }
        });
    }

    public interface messageCallback {
        void onSuccess(String message);
        void onError(String error);
    }
}

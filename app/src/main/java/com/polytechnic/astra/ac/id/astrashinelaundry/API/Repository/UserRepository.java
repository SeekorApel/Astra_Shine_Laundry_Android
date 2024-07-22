package com.polytechnic.astra.ac.id.astrashinelaundry.API.Repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.polytechnic.astra.ac.id.astrashinelaundry.API.ApiUtils;
import com.polytechnic.astra.ac.id.astrashinelaundry.API.Service.UserService;
import com.polytechnic.astra.ac.id.astrashinelaundry.API.VO.AlamatVO;
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
                if(response.isSuccessful() && response.body() != null){
                    dataLogin.setValue(response.body());
                    Log.d(TAG, "getUserLogin.onResponse() called");
                }else {
                    Log.e(TAG, "Terjadi kesalahan");
                }
            }

            @Override
            public void onFailure(Call<UserVO> call, Throwable throwable) {
                Log.e("Error API Call : ", throwable.getMessage());
            }
        });

        return dataLogin;
    }

    public MutableLiveData<UserVO> resetPasswordByEmail(String email) {
        MutableLiveData<UserVO> data = new MutableLiveData<>();
        Call<UserVO> call = mUserService.resetPasswordByEmail(email);
        call.enqueue(new Callback<UserVO>() {
            @Override
            public void onResponse(Call<UserVO> call, Response<UserVO> response) {
                if(response.isSuccessful()){
                    data.setValue(response.body());
                }else {
                    Log.e("Error API Call : ", "Gagal mengubah password");
                }
            }

            @Override
            public void onFailure(Call<UserVO> call, Throwable throwable) {
                Log.e("Error API Call : ", throwable.getMessage());
            }
        });

        return data;
    }

    public MutableLiveData<UserVO> registerUser(UserModel user){
        MutableLiveData<UserVO> data = new MutableLiveData<>();
        Call<UserVO> call = mUserService.registerUser(user);
        call.enqueue(new Callback<UserVO>() {
            @Override
            public void onResponse(Call<UserVO> call, Response<UserVO> response) {
                Log.d(TAG, "registerUser.onResponse() called");
                if(response.isSuccessful()){
                    data.setValue(response.body());
                }else {
                    Log.e("Error API Call : ", "Gagal Registrasi akun");
                }
            }

            @Override
            public void onFailure(Call<UserVO> call, Throwable throwable) {
                Log.e("Error API Call : ", throwable.getMessage());
            }
        });
        return data;
    }

    public MutableLiveData<UserVO> resetPasswordById(Integer idUser, String newPassword, String oldPassword){
        MutableLiveData<UserVO> data = new MutableLiveData<>();
        Call<UserVO> call = mUserService.resetPasswordById(idUser, newPassword, oldPassword);
        call.enqueue(new Callback<UserVO>() {
            @Override
            public void onResponse(Call<UserVO> call, Response<UserVO> response) {
                if(response.isSuccessful()){
                    data.setValue(response.body());
                }else {
                    Log.e("Error API Call : ", "Gagal Mengubah password");
                }
            }

            @Override
            public void onFailure(Call<UserVO> call, Throwable throwable) {
                Log.e("Error API Call : ", throwable.getMessage());
            }
        });

        return data;
    }

    public interface messageCallback {
        void onSuccess(String message);
        void onError(String error);
    }
}

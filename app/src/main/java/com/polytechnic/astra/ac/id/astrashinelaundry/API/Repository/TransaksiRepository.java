package com.polytechnic.astra.ac.id.astrashinelaundry.API.Repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.polytechnic.astra.ac.id.astrashinelaundry.API.ApiUtils;
import com.polytechnic.astra.ac.id.astrashinelaundry.API.Service.TransaksiService;
import com.polytechnic.astra.ac.id.astrashinelaundry.API.VO.TransaksiListVO;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TransaksiRepository {
    private static final String TAG = "TransaksiRepository";
    private static TransaksiRepository INSTANCE;
    private TransaksiService mTransaksiService;

    private TransaksiRepository(Context context) {
        mTransaksiService = ApiUtils.getAllTransaksiByStatus();
    }

    public static void initialize(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new TransaksiRepository(context);
        }
    }

    public static TransaksiRepository get() {
        return INSTANCE;
    }

    public MutableLiveData<TransaksiListVO> getAllTransaksiByStatus(String status) {
        Log.i(TAG, "getAllTransaksiByStatus() called");
        MutableLiveData<TransaksiListVO> dataLogin = new MutableLiveData<>();

        Call<TransaksiListVO> call = mTransaksiService.getAllTransaksiByStatus(status);
        call.enqueue(new Callback<TransaksiListVO>() {
            @Override
            public void onResponse(Call<TransaksiListVO> call, Response<TransaksiListVO> response) {
                if (response.isSuccessful() && response.body() != null) {
                    dataLogin.setValue(response.body());
                    Log.d(TAG, "getUserLogin.onResponse() called");
                    Log.d(TAG, response.body().getData().toString());
                } else {
                    // Logika untuk menangani kasus ketika response body null atau response tidak sukses
                    Log.e(TAG, "Response unsuccessful or body is null");
                }
            }

            @Override
            public void onFailure(Call<TransaksiListVO> call, Throwable throwable) {
                Log.e("Error API Call : ", throwable.getMessage());
            }
        });

        return dataLogin;
    }

    public MutableLiveData<TransaksiListVO> getTransaksiCustPickUp(String idUser) {
        Log.i(TAG, "getTransaksiCustPickUp() called");
        MutableLiveData<TransaksiListVO> dataLogin = new MutableLiveData<>();

        Call<TransaksiListVO> call = mTransaksiService.getTransaksiCustPickUp(idUser);
        call.enqueue(new Callback<TransaksiListVO>() {
            @Override
            public void onResponse(Call<TransaksiListVO> call, Response<TransaksiListVO> response) {
                if (response.isSuccessful() && response.body() != null) {
                    dataLogin.setValue(response.body());
                    Log.d(TAG, "getUserLogin.onResponse() called");
                    Log.d(TAG, response.body().getData().toString());
                } else {
                    // Logika untuk menangani kasus ketika response body null atau response tidak sukses
                    Log.e(TAG, "Response unsuccessful or body is null");
                }
            }

            @Override
            public void onFailure(Call<TransaksiListVO> call, Throwable throwable) {
                Log.e("Error API Call : ", throwable.getMessage());
            }
        });

        return dataLogin;
    }

    public LiveData<TransaksiListVO> batalkanTrsKurir(String idTransaksi, String catatan) {
        Log.i(TAG, "batalkanTrsKurir() called");
        MutableLiveData<TransaksiListVO> dataTransaksi = new MutableLiveData<>();

        Call<TransaksiListVO> call = mTransaksiService.batalkanTrsKurir(idTransaksi, catatan);
        call.enqueue(new Callback<TransaksiListVO>() {
            @Override
            public void onResponse(Call<TransaksiListVO> call, Response<TransaksiListVO> response) {
                if (response.isSuccessful() && response.body() != null) {
                    dataTransaksi.setValue(response.body());
                } else {
                    Log.e(TAG, "Response unsuccessful or body is null: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<TransaksiListVO> call, Throwable t) {
                Log.e(TAG, "Failed to make API call: " + t.getMessage());
            }
        });

        return dataTransaksi;
    }


}

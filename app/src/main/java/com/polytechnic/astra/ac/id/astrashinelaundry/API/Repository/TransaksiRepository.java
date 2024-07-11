package com.polytechnic.astra.ac.id.astrashinelaundry.API.Repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.polytechnic.astra.ac.id.astrashinelaundry.API.ApiUtils;
import com.polytechnic.astra.ac.id.astrashinelaundry.API.Service.TransaksiService;
import com.polytechnic.astra.ac.id.astrashinelaundry.API.VO.DetailTransaksiVO;
import com.polytechnic.astra.ac.id.astrashinelaundry.API.VO.TransaksiListVO;
import com.polytechnic.astra.ac.id.astrashinelaundry.API.VO.UserVO;
import com.polytechnic.astra.ac.id.astrashinelaundry.Model.DetailTransaksiModel;
import com.polytechnic.astra.ac.id.astrashinelaundry.Model.UserModel;

import java.util.List;

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
    public void createDetailTransaksi(List<DetailTransaksiModel> detailTransaksi,final TransaksiRepository.messageCallback callback) {
        Log.i(TAG, "createDetailTransaksi() called");
        Call<DetailTransaksiVO> call = mTransaksiService.createDetailTransaksi(detailTransaksi);
        for(int i = 0;i < detailTransaksi.size();i++){
            Log.d(TAG,"ID Transaksi :"+detailTransaksi.get(i).getIdTransaksi().toString());
            Log.d(TAG,"ID Layanan :"+detailTransaksi.get(i).getIdLayanan().toString());
            Log.d(TAG,"Nama Layanan :"+detailTransaksi.get(i).getNamaLayanan().toString());
            Log.d(TAG,"Qty Layanan :"+detailTransaksi.get(i).getQty().toString());
        }
        call.enqueue(new Callback<DetailTransaksiVO>() {
            @Override
            public void onResponse(Call<DetailTransaksiVO> call, Response<DetailTransaksiVO> response) {
                Log.d(TAG, "createDetailTransaksi.onResponse() called");
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body().getMessage());
                    Log.d(TAG, response.message());
                } else {
                    callback.onError("Create Detail Transaksi Gagal");
                }
            }

            @Override
            public void onFailure(Call<DetailTransaksiVO> call, Throwable throwable) {
                Log.e(TAG, "Error API Call: " + throwable.getMessage());
            }
        });
    }

    public interface messageCallback {
        void onSuccess(String message);
        void onError(String error);
    }

}

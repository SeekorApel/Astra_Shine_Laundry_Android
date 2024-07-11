package com.polytechnic.astra.ac.id.astrashinelaundry.API.Repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.polytechnic.astra.ac.id.astrashinelaundry.API.ApiUtils;
import com.polytechnic.astra.ac.id.astrashinelaundry.API.Service.LayananService;
import com.polytechnic.astra.ac.id.astrashinelaundry.API.Service.TransaksiService;
import com.polytechnic.astra.ac.id.astrashinelaundry.API.VO.LayananVO;
import com.polytechnic.astra.ac.id.astrashinelaundry.API.VO.TransaksiListVO;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LayananRepository {
    private static final String TAG = "LayananRepository";
    private static LayananRepository INSTANCE;
    private LayananService mLayananService;
    private LayananRepository(Context context) {
        mLayananService = ApiUtils.getAllLayanan();
    }

    public static void initialize(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new LayananRepository(context);
        }
    }

    public static LayananRepository get() {
        return INSTANCE;
    }

    public MutableLiveData<LayananVO> getAllLayanan() {
        Log.i(TAG, "getAllLayanan() called");
        MutableLiveData<LayananVO> dataLayanan = new MutableLiveData<>();

        Call<LayananVO> call = mLayananService.getAllLayanan();
        call.enqueue(new Callback<LayananVO>() {
            @Override
            public void onResponse(Call<LayananVO> call, Response<LayananVO> response) {
                if (response.isSuccessful() && response.body() != null) {
                    dataLayanan.setValue(response.body());
                    Log.d(TAG, "getAllLayanan.onResponse() called");
                    Log.d(TAG, response.body().getData().toString());
                } else {
                    // Logika untuk menangani kasus ketika response body null atau response tidak sukses
                    Log.e(TAG, "Response unsuccessful or body is null");
                }
            }

            @Override
            public void onFailure(Call<LayananVO> call, Throwable throwable) {
                Log.e("Error API Call : ", throwable.getMessage());
            }
        });

        return dataLayanan;
    }
}

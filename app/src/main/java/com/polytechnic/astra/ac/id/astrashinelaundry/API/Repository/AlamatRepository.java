package com.polytechnic.astra.ac.id.astrashinelaundry.API.Repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.polytechnic.astra.ac.id.astrashinelaundry.API.ApiUtils;
import com.polytechnic.astra.ac.id.astrashinelaundry.API.Service.AlamatService;
import com.polytechnic.astra.ac.id.astrashinelaundry.API.VO.AlamatListVO;
import com.polytechnic.astra.ac.id.astrashinelaundry.API.VO.AlamatVO;
import com.polytechnic.astra.ac.id.astrashinelaundry.Model.AlamatModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlamatRepository {

    private static final String TAG = "AlamatRepository";
    private static AlamatRepository INSTANCE;
    private AlamatService mAlamatService;

    private AlamatRepository (Context context){
        mAlamatService = ApiUtils.getAlamatService();
    }

    public static void initialize(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new AlamatRepository(context);
        }
    }

    public static AlamatRepository get(){
        return INSTANCE;
    }

    public MutableLiveData<AlamatListVO> getAllAlamatByUserId(Integer idUser){
        Log.i(TAG, "getAllAlamatByUserId() called");
        MutableLiveData<AlamatListVO> dataAlamat = new MutableLiveData<>();

        Call<AlamatListVO> call = mAlamatService.getAlamatByUserId(idUser);
        call.enqueue(new Callback<AlamatListVO>() {
            @Override
            public void onResponse(Call<AlamatListVO> call, Response<AlamatListVO> response) {
                if(response.isSuccessful() && response.body() != null){
                    dataAlamat.setValue(response.body());
                    Log.d(TAG, "getAllAlamatByUserId.onResponse() called");
                }else{
                    Log.e(TAG, "Response unsuccessful or body is null");
                }
            }

            @Override
            public void onFailure(Call<AlamatListVO> call, Throwable throwable) {
                Log.e("Error API Call : ", throwable.getMessage());
            }
        });
        return dataAlamat;
    }

    public MutableLiveData<AlamatVO> getAlamatLaundry(){
        Log.i(TAG, "getAlamatLaundry() called");
        MutableLiveData<AlamatVO> dataAlamatLaundry = new MutableLiveData<>();
        Call<AlamatVO> call = mAlamatService.getAlamatLaundry();
        call.enqueue(new Callback<AlamatVO>() {
            @Override
            public void onResponse(Call<AlamatVO> call, Response<AlamatVO> response) {
                if(response.isSuccessful() && response.body() != null){
                    dataAlamatLaundry.setValue(response.body());
                    Log.d(TAG, "getAlamatLaundry.onResponse() called");
                }else{
                    Log.e(TAG, "Response unsuccessful or body is null");
                }
            }

            @Override
            public void onFailure(Call<AlamatVO> call, Throwable throwable) {
                Log.e("Error API Call : ", throwable.getMessage());
            }
        });

        return dataAlamatLaundry;
    }

    public void saveAlamat(AlamatModel alamat, final messageCallback callback){
        Log.i(TAG, "saveAlamat() called");
        Call<AlamatVO> call = mAlamatService.saveAlamat(alamat);
        call.enqueue(new Callback<AlamatVO>() {
            @Override
            public void onResponse(Call<AlamatVO> call, Response<AlamatVO> response) {
                Log.d(TAG, "saveAlamat.onResponse() called");
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body().getMessage());
                    Log.d(TAG, response.message());
                } else {
                    callback.onError("Save Alamat Gagal");
                }
            }

            @Override
            public void onFailure(Call<AlamatVO> call, Throwable throwable) {
                Log.e("Error API Call : ", throwable.getMessage());
            }
        });
    }

    public void updateAlamat(AlamatModel updateAlamat, final messageCallback callback){
        Log.i(TAG, "updateAlamat() called");
        Call<AlamatVO> call = mAlamatService.updateAlamat(updateAlamat);
        call.enqueue(new Callback<AlamatVO>() {
            @Override
            public void onResponse(Call<AlamatVO> call, Response<AlamatVO> response) {
                Log.d(TAG, "updateAlamat.onResponse() called");
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body().getMessage());
                    Log.d(TAG, response.message());
                } else {
                    callback.onError("Update Alamat Gagal");
                }
            }

            @Override
            public void onFailure(Call<AlamatVO> call, Throwable throwable) {
                Log.e("Error API Call : ", throwable.getMessage());
            }
        });
    }

    public void deleteAlamat(Integer idAlamat, final messageCallback callback){
        Log.i(TAG, "deleteAlamat() called");
        Call<AlamatVO> call = mAlamatService.deleteAlamat(idAlamat);
        call.enqueue(new Callback<AlamatVO>() {
            @Override
            public void onResponse(Call<AlamatVO> call, Response<AlamatVO> response) {
                Log.d(TAG, "deleteAlamat.onResponse() called");
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body().getMessage());
                } else {
                    callback.onError("Registrasi Gagal");
                }
            }

            @Override
            public void onFailure(Call<AlamatVO> call, Throwable throwable) {
                Log.e("Error API Call : ", throwable.getMessage());
            }
        });
    }

    public interface messageCallback {
        void onSuccess(String message);
        void onError(String error);
    }

}

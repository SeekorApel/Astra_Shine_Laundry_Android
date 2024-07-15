package com.polytechnic.astra.ac.id.astrashinelaundry.API.Repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.polytechnic.astra.ac.id.astrashinelaundry.API.ApiUtils;
import com.polytechnic.astra.ac.id.astrashinelaundry.API.Service.TransaksiService;
import com.polytechnic.astra.ac.id.astrashinelaundry.API.VO.DetailTransaksiVo;
import com.polytechnic.astra.ac.id.astrashinelaundry.API.VO.DurasiVo;
import com.polytechnic.astra.ac.id.astrashinelaundry.API.VO.TransaksiListVO;
import com.polytechnic.astra.ac.id.astrashinelaundry.API.VO.UserVO;
import com.polytechnic.astra.ac.id.astrashinelaundry.Model.DetailTransaksiModel;
import com.polytechnic.astra.ac.id.astrashinelaundry.Model.UserModel;

import java.util.List;

import java.util.List;

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

    public MutableLiveData<DetailTransaksiVO> getDetailTransaksi(String idTransaksi) {
        Log.i(TAG, idTransaksi);
        MutableLiveData<DetailTransaksiVO> dataLogin = new MutableLiveData<>();

        Call<DetailTransaksiVO> call = mTransaksiService.getDetailTransaksi(idTransaksi);
        call.enqueue(new Callback<DetailTransaksiVO>() {
            @Override
            public void onResponse(Call<DetailTransaksiVO> call, Response<DetailTransaksiVO> response) {
                if (response.isSuccessful() && response.body() != null) {
                    dataLogin.setValue(response.body());
                    Log.d(TAG, "getDetailTransaksi.onResponse() called");
                    Log.d(TAG, response.body().getData().toString());
                } else {
                    // Logika untuk menangani kasus ketika response body null atau response tidak sukses
                    Log.e(TAG, "Response unsuccessful or body is null");
                }
            }

            @Override
            public void onFailure(Call<DetailTransaksiVO> call, Throwable throwable) {
                Log.e("Error API Call : ", throwable.getMessage());
            }
        });

        return dataLogin;
    }

    public MutableLiveData<TransaksiListVO> getTransaksiCustPickUp(String idUser) {
        Log.i(TAG, "getTransaksiCustPickUp() called");
        MutableLiveData<TransaksiListVO> dataLogin = new MutableLiveData<>();

        Call<TransaksiListVO> call = mTransaksiService.getTransaksiByIdAndStatus(idUser, status);
        call.enqueue(new Callback<TransaksiListVO>() {
            @Override
            public void onResponse(Call<TransaksiListVO> call, Response<TransaksiListVO> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d(TAG, "getUserLogin.onResponse() called");
                    Log.d(TAG, response.body().getData().toString());
                    dataTransaksi.setValue(response.body());
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

        return dataTransaksi;
    }

    public LiveData<TransaksiListVO> batalkanTrsKurir(String idTransaksi, String catatan) {
        Log.i(TAG, "batalkanTrsKurir() called");
        MutableLiveData<TransaksiListVO> dataTransaksi = new MutableLiveData<>();

        Call<TransaksiListVO> call = mTransaksiService.batalkanTrsKurir(idTransaksi, catatan);
        return dataTransaksi;
    }

    public MutableLiveData<TransaksiListVO> getTransaksiById(Integer idUser) {
        Log.i(TAG, "getUserByEmailAndPassword() called");
        MutableLiveData<TransaksiListVO> dataTransaksi = new MutableLiveData<>();

        Call<TransaksiListVO> call = mTransaksiService.getTransaksiById(idUser);
        call.enqueue(new Callback<TransaksiListVO>() {
            @Override
            public void onResponse(Call<TransaksiListVO> call, Response<TransaksiListVO> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d(TAG, "getUserLogin.onResponse() called");
                    Log.d(TAG, response.body().getData().toString());
                    dataTransaksi.setValue(response.body());
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

        return dataTransaksi;
    }
    
    public void createDetailTransaksi(List<DetailTransaksiModel> detailTransaksi,final TransaksiRepository.messageCallback callback) {
        Log.i(TAG, "createDetailTransaksi() called");
        Call<DetailTransaksiVo> call = mTransaksiService.createDetailTransaksi(detailTransaksi);
        for(int i = 0;i < detailTransaksi.size();i++){
            Log.d(TAG,"ID Transaksi :"+detailTransaksi.get(i).getIdTransaksi().toString());
            Log.d(TAG,"ID Layanan :"+detailTransaksi.get(i).getIdLayanan().toString());
            Log.d(TAG,"Nama Layanan :"+detailTransaksi.get(i).getNamaLayanan().toString());
            Log.d(TAG,"Qty Layanan :"+detailTransaksi.get(i).getQty().toString());
        }
        call.enqueue(new Callback<DetailTransaksiVo>() {
            @Override
            public void onResponse(Call<DetailTransaksiVo> call, Response<DetailTransaksiVo> response) {
                Log.d(TAG, "createDetailTransaksi.onResponse() called");
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body().getMessage());
                } else {
                    callback.onError("Create Detail Transaksi Gagal");
                }
            }

            @Override
            public void onFailure(Call<DetailTransaksiVo> call, Throwable throwable) {
                Log.e(TAG, "Error API Call: " + throwable.getMessage());
            }
        });
    }

    public interface messageCallback {
        void onSuccess(String message);
        void onError(String error);
    }
    public MutableLiveData<DurasiVo> getDurasi() {
        Log.i(TAG, "getLayanan() called");
        MutableLiveData<DurasiVo> dataDurasi = new MutableLiveData<>();

        Call<DurasiVo> call = mTransaksiService.getDurasi();
        call.enqueue(new Callback<DurasiVo>() {
            @Override
            public void onResponse(Call<DurasiVo> call, Response<DurasiVo> response) {
                if (response.isSuccessful() && response.body() != null) {
                    dataDurasi.setValue(response.body());
                    Log.d(TAG, response.body().getData().toString());
                } else {
                    // Logika untuk menangani kasus ketika response body null atau response tidak sukses
                    Log.e(TAG, "Response unsuccessful or body is null");
                }
            }

            @Override
            public void onFailure(Call<DurasiVo> call, Throwable throwable) {
                Log.e("Error API Call : ", throwable.getMessage());
            }
        });

        return dataDurasi;
    }

    public MutableLiveData<DurasiVo> getDurasiById(Integer idDurasi) {
        Log.i(TAG, "getDurasiById() called");
        MutableLiveData<DurasiVo> dataDurasi = new MutableLiveData<>();

        Call<DurasiVo> call = mTransaksiService.getDurasiById(idDurasi);
        call.enqueue(new Callback<DurasiVo>() {
            @Override
            public void onResponse(Call<DurasiVo> call, Response<DurasiVo> response) {

                if (response.isSuccessful() && response.body() != null) {
                    Log.i(TAG, "getDurasiById() hihih1");
                    Log.d(TAG,response.body().getData().toString());
                    dataDurasi.setValue(response.body());
                } else {
                    // Logika untuk menangani kasus ketika response body null atau response tidak sukses
                    Log.e(TAG, "Response unsuccessful or body is null");
                }
            }

            @Override
            public void onFailure(Call<DurasiVo> call, Throwable throwable) {
                Log.e("Error API Call : ", throwable.getMessage());
            }
        });

        return dataDurasi;
    }

    public MutableLiveData<DetailTransaksiVo> getDetailTransaksi(Integer idTransaksi) {
        Log.i(TAG, "getDetailTransaksi() called");
        MutableLiveData<DetailTransaksiVo> dataTransaksi = new MutableLiveData<>();

        Call<DetailTransaksiVo> call = mTransaksiService.getTransaksiDetail(idTransaksi);
        call.enqueue(new Callback<DetailTransaksiVo>() {
            @Override
            public void onResponse(Call<DetailTransaksiVo> call, Response<DetailTransaksiVo> response) {
                if (response.isSuccessful() && response.body() != null) {
                    dataTransaksi.setValue(response.body());
                } else {
                    Log.e("TransaksiRepository", "Respon tidak berhasil atau body null");
                }
            }

            @Override
            public void onFailure(Call<DetailTransaksiVo> call, Throwable t) {
                Log.e("TransaksiRepository", "Kesalahan panggilan API: " + t.getMessage());
            }
        });

        return dataTransaksi;
    }

    public MutableLiveData<TransaksiListVO> getHargaTotal(Integer idTransaksi) {
        Log.i(TAG, "getHargaTotal() called");
        MutableLiveData<TransaksiListVO> dataTransaksi = new MutableLiveData<>();

        Call<TransaksiListVO> call = mTransaksiService.getTotalHarga(idTransaksi);
        call.enqueue(new Callback<TransaksiListVO>() {
            @Override
            public void onResponse(Call<TransaksiListVO> call, Response<TransaksiListVO> response) {
                if (response.isSuccessful() && response.body() != null) {
                    System.out.println("sigma");
                    dataTransaksi.setValue(response.body());
                } else {
                    Log.e("TransaksiRepository", "Respon tidak berhasil atau body null");
                }
            }

            @Override
            public void onFailure(Call<TransaksiListVO> call, Throwable t) {
                Log.e("TransaksiRepository", "Kesalahan panggilan API: " + t.getMessage());
            }
        });

        return dataTransaksi;
    }
}

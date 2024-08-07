package com.polytechnic.astra.ac.id.astrashinelaundry.ViewModel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.polytechnic.astra.ac.id.astrashinelaundry.API.Repository.TransaksiRepository;
import com.polytechnic.astra.ac.id.astrashinelaundry.API.VO.DetailTransaksiVo;
import com.polytechnic.astra.ac.id.astrashinelaundry.API.VO.TransaksiListVO;
import com.polytechnic.astra.ac.id.astrashinelaundry.API.VO.TransaksiVO;
import com.polytechnic.astra.ac.id.astrashinelaundry.Model.TransaksiListModel;

import java.util.List;

public class TransaksiListViewModel extends ViewModel {
    private static final String TAG = "LoginViewModel";

    private MutableLiveData<TransaksiListVO> transaksiResponse = new MutableLiveData<>();

    private MutableLiveData<DetailTransaksiVo> detailResponse = new MutableLiveData<>();

    private LiveData<TransaksiVO> transaksiRspns = new MutableLiveData<>();
    private LiveData<TransaksiVO> transaksiRps = new MutableLiveData<>();
    private LiveData<TransaksiVO> update = new MutableLiveData<>();
    private MutableLiveData<String> successMessage = new MutableLiveData<>();
    private MutableLiveData<String> errorMessage = new MutableLiveData<>();
    private final TransaksiRepository mTransaksiRepository;

    public TransaksiListViewModel(){
        mTransaksiRepository = TransaksiRepository.get();
    }

    public LiveData<TransaksiListVO> getAllTransaksiResponse() {
        return transaksiResponse;
    }
    public LiveData<TransaksiVO> getBatalResponse() {
        return transaksiRspns;
    }
    public LiveData<TransaksiVO> getTransaksiResponse() {
        return transaksiRps;
    }
    public LiveData<TransaksiVO> getTransaksiUpdateResponse() {
        return update;
    }
    public LiveData<DetailTransaksiVo> getAllDetailResponse() {
        return detailResponse;
    }

    public void getDataTransaksi(String status) {
        Log.i(TAG, "getDataTransaksiLiveData() called");
        mTransaksiRepository.getAllTransaksiByStatus(status).observeForever(new Observer<TransaksiListVO>() {
            @Override
            public void onChanged(TransaksiListVO transaksiListVO) {
                transaksiResponse.postValue(transaksiListVO);
            }
        });
    }


    public void batalkanTrsKurir(String idTransaksi, String catatan) {
        Log.i(TAG, "batalkanTrsKurir() called");
        transaksiRspns = mTransaksiRepository.batalkanTrsKurir(idTransaksi, catatan);
    }

    public void updatePembayaran(String idTransaksi) {
        Log.i(TAG, "updatePembayaran() called");
        transaksiRps = mTransaksiRepository.updatePembayaran(idTransaksi);
    }

    public void updateStatus(String idTransaksi) {
        Log.i(TAG, "updateStatus() called");
        update = mTransaksiRepository.updateStatus(idTransaksi);
    }

    public void getTransaksiByIdAndStatus(Integer idUser, String status) {
        Log.i(TAG, "getDataTransaksiLiveData() called");
        transaksiResponse = mTransaksiRepository.getTransaksiByIdAndStatus(idUser, status);
    }

    public void getTransaksiById(Integer idUser) {
        Log.i(TAG, "getDataTransaksiLiveData() called");
        transaksiResponse = mTransaksiRepository.getTransaksiById(idUser);
    }

    public void getTransaksiDetail(Integer idTransaksi) {
        Log.i(TAG, "getDataTransaksiLiveData() called");
        detailResponse = mTransaksiRepository.getDetailTransaksi(idTransaksi);
    }

    public void getHargaTotal(Integer idTransaksi) {
        Log.i(TAG, "getDataTransaksiLiveData() called");
        transaksiResponse = mTransaksiRepository.getHargaTotal(idTransaksi);
    }

    public LiveData<String> getSuccessResponse(){
        return successMessage;
    }

    public LiveData<String> getErrorResponse(){
        return errorMessage;
    }


    public void saveTransaksi(TransaksiListModel transaksi){
        Log.i(TAG, "saveTransaksi() called");
        mTransaksiRepository.saveTransaksi(transaksi, new TransaksiRepository.messageCallback() {
            @Override
            public void onSuccess(String message) {
                successMessage.postValue(message);
            }

            @Override
            public void onError(String error) {
                errorMessage.postValue(error);
            }
        });
    }

}

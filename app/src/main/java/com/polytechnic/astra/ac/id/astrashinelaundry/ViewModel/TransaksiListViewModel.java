package com.polytechnic.astra.ac.id.astrashinelaundry.ViewModel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.polytechnic.astra.ac.id.astrashinelaundry.API.Repository.TransaksiRepository;
import com.polytechnic.astra.ac.id.astrashinelaundry.API.VO.TransaksiListVO;

import java.util.List;

public class TransaksiListViewModel extends ViewModel {
    private static final String TAG = "LoginViewModel";

    private MutableLiveData<TransaksiListVO> transaksiResponse = new MutableLiveData<>();

    private final TransaksiRepository mTransaksiRepository;

    public TransaksiListViewModel(){
        mTransaksiRepository = TransaksiRepository.get();
    }

    public LiveData<TransaksiListVO> getAllTransaksiResponse() {
        return transaksiResponse;
    }

    public void getDataTransaksi(String status) {
        Log.i(TAG, "getDataTransaksiLiveData() called");
        transaksiResponse = mTransaksiRepository.getAllTransaksiByStatus(status);
    }

    public void getDataTransaksibyUser(String idUser) {
        Log.i(TAG, "getDataTransaksiLiveData() called");
        transaksiResponse = mTransaksiRepository.getTransaksiCustPickUp(idUser);
    }
}

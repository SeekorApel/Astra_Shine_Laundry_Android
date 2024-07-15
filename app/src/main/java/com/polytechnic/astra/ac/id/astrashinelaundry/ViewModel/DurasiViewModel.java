package com.polytechnic.astra.ac.id.astrashinelaundry.ViewModel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.polytechnic.astra.ac.id.astrashinelaundry.API.Repository.TransaksiRepository;
import com.polytechnic.astra.ac.id.astrashinelaundry.API.VO.DurasiVo;

public class DurasiViewModel extends ViewModel {
    private static final String TAG = "DurasiViewModel";

    private MutableLiveData<DurasiVo> durasiResponse = new MutableLiveData<>();

    private final TransaksiRepository mTransaksiRepository;

    public DurasiViewModel(){
        mTransaksiRepository = TransaksiRepository.get();
    }

    public LiveData<DurasiVo> getAllDurasiResponse() {
        return durasiResponse;
    }

    public void getDataDurasi() {
        Log.i(TAG, "getDataDurasiLiveData() called");
        durasiResponse = mTransaksiRepository.getDurasi();
    }

    public void getDataDurasiById(Integer idDurasi) {
        Log.i(TAG, "getDataDurasiByIdLiveData() called");
        durasiResponse = mTransaksiRepository.getDurasiById(idDurasi);
    }
}

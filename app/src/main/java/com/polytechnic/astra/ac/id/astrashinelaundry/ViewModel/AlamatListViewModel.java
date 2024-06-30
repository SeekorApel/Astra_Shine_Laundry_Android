package com.polytechnic.astra.ac.id.astrashinelaundry.ViewModel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.polytechnic.astra.ac.id.astrashinelaundry.API.Repository.AlamatRepository;
import com.polytechnic.astra.ac.id.astrashinelaundry.API.VO.AlamatListVO;

public class AlamatListViewModel extends ViewModel {

    private static final String TAG = "AlamatListViewModel";

    private MutableLiveData<AlamatListVO> listAlamatResponse = new MutableLiveData<>();

    private final AlamatRepository mAlamatRepository;

    public AlamatListViewModel(){
        mAlamatRepository = AlamatRepository.get();
    }

    public LiveData<AlamatListVO> getAllAlamatResponse(){
        return listAlamatResponse;
    }

    public void getDataAlamat(Integer idUser){
        Log.i(TAG, "getDataAlamat() called");
        listAlamatResponse = mAlamatRepository.getAllAlamatByUserId(idUser);
    }

}

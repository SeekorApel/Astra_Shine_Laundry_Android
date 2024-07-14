package com.polytechnic.astra.ac.id.astrashinelaundry.ViewModel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.polytechnic.astra.ac.id.astrashinelaundry.API.Repository.AlamatRepository;
import com.polytechnic.astra.ac.id.astrashinelaundry.API.VO.AlamatListVO;
import com.polytechnic.astra.ac.id.astrashinelaundry.API.VO.AlamatVO;
import com.polytechnic.astra.ac.id.astrashinelaundry.Model.AlamatModel;

public class AlamatViewModel extends ViewModel {

    private static final String TAG = "AlamatViewModel";

    private MutableLiveData<AlamatListVO> listAlamatResponse = new MutableLiveData<>();
    private MutableLiveData<AlamatVO> getAlamatLaundryResponse = new MutableLiveData<>();
    private MutableLiveData<String> successMessage = new MutableLiveData<>();
    private MutableLiveData<String> errorMessage = new MutableLiveData<>();

    private final AlamatRepository mAlamatRepository;

    public AlamatViewModel(){
        mAlamatRepository = AlamatRepository.get();
    }

    public LiveData<AlamatListVO> getAllAlamatResponse(){
        return listAlamatResponse;
    }

    public LiveData<AlamatVO> getAlamatLaundryResponse(){
        return getAlamatLaundryResponse;
    }

    public LiveData<String> getSuccessResponse(){
        return successMessage;
    }

    public LiveData<String> getErrorResponse(){
        return errorMessage;
    }

    public void getDataAlamat(Integer idUser){
        Log.i(TAG, "getDataAlamat() called");
        listAlamatResponse = mAlamatRepository.getAllAlamatByUserId(idUser);
    }

    public void getDataAlamatLaundry(){
        Log.i(TAG, "getDataAlamatLaundry() called");
        getAlamatLaundryResponse = mAlamatRepository.getAlamatLaundry();
    }

    public void saveAlamat(AlamatModel alamat){
        Log.i(TAG, "saveAlamat() called");
        mAlamatRepository.saveAlamat(alamat, new AlamatRepository.messageCallback() {
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

    public void updateAlamat(AlamatModel updateAlamat){
        Log.i(TAG, "updateAlamat() called");
        mAlamatRepository.updateAlamat(updateAlamat, new AlamatRepository.messageCallback() {
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

    public void deleteAlamat(Integer idAlamat){
        Log.i(TAG, "deleteAlamat() called");
        mAlamatRepository.deleteAlamat(idAlamat, new AlamatRepository.messageCallback() {
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

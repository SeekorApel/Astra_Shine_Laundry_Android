package com.polytechnic.astra.ac.id.astrashinelaundry.ViewModel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.polytechnic.astra.ac.id.astrashinelaundry.API.Repository.UserRepository;

public class ForgetPasswordViewModel extends ViewModel {

    private static final String TAG = "ForgetPasswordViewModel";

    private MutableLiveData<String> successMessage = new MutableLiveData<>();
    private MutableLiveData<String> errorMessage = new MutableLiveData<>();

    private final UserRepository mUserRepository;

    public ForgetPasswordViewModel (){
        mUserRepository = UserRepository.get();
    }

    public LiveData<String> getSuccessResponse() {
        return successMessage;
    }

    public LiveData<String> getErrorResponse() {
        return errorMessage;
    }

    public void resetPasswordByEmail(String email) {
        Log.i(TAG, "resetPasswordByEmail() called");
        mUserRepository.resetPasswordByEmail(email, new UserRepository.messageCallback() {
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

    public void resetPasswordById(Integer idUser, String newPassword){
        Log.i(TAG, "resetPasswordById() called");
        mUserRepository.resetPasswordById(idUser, newPassword, new UserRepository.messageCallback() {
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

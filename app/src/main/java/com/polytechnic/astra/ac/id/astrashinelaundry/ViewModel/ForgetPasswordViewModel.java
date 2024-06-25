package com.polytechnic.astra.ac.id.astrashinelaundry.ViewModel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.polytechnic.astra.ac.id.astrashinelaundry.API.Repository.UserRepository;

public class ForgetPasswordViewModel extends ViewModel {

    private static final String TAG = "ForgetPasswordViewModel";

    private MutableLiveData<String> resetPasswordMessage = new MutableLiveData<>();
    private MutableLiveData<String> errorMessage = new MutableLiveData<>();

    private final UserRepository mUserRepository;

    public ForgetPasswordViewModel (){
        mUserRepository = UserRepository.get();
    }

    public LiveData<String> getResetPasswordMessage() {
        return resetPasswordMessage;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public void resetPasswordByEmail(String email) {
        Log.i(TAG, "resetPasswordByEmail() called");
        mUserRepository.resetPasswordByEmail(email, new UserRepository.messageCallback() {
            @Override
            public void onSuccess(String message) {
                resetPasswordMessage.postValue(message);
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
                resetPasswordMessage.postValue(message);
            }

            @Override
            public void onError(String error) {
                errorMessage.postValue(error);
            }
        });
    }

}

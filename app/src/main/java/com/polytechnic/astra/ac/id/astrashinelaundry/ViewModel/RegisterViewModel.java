package com.polytechnic.astra.ac.id.astrashinelaundry.ViewModel;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.polytechnic.astra.ac.id.astrashinelaundry.API.Repository.UserRepository;
import com.polytechnic.astra.ac.id.astrashinelaundry.Model.UserModel;

public class RegisterViewModel extends ViewModel {

    private static final String TAG = "RegisterViewModel";

    private MutableLiveData<String> registerMessage = new MutableLiveData<>();
    private MutableLiveData<String> errorMessage = new MutableLiveData<>();

    private final UserRepository mUserRepository;

    public RegisterViewModel(){
       this.mUserRepository = UserRepository.get();
    }

    public LiveData<String> getRegisterMessage() {
        return registerMessage;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public void registerUser(UserModel user) {
        Log.i(TAG, "registerUser() called");
        mUserRepository.registerUser(user, new UserRepository.messageCallback() {
            @Override
            public void onSuccess(String message) {
                registerMessage.postValue(message);
            }

            @Override
            public void onError(String error) {
                errorMessage.postValue(error);
            }
        });
    }

}

package com.polytechnic.astra.ac.id.astrashinelaundry.ViewModel;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.polytechnic.astra.ac.id.astrashinelaundry.API.Repository.UserRepository;
import com.polytechnic.astra.ac.id.astrashinelaundry.API.VO.UserVO;
import com.polytechnic.astra.ac.id.astrashinelaundry.Model.UserModel;

public class RegisterViewModel extends ViewModel {

    private static final String TAG = "RegisterViewModel";

    private MutableLiveData<UserVO> response = new MutableLiveData<>();

    private final UserRepository mUserRepository;

    public RegisterViewModel(){
       this.mUserRepository = UserRepository.get();
    }

    public LiveData<UserVO> getResponse() {
        return response;
    }

    public void registerUser(UserModel user) {
        Log.i(TAG, "registerUser() called");
        response = mUserRepository.registerUser(user);
    }

}

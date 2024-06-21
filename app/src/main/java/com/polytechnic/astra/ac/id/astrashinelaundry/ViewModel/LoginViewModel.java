package com.polytechnic.astra.ac.id.astrashinelaundry.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.polytechnic.astra.ac.id.astrashinelaundry.API.Repository.UserRepository;
import com.polytechnic.astra.ac.id.astrashinelaundry.API.VO.LoginVO;

public class LoginViewModel extends ViewModel {

    private MutableLiveData<LoginVO> loginResponse = new MutableLiveData<>();

    private UserRepository mUserRepository;

    public LoginViewModel(){
        mUserRepository = UserRepository.get();
    }

    public LiveData<LoginVO> getLoginResponse() {
        return loginResponse;
    }

    public void login(String email, String password) {
        loginResponse = mUserRepository.getUserLogin(email, password);
    }


}

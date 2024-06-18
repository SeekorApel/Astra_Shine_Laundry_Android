package com.polytechnic.astra.ac.id.astrashinelaundry.API.Service;

import com.polytechnic.astra.ac.id.astrashinelaundry.API.VO.LoginVO;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface UserService {

    @GET("user/getUserByEmailAndPassword")
    Call<LoginVO> getUserByEmailAndPassword(@Query("email") String email, @Query("password") String password);
}

package com.polytechnic.astra.ac.id.astrashinelaundry.API.Service;

import com.polytechnic.astra.ac.id.astrashinelaundry.API.VO.UserVO;
import com.polytechnic.astra.ac.id.astrashinelaundry.API.VO.ForgetPasswordVO;
import com.polytechnic.astra.ac.id.astrashinelaundry.Model.UserModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface UserService {

    @GET("user/getUserByEmailAndPassword")
    Call<UserVO> getUserByEmailAndPassword(@Query("email") String email, @Query("password") String password);

    @POST("user/resetPasswordByEmail")
    Call<ForgetPasswordVO> resetPasswordByEmail(@Query("email") String email);

    @POST("user/registerUser")
    Call<UserVO> registerUser(@Body UserModel user);

    @POST("user/resetPassword")
    Call<UserVO> resetPasswordById(@Query("idUser") Integer idUser, @Query("newPassword") String newPassword, @Query("oldPassword") String oldPassword);
}

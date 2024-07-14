package com.polytechnic.astra.ac.id.astrashinelaundry.API.Service;

import com.polytechnic.astra.ac.id.astrashinelaundry.API.VO.AlamatListVO;
import com.polytechnic.astra.ac.id.astrashinelaundry.API.VO.AlamatVO;
import com.polytechnic.astra.ac.id.astrashinelaundry.Model.AlamatModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface AlamatService {

    @GET("alamat/getAlamatLaundry")
    Call<AlamatVO> getAlamatLaundry();

    @GET("alamat/getAlamatUser")
    Call<AlamatListVO> getAlamatByUserId(@Query("idUser") Integer idUser);

    @POST("alamat/saveAlamatUser")
    Call<AlamatVO> saveAlamat(@Body AlamatModel alamat);

    @POST("alamat/deleteAlamat")
    Call<AlamatVO> deleteAlamat(@Query("idAlamat") Integer idAlamat);

}

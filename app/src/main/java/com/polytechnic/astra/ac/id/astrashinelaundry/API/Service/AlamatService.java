package com.polytechnic.astra.ac.id.astrashinelaundry.API.Service;

import com.polytechnic.astra.ac.id.astrashinelaundry.API.VO.AlamatListVO;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface AlamatService {

    @GET("alamat/")
    Call<AlamatListVO> getAlamatByUserId(@Query("idUser") Integer idUser);

}

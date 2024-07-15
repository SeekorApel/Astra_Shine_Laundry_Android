package com.polytechnic.astra.ac.id.astrashinelaundry.API.Service;

import com.polytechnic.astra.ac.id.astrashinelaundry.API.VO.LayananVO;
import com.polytechnic.astra.ac.id.astrashinelaundry.API.VO.TransaksiListVO;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface LayananService {
    @GET("layanan/getAllLayanan")
    Call<LayananVO> getAllLayanan();

}

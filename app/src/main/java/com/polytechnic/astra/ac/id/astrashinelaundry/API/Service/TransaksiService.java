package com.polytechnic.astra.ac.id.astrashinelaundry.API.Service;

import com.polytechnic.astra.ac.id.astrashinelaundry.API.VO.TransaksiListVO;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TransaksiService {
    @GET("transaksi/getAllTransaksiByStatus")
    Call<TransaksiListVO> getAllTransaksiByStatus(@Query("status") String status);

    @GET("transaksi/getTransaksiCustPickUp")
    Call<TransaksiListVO> getTransaksiCustPickUp(@Query("idUser") String idUser);
}

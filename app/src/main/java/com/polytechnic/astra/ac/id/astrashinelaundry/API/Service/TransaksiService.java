package com.polytechnic.astra.ac.id.astrashinelaundry.API.Service;

import com.polytechnic.astra.ac.id.astrashinelaundry.API.VO.TransaksiListVO;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TransaksiService {
    @GET("transaksi/getAllTransaksiByStatus")
    Call<TransaksiListVO> getAllTransaksiByStatus(@Query("status") String status);

    @GET("transaksi/getTransaksiCustPickUp")
    Call<TransaksiListVO> getTransaksiCustPickUp(@Query("idUser") String idUser);

    @POST("transaksi/batalkanTrsKurir/{idTransaksi}/")
    Call<TransaksiListVO> batalkanTrsKurir(@Path("idTransaksi") String idTransaksi, @Query("catatan") String catatan);
}

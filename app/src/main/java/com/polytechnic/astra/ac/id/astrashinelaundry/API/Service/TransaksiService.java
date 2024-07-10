package com.polytechnic.astra.ac.id.astrashinelaundry.API.Service;

import com.polytechnic.astra.ac.id.astrashinelaundry.API.VO.DetailTransaksiVo;
import com.polytechnic.astra.ac.id.astrashinelaundry.API.VO.DurasiVo;
import com.polytechnic.astra.ac.id.astrashinelaundry.API.VO.TransaksiListVO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TransaksiService {
    @GET("transaksi/getAllTransaksiByStatus")
    Call<TransaksiListVO> getAllTransaksiByStatus(@Query("status") String status);

    @POST("transaksi/batalkanTrsKurir/{idTransaksi}/")
    Call<TransaksiListVO> batalkanTrsKurir(@Path("idTransaksi") String idTransaksi, @Query("catatan") String catatan);
    @GET("transaksi/getTransaksiByIdAndStatus")
    Call<TransaksiListVO> getTransaksiByIdAndStatus(@Query("idUser") Integer idUser, @Query("status") String status);

    @GET("transaksi/getTransaksiById")
    Call<TransaksiListVO> getTransaksiById(@Query("idUser") Integer idUser);

    @GET("durasi/getAllDurasi")
    Call<DurasiVo> getDurasi();

    @GET("durasi/getDurasiById")
    Call<DurasiVo> getDurasiById(@Query("idDurasi") Integer idDurasi);

    @GET("transaksi/getTransaksiDetail")
    Call<DetailTransaksiVo> getTransaksiDetail(@Query("idTransaksi") Integer idTransaksi);

}

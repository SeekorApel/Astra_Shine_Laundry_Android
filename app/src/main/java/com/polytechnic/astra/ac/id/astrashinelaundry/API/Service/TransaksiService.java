package com.polytechnic.astra.ac.id.astrashinelaundry.API.Service;

import com.polytechnic.astra.ac.id.astrashinelaundry.API.VO.AlamatVO;
import com.polytechnic.astra.ac.id.astrashinelaundry.API.VO.DetailTransaksiVo;
import com.polytechnic.astra.ac.id.astrashinelaundry.API.VO.DurasiVo;
import com.polytechnic.astra.ac.id.astrashinelaundry.API.VO.TransaksiListVO;
import com.polytechnic.astra.ac.id.astrashinelaundry.API.VO.UserVO;
import com.polytechnic.astra.ac.id.astrashinelaundry.Model.AlamatModel;
import com.polytechnic.astra.ac.id.astrashinelaundry.Model.DetailTransaksiModel;
import com.polytechnic.astra.ac.id.astrashinelaundry.Model.TransaksiListModel;
import com.polytechnic.astra.ac.id.astrashinelaundry.Model.TransaksiModel;
import com.polytechnic.astra.ac.id.astrashinelaundry.Model.UserModel;
import java.util.List;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TransaksiService {
    @GET("transaksi/getAllTransaksiByStatus")
    Call<TransaksiListVO> getAllTransaksiByStatus(@Query("status") String status);

    @GET("transaksi/getTransaksiCustPickUp")
    Call<TransaksiListVO> getTransaksiCustPickUp(@Query("idUser") String idUser);

    @GET("transaksi/getTransaksiDetail")
    Call<DetailTransaksiVo> getDetailTransaksi(@Query("idTransaksi") String idTransaksi);

    @POST("transaksi/saveTotal")
    Call<TransaksiListVO> saveTotal(@Query("idTransaksi") String idTransaksi,@Query("total") String total);

    @POST("transaksi/batalkanTrsKurir/{idTransaksi}/")
    Call<TransaksiListVO> batalkanTrsKurir(@Path("idTransaksi") String idTransaksi, @Query("catatan") String catatan);

    @POST("detailTransaksi/saveDetail")
    Call<DetailTransaksiVo> createDetailTransaksi(@Body List<DetailTransaksiModel> detailTransaksi);
    
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

    @GET("transaksi/getTotalHarga")
    Call<TransaksiListVO> getTotalHarga(@Query("idTransaksi") Integer idTransaksi);

    @POST("transaksi/saveTransaksi")
    Call<TransaksiListVO> saveTransaksi(@Body TransaksiListModel transaksiModel);
}

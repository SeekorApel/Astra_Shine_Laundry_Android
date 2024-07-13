package com.polytechnic.astra.ac.id.astrashinelaundry.API;

import com.polytechnic.astra.ac.id.astrashinelaundry.API.Service.LayananService;
import com.polytechnic.astra.ac.id.astrashinelaundry.API.Service.AlamatService;
import com.polytechnic.astra.ac.id.astrashinelaundry.API.Service.TransaksiService;
import com.polytechnic.astra.ac.id.astrashinelaundry.API.Service.UserService;

public class ApiUtils {

    public static final String API_BASE_URL = "http://192.168.1.7:8080/";

    public ApiUtils() {
    }

    public static UserService getUserService(){
        return RetrofitClient.getClient(API_BASE_URL).create(UserService.class);
    }

    public static TransaksiService getAllTransaksiByStatus(){
        return RetrofitClient.getClient(API_BASE_URL).create(TransaksiService.class);
    }
    public static LayananService getAllLayanan(){
        return RetrofitClient.getClient(API_BASE_URL).create(LayananService.class);
    }

    public static AlamatService getAlamatService(){
        return RetrofitClient.getClient(API_BASE_URL).create(AlamatService.class);
    }

}

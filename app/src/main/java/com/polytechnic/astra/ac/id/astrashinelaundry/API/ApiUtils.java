package com.polytechnic.astra.ac.id.astrashinelaundry.API;

import com.polytechnic.astra.ac.id.astrashinelaundry.API.Service.TransaksiService;
import com.polytechnic.astra.ac.id.astrashinelaundry.API.Service.UserService;

public class ApiUtils {

    public static final String API_BASE_URL = "http://10.1.13.101:8080/";

    public ApiUtils() {
    }

    public static UserService getUserService(){
        return RetrofitClient.getClient(API_BASE_URL).create(UserService.class);
    }

    public static TransaksiService getAllTransaksiByStatus(){
        return RetrofitClient.getClient(API_BASE_URL).create(TransaksiService.class);
    }

}

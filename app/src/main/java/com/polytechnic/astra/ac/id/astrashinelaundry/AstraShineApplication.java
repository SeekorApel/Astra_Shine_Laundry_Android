package com.polytechnic.astra.ac.id.astrashinelaundry;

import android.app.Application;
import android.util.Log;

import com.polytechnic.astra.ac.id.astrashinelaundry.API.Repository.UserRepository;

public class AstraShineApplication extends Application {

    private static final String TAG = "AstraShineApplication";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "AstraShineLaundryApplication.onCreate() called");
    }
}

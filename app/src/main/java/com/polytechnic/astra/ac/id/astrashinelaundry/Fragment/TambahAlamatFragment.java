package com.polytechnic.astra.ac.id.astrashinelaundry.Fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;
import com.polytechnic.astra.ac.id.astrashinelaundry.API.Repository.AlamatRepository;
import com.polytechnic.astra.ac.id.astrashinelaundry.API.VO.AlamatVO;
import com.polytechnic.astra.ac.id.astrashinelaundry.Model.AlamatModel;
import com.polytechnic.astra.ac.id.astrashinelaundry.Model.UserModel;
import com.polytechnic.astra.ac.id.astrashinelaundry.R;
import com.polytechnic.astra.ac.id.astrashinelaundry.ViewModel.AlamatViewModel;

public class TambahAlamatFragment extends Fragment implements OnMapReadyCallback {
    private MapView mMapView;
    private GoogleMap mGoogleMap;
    private Button mBtnSimpan, mBtnGetLocation;
    private EditText mEdtNamaAlamat, mEdtAlamat;
    private FusedLocationProviderClient mFusedLocationClient;
    private static final int REQUEST_LOCATION_SETTINGS = 1001;
    private static final int REQUEST_LOCATION_PERMISSION = 1;
    private double mLaTitude, mLongTitude, mLaTLaundry, mLongLaundry;
    private AlamatViewModel mAlamatViewModel;

    private static final String TAG = "TambahAlamatFragment";

    public TambahAlamatFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AlamatRepository.initialize(requireContext());
        mAlamatViewModel = new ViewModelProvider(this).get(AlamatViewModel.class);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity());
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION_PERMISSION);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tambah_alamat, container, false);
        mBtnSimpan = view.findViewById(R.id.btn_simpan);
        mBtnGetLocation = view.findViewById(R.id.btn_get_location);
        mEdtNamaAlamat = view.findViewById(R.id.edt_nama_alamat);
        mEdtAlamat = view.findViewById(R.id.edt_alamat);

        mMapView = view.findViewById(R.id.maps_view);
        mMapView.onCreate(savedInstanceState);
        mMapView.getMapAsync(this);

        getAlamatLaundry();

        mBtnGetLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkLocationSettings();
            }
        });

        mBtnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float[] results = new float[1];
                Location.distanceBetween(mLaTLaundry, mLongLaundry, mLaTitude, mLongTitude, results);
                float distanceInMeters = results[0];
                double resultJarak = distanceInMeters / 1000.0;
                UserModel user = getUserModel();
                String namaAlamat = mEdtNamaAlamat.getText().toString().trim();
                String alamatString = mEdtAlamat.getText().toString().trim();

                AlamatModel alamat = new AlamatModel(user.getIdUser(), namaAlamat, alamatString, mLaTitude, mLongTitude, resultJarak);
                mAlamatViewModel.saveAlamat(alamat);

                mAlamatViewModel.getSuccessResponse().observe(getViewLifecycleOwner(), message -> {
                    Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
                    navigateToFragmentAlamat();
                });

                mAlamatViewModel.getErrorResponse().observe(getViewLifecycleOwner(), error -> {
                    Toast.makeText(getContext(), error, Toast.LENGTH_LONG).show();
                });
            }
        });

        return view;
    }

    private void checkLocationSettings() {
        LocationRequest locationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);
        SettingsClient settingsClient = LocationServices.getSettingsClient(requireActivity());
        Task<LocationSettingsResponse> task = settingsClient.checkLocationSettings(builder.build());

        task.addOnSuccessListener(requireActivity(), new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                getLastLocation();
            }
        });

        task.addOnFailureListener(requireActivity(), new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (e instanceof ResolvableApiException) {
                    try {
                        ResolvableApiException resolvable = (ResolvableApiException) e;
                        resolvable.startResolutionForResult(requireActivity(), REQUEST_LOCATION_SETTINGS);
                    } catch (IntentSender.SendIntentException sendEx) {
                        Log.e(TAG, sendEx.getMessage());
                    }
                }
            }
        });
    }

    private void getAlamatLaundry(){
        mAlamatViewModel.getDataAlamatLaundry();
        mAlamatViewModel.getAlamatLaundryResponse().observe(getViewLifecycleOwner(), new Observer<AlamatVO>() {
            @Override
            public void onChanged(AlamatVO alamatVO) {
                mLaTLaundry = alamatVO.getData().getLatitude();
                mLongLaundry = alamatVO.getData().getLongtitude();
            }
        });
    }

    private UserModel getUserModel(){
        Context context = getActivity();
        if (context != null) {
            SharedPreferences sharedPreferences = context.getSharedPreferences("loginSession", Context.MODE_PRIVATE);
            String userJson = sharedPreferences.getString("dataUser", null);
            // Use Gson to convert JSON string to object
            Gson gson = new Gson();
            UserModel user = gson.fromJson(userJson, UserModel.class);
            return user;
        }
        return null;
    }

    private void navigateToFragmentAlamat(){
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_view_transaksi, new AlamatFragment());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_LOCATION_SETTINGS) {
            if (resultCode == Activity.RESULT_OK) {
                getLastLocation();
            } else {
                // Pengguna menolak untuk mengaktifkan pengaturan lokasi
                Toast.makeText(requireActivity(), "Lokasi tidak diaktifkan, tidak dapat mendapatkan lokasi terkini.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @SuppressLint("MissingPermission")
    private void getLastLocation() {
        LocationRequest locationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10000)
                .setFastestInterval(5000);

        mFusedLocationClient.requestLocationUpdates(locationRequest, new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    Toast.makeText(requireActivity(), "Tidak dapat mendapatkan lokasi terkini.", Toast.LENGTH_SHORT).show();
                    return;
                }
                Location location = locationResult.getLastLocation();
                if (location != null) {
                    LatLng currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
                    mGoogleMap.addMarker(new MarkerOptions().position(currentLocation));
                    mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15));
                    mLaTitude = currentLocation.latitude;
                    mLongTitude = currentLocation.longitude;
                }
                mFusedLocationClient.removeLocationUpdates(this);
            }
        }, Looper.getMainLooper());
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        this.mGoogleMap = googleMap;
        LatLng location = new LatLng(-6.2088, 106.8456);
        googleMap.addMarker(new MarkerOptions().position(location));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 12));
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }
}
package com.polytechnic.astra.ac.id.astrashinelaundry.Fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.IntentSender;
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
import android.text.TextUtils;
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
import com.polytechnic.astra.ac.id.astrashinelaundry.API.Repository.AlamatRepository;
import com.polytechnic.astra.ac.id.astrashinelaundry.API.VO.AlamatVO;
import com.polytechnic.astra.ac.id.astrashinelaundry.Model.AlamatModel;
import com.polytechnic.astra.ac.id.astrashinelaundry.R;
import com.polytechnic.astra.ac.id.astrashinelaundry.ViewModel.AlamatViewModel;

public class EditAlamatFragment extends Fragment implements OnMapReadyCallback {

    private MapView mMapView;
    private GoogleMap mGoogleMap;
    private Button mBtnUbah, mBtnGetLocation, mBtnKembali, mBtnDelete;
    private EditText mEdtNamaAlamat, mEdtAlamat;
    private FusedLocationProviderClient mFusedLocationClient;
    private double mLaTitude, mLaTitudeTemp, mLongTitude, mLongTitudeTemp, mLaTLaundry, mLongLaundry;
    private static final int REQUEST_LOCATION_SETTINGS = 1001;
    private static final int REQUEST_LOCATION_PERMISSION = 1;
    private AlamatViewModel mAlamatViewModel;
    private static final String ARG_ALAMAT = "alamat";
    private static final String TAG = "EditAlamatFragment";
    private AlamatModel mAlamatModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AlamatRepository.initialize(requireContext());
        mAlamatViewModel = new ViewModelProvider(this).get(AlamatViewModel.class);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity());
        if (getArguments() != null) {
            mAlamatModel = (AlamatModel) getArguments().getSerializable("alamat");
        }
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
        View view = inflater.inflate(R.layout.fragment_edit_alamat, container, false);
        mBtnUbah = view.findViewById(R.id.btn_ubah);
        mBtnDelete = view.findViewById(R.id.btn_delete);
        mBtnGetLocation = view.findViewById(R.id.btn_get_location);
        mEdtNamaAlamat = view.findViewById(R.id.edt_nama_alamat);
        mEdtAlamat = view.findViewById(R.id.edt_alamat);
        mBtnKembali = view.findViewById(R.id.btn_kembali);

        mMapView = view.findViewById(R.id.maps_view);
        mMapView.onCreate(savedInstanceState);
        mMapView.getMapAsync(this);

        if (mAlamatModel != null) {
            setAlamatData(mAlamatModel);
        }

        getAlamatLaundry();

        mBtnUbah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String alamat = mEdtAlamat.getText().toString().trim();
                String namaAlamat = mEdtNamaAlamat.getText().toString().trim();
                String status = "Aktif";
                double updateLat = 0;
                double updateLong = 0;

                if (TextUtils.isEmpty(namaAlamat)) {
                    mEdtNamaAlamat.setError("Title wajib Di isi");
                    mEdtNamaAlamat.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(alamat)) {
                    mEdtAlamat.setError("Alamat wajib Di isi");
                    mEdtAlamat.requestFocus();
                    return;
                }

                if(mLaTitude != mLaTitudeTemp && mLongTitude != mLongTitudeTemp){
                    updateLat = mLaTitude;
                    updateLong = mLongTitude;
                    float[] results = new float[1];
                    Location.distanceBetween(mLaTLaundry, mLongLaundry, updateLat, updateLong, results);
                    float distanceInMeters = results[0];
                    double resultJarak = distanceInMeters / 1000.0;
                    AlamatModel updateAlamat = new AlamatModel(mAlamatModel.getIdAlamat(), mAlamatModel.getIdUser(), namaAlamat, alamat, updateLat, updateLong, resultJarak, status);
                    updateAlamat(updateAlamat);
                }else {
                    AlamatModel updateAlamat = new AlamatModel(mAlamatModel.getIdAlamat(), mAlamatModel.getIdUser(), namaAlamat, alamat, mAlamatModel.getLatitude(), mAlamatModel.getLongtitude(), mAlamatModel.getJarak(), status);
                    updateAlamat(updateAlamat);
                }
            }
        });

        mBtnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteDialog(mAlamatModel.getIdAlamat());
            }
        });

        mBtnGetLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkLocationSettings();
            }
        });

        mBtnKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToFragmentAlamat();
            }
        });

        return view;
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        this.mGoogleMap = googleMap;
        LatLng location = new LatLng(mAlamatModel.getLatitude(), mAlamatModel.getLongtitude());
        googleMap.addMarker(new MarkerOptions().position(location));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15));
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

    private void showDeleteDialog(Integer idAlamat) {
        new AlertDialog.Builder(getContext())
                .setTitle("Confirm Delete")
                .setMessage("Apakah Anda yakin ingin menghapus data ini?")
                .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                    mAlamatViewModel.deleteAlamat(idAlamat);
                    mAlamatViewModel.getSuccessResponse().observe(getViewLifecycleOwner(), message -> {
                        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                        navigateToFragmentAlamat();
                    });

                    mAlamatViewModel.getErrorResponse().observe(getViewLifecycleOwner(), error -> {
                        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
                    });
                })
                .setNegativeButton(android.R.string.no, null)
                .show();
    }

    private void updateAlamat(AlamatModel updateAlamat){
        mAlamatViewModel.updateAlamat(updateAlamat);

        mAlamatViewModel.getSuccessResponse().observe(getViewLifecycleOwner(), message -> {
            Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
            navigateToFragmentAlamat();
        });

        mAlamatViewModel.getErrorResponse().observe(getViewLifecycleOwner(), error -> {
            Toast.makeText(getContext(), error, Toast.LENGTH_LONG).show();
        });
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

    @SuppressLint("SetTextI18n")
    private void setAlamatData(AlamatModel alamat) {
        mLaTitude = alamat.getLatitude();
        mLongTitude = alamat.getLongtitude();
        mLaTitudeTemp = alamat.getLatitude();
        mLongTitudeTemp = alamat.getLongtitude();
        mEdtAlamat.setText(alamat.getAlamat());
        mEdtNamaAlamat.setText(alamat.getNamaAlamat());
        Log.d("Angela White", alamat.toString());
    }

    private void navigateToFragmentAlamat(){
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container_customer, new AlamatFragment());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
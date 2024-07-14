package com.polytechnic.astra.ac.id.astrashinelaundry.Fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.polytechnic.astra.ac.id.astrashinelaundry.R;

public class TambahAlamatFragment extends Fragment implements OnMapReadyCallback {
    private MapView mMapView;
    private GoogleMap mGoogleMap;
    private Button mBtnSimpan, mBtnGetLocation;
    private EditText mEdtNamaAlamat, mEdtAlamat;
    private FusedLocationProviderClient mFusedLocationClient;

    public TambahAlamatFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity());
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

        mBtnGetLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        return view;
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        this.mGoogleMap = googleMap;
        LatLng location = new LatLng(5.99, 28.20);
        googleMap.addMarker(new MarkerOptions().position(location));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 12));
    }

}
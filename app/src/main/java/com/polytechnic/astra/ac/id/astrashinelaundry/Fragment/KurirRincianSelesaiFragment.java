package com.polytechnic.astra.ac.id.astrashinelaundry.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.google.gson.Gson;
import com.polytechnic.astra.ac.id.astrashinelaundry.API.VO.TransaksiListVO;
import com.polytechnic.astra.ac.id.astrashinelaundry.API.VO.TransaksiVO;
import com.polytechnic.astra.ac.id.astrashinelaundry.Activity.KurirActivity;
import com.polytechnic.astra.ac.id.astrashinelaundry.Model.TransaksiModel;
import com.polytechnic.astra.ac.id.astrashinelaundry.Model.UserModel;
import com.polytechnic.astra.ac.id.astrashinelaundry.R;
import com.polytechnic.astra.ac.id.astrashinelaundry.ViewModel.TransaksiListViewModel;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class KurirRincianSelesaiFragment extends Fragment {
    private Button mBtnKembali, mBtnCekPesanan, mBtnBatal,mBtnPembayaran;
    private ImageButton mBtnLokasi;
    private TextView txtNamaCustomer, txtNoTelp,  txtStatus, txtTanggalPesanan, txtTanggalSelesai, txtDurasi, txtStatusPembayaran;
    private TransaksiModel transaksi;
    private TransaksiListViewModel mTransaksiListViewModel;
    private Integer posisiTab;
    private String userJson;

    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            transaksi = (TransaksiModel) getArguments().getSerializable("transaksi");
            posisiTab = getArguments().getInt("posisiTab");
        }
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("loginSession", Context.MODE_PRIVATE);
        userJson = sharedPreferences.getString("dataUser", null);

        // Gunakan Gson untuk mengonversi JSON string menjadi objek
        Gson gson = new Gson();
        UserModel user = gson.fromJson(userJson, UserModel.class);
        mTransaksiListViewModel = new TransaksiListViewModel();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_kurir_rincian_selesai, container, false);

        // Inisialisasi dan pengaturan tampilan sesuai dengan data transaksi
        txtNamaCustomer = view.findViewById(R.id.txt_nama_customer);
        txtNoTelp = view.findViewById(R.id.txt_no_telp);
        txtStatus = view.findViewById(R.id.status);
        txtTanggalPesanan = view.findViewById(R.id.tanggal_pesanan);
        txtTanggalSelesai = view.findViewById(R.id.tanggal_selesai);
        txtDurasi = view.findViewById(R.id.durasi);
        txtStatusPembayaran = view.findViewById(R.id.status_pembayaran);

        if (transaksi != null) {
            txtNamaCustomer.setText(transaksi.getNamaUser());
            txtNoTelp.setText(transaksi.getNoTelp());
            txtStatus.setText(transaksi.getStatusPesanan());
            txtTanggalPesanan.setText(dateFormat.format(transaksi.getTanggalPesanan()));
            txtTanggalSelesai.setText(dateFormat.format(transaksi.getTanggalPengiriman()));
            txtDurasi.setText(transaksi.getNamaDurasi());

            // Set status color based on actual payment status in transaction
            String paymentStatus = transaksi.getStatusPembayaran();
            int color;
            if (paymentStatus.equals("Belum Bayar")) {
                color = Color.RED;  // Unpaid color
            } else {
                color = Color.BLACK;  // Paid color (or any other color)
            }
            txtStatusPembayaran.setTextColor(color);
            txtStatusPembayaran.setText(paymentStatus);
        }

        mBtnKembali = view.findViewById(R.id.btn_kembali);
        mBtnKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });

        mBtnCekPesanan = view.findViewById(R.id.selesai);
        mBtnCekPesanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String paymentStatus = txtStatusPembayaran.getText().toString();
                if (paymentStatus.equals("Sudah Bayar")) {
                    mTransaksiListViewModel.updateStatus(transaksi.getIdTransaksi().toString());
                    Toast.makeText(getContext(), "Transaksi Selesai", Toast.LENGTH_SHORT).show();

                    PickUpKurirFragment listTransaksi = new PickUpKurirFragment();
                    Bundle args = new Bundle();
                    args.putInt("posisiTab", posisiTab);
                    args.putString("userJson", userJson);
                    listTransaksi.setArguments(args);

                    getParentFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container_kurir, listTransaksi)  // Make sure R.id.PickUpKurir is correct
                            .addToBackStack(null)
                            .commit();
                }else{
                    Toast.makeText(getContext(), "Bayar Terlebih Dahulu", Toast.LENGTH_SHORT).show();
                }

            }
        });

        mBtnPembayaran = view.findViewById(R.id.pembayaran);

        mBtnPembayaran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String paymentStatus = txtStatusPembayaran.getText().toString();
                if (paymentStatus.equals("Sudah Bayar")) {
                    Toast.makeText(getContext(), "Customer Sudah Bayar", Toast.LENGTH_SHORT).show();
                }else{
                    mTransaksiListViewModel.updatePembayaran(transaksi.getIdTransaksi().toString());
                    Toast.makeText(getContext(), "Pembayaran berhasil", Toast.LENGTH_SHORT).show();
                    mTransaksiListViewModel.updateStatus(transaksi.getIdTransaksi().toString());
                    mTransaksiListViewModel.getTransaksiResponse().observe(getViewLifecycleOwner(), new Observer<TransaksiVO>() {
                        @Override
                        public void onChanged(TransaksiVO transaksiListVO) {
                            mTransaksiListViewModel.getTransaksiUpdateResponse().observe(getViewLifecycleOwner(), new Observer<TransaksiVO>() {
                                @Override
                                public void onChanged(TransaksiVO transaksiVO) {
                                    if (transaksiListVO != null){
                                        PickUpKurirFragment listTransaksi = new PickUpKurirFragment();
                                        Bundle args = new Bundle();
                                        args.putInt("posisiTab", posisiTab);
                                        args.putString("userJson", userJson);
                                        listTransaksi.setArguments(args);

                                        getParentFragmentManager().beginTransaction()
                                                .replace(R.id.fragment_container_kurir, listTransaksi)  // Make sure R.id.PickUpKurir is correct
                                                .addToBackStack(null)
                                                .commit();
                                    }
                                }
                            });

                        }
                    });
                }

            }
        });

        mBtnLokasi = view.findViewById(R.id.btn_location);
        mBtnLokasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (transaksi != null) {
                    Double latitude = transaksi.getLatitude();
                    Double longitude = transaksi.getLongitude();
                    if (latitude != null && longitude != null) {
                        Uri gmmIntentUri = Uri.parse("geo:" + latitude + "," + longitude + "?q=" + latitude + "," + longitude + "(Lokasi+Pelanggan)");
                        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                        mapIntent.setPackage("com.google.android.apps.maps");
                        startActivity(mapIntent);
                    }
                }
            }
        });

        return view;
    }
}

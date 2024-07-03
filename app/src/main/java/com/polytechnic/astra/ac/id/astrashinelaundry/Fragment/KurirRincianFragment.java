package com.polytechnic.astra.ac.id.astrashinelaundry.Fragment;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.polytechnic.astra.ac.id.astrashinelaundry.Model.TransaksiModel;
import com.polytechnic.astra.ac.id.astrashinelaundry.R;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class KurirRincianFragment extends Fragment {

    private Button mBtnKembali, mBtnCekPesanan, mBtnBatal;
    private ImageButton mBtnLokasi;
    private TextView txtNamaCustomer, txtNoTelp,  txtStatus, txtTanggalPesanan, txtTanggalSelesai, txtDurasi, txtStatusPembayaran;
    private TransaksiModel transaksi;

    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            transaksi = (TransaksiModel) getArguments().getSerializable("transaksi");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_kurir_rincian, container, false);

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
                // Replace the current fragment with fragment_kurir_pickup
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.PickUpKurir, new PickUpKurirFragment());
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        mBtnBatal = view.findViewById(R.id.btn_batal);
//        mBtnBatal.setEnabled(transaksi != null && transaksi.getCatatan() == null);
        mBtnBatal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KurirBatalFragment kurirBatal = new KurirBatalFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable("transaksi", transaksi);
                kurirBatal.setArguments(bundle);
                kurirBatal.show(getParentFragmentManager(), "KurirBatalFragment");
            }
        });

        mBtnLokasi = view.findViewById(R.id.btn_location);
        mBtnLokasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (transaksi != null) {
                    String latitude = transaksi.getLatitude();
                    String longitude = transaksi.getLongitude();
                    if (latitude != null && longitude != null) {
                        Uri gmmIntentUri = Uri.parse("geo:" + longitude + "," + latitude + "?q=" + longitude + "," + latitude + "(Lokasi+Pelanggan)");
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

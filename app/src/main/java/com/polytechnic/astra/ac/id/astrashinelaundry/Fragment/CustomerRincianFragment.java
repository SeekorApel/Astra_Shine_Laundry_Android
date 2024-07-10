package com.polytechnic.astra.ac.id.astrashinelaundry.Fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.polytechnic.astra.ac.id.astrashinelaundry.API.VO.DetailTransaksiVo;
import com.polytechnic.astra.ac.id.astrashinelaundry.API.VO.DurasiVo;
import com.polytechnic.astra.ac.id.astrashinelaundry.Model.DetailTransaksiModel;
import com.polytechnic.astra.ac.id.astrashinelaundry.Model.DurasiModel;
import com.polytechnic.astra.ac.id.astrashinelaundry.Model.TransaksiModel;
import com.polytechnic.astra.ac.id.astrashinelaundry.Model.UserModel;
import com.polytechnic.astra.ac.id.astrashinelaundry.R;
import com.polytechnic.astra.ac.id.astrashinelaundry.ViewModel.DurasiViewModel;
import com.polytechnic.astra.ac.id.astrashinelaundry.ViewModel.TransaksiListViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class CustomerRincianFragment extends Fragment {
    private Button mButtonBatal, mButtonKembali;
    private TextView mTextViewStatus, mTextViewTanggal, mTextViewEstimasi, mTextViewDurasi, mTextViewStatusBayar, mTextViewLayanan, mTextViewOngkir, mTextViewTotal;
    private TransaksiModel mTransaksiModel;
    private DurasiModel mDurasiModel;
    private DurasiViewModel mDurasiViewModel;
    private TransaksiListViewModel mTransaksiListViewModel;
    SimpleDateFormat mDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTransaksiModel = (TransaksiModel) getArguments().getSerializable("transaksi");
        }
        mTransaksiListViewModel = new ViewModelProvider(this).get(TransaksiListViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_customer_rincian, container, false);

        mTextViewStatus = view.findViewById(R.id.status);
        mTextViewTanggal = view.findViewById(R.id.tanggal_pesanan);
        mTextViewEstimasi = view.findViewById(R.id.tanggal_selesai);
        mTextViewDurasi = view.findViewById(R.id.durasi);
        mTextViewStatusBayar = view.findViewById(R.id.status_pembayaran);
        mTextViewLayanan = view.findViewById(R.id.biaya_layanan);
        mTextViewOngkir = view.findViewById(R.id.biaya_antar);
        mTextViewTotal = view.findViewById(R.id.total_pembayaran);

        Integer idDurasi = mTransaksiModel.getIdDurasi();
        Log.d("sigma", String.valueOf(idDurasi));
        mDurasiViewModel = new ViewModelProvider(this).get(DurasiViewModel.class);

        mDurasiViewModel.getDataDurasiById(idDurasi);
        mDurasiViewModel.getAllDurasiResponse().observe(getViewLifecycleOwner(), new Observer<DurasiVo>() {
            @Override
            public void onChanged(DurasiVo durasiVo) {
                mTextViewDurasi.setText(durasiVo.getData().getNamaDurasi());
            }
        });

        if ("Pick Up".equals(mTransaksiModel.getStatusPesanan())) {
            mTextViewLayanan.setText("Rp. 0");
        } else {
            Integer idTransaksi = mTransaksiModel.getIdTransaksi();
            Log.d("woiii", "ini idtransaksi bg " + idTransaksi);
            mTransaksiListViewModel.getTransaksiDetail(idTransaksi);
            mTransaksiListViewModel.getAllDetailResponse().observe(getViewLifecycleOwner(), new Observer<DetailTransaksiVo>() {
                @Override
                public void onChanged(DetailTransaksiVo detailTransaksiVos) {
                    if (detailTransaksiVos == null) {
                        Log.d("Detail Name", "Daftar detail transaksi kosong atau null");
                        // Tambahkan logika lain jika perlu, misalnya menampilkan pesan ke pengguna
                    } else {
                        List<DetailTransaksiModel> dataList = detailTransaksiVos.getData();
                        if (dataList != null && !dataList.isEmpty()) {
                            for (DetailTransaksiModel model : dataList) {
                                Integer qty = model.getQty();

                            }
                        } else {
                            Log.d("Detail Name", "Daftar data detail transaksi kosong atau null");
                        }
                    }
                }
            });
        }

        if (mTransaksiModel != null){
            String statusPembayaran = mTransaksiModel.getStatusPembayaran();
            if (Objects.equals(statusPembayaran, "Belum Bayar")){
                mTextViewStatusBayar.setTextColor(ContextCompat.getColor(getContext(), R.color.red));
            }else {
                mTextViewStatusBayar.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
            }

            mTextViewStatus.setText(mTransaksiModel.getStatusPesanan());
            mTextViewTanggal.setText(mDateFormat.format(mTransaksiModel.getTanggalPesanan()));
            mTextViewEstimasi.setText(mDateFormat.format(mTransaksiModel.getTanggalPengiriman()));
            mTextViewStatus.setText(mTransaksiModel.getStatusPesanan());
            mTextViewTanggal.setText(mDateFormat.format(mTransaksiModel.getTanggalPesanan()));
            mTextViewEstimasi.setText(mDateFormat.format(mTransaksiModel.getTanggalPengiriman()));
            mTextViewStatusBayar.setText(statusPembayaran);

            Integer ongkir = mTransaksiModel.getOngkir();
            String ongkirText = (ongkir == null) ? "Rp. 0" : "Rp. " + ongkir;
            mTextViewOngkir.setText(ongkirText);

            mTextViewTotal.setText("Rp. "+String.valueOf(mTransaksiModel.getTotalHarga()));
        }

        mButtonBatal = view.findViewById(R.id.btn_batal_pesanan);
        mButtonBatal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mButtonKembali = view.findViewById(R.id.btn_kembali);
        mButtonKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });

        return view;
    }

    public class CustomerRincianAdapter extends RecyclerView.Adapter<CustomerRincianFragment.CustomerRincianAdapter.CustomerRincianViewHolder> {

        @NonNull
        @Override
        public CustomerRincianViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return null;
        }

        @Override
        public void onBindViewHolder(@NonNull CustomerRincianViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 0;
        }

        class CustomerRincianViewHolder extends RecyclerView.ViewHolder {

            public CustomerRincianViewHolder(@NonNull View itemView) {
                super(itemView);
            }
        }
    }
}
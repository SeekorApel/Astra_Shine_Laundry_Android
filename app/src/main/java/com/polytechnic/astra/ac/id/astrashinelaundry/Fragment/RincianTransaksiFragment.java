package com.polytechnic.astra.ac.id.astrashinelaundry.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.polytechnic.astra.ac.id.astrashinelaundry.API.Repository.LayananRepository;
import com.polytechnic.astra.ac.id.astrashinelaundry.API.VO.DetailTransaksiVo;
import com.polytechnic.astra.ac.id.astrashinelaundry.API.VO.DurasiVo;
import com.polytechnic.astra.ac.id.astrashinelaundry.Model.DetailTransaksiModel;
import com.polytechnic.astra.ac.id.astrashinelaundry.Model.DurasiModel;
import com.polytechnic.astra.ac.id.astrashinelaundry.Model.TransaksiModel;
import com.polytechnic.astra.ac.id.astrashinelaundry.R;
import com.polytechnic.astra.ac.id.astrashinelaundry.ViewModel.DetailTransaksiKurirViewModel;
import com.polytechnic.astra.ac.id.astrashinelaundry.ViewModel.DurasiViewModel;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class RincianTransaksiFragment extends Fragment {
    private DetailTransaksiKurirViewModel mViewModel;
    private DurasiViewModel mDurasiViewModel;
    private RecyclerView mLayananRecyclerView;
    private Button btnkonfirmasi;
    private LayananAdapter mAdapter;
    private DurasiModel mDurasiModel;
    private TransaksiModel transaksi;
    private Integer Total, totalSeluruh;
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
    NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            transaksi = (TransaksiModel) getArguments().getSerializable("transaksi");
            Total = (Integer) getArguments().getSerializable("total");
        }
        LayananRepository.initialize(requireContext());
        mViewModel = new ViewModelProvider(this).get(DetailTransaksiKurirViewModel.class);
        mDurasiViewModel = new ViewModelProvider(this).get(DurasiViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_kurir_rincian_detail, container, false);

        btnkonfirmasi = view.findViewById(R.id.pesanan_siap);
        TextView status = view.findViewById(R.id.statusDetail);
        TextView tanggalPesanan = view.findViewById(R.id.tanggal_pesanan_detail);
        TextView tanggalSelesai = view.findViewById(R.id.tanggal_selesai_detail);
        TextView durasi = view.findViewById(R.id.durasi_detail);
        TextView statusBayar = view.findViewById(R.id.status_pembayaran_detail);
        TextView totalLayanan = view.findViewById(R.id.total_layanan);
        TextView total = view.findViewById(R.id.total);

        mDurasiViewModel.getDataDurasiById(transaksi.getIdDurasi());
        mDurasiViewModel.getAllDurasiResponse().observe(getViewLifecycleOwner(), new Observer<DurasiVo>() {
            @Override
            public void onChanged(DurasiVo durasiVo) {
                List<DurasiModel> dataList = durasiVo.getData();
                if (dataList != null && !dataList.isEmpty()) {
                    for (DurasiModel model : dataList) {
                        totalSeluruh = (Total != null ? Total : 0) + model.getHargaDurasi();
                        updateTotalFields(totalLayanan, total);
                    }
                } else {
                    Log.d("Detail Name", "Daftar data detail transaksi kosong atau null");
                }
            }
        });

        if (transaksi != null) {
            status.setText(transaksi.getStatusPesanan());
            tanggalPesanan.setText(dateFormat.format(transaksi.getTanggalPesanan()));
            tanggalSelesai.setText(dateFormat.format(transaksi.getTanggalPengiriman()));
            durasi.setText(transaksi.getNamaDurasi());
            statusBayar.setText(transaksi.getStatusPembayaran());
            totalLayanan.setText(currencyFormat.format(Total != null ? Total : 0));
            total.setText(currencyFormat.format(totalSeluruh != null ? totalSeluruh : 0));
        }

        mLayananRecyclerView = view.findViewById(R.id.listLayananDetail);
        mLayananRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mAdapter = new LayananAdapter();
        mLayananRecyclerView.setAdapter(mAdapter);
        mViewModel.getDetailTransaksi(transaksi.getIdTransaksi().toString());

        mViewModel.getAllDetailResponse().observe(getViewLifecycleOwner(), new Observer<DetailTransaksiVo>() {
            @Override
            public void onChanged(DetailTransaksiVo layananVO) {
                if (layananVO != null) {
                    mAdapter.setLayananList(layananVO.getData());
                } else {
                    mAdapter.setLayananList(new ArrayList<>()); // Clear the list if null
                }
            }
        });

        btnkonfirmasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Logic for confirming the order goes here
            }
        });
        return view;
    }

    private void updateTotalFields(TextView totalLayanan, TextView total) {
        totalLayanan.setText(currencyFormat.format(Total != null ? Total : 0));
        total.setText(currencyFormat.format(totalSeluruh != null ? totalSeluruh : 0));
    }

    public class LayananAdapter extends RecyclerView.Adapter<LayananAdapter.LayananViewHolder> {
        private List<DetailTransaksiModel> layananList = new ArrayList<>();

        @NonNull
        @Override
        public LayananViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_layanan_detail, parent, false);
            return new LayananViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull LayananViewHolder holder, int position) {
            DetailTransaksiModel layanan = layananList.get(position);
            holder.bind(layanan);
        }

        @Override
        public int getItemCount() {
            return layananList.size();
        }

        public void setLayananList(List<DetailTransaksiModel> layananList) {
            this.layananList.clear(); // Clear existing list
            this.layananList.addAll(layananList); // Add new data
            notifyDataSetChanged();
        }

        class LayananViewHolder extends RecyclerView.ViewHolder {
            private TextView mLayanan;
            private TextView mQty;

            public LayananViewHolder(@NonNull View itemView) {
                super(itemView);
                mLayanan = itemView.findViewById(R.id.namaLayanan);
                mQty = itemView.findViewById(R.id.qty);
            }

            public void bind(DetailTransaksiModel layanan) {
                mLayanan.setText(layanan.getNamaLayanan());
                mQty.setText(layanan.getQty().toString());
            }
        }
    }
}

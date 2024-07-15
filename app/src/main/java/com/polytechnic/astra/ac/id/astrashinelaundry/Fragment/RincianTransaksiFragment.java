package com.polytechnic.astra.ac.id.astrashinelaundry.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.polytechnic.astra.ac.id.astrashinelaundry.API.Repository.LayananRepository;
import com.polytechnic.astra.ac.id.astrashinelaundry.API.VO.DetailTransaksiVO;
import com.polytechnic.astra.ac.id.astrashinelaundry.API.VO.LayananVO;
import com.polytechnic.astra.ac.id.astrashinelaundry.Model.DetailTransaksiModel;
import com.polytechnic.astra.ac.id.astrashinelaundry.Model.LayananModel;
import com.polytechnic.astra.ac.id.astrashinelaundry.Model.TransaksiModel;
import com.polytechnic.astra.ac.id.astrashinelaundry.R;
import com.polytechnic.astra.ac.id.astrashinelaundry.ViewModel.DetailTransaksiKurirViewModel;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class RincianTransaksiFragment extends Fragment {
    private DetailTransaksiKurirViewModel mViewModel;
    private String TAG = "RincianTransaksiFragment";
    private RecyclerView mLayananRecyclerView;
    private Button btnkonfirmasi;
    private RincianTransaksiFragment.LayananAdapter mAdapter;
    private TransaksiModel transaksi;
    private Integer Total,totalSeluruh;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            transaksi = (TransaksiModel) getArguments().getSerializable("transaksi");
            Total = (Integer) getArguments().getSerializable("total");
        }
        LayananRepository.initialize(requireContext());
        mViewModel = new ViewModelProvider(this).get(DetailTransaksiKurirViewModel.class);
        Log.d(TAG,"Total : "+Total);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_kurir_rincian_detail, container, false);

        btnkonfirmasi = view.findViewById(R.id.pesanan_siap);
        TextView status = view.findViewById(R.id.status);
        TextView tanggalPesanan = view.findViewById(R.id.tanggal_pesanan);
        TextView tanggalSelesai = view.findViewById(R.id.tanggal_selesai);
        TextView durasi = view.findViewById(R.id.durasi);
        TextView statusBayar = view.findViewById(R.id.status_pembayaran);
        TextView totalLayanan = view.findViewById(R.id.total_layanan);
        TextView total = view.findViewById(R.id.total);

        if (transaksi != null) {
            status.setText(transaksi.getStatusPesanan());
            tanggalPesanan.setText(transaksi.getTanggalPesanan().toString());
            tanggalSelesai.setText(transaksi.getTanggalPengiriman().toString());
            durasi.setText(transaksi.getNamaDurasi());
            statusBayar.setText(transaksi.getStatusPembayaran());
            totalLayanan.setText(Total);
            total.setText(Total);
        }
        mLayananRecyclerView = view.findViewById(R.id.listLayananDetail);
        mLayananRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mAdapter = new LayananAdapter();
        mLayananRecyclerView.setAdapter(mAdapter);
        mViewModel.getDetailTransaksi(transaksi.getIdTransaksi().toString());

        mViewModel.getAllDetailResponse().observe(getViewLifecycleOwner(), new Observer<DetailTransaksiVO>() {
            @Override
            public void onChanged(DetailTransaksiVO layananVO) {
                if (layananVO != null) {
                    mAdapter.setLayananList(layananVO.getData());
                } else {
                    mAdapter.setLayananList(new ArrayList<>()); // Clear the list if null
                }
            }
        });
        return view;
    }
    public class LayananAdapter extends RecyclerView.Adapter<RincianTransaksiFragment.LayananAdapter.LayananViewHolder> {
        private List<DetailTransaksiModel> layananList = new ArrayList<>();

        @NonNull
        @Override
        public RincianTransaksiFragment.LayananAdapter.LayananViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_layanan_detail, parent, false);
            return new RincianTransaksiFragment.LayananAdapter.LayananViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RincianTransaksiFragment.LayananAdapter.LayananViewHolder holder, int position) {
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
            private DetailTransaksiModel currentLayananModel;

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

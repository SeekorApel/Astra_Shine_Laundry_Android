package com.polytechnic.astra.ac.id.astrashinelaundry.Fragment;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.polytechnic.astra.ac.id.astrashinelaundry.API.Repository.LayananRepository;
import com.polytechnic.astra.ac.id.astrashinelaundry.API.VO.DetailTransaksiVo;
import com.polytechnic.astra.ac.id.astrashinelaundry.API.VO.LayananVO;
import com.polytechnic.astra.ac.id.astrashinelaundry.API.VO.TransaksiListVO;
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

public class DetailTransaksiKurirFragment extends Fragment {

    private DetailTransaksiKurirViewModel mViewModel;
    private RecyclerView mLayananRecyclerView;
    private LayananAdapter mAdapter;
    private TextView mTotal;
    private TransaksiModel transaksi;
    private TextView txtNamaCustomer,txtNoTelp;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            transaksi = (TransaksiModel) getArguments().getSerializable("transaksi");
        }
        LayananRepository.initialize(requireContext());
        mViewModel = new ViewModelProvider(this).get(DetailTransaksiKurirViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_transaksi_kurir, container, false);

        TextView mKg = view.findViewById(R.id.kg);
        TextView mPcs = view.findViewById(R.id.pcs);
        mTotal = view.findViewById(R.id.total_price);
        Button mLanjut = view.findViewById(R.id.lanjut);
        txtNamaCustomer = view.findViewById(R.id.nama_customer);
        txtNoTelp = view.findViewById(R.id.no_telp);

        if (transaksi != null) {
            txtNamaCustomer.setText(transaksi.getNamaUser());
            txtNoTelp.setText(transaksi.getNoTelp());
        }

        mViewModel.getTotalKg().observe(getViewLifecycleOwner(), new Observer<Double>() {
            @Override
            public void onChanged(Double totalKg) {
                mKg.setText(String.format("%.1f", totalKg) + " Kg");
            }
        });

        mViewModel.getTotalPcs().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer totalPcs) {
                mPcs.setText(String.valueOf(totalPcs) + " Pcs");
            }
        });

        mViewModel.getTotalHarga().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer totalHarga) {
                NumberFormat formatter = NumberFormat.getNumberInstance(Locale.getDefault());
                String formattedTotalHarga = formatter.format(totalHarga);

                // Set text dengan format "Rp. xxx,xxx"
                mTotal.setText("Rp. " + formattedTotalHarga);
            }
        });
        mLanjut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mViewModel.getTotalKg().getValue() > 0.9 || mViewModel.getTotalKg().getValue() < 0.1 && mViewModel.getTotalPcs().getValue() > 0 ){
                    List<DetailTransaksiModel> selectedLayananList = mViewModel.getSelectedLayananList().getValue();
                    if (selectedLayananList != null && !selectedLayananList.isEmpty()) {
                        mViewModel.createDetailTransaksi(selectedLayananList);
                    }
                    mViewModel.getAllDetailResponse().observe(getViewLifecycleOwner(), new Observer<DetailTransaksiVo>() {
                        @Override
                        public void onChanged(DetailTransaksiVo detailTransaksiVo) {
                            RincianTransaksiFragment rincianTransaksiFragment = new RincianTransaksiFragment();
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("transaksi", transaksi);
                            bundle.putSerializable("total",mViewModel.getTotalHarga().getValue());
                            rincianTransaksiFragment.setArguments(bundle);

                            getParentFragmentManager().beginTransaction()
                                    .replace(R.id.fragment_container_kurir, rincianTransaksiFragment)  // Make sure R.id.PickUpKurir is correct
                                    .addToBackStack(null)
                                    .commit();
                        }
                    });

            } else {
                // Tampilkan pesan bahwa minimal Kg harus 1
                // Misalnya:
                Toast.makeText(getActivity(), "Minimal 1 Kg untuk melanjutkan", Toast.LENGTH_SHORT).show();
            }
            }
        });

        mLayananRecyclerView = view.findViewById(R.id.listLayanan);
        mLayananRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mAdapter = new LayananAdapter();
        mLayananRecyclerView.setAdapter(mAdapter);
        mViewModel.getDataLayanan();

        mViewModel.getAllLayananResponse().observe(getViewLifecycleOwner(), new Observer<LayananVO>() {
            @Override
            public void onChanged(LayananVO layananVO) {
                if (layananVO != null) {
                    mAdapter.setLayananList(layananVO.getData());
                } else {
                    mAdapter.setLayananList(new ArrayList<>()); // Clear the list if null
                }
            }
        });

        return view;
    }

    public void show(FragmentManager parentFragmentManager, String kurirBatalFragment) {
    }

    public class LayananAdapter extends RecyclerView.Adapter<LayananAdapter.LayananViewHolder> {
        private List<LayananModel> layananList = new ArrayList<>();

        @NonNull
        @Override
        public LayananViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_list_layanan, parent, false);
            return new LayananViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull LayananViewHolder holder, int position) {
            LayananModel layanan = layananList.get(position);
            holder.bind(layanan);
        }

        @Override
        public int getItemCount() {
            return layananList.size();
        }

        public void setLayananList(List<LayananModel> layananList) {
            this.layananList.clear(); // Clear existing list
            this.layananList.addAll(layananList); // Add new data
            notifyDataSetChanged();
        }

        class LayananViewHolder extends RecyclerView.ViewHolder {
            private TextView mLayanan;
            private TextView mHarga;
            private TextView mQty;
            private Button mPlus;
            private Button mMinus;
            private Double quantity;
            private double total;
            private DetailTransaksiModel currentLayananModel;

            public LayananViewHolder(@NonNull View itemView) {
                super(itemView);
                mLayanan = itemView.findViewById(R.id.layanan);
                mHarga = itemView.findViewById(R.id.harga);
                mQty = itemView.findViewById(R.id.qty);
                mPlus = itemView.findViewById(R.id.plus);
                mMinus = itemView.findViewById(R.id.minus);
                quantity = 0.0;
                total = 0.0;
            }

            public void bind(LayananModel layanan) {
                mLayanan.setText(layanan.getNamaLayanan());
                mHarga.setText("Rp. " + layanan.getHargaLayanan());
                mQty.setText(String.format("%.1f", quantity));
                currentLayananModel = new DetailTransaksiModel();
                currentLayananModel.setIdTransaksi(transaksi.getIdTransaksi());
                currentLayananModel.setIdLayanan(layanan.getIdLayanan());
                currentLayananModel.setNamaLayanan(layanan.getNamaLayanan());

                mPlus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (layanan.getSatuanLayanan().equals("Kg")) {
                            quantity += 0.1;
                            total = Math.ceil(Double.parseDouble(layanan.getHargaLayanan()) * quantity);
                            mViewModel.addHargaKg((int) total);
                        } else {
                            quantity += 1;
                            total = Math.ceil(Double.parseDouble(layanan.getHargaLayanan()) * quantity);
                            if(mViewModel.getTotalHargaKg().getValue().equals(0)) {
                                double totalPcs = Double.parseDouble(mViewModel.getTotalHarga().getValue().toString()) + Double.parseDouble(layanan.getHargaLayanan());
                                mViewModel.addHargaPcs((int) totalPcs);
                            } else if (!mViewModel.getTotalHargaPcs().getValue().equals(0)) {
                                double totalPcs = Double.parseDouble(mViewModel.getTotalHargaPcs().getValue().toString()) + Double.parseDouble(layanan.getHargaLayanan());
                                mViewModel.addHargaPcs((int) totalPcs);
                            } else{
                                mViewModel.addHargaPcs((int) total);
                            }
                        }

                        currentLayananModel.setQty(quantity);
                        mViewModel.addLayanan(currentLayananModel);


                        // Update text qty
                        mQty.setText(String.format("%.1f", quantity));

                        // Update ViewModel
                        if (layanan.getSatuanLayanan().equals("Kg")) {
                            mViewModel.addKg(0.1); // Panggil metode addKg di ViewModel
                        } else {
                            mViewModel.addPcs(1); // Panggil metode addPcs di ViewModel
                        }
                        int totalSeluruh = mViewModel.getTotalHargaKg().getValue() + mViewModel.getTotalHargaPcs().getValue();
                        // Panggil metode addHarga di ViewModel
                        mViewModel.addHarga(totalSeluruh);
                    }
                });

                mMinus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (layanan.getSatuanLayanan().equals("Kg")) {
                            if (quantity >= 0.1) {
                                quantity -= 0.1;
                                total = Math.ceil(Double.parseDouble(layanan.getHargaLayanan()) * quantity);
                                mViewModel.addHargaKg((int) total);
                            } else {
                                quantity = 0.0;
                                total = Math.ceil(Double.parseDouble(layanan.getHargaLayanan()) * quantity);
                                mViewModel.addHargaKg((int) total);
                            }
                        } else {
                            if (quantity >= 1) {
                                quantity -= 1;
                                total = Double.parseDouble(layanan.getHargaLayanan()) * quantity;
                                if(mViewModel.getTotalHargaKg().getValue().equals(0)) {
                                    double totalPcs = Double.parseDouble(mViewModel.getTotalHarga().getValue().toString()) - Double.parseDouble(layanan.getHargaLayanan());
                                    mViewModel.addHargaPcs((int) totalPcs);
                                } else if (!mViewModel.getTotalHargaPcs().getValue().equals(0)) {
                                    double totalPcs = Double.parseDouble(mViewModel.getTotalHargaPcs().getValue().toString()) - Double.parseDouble(layanan.getHargaLayanan());
                                    mViewModel.addHargaPcs((int) totalPcs);
                                } else{
                                    mViewModel.addHargaPcs((int) total);
                                }
                            } else {
                                quantity = 0.0;
//                                total = Double.parseDouble(layanan.getHargaLayanan()) * quantity;
//                                double totalPcs = Double.parseDouble(mViewModel.getTotalHarga().getValue().toString())-Double.parseDouble(layanan.getHargaLayanan());
//                                mViewModel.addHargaPcs((int) totalPcs);
                            }
                        }

                        currentLayananModel.setQty(quantity);
                        mViewModel.removeLayanan(currentLayananModel);

                        // Update text qty
                        mQty.setText(String.format("%.1f", quantity));

                        // Update ViewModel
                        if (layanan.getSatuanLayanan().equals("Kg")) {
                            mViewModel.subtractKg(0.1); // Panggil metode subtractKg di ViewModel
                        } else {
                            mViewModel.subtractPcs(1); // Panggil metode subtractPcs di ViewModel
                        } // Panggil metode subtractHarga di ViewModel
                        // Panggil metode addHarga di ViewModel
                        int totalSeluruh = mViewModel.getTotalHargaKg().getValue() + mViewModel.getTotalHargaPcs().getValue();
                        // Panggil metode addHarga di ViewModel
                        mViewModel.addHarga(totalSeluruh);
                    }
                });

            }
        }
    }
}


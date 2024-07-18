package com.polytechnic.astra.ac.id.astrashinelaundry.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.polytechnic.astra.ac.id.astrashinelaundry.API.Repository.TransaksiRepository;
import com.polytechnic.astra.ac.id.astrashinelaundry.API.VO.TransaksiListVO;
import com.polytechnic.astra.ac.id.astrashinelaundry.Activity.MainActivity;
import com.polytechnic.astra.ac.id.astrashinelaundry.Model.TransaksiModel;
import com.polytechnic.astra.ac.id.astrashinelaundry.Model.UserModel;
import com.polytechnic.astra.ac.id.astrashinelaundry.R;
import com.polytechnic.astra.ac.id.astrashinelaundry.ViewModel.LoginViewModel;
import com.polytechnic.astra.ac.id.astrashinelaundry.ViewModel.TransaksiListViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class PickUpKurirFragment extends Fragment {
    private Button mBtnLogout;
    private TransaksiListViewModel mTransaksiListViewModel;
    private RecyclerView mTransaksiRecyclerView;
    private TransaksiAdapter mAdapter;
    private TabLayout mTabLayout;
    private View mViewBackground;
    private LinearLayout mHeaderLayout;
    private Integer currentTabPosition = 0;

    public PickUpKurirFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TransaksiRepository.initialize(requireContext());
        mTransaksiListViewModel = new ViewModelProvider(this).get(TransaksiListViewModel.class);
        if (getArguments() != null) {
            currentTabPosition = getArguments().getInt("posisiTab");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_kurir_pickup, container, false);

        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("loginSession", Context.MODE_PRIVATE);
        String userJson = sharedPreferences.getString("dataUser", null);
        mTransaksiListViewModel.getDataTransaksi("Pick Up");

        final UserModel user;
        if (userJson != null) {
            Gson gson = new Gson();
            user = gson.fromJson(userJson, UserModel.class);
        } else {
            user = null;
        }

        TextView txtName = view.findViewById(R.id.txt_name);
        if (user != null) {
            txtName.setText("Halo, " + user.getNamaUser());
        } else {
            txtName.setText("Halo, Pengguna");
        }

        TextView txtDate = view.findViewById(R.id.txt_date);
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, dd MMM yyyy", new Locale("id", "ID"));
        String todayDate = dateFormat.format(calendar.getTime());
        txtDate.setText(todayDate);

        mTransaksiRecyclerView = view.findViewById(R.id.listPickUpTransaksi);
        mTransaksiRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mAdapter = new TransaksiAdapter();
        mTransaksiRecyclerView.setAdapter(mAdapter);

        mTabLayout = view.findViewById(R.id.tabLayout);
        mViewBackground = view.findViewById(R.id.viewBackground);
        mHeaderLayout = view.findViewById(R.id.headerLayout);

        mTabLayout.setVisibility(View.VISIBLE);
        mViewBackground.setVisibility(View.VISIBLE);
        mHeaderLayout.setVisibility(View.VISIBLE);

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0: // Tab "PickUp"
                        Log.d("kocakk","Pick UP");
                        mTransaksiListViewModel.getDataTransaksi("Pick Up");
                        break;
                    case 1: // Tab "Antar"
                        Log.d("kocakk","Antar");
                        mTransaksiListViewModel.getDataTransaksi("Proses");
                        break;
                }
                currentTabPosition = tab.getPosition();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                // No action needed here
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // No action needed here
            }
        });

        if (currentTabPosition == 0){
            mTransaksiListViewModel.getDataTransaksi("Pick Up");
        }else {
            TabLayout.Tab tab = mTabLayout.getTabAt(currentTabPosition);
            if (tab != null) {
                tab.select();
                mTransaksiListViewModel.getDataTransaksi("Pick Up");
                if (currentTabPosition == 0){
                    mTransaksiListViewModel.getDataTransaksi("Pick Up");
                } else if (currentTabPosition == 1) {
                    mTransaksiListViewModel.getDataTransaksi("Proses");
                }
            }
        }

        // Initial data load

        mTransaksiListViewModel.getAllTransaksiResponse().observe(getViewLifecycleOwner(), new Observer<TransaksiListVO>() {
            @Override
            public void onChanged(TransaksiListVO transaksiListVO) {
                if (transaksiListVO != null) {
                    Log.d("kocakk",transaksiListVO.getData().toString());
                    mAdapter.setTransaksiList(transaksiListVO.getData());
                } else {
                    mAdapter.setTransaksiList(new ArrayList<>()); // Clear the list if null
                }
            }
        });

        mBtnLogout = view.findViewById(R.id.btn_logout);
        mBtnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });


        return view;
    }

    public class TransaksiAdapter extends RecyclerView.Adapter<TransaksiAdapter.TransaksiViewHolder> {
        private List<TransaksiModel> transaksiList = new ArrayList<>();
        private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

        @NonNull
        @Override
        public TransaksiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_listpickup_kurir, parent, false);
            return new TransaksiViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull TransaksiViewHolder holder, int position) {
            TransaksiModel transaksi = transaksiList.get(position);
            holder.bind(transaksi);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(transaksi.getStatusPesanan().equals("Pick Up")){
                        KurirRincianFragment kurirRincianFragment = new KurirRincianFragment();
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("transaksi", transaksi);
                        bundle.putInt("posisiTab", currentTabPosition);
                        kurirRincianFragment.setArguments(bundle);

                        getParentFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container_kurir, kurirRincianFragment)  // Make sure R.id.PickUpKurir is correct
                                .addToBackStack(null)
                                .commit();
                    }else{
                        KurirRincianSelesaiFragment kurirRincianFragment = new KurirRincianSelesaiFragment();
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("transaksi", transaksi);
                        bundle.putInt("posisiTab", currentTabPosition);
                        kurirRincianFragment.setArguments(bundle);

                        getParentFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container_kurir, kurirRincianFragment)  // Make sure R.id.PickUpKurir is correct
                                .addToBackStack(null)
                                .commit();
                    }
                    // Open the detail fragment with the selected transaction

                }
            });
        }

        @Override
        public int getItemCount() {
            return transaksiList.size();
        }

        public void setTransaksiList(List<TransaksiModel> transaksiList) {
            this.transaksiList.clear(); // Clear existing list
            this.transaksiList.addAll(transaksiList); // Add new data
            notifyDataSetChanged();
        }

        class TransaksiViewHolder extends RecyclerView.ViewHolder {
            private TextView mCustomer;
            private TextView mTanggalPesanan;
            private TextView mTanggalSelesai;

            public TransaksiViewHolder(@NonNull View itemView) {
                super(itemView);
                mCustomer = itemView.findViewById(R.id.Customer);
                mTanggalPesanan = itemView.findViewById(R.id.tanggal_pesanan);
                mTanggalSelesai = itemView.findViewById(R.id.tanggal_selesai);
            }

            public void bind(TransaksiModel transaksi) {
                mCustomer.setText(transaksi.getNamaUser());
                mTanggalPesanan.setText("Tanggal Pesanan: " + dateFormat.format(transaksi.getTanggalPesanan()));
                mTanggalSelesai.setText("Estimasi Selesai: " + dateFormat.format(transaksi.getTanggalPengiriman()));
            }
        }
    }

    private void logout(){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("loginSession", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

}

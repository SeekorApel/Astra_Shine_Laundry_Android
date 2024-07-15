package com.polytechnic.astra.ac.id.astrashinelaundry.Fragment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.polytechnic.astra.ac.id.astrashinelaundry.API.Repository.TransaksiRepository;
import com.polytechnic.astra.ac.id.astrashinelaundry.API.VO.DurasiVo;
import com.polytechnic.astra.ac.id.astrashinelaundry.API.VO.TransaksiListVO;
import com.polytechnic.astra.ac.id.astrashinelaundry.Activity.MainActivity;
import com.polytechnic.astra.ac.id.astrashinelaundry.Model.DurasiModel;
import com.polytechnic.astra.ac.id.astrashinelaundry.Model.TransaksiModel;
import com.polytechnic.astra.ac.id.astrashinelaundry.Model.UserModel;
import com.polytechnic.astra.ac.id.astrashinelaundry.R;
import com.polytechnic.astra.ac.id.astrashinelaundry.ViewModel.DurasiViewModel;
import com.polytechnic.astra.ac.id.astrashinelaundry.ViewModel.TransaksiListViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ViewTransaksiFragment extends Fragment {
    private TransaksiListViewModel mTransaksiListViewModel;
    private RecyclerView mTransaksiRecyclerView;
    private TransaksiAdapter mAdapter;
    private TabLayout mTabLayout;
    private String status = "Pick Up";
    private Button mButtonSetting, mAddButtonEmpty;
    private FloatingActionButton mButtonAdd;
    private EditText mEditTextTanggal;
    private Spinner mSpinnerDurasi, mSpinnerAlamat;
    private ArrayAdapter<String> mDropDownAdapter;
    private DurasiViewModel mDurasiViewModel;

    public ViewTransaksiFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TransaksiRepository.initialize(requireContext());
        mTransaksiListViewModel = new ViewModelProvider(this).get(TransaksiListViewModel.class);
        mDurasiViewModel = new ViewModelProvider(this).get(DurasiViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_transaksi, container, false);

        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("loginSession", Context.MODE_PRIVATE);
        String userJson = sharedPreferences.getString("dataUser", null);

        final UserModel user;
        if (userJson != null) {
            Gson gson = new Gson();
            user = gson.fromJson(userJson, UserModel.class);
        } else {
            user = null;
        }

        // Mengambil TextView dari layout
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

        mTransaksiRecyclerView = view.findViewById(R.id.list_transaksi);
        mTransaksiRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new TransaksiAdapter(requireContext(), user);
        mTransaksiRecyclerView.setAdapter(mAdapter);

        // Mengambil elemen lain yang ingin diatur visibilitasnya
        ImageView imgDataEmpty = view.findViewById(R.id.img_data_empty);
        TextView txtEmptyMessage = view.findViewById(R.id.txt_empty_message);
        TextView txtEmptyMessage2 = view.findViewById(R.id.txt_empty_message2);
        mAddButtonEmpty = view.findViewById(R.id.btn_tambah);
        mButtonAdd = view.findViewById(R.id.btn_add);

        // Mengatur TabLayout
        mTabLayout = view.findViewById(R.id.tabLayout);
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0: // Tab "PickUp"
                        status = "Pick Up";
                        break;
                    case 1: // Tab "Proses"
                        status = "Proses";
                        break;
                    case 2: // Tab "Selesai"
                        status = "Selesai";
                        break;
                }
                loadData(user, status, imgDataEmpty, txtEmptyMessage, txtEmptyMessage2, mAddButtonEmpty, mButtonAdd);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        loadData(user, status, imgDataEmpty, txtEmptyMessage, txtEmptyMessage2, mAddButtonEmpty, mButtonAdd);

        mButtonSetting = view.findViewById(R.id.btn_setting);
        mButtonSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PengaturanFragment fragmentPengaturan = new PengaturanFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                // Ganti fragment dan tambahkan ke back stack
                transaction.replace(R.id.fragment_view_transaksi, fragmentPengaturan);
                transaction.addToBackStack(null);  // Menambahkan ke back stack untuk bisa kembali
                transaction.commit();
            }
        });

        mButtonAdd = view.findViewById(R.id.btn_add);
        mButtonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(v.getContext());
                View bottomSheetView = getLayoutInflater().inflate(R.layout.fragment_add_transaksi, null);
                bottomSheetDialog.setContentView(bottomSheetView);

                mEditTextTanggal = bottomSheetView.findViewById(R.id.tanggal_pickup);
                mSpinnerDurasi = bottomSheetView.findViewById(R.id.cb_durasi);
                mSpinnerAlamat = bottomSheetView.findViewById(R.id.cb_alamat);

                getDataDurasi();;

                mEditTextTanggal.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getTanggalPickUp();
                    }
                });

                Button closeButton = bottomSheetView.findViewById(R.id.btn_kembali);
                closeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bottomSheetDialog.dismiss();
                    }
                });

                bottomSheetDialog.show();
            }
        });

        mAddButtonEmpty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(v.getContext());
                View bottomSheetView = getLayoutInflater().inflate(R.layout.fragment_add_transaksi, null);
                bottomSheetDialog.setContentView(bottomSheetView);

                Button closeButton = bottomSheetView.findViewById(R.id.btn_kembali);
                closeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bottomSheetDialog.dismiss();
                    }
                });

                bottomSheetDialog.show();
            }
        });

        return view;
    }

    private void loadData(UserModel user, String status, ImageView imgDataEmpty, TextView txtEmptyMessage, TextView txtEmptyMessage2, Button mAddButtonEmpty, FloatingActionButton mButtonAdd) {
        if (user != null) {
            mAdapter.clear();
            mTransaksiListViewModel.getTransaksiByIdAndStatus(user.getIdUser(), status);
            mTransaksiListViewModel.getAllTransaksiResponse().observe(getViewLifecycleOwner(), new Observer<TransaksiListVO>() {
                @Override
                public void onChanged(TransaksiListVO transaksiListVO) {
                    if (transaksiListVO != null && !transaksiListVO.getData().isEmpty()) {
                        Log.e("ViewTransaksiFragment", "Data received: " + transaksiListVO.getData().size());
                        mAdapter.setTransaksiList(transaksiListVO.getData());
                        mTransaksiRecyclerView.setVisibility(View.VISIBLE);
                        imgDataEmpty.setVisibility(View.GONE);
                        txtEmptyMessage.setVisibility(View.GONE);
                        txtEmptyMessage2.setVisibility(View.GONE);
                        mAddButtonEmpty.setVisibility(View.GONE);
                        mButtonAdd.setVisibility(View.VISIBLE);
                    } else {
                        Log.e("ViewTransaksiFragment", "TransaksiListVO is null or empty");
                        mAdapter.setTransaksiList(new ArrayList<>()); // Clear the list if null or empty
                        mTransaksiRecyclerView.setVisibility(View.GONE);
                        imgDataEmpty.setVisibility(View.VISIBLE);
                        txtEmptyMessage.setVisibility(View.VISIBLE);
                        txtEmptyMessage2.setVisibility(View.VISIBLE);
                        mAddButtonEmpty.setVisibility(View.VISIBLE);
                        mButtonAdd.setVisibility(View.GONE);
                    }
                }
            });
        }
    }

    public class TransaksiAdapter extends RecyclerView.Adapter<ViewTransaksiFragment.TransaksiAdapter.TransaksiViewHolder> {
        private List<TransaksiModel> transaksiList = new ArrayList<>();
        private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        private UserModel user;
        private Context context;

        public TransaksiAdapter(Context context, UserModel user) {
            this.context = context;
            this.user = user;
        }

        @NonNull
        @Override
        public TransaksiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_listpickup_kurir, parent, false);
            return new TransaksiViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull TransaksiViewHolder holder, int position) {
            TransaksiModel transaksi = transaksiList.get(position);
            if (user != null) {
                holder.bind(transaksi, user);
                holder.itemView.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        // Open the detail fragment with the selected transaction
                        CustomerRincianFragment customerRincianFragment = new CustomerRincianFragment();
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("transaksi", transaksi);
                        customerRincianFragment.setArguments(bundle);

                        getParentFragmentManager().beginTransaction()
                                .replace(R.id.fragment_view_transaksi, customerRincianFragment)  // Make sure R.id.PickUpKurir is correct
                                .addToBackStack(null)
                                .commit();
                    }
                });
            } else {
                Log.e("ViewTransaksiFragment", "User is null");
            }
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

        public void clear() {
            this.transaksiList.clear();
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

            public void bind(TransaksiModel transaksi, UserModel userModel) {
                Log.e("BIND", "Binding data for user: " + userModel.getIdUser());
                mCustomer.setText("NOTA-00" + transaksi.getIdTransaksi());
                mTanggalPesanan.setText("Tanggal Pesanan: " + dateFormat.format(transaksi.getTanggalPesanan()));
                mTanggalSelesai.setText("Estimasi Selesai: " + dateFormat.format(transaksi.getTanggalPengiriman()));
            }
        }
    }

    private void getDataDurasi(){
        mDurasiViewModel.getDataDurasi();
        mDurasiViewModel.getAllDurasiResponse().observe(getViewLifecycleOwner(), new Observer<DurasiVo>() {
            @Override
            public void onChanged(DurasiVo durasiVo) {
                List<DurasiModel> durasiModels = durasiVo.getData();
                List<String> dropdownValues = new ArrayList<>();
                dropdownValues.add("--pilih durasi--");
                for (DurasiModel model : durasiModels) {
                    dropdownValues.add(model.getNamaDurasi());
                }

                // Buat dan setel adapter dengan data dari database
                ArrayAdapter<String> mDropDownAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, dropdownValues);
                mDropDownAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mSpinnerDurasi.setAdapter(mDropDownAdapter);

                // Atur default selection (opsional)
                mSpinnerDurasi.setSelection(0);
            }
        });
    }

    private void getTanggalPickUp(){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(
                getActivity(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month + 1;
                        String date = dayOfMonth + "-" + month + "-" + year;
                        mEditTextTanggal.setText(date);
                    }
                },
                year, month, day);
        dialog.show();
    }

    private void getDataAlamat(){
        mDurasiViewModel.getDataDurasi();
        mDurasiViewModel.getAllDurasiResponse().observe(getViewLifecycleOwner(), new Observer<DurasiVo>() {
            @Override
            public void onChanged(DurasiVo durasiVo) {
                List<DurasiModel> durasiModels = durasiVo.getData();
                List<String> dropdownValues = new ArrayList<>();
                for (DurasiModel model : durasiModels) {
                    dropdownValues.add(model.getNamaDurasi());
                }

                // Buat dan setel adapter dengan data dari database
                ArrayAdapter<String> mDropDownAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, dropdownValues);
                mDropDownAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mSpinnerDurasi.setAdapter(mDropDownAdapter);

                // Atur default selection (opsional)
                mSpinnerDurasi.setSelection(0);
            }
        });
    }
}
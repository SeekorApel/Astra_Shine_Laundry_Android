package com.polytechnic.astra.ac.id.astrashinelaundry.Fragment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
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
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.polytechnic.astra.ac.id.astrashinelaundry.API.Repository.AlamatRepository;
import com.polytechnic.astra.ac.id.astrashinelaundry.API.Repository.TransaksiRepository;
import com.polytechnic.astra.ac.id.astrashinelaundry.API.VO.AlamatListVO;
import com.polytechnic.astra.ac.id.astrashinelaundry.API.VO.DurasiVo;
import com.polytechnic.astra.ac.id.astrashinelaundry.API.VO.TransaksiListVO;
import com.polytechnic.astra.ac.id.astrashinelaundry.Model.AlamatModel;
import com.polytechnic.astra.ac.id.astrashinelaundry.Model.DurasiModel;
import com.polytechnic.astra.ac.id.astrashinelaundry.Model.TransaksiListModel;
import com.polytechnic.astra.ac.id.astrashinelaundry.Model.TransaksiModel;
import com.polytechnic.astra.ac.id.astrashinelaundry.Model.UserModel;
import com.polytechnic.astra.ac.id.astrashinelaundry.R;
import com.polytechnic.astra.ac.id.astrashinelaundry.ViewModel.AlamatViewModel;
import com.polytechnic.astra.ac.id.astrashinelaundry.ViewModel.DurasiViewModel;
import com.polytechnic.astra.ac.id.astrashinelaundry.ViewModel.TransaksiListViewModel;

import java.text.ParseException;
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
    private Button mButtonSetting, mAddButtonEmpty, mButtonPesan;
    private FloatingActionButton mButtonAdd;
    private EditText mEditTextTanggal;
    private Spinner mSpinnerDurasi, mSpinnerAlamat;
    private ArrayAdapter<String> mDropDownAdapter;
    private DurasiViewModel mDurasiViewModel;
    private AlamatViewModel  mAlamatViewModel;
    private Integer idUser;
    private List<DurasiModel> mDurasiModelList;
    private List<AlamatModel> mAlamatModels;
    private Integer currentTabPosition = 0;
    private Integer sizeAlamat = 0;
    public ViewTransaksiFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TransaksiRepository.initialize(requireContext());
        AlamatRepository.initialize(requireContext());
        mTransaksiListViewModel = new ViewModelProvider(this).get(TransaksiListViewModel.class);
        mDurasiViewModel = new ViewModelProvider(this).get(DurasiViewModel.class);
        mAlamatViewModel = new ViewModelProvider(this).get(AlamatViewModel.class);
        if (getArguments() != null) {
            currentTabPosition = getArguments().getInt("posisiTab");
        }
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

        idUser = user.getIdUser();

        mAlamatViewModel.getDataAlamat(idUser);
        mAlamatViewModel.getAllAlamatResponse().observe(getViewLifecycleOwner(), new Observer<AlamatListVO>() {
            @Override
            public void onChanged(AlamatListVO alamatListVO) {
                sizeAlamat = alamatListVO.getData().size();

                if(sizeAlamat == 0){
                    AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
                    builder.setTitle("Warning");
                    builder.setMessage("Apakah anda ingin menambahkan alamat?");

                    builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            navigateToFragmentAlamat();
                        }
                    });

                    builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }
        });

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
                        status = "Antar";
                        break;
                    case 3: // Tab "Selesai"
                        status = "Selesai";
                        break;
                }
                currentTabPosition = tab.getPosition();
                loadData(user, status, imgDataEmpty, txtEmptyMessage, txtEmptyMessage2, mAddButtonEmpty, mButtonAdd);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }

        });

        Log.d("SIgma", String.valueOf(currentTabPosition));
        if (currentTabPosition == 0){
            status = "Pick Up";
        }else {
            TabLayout.Tab tab = mTabLayout.getTabAt(currentTabPosition);
            if (tab != null) {
                tab.select();
                status = "Pick Up";
                if (currentTabPosition == 1){
                    status = "Proses";
                } else if (currentTabPosition == 2) {
                    status = "Antar";
                } else if (currentTabPosition == 3) {
                    status = "Selesai";
                }
            }
        }

        loadData(user, status, imgDataEmpty, txtEmptyMessage, txtEmptyMessage2, mAddButtonEmpty, mButtonAdd);
        mButtonSetting = view.findViewById(R.id.btn_setting);
        mButtonSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PengaturanFragment fragmentPengaturan = new PengaturanFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                // Ganti fragment dan tambahkan ke back stack
                transaction.replace(R.id.fragment_container_customer, fragmentPengaturan);
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
                mButtonPesan = bottomSheetView.findViewById(R.id.btn_pesan);

                getDataDurasi();

                getDataAlamat(idUser);

                saveTransaksi(idUser,bottomSheetDialog);


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

        mAddButtonEmpty = view.findViewById(R.id.btn_tambah);
        mAddButtonEmpty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAlamatViewModel.getDataAlamat(idUser);
                mAlamatViewModel.getAllAlamatResponse().observe(getViewLifecycleOwner(), new Observer<AlamatListVO>() {
                    @Override
                    public void onChanged(AlamatListVO alamatListVO) {
                        sizeAlamat = alamatListVO.getData().size();
                        Log.d("sizedor",String.valueOf(sizeAlamat));
                        if(sizeAlamat == 0){
                            AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
                            builder.setTitle("Warning");
                            builder.setMessage("Apakah anda ingin menambahkan alamat?");

                            builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    navigateToFragmentAlamat();
                                }
                            });

                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }else {
                            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(v.getContext());
                            View bottomSheetView = getLayoutInflater().inflate(R.layout.fragment_add_transaksi, null);
                            bottomSheetDialog.setContentView(bottomSheetView);

                            mEditTextTanggal = bottomSheetView.findViewById(R.id.tanggal_pickup);
                            mSpinnerDurasi = bottomSheetView.findViewById(R.id.cb_durasi);
                            mSpinnerAlamat = bottomSheetView.findViewById(R.id.cb_alamat);
                            mButtonPesan = bottomSheetView.findViewById(R.id.btn_pesan);

                            getDataDurasi();


                            getDataAlamat(idUser);

                            saveTransaksi(idUser,bottomSheetDialog);

                            Button closeButton = bottomSheetView.findViewById(R.id.btn_kembali);
                            closeButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    bottomSheetDialog.dismiss();
                                }
                            });

                            bottomSheetDialog.show();
                        }
                    }
                });
            }
        });

        return view;
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
                        Bundle args = new Bundle();
                        args.putSerializable("transaksi", transaksi);
                        args.putInt("posisiTab", currentTabPosition);
                        customerRincianFragment.setArguments(args);

                        getParentFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container_customer, customerRincianFragment)  // Make sure R.id.PickUpKurir is correct
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
                mCustomer.setText("NOTA-" + transaksi.getIdTransaksi());
                mTanggalPesanan.setText("Tanggal Pesanan: " + dateFormat.format(transaksi.getTanggalPesanan()));
                mTanggalSelesai.setText("Estimasi Selesai: " + dateFormat.format(transaksi.getTanggalPengiriman()));
            }
        }
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

    private void getDataDurasi(){
        mDurasiViewModel.getDataDurasi();
        mDurasiViewModel.getAllDurasiResponse().observe(getViewLifecycleOwner(), new Observer<DurasiVo>() {
            @Override
            public void onChanged(DurasiVo durasiVo) {
                mDurasiModelList = durasiVo.getData();
                List<String> dropdownValues = new ArrayList<>();
                dropdownValues.add("-- Pilih Durasi --");
                for (DurasiModel model : mDurasiModelList) {
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

    private void getTanggalPickUp() {
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

        // Set the minimum date to today
        dialog.getDatePicker().setMinDate(calendar.getTimeInMillis());

        dialog.show();
    }

    private void getDataAlamat(Integer id_user){
        mAlamatViewModel.getDataAlamat(id_user);
        mAlamatViewModel.getAllAlamatResponse().observe(getViewLifecycleOwner(), new Observer<AlamatListVO>() {
            @Override
            public void onChanged(AlamatListVO alamatListVO) {
                mAlamatModels = alamatListVO.getData();
                List<String> dropdownValues = new ArrayList<>();
                dropdownValues.add("-- Pilih Alamat --");
                for (AlamatModel model : mAlamatModels) {
                    dropdownValues.add(model.getNamaAlamat());
                }

                // Buat dan setel adapter dengan data dari database
                ArrayAdapter<String> mDropDownAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, dropdownValues);
                mDropDownAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mSpinnerAlamat.setAdapter(mDropDownAdapter);

                // Atur default selection (opsional)
                mSpinnerAlamat.setSelection(0);
            }
        });
    }

    private void saveTransaksi(Integer idUser, BottomSheetDialog bottomSheetDialog){
        mEditTextTanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getTanggalPickUp();
            }
        });

        mButtonPesan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int durasiId = 0;
                int alamatId = 0;
                Double jarak;
                Integer total_harga, ongkir;
                String tanggal_pickup;
                tanggal_pickup = mEditTextTanggal.getText().toString();

                String durasi = mSpinnerDurasi.getSelectedItem().toString();
                String alamat = mSpinnerAlamat.getSelectedItem().toString();

                int durasiIndex = mSpinnerDurasi.getSelectedItemPosition();
                int alamatIndex = mSpinnerAlamat.getSelectedItemPosition();

                Log.d("tanggal",String.valueOf(tanggal_pickup));
                if (durasiIndex > 0 && alamatIndex > 0) {
                    durasiId = mDurasiModelList.get(durasiIndex - 1).getIdDurasi();
                    alamatId = mAlamatModels.get(alamatIndex - 1).getIdAlamat();
                    jarak = mAlamatModels.get(alamatIndex - 1).getJarak();


                    if (jarak < 3.0){
                        ongkir = 0;
                    }else if (jarak < 5.0){
                        ongkir = 5000;
                    }else {
                        ongkir = 10000;
                    }

                    Log.d("tolong",String.valueOf(ongkir));

                    total_harga = Integer.valueOf(String.valueOf(ongkir));
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-M-yyyy");
                    Date today = new Date();
                    Date datePickup = null;
                    try {
                        datePickup = dateFormat.parse(tanggal_pickup);
                        today = dateFormat.parse(dateFormat.format(today));
                        if (datePickup.before(today)){
                            Toast.makeText(getContext(), "Tanggal pick up tidak boleh kurang dari hari ini", Toast.LENGTH_LONG).show();
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                        Toast.makeText(getContext(), "Data tidak boleh kosong", Toast.LENGTH_LONG).show();
                        return;
                    }

                    Calendar calendar = Calendar.getInstance();

                    int hour = calendar.get(Calendar.HOUR_OF_DAY);

                    if (datePickup.equals(today) && hour > 19){
                        Toast.makeText(getContext(), "Pick up hanya sampai jam 7", Toast.LENGTH_LONG).show();
                        return;
                    }

                    // Log the formatted date for debugging
                    SimpleDateFormat logDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                    String formattedDatePickup = logDateFormat.format(datePickup);
                    Log.d("tanggal", formattedDatePickup);

                    TransaksiListModel transaksiModel = new TransaksiListModel(idUser, alamatId, durasiId, formattedDatePickup, ongkir, total_harga);
                    mTransaksiListViewModel.saveTransaksi(transaksiModel);

                    navigateToFragmentTransaksi();
                    bottomSheetDialog.dismiss();
                    Toast.makeText(getContext(), "Data berhasil disimpan", Toast.LENGTH_LONG).show();

                    mTransaksiListViewModel.getSuccessResponse().observe(getViewLifecycleOwner(), message -> {
                        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
                        navigateToFragmentTransaksi();
                    });

                    mTransaksiListViewModel.getErrorResponse().observe(getViewLifecycleOwner(), error -> {
                        Toast.makeText(getContext(), error, Toast.LENGTH_LONG).show();
                    });
                }else {
                    Toast.makeText(getContext(), "Data Tidak Boleh Kosong", Toast.LENGTH_LONG).show();
                }
            }
        });


    }
    private void navigateToFragmentTransaksi(){
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container_customer, new ViewTransaksiFragment());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void navigateToFragmentAlamat(){
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container_customer, new AlamatFragment());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

}
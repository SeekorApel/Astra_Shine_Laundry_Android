package com.polytechnic.astra.ac.id.astrashinelaundry.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;

import com.polytechnic.astra.ac.id.astrashinelaundry.API.Repository.TransaksiRepository;
import com.polytechnic.astra.ac.id.astrashinelaundry.R;
import com.polytechnic.astra.ac.id.astrashinelaundry.ViewModel.DurasiViewModel;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;

import com.polytechnic.astra.ac.id.astrashinelaundry.API.Repository.TransaksiRepository;
import com.polytechnic.astra.ac.id.astrashinelaundry.R;
import com.polytechnic.astra.ac.id.astrashinelaundry.ViewModel.DurasiViewModel;

public class AddTransaksiFragment extends Fragment {

    private DurasiViewModel mDurasiViewModel;
    private Spinner mSpinnerDurasi, mSpinnerAlamat;
    private EditText mEditTextTanggal;

    public AddTransaksiFragment() {
        // Required empty public constructor
    }

    public static AddTransaksiFragment newInstance(String param1, String param2) {
        AddTransaksiFragment fragment = new AddTransaksiFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TransaksiRepository.initialize(requireContext());
        mDurasiViewModel = new ViewModelProvider(this).get(DurasiViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_transaksi, container, false);

        return view;
    }

}
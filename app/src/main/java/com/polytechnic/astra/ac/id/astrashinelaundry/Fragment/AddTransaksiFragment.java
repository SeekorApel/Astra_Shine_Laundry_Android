package com.polytechnic.astra.ac.id.astrashinelaundry.Fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import com.polytechnic.astra.ac.id.astrashinelaundry.API.Repository.TransaksiRepository;
import com.polytechnic.astra.ac.id.astrashinelaundry.Activity.CustomerActivity;
import com.polytechnic.astra.ac.id.astrashinelaundry.Model.DurasiModel;
import com.polytechnic.astra.ac.id.astrashinelaundry.R;
import com.polytechnic.astra.ac.id.astrashinelaundry.ViewModel.DurasiViewModel;
import com.polytechnic.astra.ac.id.astrashinelaundry.ViewModel.TransaksiListViewModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddTransaksiFragment extends Fragment {

    private DurasiViewModel mDurasiViewModel;
    private Spinner mSpinnerDurasi;
    private Button mButtonDate;

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

        mButtonDate = view.findViewById(R.id.tanggal_pickup);
        mButtonDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateTimePicker();
            }
        });


        return view;
    }


    private void showDateTimePicker() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                requireContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // Tampilkan tanggal yang dipilih di button
                        String selectedDate = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                        mButtonDate.setText(selectedDate);
                    }
                },
                year, month, dayOfMonth);

        // Tampilkan DatePickerDialog
        datePickerDialog.show();
    }
}
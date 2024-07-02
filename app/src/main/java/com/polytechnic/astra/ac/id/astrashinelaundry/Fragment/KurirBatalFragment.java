package com.polytechnic.astra.ac.id.astrashinelaundry.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.polytechnic.astra.ac.id.astrashinelaundry.API.VO.TransaksiListVO;
import com.polytechnic.astra.ac.id.astrashinelaundry.Model.TransaksiModel;
import com.polytechnic.astra.ac.id.astrashinelaundry.R;
import com.polytechnic.astra.ac.id.astrashinelaundry.ViewModel.TransaksiListViewModel;

public class KurirBatalFragment extends BottomSheetDialogFragment {
    private Button mBtnKembali, mBtnBatal;
    private EditText mEditCatatan;
    private TransaksiListViewModel mTransaksiListViewModel;
    private TransaksiModel transaksi;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mTransaksiListViewModel = new TransaksiListViewModel();

        if (getArguments() != null) {
            transaksi = (TransaksiModel) getArguments().getSerializable("transaksi");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_kurir_batal_pemesanan, container, false);

        mEditCatatan = view.findViewById(R.id.catatan);
        if (transaksi.getCatatan() != null) {
            mEditCatatan.setText(transaksi.getCatatan());
            mEditCatatan.setEnabled(false);
        }

        mBtnBatal = view.findViewById(R.id.btn_batal);
        mBtnBatal.setEnabled(transaksi != null && transaksi.getCatatan() == null);
        mBtnBatal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String catatan = mEditCatatan.getText().toString();
                if (!catatan.isEmpty() && transaksi != null) {
                    mTransaksiListViewModel.batalkanTrsKurir(transaksi.getIdTransaksi().toString(), catatan);
                    Toast.makeText(getContext(), "Catatan pembatalan berhasil dibuat", Toast.LENGTH_SHORT).show();
                    dismiss();
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.PickUpKurir, new PickUpKurirFragment());
                    transaction.addToBackStack(null);
                    transaction.commit();
                } else {
                    Toast.makeText(getContext(), "Caatan tidak boleh kosong", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mBtnKembali = view.findViewById(R.id.btn_kembali);
        mBtnKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return view;
    }
}

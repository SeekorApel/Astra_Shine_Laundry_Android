package com.polytechnic.astra.ac.id.astrashinelaundry.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.polytechnic.astra.ac.id.astrashinelaundry.API.Repository.AlamatRepository;
import com.polytechnic.astra.ac.id.astrashinelaundry.API.VO.AlamatListVO;
import com.polytechnic.astra.ac.id.astrashinelaundry.Model.AlamatModel;
import com.polytechnic.astra.ac.id.astrashinelaundry.Model.UserModel;
import com.polytechnic.astra.ac.id.astrashinelaundry.R;
import com.polytechnic.astra.ac.id.astrashinelaundry.ViewModel.AlamatViewModel;

import java.util.ArrayList;
import java.util.List;


public class AlamatFragment extends Fragment {

    private FloatingActionButton mBtnAdd;

    private AlamatViewModel mAlamatViewModel;

    private RecyclerView mListAlamatRecyclerView;

    private ListAlamatAdapter mListAlamatAdapter;

    private ImageView mBtnKembali;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AlamatRepository.initialize(requireContext());
        mAlamatViewModel = new ViewModelProvider(this).get(AlamatViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_alamat, container, false);

        mBtnAdd = view.findViewById(R.id.btn_add);
        mBtnKembali = view.findViewById(R.id.btn_kembali);
        mListAlamatRecyclerView = view.findViewById(R.id.list_view_item_alamat);
        mListAlamatRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mListAlamatAdapter = new ListAlamatAdapter();
        mListAlamatRecyclerView.setAdapter(mListAlamatAdapter);

        //Mendapatkan Session Login
        UserModel user = getUserModel();

        mBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToFragmentAddAlamat();
            }
        });

        mBtnKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PengaturanFragment fragmentPengaturan = new PengaturanFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container_customer, fragmentPengaturan);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        //Mengambil data dari database
        mAlamatViewModel.getDataAlamat(user.getIdUser());
        mAlamatViewModel.getAllAlamatResponse().observe(getViewLifecycleOwner(), new Observer<AlamatListVO>() {
            @Override
            public void onChanged(AlamatListVO alamatListVO) {

                //Memberikan untuk adapter
                if(alamatListVO != null){
                    mListAlamatAdapter.setAlamatList(alamatListVO.getData());
                }else {
                    mListAlamatAdapter.setAlamatList(new ArrayList<>());
                }
            }
        });

        return view;
    }

    private UserModel getUserModel(){
        Context context = getActivity();
        if (context != null) {
            SharedPreferences sharedPreferences = context.getSharedPreferences("loginSession", Context.MODE_PRIVATE);
            String userJson = sharedPreferences.getString("dataUser", null);
            // Use Gson to convert JSON string to object
            Gson gson = new Gson();
            UserModel user = gson.fromJson(userJson, UserModel.class);
            return user;
        }
        return null;
    }

    private class ListAlamatAdapter extends RecyclerView.Adapter<ListAlamatAdapter.ListAlamatViewHolder>{

        public class ListAlamatViewHolder extends RecyclerView.ViewHolder{

            private TextView mTxtTitleAlamat, mTxtAlamat;

            public ListAlamatViewHolder(@NonNull View itemView) {
                super(itemView);
                mTxtTitleAlamat = itemView.findViewById(R.id.txt_title_alamat);
                mTxtAlamat = itemView.findViewById(R.id.txt_alamat);
            }

            public void bind(AlamatModel alamat){
                mTxtTitleAlamat.setText(alamat.getNamaAlamat());
                mTxtAlamat.setText(alamat.getAlamat());
            }
        }

        private List<AlamatModel> alamatList = new ArrayList<>();

        @NonNull
        @Override
        public ListAlamatAdapter.ListAlamatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_list_item_alamat, parent,false);
            return new ListAlamatViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ListAlamatAdapter.ListAlamatViewHolder holder, int position) {
            AlamatModel alamat = alamatList.get(position);
            holder.bind(alamat);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Open the detail fragment with the selected transaction
                    EditAlamatFragment editAlamatFragment = new EditAlamatFragment();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("alamat", alamat);
                    editAlamatFragment.setArguments(bundle);

                    getParentFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container_customer, editAlamatFragment)  // Make sure R.id.PickUpKurir is correct
                            .addToBackStack(null)
                            .commit();
                }
            });
        }

        @Override
        public int getItemCount() {
            return alamatList.size();
        }

        public void setAlamatList(List<AlamatModel> alamatList){
           this.alamatList.clear();
           this.alamatList.addAll(alamatList);
           notifyDataSetChanged();
        }
    }

    private void navigateToFragmentAddAlamat(){
        AddAlamatFragment fragmentTambahAlamat = new AddAlamatFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container_customer, fragmentTambahAlamat);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}
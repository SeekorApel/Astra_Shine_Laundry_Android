package com.polytechnic.astra.ac.id.astrashinelaundry.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.polytechnic.astra.ac.id.astrashinelaundry.API.Repository.AlamatRepository;
import com.polytechnic.astra.ac.id.astrashinelaundry.API.VO.AlamatListVO;
import com.polytechnic.astra.ac.id.astrashinelaundry.Model.AlamatModel;
import com.polytechnic.astra.ac.id.astrashinelaundry.Model.UserModel;
import com.polytechnic.astra.ac.id.astrashinelaundry.R;
import com.polytechnic.astra.ac.id.astrashinelaundry.ViewModel.AlamatListViewModel;
import com.polytechnic.astra.ac.id.astrashinelaundry.ViewModel.TransaksiListViewModel;

import java.util.ArrayList;
import java.util.List;


public class AlamatFragment extends Fragment {

    private AlamatListViewModel mAlamatListViewModel;

    private RecyclerView mListAlamatRecyclerView;

    private ListAlamatAdapter mListAlamatAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AlamatRepository.initialize(requireContext());
        mAlamatListViewModel = new ViewModelProvider(this).get(AlamatListViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_alamat, container, false);

        mListAlamatRecyclerView = view.findViewById(R.id.list_view_item_alamat);
        mListAlamatRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mListAlamatAdapter = new ListAlamatAdapter();
        mListAlamatRecyclerView.setAdapter(mListAlamatAdapter);

        //Mendapatkan Session Login
        UserModel user = getUserModel();

        //Mengambil data dari database
        mAlamatListViewModel.getDataAlamat(user.getIdUser());
        mAlamatListViewModel.getAllAlamatResponse().observe(getViewLifecycleOwner(), new Observer<AlamatListVO>() {
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
}
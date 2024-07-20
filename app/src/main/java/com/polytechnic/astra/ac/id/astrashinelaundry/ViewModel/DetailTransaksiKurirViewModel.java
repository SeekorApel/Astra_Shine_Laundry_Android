package com.polytechnic.astra.ac.id.astrashinelaundry.ViewModel;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.polytechnic.astra.ac.id.astrashinelaundry.API.Repository.LayananRepository;
import com.polytechnic.astra.ac.id.astrashinelaundry.API.Repository.TransaksiRepository;
import com.polytechnic.astra.ac.id.astrashinelaundry.API.Repository.UserRepository;
import com.polytechnic.astra.ac.id.astrashinelaundry.API.VO.DetailTransaksiVo;
import com.polytechnic.astra.ac.id.astrashinelaundry.API.VO.LayananVO;
import com.polytechnic.astra.ac.id.astrashinelaundry.API.VO.TransaksiListVO;
import com.polytechnic.astra.ac.id.astrashinelaundry.Model.DetailTransaksiModel;
import com.polytechnic.astra.ac.id.astrashinelaundry.Model.UserModel;

import java.util.ArrayList;
import java.util.List;

public class DetailTransaksiKurirViewModel extends ViewModel {
    private static final String TAG = "DetailTransaksiViewModel";
    private MutableLiveData<Double> totalKg = new MutableLiveData<>(0.0);
    private MutableLiveData<Integer> totalPcs = new MutableLiveData<>(0);
    private MutableLiveData<Integer> totalHarga = new MutableLiveData<>(0);
    private MutableLiveData<Integer> totalHargaPcs = new MutableLiveData<>(0);
    private MutableLiveData<Integer> totalHargaKg = new MutableLiveData<>(0);

    private MutableLiveData<LayananVO> LayananResponse = new MutableLiveData<>();
    private MutableLiveData<DetailTransaksiVo> detailTransaksiResponse = new MutableLiveData<>();
    private MutableLiveData<TransaksiListVO> transaksiResponse = new MutableLiveData<>();
    public LiveData<Double> getTotalKg() {
        return totalKg;
    }

    public LiveData<Integer> getTotalPcs() {
        return totalPcs;
    }

    public LiveData<Integer> getTotalHarga() {
        return totalHarga;
    }
    public LiveData<Integer> getTotalHargaPcs() {
        return totalHargaPcs;
    }
    public LiveData<Integer> getTotalHargaKg() {
        return totalHargaKg;
    }

    public void addKg(Double kg) {
        totalKg.setValue(totalKg.getValue() + kg);
    }

    public void addPcs(int pcs) {
        totalPcs.setValue(totalPcs.getValue() + pcs);
    }

    public void addHarga(int harga) {
        totalHarga.setValue(harga);
    }
    public void addHargaPcs(int harga) {
        totalHargaPcs.setValue(harga);
    }
    public void addHargaKg(int harga) {
        totalHargaKg.setValue(harga);
    }

    public void subtractKg(Double kg) {
        double newKg = totalKg.getValue() - kg;
        totalKg.setValue(newKg < 0 ? 0.0 : newKg);
    }

    public void subtractPcs(int pcs) {
        int newPcs = totalPcs.getValue() - pcs;
        totalPcs.setValue(newPcs < 0 ? 0 : newPcs);
    }

    public void subtractHarga(int harga) {
        int newHarga = totalHarga.getValue() - harga;
        totalHarga.setValue(newHarga < 0 ? 0 : newHarga);
    }
    private MutableLiveData<List<DetailTransaksiModel>> selectedLayananList = new MutableLiveData<>(new ArrayList<>());

    public void addLayanan(DetailTransaksiModel layanan) {
        List<DetailTransaksiModel> currentList = selectedLayananList.getValue();
        currentList.add(layanan);
        selectedLayananList.setValue(currentList);
    }

    public void removeLayanan(DetailTransaksiModel layanan) {
        List<DetailTransaksiModel> currentList = selectedLayananList.getValue();
        currentList.remove(layanan);
        selectedLayananList.setValue(currentList);
    }

    public LiveData<List<DetailTransaksiModel>> getSelectedLayananList() {
        return selectedLayananList;
    }

    private final LayananRepository mLayananRepository;
    private final TransaksiRepository mTransaksiRepository;

    public DetailTransaksiKurirViewModel(){
        mLayananRepository = LayananRepository.get();
        mTransaksiRepository = TransaksiRepository.get();
    }

    public LiveData<LayananVO> getAllLayananResponse() {
        return LayananResponse;
    }
    public LiveData<DetailTransaksiVo> getAllDetailResponse() {
        return detailTransaksiResponse;
    }
    public LiveData<TransaksiListVO> getAllTransaksiResponse() {
        return transaksiResponse;
    }
    private MutableLiveData<String> createDetailMessage = new MutableLiveData<>();
    private MutableLiveData<String> errorMessage = new MutableLiveData<>();

    public void getDataLayanan() {
        LayananResponse = mLayananRepository.getAllLayanan();
    }

    @SuppressLint("LongLogTag")
    public void getDetailTransaksi(String idTransaksi) {
        Log.d(TAG,"getDetailTransaksi()");
        detailTransaksiResponse = mTransaksiRepository.getDetailTransaksi(idTransaksi);
    }
    @SuppressLint("LongLogTag")
    public void saveTotal(String idTransaksi, String total) {
        Log.i(TAG, "saveTotal() called");
        transaksiResponse = mTransaksiRepository.saveTotal(idTransaksi,total);
    }

    @SuppressLint("LongLogTag")
    public void createDetailTransaksi(List<DetailTransaksiModel> detailTransaksi) {
        Log.i(TAG, "createDetailTransaksi() called");
        detailTransaksiResponse = mTransaksiRepository.createDetailTransaksi(detailTransaksi, new TransaksiRepository.messageCallback() {
            @Override
            public void onSuccess(String message) {
                createDetailMessage.postValue(message);
            }

            @Override
            public void onError(String error) {
                errorMessage.postValue(error);
            }
        });
    }
}
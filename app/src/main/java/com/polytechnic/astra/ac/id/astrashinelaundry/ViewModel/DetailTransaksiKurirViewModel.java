package com.polytechnic.astra.ac.id.astrashinelaundry.ViewModel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.polytechnic.astra.ac.id.astrashinelaundry.API.Repository.LayananRepository;
import com.polytechnic.astra.ac.id.astrashinelaundry.API.Repository.TransaksiRepository;
import com.polytechnic.astra.ac.id.astrashinelaundry.API.VO.LayananVO;
import com.polytechnic.astra.ac.id.astrashinelaundry.API.VO.TransaksiListVO;

public class DetailTransaksiKurirViewModel extends ViewModel {
    private static final String TAG = "DetailTransaksiViewModel";
    private MutableLiveData<Double> totalKg = new MutableLiveData<>(0.0);
    private MutableLiveData<Integer> totalPcs = new MutableLiveData<>(0);
    private MutableLiveData<Integer> totalHarga = new MutableLiveData<>(0);
    private MutableLiveData<Integer> totalHargaPcs = new MutableLiveData<>(0);
    private MutableLiveData<Integer> totalHargaKg = new MutableLiveData<>(0);

    private MutableLiveData<LayananVO> LayananResponse = new MutableLiveData<>();
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

    private final LayananRepository mLayananRepository;

    public DetailTransaksiKurirViewModel(){
        mLayananRepository = LayananRepository.get();
    }

    public LiveData<LayananVO> getAllLayananResponse() {
        return LayananResponse;
    }

    public void getDataLayanan() {
        LayananResponse = mLayananRepository.getAllLayanan();
    }
}
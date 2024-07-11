package com.polytechnic.astra.ac.id.astrashinelaundry.API.VO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.polytechnic.astra.ac.id.astrashinelaundry.Model.DetailTransaksiModel;
import com.polytechnic.astra.ac.id.astrashinelaundry.Model.TransaksiModel;

import java.util.List;

public class DetailTransaksiVO {
    private int status;

    private List<DetailTransaksiModel> data;

    private String message;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<DetailTransaksiModel> getData() {
        return data;
    }

    public void setData(List<DetailTransaksiModel> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

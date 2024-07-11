package com.polytechnic.astra.ac.id.astrashinelaundry.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DetailTransaksiModel {
    @SerializedName("idTransaksi")
    @Expose
    private Integer idTransaksi;

    @SerializedName("idLayanan")
    @Expose
    private Integer idLayanan;

    @SerializedName("namaLayanan")
    @Expose
    private String namaLayanan;

    @SerializedName("qty")
    @Expose
    private Double qty;

    public Integer getIdTransaksi() {
        return idTransaksi;
    }

    public void setIdTransaksi(Integer idTransaksi) {
        this.idTransaksi = idTransaksi;
    }

    public Integer getIdLayanan() {
        return idLayanan;
    }

    public void setIdLayanan(Integer idLayanan) {
        this.idLayanan = idLayanan;
    }

    public String getNamaLayanan() {
        return namaLayanan;
    }

    public void setNamaLayanan(String namaLayanan) {
        this.namaLayanan = namaLayanan;
    }

    public Double getQty() {
        return qty;
    }

    public void setQty(Double qty) {
        this.qty = qty;
    }
}

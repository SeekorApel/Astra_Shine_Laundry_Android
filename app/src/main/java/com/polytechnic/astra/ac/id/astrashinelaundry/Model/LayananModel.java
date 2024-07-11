package com.polytechnic.astra.ac.id.astrashinelaundry.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LayananModel {
    @SerializedName("idLayanan")
    @Expose
    private Integer idLayanan;

    @SerializedName("namaLayanan")
    @Expose
    private String namaLayanan;

    @SerializedName("hargaLayanan")
    @Expose
    private String hargaLayanan;

    @SerializedName("satuanLayanan")
    @Expose
    private String satuanLayanan;

    @SerializedName("status")
    @Expose
    private String status;

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

    public String getHargaLayanan() {
        return hargaLayanan;
    }

    public void setHargaLayanan(String hargaLayanan) {
        this.hargaLayanan = hargaLayanan;
    }

    public String getSatuanLayanan() {
        return satuanLayanan;
    }

    public void setSatuanLayanan(String satuanLayanan) {
        this.satuanLayanan = satuanLayanan;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

package com.polytechnic.astra.ac.id.astrashinelaundry.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DurasiModel {
    @SerializedName("idDurasi")
    @Expose
    private Integer idDurasi;

    @SerializedName("namaDurasi")
    @Expose
    private String namaDurasi;

    @SerializedName("lamaDurasi")
    @Expose
    private Integer lamaDurasi;

    @SerializedName("hargaDurasi")
    @Expose
    private Integer hargaDurasi;

    @SerializedName("status")
    @Expose
    private String status;

    public DurasiModel() {
    }

    public DurasiModel(Integer idDurasi, String namaDurasi, Integer lamaDurasi, Integer hargaDurasi, String status) {
        this.idDurasi = idDurasi;
        this.namaDurasi = namaDurasi;
        this.lamaDurasi = lamaDurasi;
        this.hargaDurasi = hargaDurasi;
        this.status = status;
    }

    @Override
    public String toString() {
        return "DurasiModel{" +
                "idDurasi=" + idDurasi +
                ", namaDurasi='" + namaDurasi + '\'' +
                ", lamaDurasi=" + lamaDurasi +
                ", hargaDurasi=" + hargaDurasi +
                ", status='" + status + '\'' +
                '}';
    }

    public Integer getIdDurasi() {
        return idDurasi;
    }

    public void setIdDurasi(Integer idDurasi) {
        this.idDurasi = idDurasi;
    }

    public String getNamaDurasi() {
        return namaDurasi;
    }

    public void setNamaDurasi(String namaDurasi) {
        this.namaDurasi = namaDurasi;
    }

    public Integer getLamaDurasi() {
        return lamaDurasi;
    }

    public void setLamaDurasi(Integer lamaDurasi) {
        this.lamaDurasi = lamaDurasi;
    }

    public Integer getHargaDurasi() {
        return hargaDurasi;
    }

    public void setHargaDurasi(Integer hargaDurasi) {
        this.hargaDurasi = hargaDurasi;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

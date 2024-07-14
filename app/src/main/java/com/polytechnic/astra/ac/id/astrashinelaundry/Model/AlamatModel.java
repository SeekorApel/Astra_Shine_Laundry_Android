package com.polytechnic.astra.ac.id.astrashinelaundry.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class AlamatModel implements Serializable {

    @SerializedName("idAlamat")
    @Expose
    private Integer idAlamat;

    @SerializedName("idUser")
    @Expose
    private Integer idUser;

    @SerializedName("namaAlamat")
    @Expose
    private String namaAlamat;

    @SerializedName("alamat")
    @Expose
    private String alamat;

    @SerializedName("latitude")
    @Expose
    private Double latitude;

    @SerializedName("longtitude")
    @Expose
    private Double longtitude;

    @SerializedName("jarak")
    @Expose
    private Double jarak;

    @SerializedName("status")
    @Expose
    private String status;

    public AlamatModel() {
    }

    public AlamatModel(Integer idUser, String namaAlamat, String alamat, Double latitude, Double longtitude, Double jarak) {
        this.idUser = idUser;
        this.namaAlamat = namaAlamat;
        this.alamat = alamat;
        this.latitude = latitude;
        this.longtitude = longtitude;
        this.jarak = jarak;
    }

    public Integer getIdAlamat() {
        return idAlamat;
    }

    public void setIdAlamat(Integer idAlamat) {
        this.idAlamat = idAlamat;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public String getNamaAlamat() {
        return namaAlamat;
    }

    public void setNamaAlamat(String namaAlamat) {
        this.namaAlamat = namaAlamat;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public Double getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(Double longtitude) {
        this.longtitude = longtitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getJarak() {
        return jarak;
    }

    public void setJarak(Double jarak) {
        this.jarak = jarak;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "AlamatModel{" +
                "idAlamat=" + idAlamat +
                ", idUser=" + idUser +
                ", namaAlamat='" + namaAlamat + '\'' +
                ", alamat='" + alamat + '\'' +
                ", langtitude='" + longtitude + '\'' +
                ", latitude='" + latitude + '\'' +
                ", jarak=" + jarak +
                ", status='" + status + '\'' +
                '}';
    }
}

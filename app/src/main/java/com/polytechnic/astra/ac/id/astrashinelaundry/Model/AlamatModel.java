package com.polytechnic.astra.ac.id.astrashinelaundry.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AlamatModel {

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

    @SerializedName("langtitude")
    @Expose
    private String langtitude;

    @SerializedName("latitude")
    @Expose
    private String latitude;

    @SerializedName("jarak")
    @Expose
    private Integer jarak;

    @SerializedName("status")
    @Expose
    private String status;

    public AlamatModel() {
    }

    public AlamatModel(Integer idAlamat, Integer idUser, String namaAlamat, String alamat, String langtitude, String latitude, Integer jarak, String status) {
        this.idAlamat = idAlamat;
        this.idUser = idUser;
        this.namaAlamat = namaAlamat;
        this.alamat = alamat;
        this.langtitude = langtitude;
        this.latitude = latitude;
        this.jarak = jarak;
        this.status = status;
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

    public String getLangtitude() {
        return langtitude;
    }

    public void setLangtitude(String langtitude) {
        this.langtitude = langtitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public Integer getJarak() {
        return jarak;
    }

    public void setJarak(Integer jarak) {
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
                ", langtitude='" + langtitude + '\'' +
                ", latitude='" + latitude + '\'' +
                ", jarak=" + jarak +
                ", status='" + status + '\'' +
                '}';
    }
}

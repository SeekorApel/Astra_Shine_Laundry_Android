package com.polytechnic.astra.ac.id.astrashinelaundry.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

public class TransaksiModel implements Serializable {
    @SerializedName("idTransaksi")
    @Expose
    private Integer idTransaksi;

    @SerializedName("idUser")
    @Expose
    private Integer idUser;

    @SerializedName("namaUser")
    @Expose
    private String namaUser;

    @SerializedName("noTelp")
    @Expose
    private String noTelp;

    @SerializedName("idAlamat")
    @Expose
    private Integer idAlamat;

    @SerializedName("longitude")
    @Expose
    private String longitude;

    @SerializedName("latitude")
    @Expose
    private String latitude;

    @SerializedName("idDurasi")
    @Expose
    private Integer idDurasi;

    @SerializedName("namaDurasi")
    @Expose
    private String namaDurasi;

    @SerializedName("catatan")
    @Expose
    private String catatan;

    @SerializedName("tanggalPesanan")
    @Expose
    private Date tanggalPesanan;

    @SerializedName("tanggalPengiriman")
    @Expose
    private Date tanggalPengiriman;

    @SerializedName("statusPembayaran")
    @Expose
    private String statusPembayaran;

    @SerializedName("statusPesanan")
    @Expose
    private String statusPesanan;

    @SerializedName("ongkir")
    @Expose
    private Integer ongkir;

    @SerializedName("totalHarga")
    @Expose
    private Integer totalHarga;

    public TransaksiModel() {
    }

    public TransaksiModel(Integer idTransaksi, Integer idUser, String namaUser, String noTelp, Integer idAlamat, String longitude, String latitude, Integer idDurasi, String namaDurasi, String catatan, Date tanggalPesanan, Date tanggalPengiriman, String statusPembayaran, String statusPesanan, Integer ongkir, Integer totalHarga) {
        this.idTransaksi = idTransaksi;
        this.idUser = idUser;
        this.namaUser = namaUser;
        this.noTelp = noTelp;
        this.idAlamat = idAlamat;
        this.longitude = longitude;
        this.latitude = latitude;
        this.idDurasi = idDurasi;
        this.namaDurasi = namaDurasi;
        this.catatan = catatan;
        this.tanggalPesanan = tanggalPesanan;
        this.tanggalPengiriman = tanggalPengiriman;
        this.statusPembayaran = statusPembayaran;
        this.statusPesanan = statusPesanan;
        this.ongkir = ongkir;
        this.totalHarga = totalHarga;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getNoTelp() {
        return noTelp;
    }

    public void setNoTelp(String noTelp) {
        this.noTelp = noTelp;
    }

    public String getNamaUser() {
        return namaUser;
    }

    public void setNamaUser(String namaUser) {
        this.namaUser = namaUser;
    }

    public String getNamaDurasi() {
        return namaDurasi;
    }

    public void setNamaDurasi(String namaDurasi) {
        this.namaDurasi = namaDurasi;
    }

    public Integer getIdTransaksi() {
        return idTransaksi;
    }

    public void setIdTransaksi(Integer idTransaksi) {
        this.idTransaksi = idTransaksi;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public Integer getIdAlamat() {
        return idAlamat;
    }

    public void setIdAlamat(Integer idAlamat) {
        this.idAlamat = idAlamat;
    }

    public Integer getIdDurasi() {
        return idDurasi;
    }

    public void setIdDurasi(Integer idDurasi) {
        this.idDurasi = idDurasi;
    }

    public String getCatatan() {
        return catatan;
    }

    public void setCatatan(String catatan) {
        this.catatan = catatan;
    }

    public Date getTanggalPesanan() {
        return tanggalPesanan;
    }

    public void setTanggalPesanan(Date tanggalPesanan) {
        this.tanggalPesanan = tanggalPesanan;
    }

    public Date getTanggalPengiriman() {
        return tanggalPengiriman;
    }

    public void setTanggalPengiriman(Date tanggalPengiriman) {
        this.tanggalPengiriman = tanggalPengiriman;
    }

    public String getStatusPembayaran() {
        return statusPembayaran;
    }

    public void setStatusPembayaran(String statusPembayaran) {
        this.statusPembayaran = statusPembayaran;
    }

    public String getStatusPesanan() {
        return statusPesanan;
    }

    public void setStatusPesanan(String statusPesanan) {
        this.statusPesanan = statusPesanan;
    }

    public Integer getOngkir() {
        return ongkir;
    }

    public void setOngkir(Integer ongkir) {
        this.ongkir = ongkir;
    }

    public Integer getTotalHarga() {
        return totalHarga;
    }

    public void setTotalHarga(Integer totalHarga) {
        this.totalHarga = totalHarga;
    }

    @Override
    public String toString() {
        return "TransaksiModel{" +
                "idTransaksi=" + idTransaksi +
                ", idUser=" + idUser +
                ", idAlamat=" + idAlamat +
                ", idDurasi=" + idDurasi +
                ", catatan='" + catatan + '\'' +
                ", tanggalPesanan=" + tanggalPesanan +
                ", tanggalPengiriman=" + tanggalPengiriman +
                ", statusPembayaran='" + statusPembayaran + '\'' +
                ", statusPesanan='" + statusPesanan + '\'' +
                ", ongkir=" + ongkir +
                ", totalHarga=" + totalHarga +
                '}';
    }
}

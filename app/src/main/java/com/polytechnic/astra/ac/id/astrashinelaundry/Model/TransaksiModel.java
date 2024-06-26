package com.polytechnic.astra.ac.id.astrashinelaundry.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class TransaksiModel {
    @SerializedName("idTransaksi")
    @Expose
    private Integer idTransaksi;

    @SerializedName("idUser")
    @Expose
    private Integer idUser;
    @SerializedName("idAlamat")
    @Expose
    private Integer idAlamat;

    @SerializedName("idDurasi")
    @Expose
    private Integer idDurasi;

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

    public TransaksiModel(Integer idTransaksi, Integer idUser, Integer idAlamat, Integer idDurasi, String catatan, Date tanggalPesanan, Date tanggalPengiriman, String statusPembayaran, String statusPesanan, Integer ongkir, Integer totalHarga) {
        this.idTransaksi = idTransaksi;
        this.idUser = idUser;
        this.idAlamat = idAlamat;
        this.idDurasi = idDurasi;
        this.catatan = catatan;
        this.tanggalPesanan = tanggalPesanan;
        this.tanggalPengiriman = tanggalPengiriman;
        this.statusPembayaran = statusPembayaran;
        this.statusPesanan = statusPesanan;
        this.ongkir = ongkir;
        this.totalHarga = totalHarga;
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

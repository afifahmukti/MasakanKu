package com.example.customer.myapplication;

/**
 * Created by afi on 19/12/2017.
 */

public class DataResep {
    String bahan,gambar, penyajian, nama, tips;

    public DataResep(){

    }

    public DataResep(String bahan, String gambar, String penyajian, String nama,String tips) {
        this.bahan = bahan;
        this.gambar = gambar;
        this.penyajian = penyajian;
        this.nama = nama;
        this.tips = tips;
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getBahan() {
        return bahan;
    }

    public void setBahan(String bahan) {
        this.bahan = bahan;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }

    public String getPenyajian() {
        return penyajian;
    }

    public void setPenyajian(String penyajian) {
        this.penyajian = penyajian;
    }
}

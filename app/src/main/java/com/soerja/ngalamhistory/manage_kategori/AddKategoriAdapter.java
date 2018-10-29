package com.soerja.ngalamhistory.manage_kategori;

public class AddKategoriAdapter {

    private String jenis_kategori, gambar_kategori, deskripsi_kategori;

    public AddKategoriAdapter() {

    }

    public AddKategoriAdapter(String jenis_kategori, String gambar_kategori, String deskripsi_kategori) {
        this.jenis_kategori = jenis_kategori;
        this.gambar_kategori = gambar_kategori;
        this.deskripsi_kategori = deskripsi_kategori;
    }

    public String getJenis_kategori() {
        return jenis_kategori;
    }

    public void setJenis_kategori(String jenis_kategori) {
        this.jenis_kategori = jenis_kategori;
    }

    public String getGambar_kategori() {
        return gambar_kategori;
    }

    public void setGambar_kategori(String gambar_kategori) {
        this.gambar_kategori = gambar_kategori;
    }

    public String getDeskripsi_kategori() {
        return deskripsi_kategori;
    }

    public void setDeskripsi_kategori(String deskripsi_kategori) {
        this.deskripsi_kategori = deskripsi_kategori;
    }
}

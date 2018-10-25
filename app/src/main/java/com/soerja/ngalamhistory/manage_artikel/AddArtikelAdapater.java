package com.soerja.ngalamhistory.manage_artikel;

public class AddArtikelAdapater {

    private String judul_artikel, judul_gambar, kontent,uploader, kategori;

    public AddArtikelAdapater(){

    }

    public AddArtikelAdapater(String judul_artikel, String judul_gambar, String kontent, String uploader, String kategori) {
        this.judul_artikel = judul_artikel;
        this.judul_gambar = judul_gambar;
        this.kontent = kontent;
        this.uploader = uploader;
        this.kategori = kategori;
    }

    public String getJudul_artikel() {
        return judul_artikel;
    }

    public String getJudul_gambar() {
        return judul_gambar;
    }

    public String getKontent() {
        return kontent;
    }

    public String getUploader() {
        return uploader;
    }

    public String getKategori() {
        return kategori;
    }

    public void setJudul_artikel(String judul_artikel) {
        this.judul_artikel = judul_artikel;
    }

    public void setJudul_gambar(String judul_gambar) {
        this.judul_gambar = judul_gambar;
    }

    public void setKontent(String kontent) {
        this.kontent = kontent;
    }

    public void setUploader(String uploader) {
        this.uploader = uploader;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }
}

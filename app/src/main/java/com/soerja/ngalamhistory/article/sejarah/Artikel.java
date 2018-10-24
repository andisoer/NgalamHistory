package com.soerja.ngalamhistory.article.sejarah;


public class Artikel  {

   private String judul, judulImage;


   public Artikel(){

   }

    public Artikel(String judul, String judulImage) {
        this.judul = judul;
        this.judulImage = judulImage;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getJudulImage() {
        return judulImage;
    }

    public void setJudulImage(String judulImage) {
        this.judulImage = judulImage;
    }
}

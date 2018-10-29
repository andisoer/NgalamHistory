package com.soerja.ngalamhistory.article.sejarah.photodesc;

public class ListKomentAdapter {

    private String userName, komentar, waktu;

    public ListKomentAdapter() {

    }

    public ListKomentAdapter(String userName, String komentar, String waktu) {
        this.userName = userName;
        this.komentar = komentar;
        this.waktu = waktu;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getKomentar() {
        return komentar;
    }

    public void setKomentar(String komentar) {
        this.komentar = komentar;
    }

    public String getWaktu() {
        return waktu;
    }

    public void setWaktu(String waktu) {
        this.waktu = waktu;
    }
}

package com.kp.penggajian.ui.pegawai;

public class StoreDataPegawai {

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    String key, sNik, sNamaPegawai, sGolongan, sTglLahir, sTglPensiun, sJabatan, sDivisi, sArea;

    public StoreDataPegawai(){
    }

    public StoreDataPegawai(String sNik, String sNamaPegawai, String sGolongan, String sTglLahir, String sTglPensiun, String sJabatan, String sDivisi, String sArea) {
        this.sNik = sNik;
        this.sNamaPegawai = sNamaPegawai;
        this.sGolongan = sGolongan;
        this.sTglLahir = sTglLahir;
        this.sTglPensiun = sTglPensiun;
        this.sJabatan = sJabatan;
        this.sDivisi = sDivisi;
        this.sArea = sArea;
    }

    public String getsNik() {
        return sNik;
    }

    public void setsNik(String sNik) {
        this.sNik = sNik;
    }

    public String getsNamaPegawai() {
        return sNamaPegawai;
    }

    public void setsNamaPegawai(String sNamaPegawai) {
        this.sNamaPegawai = sNamaPegawai;
    }

    public String getsGolongan() {
        return sGolongan;
    }

    public void setsGolongan(String sGolongan) {
        this.sGolongan = sGolongan;
    }

    public String getsTglLahir() {
        return sTglLahir;
    }

    public void setsTglLahir(String sTglLahir) {
        this.sTglLahir = sTglLahir;
    }

    public String getsTglPensiun() {
        return sTglPensiun;
    }

    public void setsTglPensiun(String sTglPensiun) {
        this.sTglPensiun = sTglPensiun;
    }

    public String getsJabatan() {
        return sJabatan;
    }

    public void setsJabatan(String sJabatan) {
        this.sJabatan = sJabatan;
    }

    public String getsDivisi() {
        return sDivisi;
    }

    public void setsDivisi(String sDivisi) {
        this.sDivisi = sDivisi;
    }

    public String getsArea() {
        return sArea;
    }

    public void setsArea(String sArea) {
        this.sArea = sArea;
    }
}

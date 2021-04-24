package com.kp.penggajian.ui.pegawai;

import java.util.Comparator;

public class StoreDataPegawai {

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    String key;
    String sNik;
    String sNamaPegawai;
    String sGolongan;
    String sTglLahir;
    String sTglPensiun;
    String sDivisi;
    String sJabatan;
    String sNoHp;
    String sAlamat;
    String sGajiPokok;
    String sGajiPegawai;
    String sTunjanganJabatan;
    String sTunjanganKeluarga;
    String sTunjanganBeras;
    String sTunjanganKinerja;
    String sJumlahKotor;
    String sDapenma;
    String sJamsostek;
    String sPPH21;

    public String getsGajiPokok() {
        return sGajiPokok;
    }

    public void setsGajiPokok(String sGajiPokok) {
        this.sGajiPokok = sGajiPokok;
    }

    public String getsTunjanganJabatan() {
        return sTunjanganJabatan;
    }

    public void setsTunjanganJabatan(String sTunjanganJabatan) {
        this.sTunjanganJabatan = sTunjanganJabatan;
    }

    public String getsGajiPegawai() {
        return sGajiPegawai;
    }

    public void setsGajiPegawai(String sGajiPegawai) {
        this.sGajiPegawai = sGajiPegawai;
    }

    public String getsAlamat() {
        return sAlamat;
    }

    public void setsAlamat(String sAlamat) {
        this.sAlamat = sAlamat;
    }

    public StoreDataPegawai(){
    }

    public StoreDataPegawai(String sNik, String sNamaPegawai, String sGolongan, String sTglLahir, String sTglPensiun, String sDivisi, String sJabatan, String sNoHp, String sAlamat, String sGajiPokok, String sGajiPegawai, String sTunjanganJabatan, String sTunjanganKeluarga, String sTunjanganBeras, String sTunjanganKinerja, String sJumlahKotor, String sDapenma, String sJamsostek, String sPPH21) {
        this.sNik = sNik;
        this.sNamaPegawai = sNamaPegawai;
        this.sGolongan = sGolongan;
        this.sTglLahir = sTglLahir;
        this.sTglPensiun = sTglPensiun;
        this.sDivisi = sDivisi;
        this.sJabatan = sJabatan;
        this.sNoHp = sNoHp;
        this.sAlamat = sAlamat;
        this.sGajiPokok = sGajiPokok;
        this.sGajiPegawai = sGajiPegawai;
        this.sTunjanganJabatan = sTunjanganJabatan;
        this.sTunjanganKeluarga = sTunjanganKeluarga;
        this.sTunjanganBeras = sTunjanganBeras;
        this.sTunjanganKinerja = sTunjanganKinerja;
        this.sJumlahKotor = sJumlahKotor;
        this.sDapenma = sDapenma;
        this.sJamsostek = sJamsostek;
        this.sPPH21 = sPPH21;
    }

    public String getsTunjanganKeluarga() {
        return sTunjanganKeluarga;
    }

    public void setsTunjanganKeluarga(String sTunjanganKeluarga) {
        this.sTunjanganKeluarga = sTunjanganKeluarga;
    }

    public String getsTunjanganBeras() {
        return sTunjanganBeras;
    }

    public void setsTunjanganBeras(String sTunjanganBeras) {
        this.sTunjanganBeras = sTunjanganBeras;
    }

    public String getsTunjanganKinerja() {
        return sTunjanganKinerja;
    }

    public void setsTunjanganKinerja(String sTunjanganKinerja) {
        this.sTunjanganKinerja = sTunjanganKinerja;
    }

    public String getsJumlahKotor() {
        return sJumlahKotor;
    }

    public void setsJumlahKotor(String sJumlahKotor) {
        this.sJumlahKotor = sJumlahKotor;
    }

    public String getsDapenma() {
        return sDapenma;
    }

    public void setsDapenma(String sDapenma) {
        this.sDapenma = sDapenma;
    }

    public String getsJamsostek() {
        return sJamsostek;
    }

    public void setsJamsostek(String sJamsostek) {
        this.sJamsostek = sJamsostek;
    }

    public String getsPPH21() {
        return sPPH21;
    }

    public void setsPPH21(String sPPH21) {
        this.sPPH21 = sPPH21;
    }

    public static Comparator<StoreDataPegawai> storeDataPegawaiComparator = new Comparator<StoreDataPegawai>() {
        @Override
        public int compare(StoreDataPegawai storeDataPegawai, StoreDataPegawai t1) {
            return storeDataPegawai.getsNamaPegawai().compareTo(t1.getsNamaPegawai());
        }
    };

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

    public String getsNoHp() {
        return sNoHp;
    }

    public void setsNoHp(String sNoHp) {
        this.sNoHp = sNoHp;
    }
}

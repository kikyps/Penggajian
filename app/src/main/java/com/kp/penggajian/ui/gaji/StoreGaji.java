package com.kp.penggajian.ui.gaji;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class StoreGaji {

    String key;
    String sNamaPegawai;

    String sGajiPegawai;
    String sTunjanganJabatan;
    String sTunjanganKeluarga;
    String sTunjanganBeras;
    String sTunjanganKinerja;
    String sJumlahKotor;
    String sDapenma;
    String sJamSostek;
    String sPPH21;

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

    public StoreGaji(String sTunjanganKeluarga, String sTunjanganBeras, String sTunjanganKinerja, String sJumlahKotor, String sDapenma, String sJamSostek, String sPPH21, String sGajiPegawai) {
        this.sTunjanganKeluarga = sTunjanganKeluarga;
        this.sTunjanganBeras = sTunjanganBeras;
        this.sTunjanganKinerja = sTunjanganKinerja;
        this.sJumlahKotor = sJumlahKotor;
        this.sDapenma = sDapenma;
        this.sJamSostek = sJamSostek;
        this.sPPH21 = sPPH21;
        this.sGajiPegawai = sGajiPegawai;
    }

    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("sTunjanganKeluarga", sTunjanganKeluarga);
        result.put("sTunjanganBeras", sTunjanganBeras);
        result.put("sTunjanganKinerja", sTunjanganKinerja);
        result.put("sJumlahKotor", sJumlahKotor);
        result.put("sDapenma", sDapenma);
        result.put("sJamsostek", sJamSostek);
        result.put("sPPH21", sPPH21);
        result.put("sGajiPegawai", sGajiPegawai);
        return result;
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

    public String getsJamSostek() {
        return sJamSostek;
    }

    public void setsJamSostek(String sJamSostek) {
        this.sJamSostek = sJamSostek;
    }

    public String getsPPH21() {
        return sPPH21;
    }

    public void setsPPH21(String sPPH21) {
        this.sPPH21 = sPPH21;
    }

    public static Comparator<StoreGaji> storeGajiComparator = new Comparator<StoreGaji>() {
        @Override
        public int compare(StoreGaji storeDevisi, StoreGaji t1) {
            return storeDevisi.getsNamaPegawai().compareTo(t1.getsNamaPegawai());
        }
    };

    public StoreGaji() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getsNamaPegawai() {
        return sNamaPegawai;
    }

    public void setsNamaPegawai(String sNamaPegawai) {
        this.sNamaPegawai = sNamaPegawai;
    }
}

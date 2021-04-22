package com.kp.penggajian.ui.bagian;

import java.util.Comparator;

public class StoreDivisi {

    String key, sBagianDivisi, sGajiPokok;

    public StoreDivisi(String sBagianDivisi, String sGajiPokok) {
        this.sBagianDivisi = sBagianDivisi;
        this.sGajiPokok = sGajiPokok;
    }

    public static Comparator<StoreDivisi> storeDevisiComparator = new Comparator<StoreDivisi>() {
        @Override
        public int compare(StoreDivisi storeDevisi, StoreDivisi t1) {
            return storeDevisi.getsBagianDivisi().compareTo(t1.getsBagianDivisi());
        }
    };

    public StoreDivisi() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getsBagianDivisi() {
        return sBagianDivisi;
    }

    public void setsBagianDivisi(String sBagianDivisi) {
        this.sBagianDivisi = sBagianDivisi;
    }

    public String getsGajiPokok() {
        return sGajiPokok;
    }

    public void setsGajiPokok(String sGajiPokok) {
        this.sGajiPokok = sGajiPokok;
    }
}

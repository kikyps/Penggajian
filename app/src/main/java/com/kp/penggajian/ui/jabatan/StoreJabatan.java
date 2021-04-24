package com.kp.penggajian.ui.jabatan;

import java.util.Comparator;

public class StoreJabatan {

    String key;
    String sJabatan;
    String sTunjanganJabatan;


    public StoreJabatan(String sJabatan, String sTunjanganJabatan) {
        this.sJabatan = sJabatan;
        this.sTunjanganJabatan = sTunjanganJabatan;
    }

    public static Comparator<StoreJabatan> storejabatanComparator = new Comparator<StoreJabatan>() {
        @Override
        public int compare(StoreJabatan storeDevisi, StoreJabatan t1) {
            return storeDevisi.getsJabatan().compareTo(t1.getsJabatan());
        }
    };

    public StoreJabatan() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getsJabatan() {
        return sJabatan;
    }

    public void setsJabatan(String sJabatan) {
        this.sJabatan = sJabatan;
    }

    public String getsTunjanganJabatan() {
        return sTunjanganJabatan;
    }

    public void setsTunjanganJabatan(String sTunjanganJabatan) {
        this.sTunjanganJabatan = sTunjanganJabatan;
    }
}

package com.kp.penggajian.ui.pegawai;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.kp.penggajian.R;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {
    private List<StoreDataPegawai> mList;

    public RecyclerAdapter(List<StoreDataPegawai> mList) {
        this.mList = mList;
    }

    @NonNull
    @Override
    public RecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.layout_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.MyViewHolder holder, int position) {
        StoreDataPegawai storeDataPegawai = mList.get(position);
        holder.tv_nik.setText("Nik : " + storeDataPegawai.getsNik());
        holder.tv_nama.setText("Nama : " + storeDataPegawai.getsNamaPegawai());
        holder.tv_jabatan.setText("Jabatan : " + storeDataPegawai.getsJabatan());
        holder.tv_area.setText("Area : " + storeDataPegawai.getsArea());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_nik;
        TextView tv_nama;
        TextView tv_jabatan;
        TextView tv_area;

        CardView card_view;


        public MyViewHolder(@NonNull View iteView){
            super(iteView);

            tv_nik = iteView.findViewById(R.id.sNik);
            tv_nama = iteView.findViewById(R.id.sNamaPegawai);
            tv_jabatan = iteView.findViewById(R.id.sJabatan);
            tv_area = iteView.findViewById(R.id.sArea);
            card_view = iteView.findViewById(R.id.card_view);
        }
    }
}

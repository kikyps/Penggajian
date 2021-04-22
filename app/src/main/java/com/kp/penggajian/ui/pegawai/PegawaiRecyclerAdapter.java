package com.kp.penggajian.ui.pegawai;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.kp.penggajian.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PegawaiRecyclerAdapter extends RecyclerView.Adapter<PegawaiRecyclerAdapter.MyViewHolder> implements Filterable {
    private List<StoreDataPegawai> AllList;
    public List<StoreDataPegawai> FilteredList;
    Context context;

    public PegawaiRecyclerAdapter(List<StoreDataPegawai> mList, Context context) {
        this.context = context;
        this.AllList = mList;
        //this.FilteredList = mList;
        FilteredList = new ArrayList<>(mList);
    }

    @NonNull
    @Override
    public PegawaiRecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.item_pegawai, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PegawaiRecyclerAdapter.MyViewHolder holder, int position) {
        Collections.sort(AllList, StoreDataPegawai.storeDataPegawaiComparator);
        StoreDataPegawai storeDataPegawai = AllList.get(position);
        holder.tv_nik.setText("Nik : " + storeDataPegawai.getsNik());
        holder.tv_nama.setText("Nama : " + storeDataPegawai.getsNamaPegawai());
        holder.tv_divisi.setText("Divisi : " + storeDataPegawai.getsDivisi());
        holder.tv_jabatan.setText("Jabatan : " + storeDataPegawai.getsJabatan());
        holder.card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ShowDetailData.class);
                intent.putExtra("idData", AllList.get(position).getKey());
                context.startActivities(new Intent[]{intent});
            }
        });
    }

    @Override
    public int getItemCount() {
        return AllList.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            String searchText = charSequence.toString().toLowerCase();
            List<StoreDataPegawai> listFiltered = new ArrayList<>();

            if (searchText.isEmpty()) {
                listFiltered.addAll(FilteredList);
            } else {
                for (StoreDataPegawai data : FilteredList) {
                    if (data.getsNamaPegawai().toLowerCase().contains(searchText)) {
                        listFiltered.add(data);
                    }
                }
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = listFiltered;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            AllList.clear();
            AllList.addAll((List) filterResults.values);
            notifyDataSetChanged();
        }
    };

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_nik;
        TextView tv_nama;
        TextView tv_divisi;
        TextView tv_jabatan;
        CardView card_view;


        public MyViewHolder(@NonNull View iteView){
            super(iteView);

            tv_nik = iteView.findViewById(R.id.sNik);
            tv_nama = iteView.findViewById(R.id.sNamaPegawai);
            tv_divisi = iteView.findViewById(R.id.sJabatan);
            tv_jabatan = iteView.findViewById(R.id.sNoHp);
            card_view = iteView.findViewById(R.id.card_view);
        }
    }
}

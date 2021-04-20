package com.kp.penggajian.ui.pegawai;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.kp.penggajian.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> implements Filterable {
    private List<StoreDataPegawai> AllList;
    public List<StoreDataPegawai> FilteredList;

    public RecyclerAdapter(List<StoreDataPegawai> mList) {
        this.AllList = mList;
        //this.FilteredList = mList;
        FilteredList = new ArrayList<>(mList);
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
        Collections.sort(AllList, StoreDataPegawai.storeDataPegawaiComparator);
        StoreDataPegawai storeDataPegawai = AllList.get(position);
        holder.tv_nik.setText("Nik : " + storeDataPegawai.getsNik());
        holder.tv_nama.setText("Nama : " + storeDataPegawai.getsNamaPegawai());
        holder.tv_jabatan.setText("Jabatan : " + storeDataPegawai.getsJabatan());
        holder.tv_area.setText("Area : " + storeDataPegawai.getsArea());
        PegawaiFragment.progressDialog.dismiss();
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
        TextView tv_jabatan;
        TextView tv_area;
        CardView card_view;
        ConstraintLayout parentLayout;


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

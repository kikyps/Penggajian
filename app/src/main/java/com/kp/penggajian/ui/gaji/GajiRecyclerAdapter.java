package com.kp.penggajian.ui.gaji;

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

public class GajiRecyclerAdapter extends RecyclerView.Adapter<GajiRecyclerAdapter.MyViewHolder> implements Filterable {
    private List<StoreGaji> AllList;
    public List<StoreGaji> FilteredList;
    Context context;

    public GajiRecyclerAdapter(List<StoreGaji> mList, Context context) {
        this.context = context;
        this.AllList = mList;
        //this.FilteredList = mList;
        FilteredList = new ArrayList<>(mList);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.item_gaji, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Collections.sort(AllList, StoreGaji.storeGajiComparator);
        StoreGaji storeGaji = AllList.get(position);
        holder.tv_NamaPegawai.setText(storeGaji.getsNamaPegawai());
        holder.tv_GajiPegawai.setText(storeGaji.getsGajiPegawai());
        holder.card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UpdateGajiPegawai.class);
                intent.putExtra("idGaji", AllList.get(position).getKey());
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
            List<StoreGaji> listFiltered = new ArrayList<>();

            if (searchText.isEmpty()) {
                listFiltered.addAll(FilteredList);
            } else {
                for (StoreGaji data : FilteredList) {
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
        TextView tv_NamaPegawai;
        TextView tv_GajiPegawai;
        CardView card_view;


        public MyViewHolder(@NonNull View iteView){
            super(iteView);

            tv_NamaPegawai = iteView.findViewById(R.id.sNamaPegawai);
            tv_GajiPegawai = iteView.findViewById(R.id.sGajiPegawai);
            card_view = iteView.findViewById(R.id.card_view);
        }
    }
}

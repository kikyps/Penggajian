package com.kp.penggajian.ui.bagian;

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

public class BagianRecyclerAdapter extends RecyclerView.Adapter<BagianRecyclerAdapter.MyViewHolder> implements Filterable {
    private List<StoreDivisi> AllList;
    public List<StoreDivisi> FilteredList;
    Context context;

    public BagianRecyclerAdapter(List<StoreDivisi> mList, Context context) {
        this.context = context;
        this.AllList = mList;
        //this.FilteredList = mList;
        FilteredList = new ArrayList<>(mList);
    }

    @NonNull
    @Override
    public BagianRecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.item_bagian, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BagianRecyclerAdapter.MyViewHolder holder, int position) {
        Collections.sort(AllList, StoreDivisi.storeDevisiComparator);
        StoreDivisi storeDevisi = AllList.get(position);
        holder.tv_BagianDivisi.setText(storeDevisi.getsBagianDivisi());
        holder.tv_GajiPokok.setText(storeDevisi.getsGajiPokok());
        holder.card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UpdateBagianDevisi.class);
                intent.putExtra("idDivisi", AllList.get(position).getKey());
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
            List<StoreDivisi> listFiltered = new ArrayList<>();

            if (searchText.isEmpty()) {
                listFiltered.addAll(FilteredList);
            } else {
                for (StoreDivisi data : FilteredList) {
                    if (data.getsBagianDivisi().toLowerCase().contains(searchText)) {
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
        TextView tv_BagianDivisi;
        TextView tv_GajiPokok;
        CardView card_view;


        public MyViewHolder(@NonNull View iteView){
            super(iteView);

            tv_BagianDivisi = iteView.findViewById(R.id.sBagianDivisi);
            tv_GajiPokok = iteView.findViewById(R.id.sGajiPokok);
            card_view = iteView.findViewById(R.id.card_view);
        }
    }
}

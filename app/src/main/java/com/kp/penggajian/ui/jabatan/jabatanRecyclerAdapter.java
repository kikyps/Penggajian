package com.kp.penggajian.ui.jabatan;

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

public class jabatanRecyclerAdapter extends RecyclerView.Adapter<jabatanRecyclerAdapter.MyViewHolder> implements Filterable {
    private List<StoreJabatan> AllList;
    public List<StoreJabatan> FilteredList;
    Context context;

    public jabatanRecyclerAdapter(List<StoreJabatan> mList, Context context) {
        this.context = context;
        this.AllList = mList;
        //this.FilteredList = mList;
        FilteredList = new ArrayList<>(mList);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.item_jabatan, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Collections.sort(AllList, StoreJabatan.storejabatanComparator);
        StoreJabatan storeJabatan = AllList.get(position);
        holder.tv_Jabatan.setText(storeJabatan.getsJabatan());
        holder.tv_GajiJabatan.setText(storeJabatan.getsTunjanganJabatan());
        holder.card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UpdateJabatan.class);
                intent.putExtra("idJabatan", AllList.get(position).getKey());
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
            List<StoreJabatan> listFiltered = new ArrayList<>();

            if (searchText.isEmpty()) {
                listFiltered.addAll(FilteredList);
            } else {
                for (StoreJabatan data : FilteredList) {
                    if (data.getsJabatan().toLowerCase().contains(searchText)) {
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
        TextView tv_Jabatan;
        TextView tv_GajiJabatan;
        CardView card_view;


        public MyViewHolder(@NonNull View iteView){
            super(iteView);

            tv_Jabatan = iteView.findViewById(R.id.sJabatan);
            tv_GajiJabatan = iteView.findViewById(R.id.sGajiJabatan);
            card_view = iteView.findViewById(R.id.card_view);
        }
    }
}

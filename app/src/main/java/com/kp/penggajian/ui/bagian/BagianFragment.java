package com.kp.penggajian.ui.bagian;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kp.penggajian.MainActivity;
import com.kp.penggajian.R;

import java.util.ArrayList;
import java.util.Collections;

public class BagianFragment extends Fragment {

    BagianRecyclerAdapter recyclerAdapter;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    ArrayList<StoreDivisi> listDivisi = new ArrayList<>();
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;

    ProgressDialog progressDialog;

    public static SearchView searchView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_bagian, container, false);

        recyclerView = root.findViewById(R.id.rv_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayout = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayout);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        customProgresBar();
        showData();
        Collections.sort(listDivisi, StoreDivisi.storeDevisiComparator);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0 && MainActivity.fab.getVisibility() == View.VISIBLE){
                    MainActivity.fab.hide();
                } else if (dy < 0 && MainActivity.fab.getVisibility() != View.VISIBLE){
                    MainActivity.fab.show();
                }
            }
        });

        swipeRefreshLayout = root.findViewById(R.id.swiper);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                showData();

                swipeRefreshLayout.setRefreshing(false);
            }
        });

        setHasOptionsMenu(true);
        return root;
    }

    public void customProgresBar(){
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.show();
        progressDialog.setContentView(R.layout.cutom_progress_bar);
        progressDialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.divisi_menu, menu);
        MenuItem item = menu.findItem(R.id.filter_divisi);
        searchView = (SearchView) item.getActionView();
        searchView.setQueryHint(getResources().getString(R.string.search_divisi_bagian));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                recyclerAdapter.getFilter().filter(s);
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void showData(){
        databaseReference.child("DataBagianDivisi").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listDivisi = new ArrayList<>();
                if (snapshot.exists()) {
                    for (DataSnapshot item : snapshot.getChildren()) {
                        StoreDivisi divisi = item.getValue(StoreDivisi.class);
                        divisi.setKey(item.getKey());
                        listDivisi.add(divisi);
                        progressDialog.dismiss();
                    }
                }else {
                        progressDialog.dismiss();
                    }
                recyclerAdapter = new BagianRecyclerAdapter(listDivisi, getContext());
                recyclerView.setAdapter(recyclerAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
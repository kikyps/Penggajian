package com.kp.penggajian.ui.dashboard;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kp.penggajian.R;

public class DashbordFragment extends Fragment {

    TextView dataCount;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    ProgressDialog progressDialog;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        dataCount = root.findViewById(R.id.jumlah_pegawai);
        customProgresBar();
        getsCount();
        return root;
    }

    private void getsCount(){
        databaseReference.child("DataPegawai").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    int count = (int) snapshot.getChildrenCount();
                    dataCount.setText(Integer.toString(count));
                    progressDialog.dismiss();
                } else {
                    dataCount.setText("0");
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void customProgresBar(){
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.show();
        progressDialog.setContentView(R.layout.cutom_progress_bar);
        progressDialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
    }
}
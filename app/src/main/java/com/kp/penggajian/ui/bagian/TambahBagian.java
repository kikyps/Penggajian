package com.kp.penggajian.ui.bagian;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kp.penggajian.R;

public class TambahBagian extends AppCompatActivity {

    TextInputEditText bagianDivisi, gajiPokok;
    Button BtnTambahDivisi;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tambah_bagian);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        bagianDivisi = findViewById(R.id.bagian_divisi);
        gajiPokok = findViewById(R.id.gaji_pokok);
        BtnTambahDivisi = findViewById(R.id.tambah_divisi);

        bagianDivisi.addTextChangedListener(tambahDiv);
        gajiPokok.addTextChangedListener(tambahDiv);

        BtnTambahDivisi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sDivisi = bagianDivisi.getText().toString().trim();
                String sGajiPokok = gajiPokok.getText().toString().trim();

                StoreDivisi storeDivisi = new StoreDivisi(sDivisi, sGajiPokok);
                databaseReference.child("DataBagianDivisi").push().setValue(storeDivisi).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.add_pegawai_success), Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.failed_data_upload), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    private TextWatcher tambahDiv = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String divisi = bagianDivisi.getText().toString().trim();
            String gaji = gajiPokok.getText().toString().trim();

            BtnTambahDivisi.setEnabled(!divisi.isEmpty() && !gaji.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        BtnTambahDivisi.setEnabled(false);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
package com.kp.penggajian.ui.jabatan;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kp.penggajian.R;

public class TambahJabatan extends AppCompatActivity {

    TextInputEditText jabatan, tunjanganJabatan;
    Button BtnTambahJabatan;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tambah_jabatan);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        jabatan = findViewById(R.id.id_jabatan);
        tunjanganJabatan = findViewById(R.id.tunjangan_jabatan);
        BtnTambahJabatan = findViewById(R.id.tambah_jabatan);

        jabatan.addTextChangedListener(tambahJabatan);
        tunjanganJabatan.addTextChangedListener(tambahJabatan);

        BtnTambahJabatan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sJabatan = jabatan.getText().toString().trim();
                String sTunjanganJabatan = tunjanganJabatan.getText().toString().trim();

                StoreJabatan storeJabatan = new StoreJabatan(sJabatan, sTunjanganJabatan);
                databaseReference.child("DataJabatan").push().setValue(storeJabatan).addOnSuccessListener(new OnSuccessListener<Void>() {
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

    private TextWatcher tambahJabatan = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String divisi = jabatan.getText().toString().trim();
            String gaji = tunjanganJabatan.getText().toString().trim();

            BtnTambahJabatan.setEnabled(!divisi.isEmpty() && !gaji.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        BtnTambahJabatan.setEnabled(false);
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
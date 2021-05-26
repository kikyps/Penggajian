package com.kp.penggajian.ui.bagian;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kp.penggajian.R;
import com.kp.penggajian.ui.gaji.StoreGaji;

import java.util.Map;

public class UpdateBagianDevisi extends AppCompatActivity {

    TextInputEditText etBagianDivisi, etGajiPokok;
    TextInputLayout lDivisi, lGaji;
    DatabaseReference databaseReference;
    String iddivisi, BagianDivisi, GajiPokok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_bagian_devisi);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("DataBagianDivisi");

        etBagianDivisi = findViewById(R.id.bagian_divisi);
        etGajiPokok = findViewById(R.id.gaji_pokok);

        lDivisi = findViewById(R.id.lDivisi);
        lGaji = findViewById(R.id.lGaji);

        iddivisi = getIntent().getStringExtra("idDivisi");

        databaseReference.child(iddivisi).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    BagianDivisi = snapshot.child("sBagianDivisi").getValue().toString();
                    GajiPokok = snapshot.child("sGajiPokok").getValue().toString();

                    etBagianDivisi.setText(BagianDivisi);
                    etGajiPokok.setText(GajiPokok);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.update_divisi_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;
            case R.id.update_divisi:
                updateDivisi();
                return true;
            case R.id.delete_divisi:
                deleteDivisi();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



    private boolean validateDivisi() {
        String val = lDivisi.getEditText().getText().toString();

        if (val.isEmpty()){
            lDivisi.setError(getString(R.string.empty_field));
            return false;
        } else {
            lDivisi.setError(null);
            lDivisi.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateGaji() {
        String val = lGaji.getEditText().getText().toString();

        if (val.isEmpty()){
            lGaji.setError(getString(R.string.empty_field));
            return false;
        } else {
            lGaji.setError(null);
            lGaji.setErrorEnabled(false);
            return true;
        }
    }

    private void updateDivisi() {
        if (!validateDivisi() | !validateGaji()) {
            return;
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.update_divisi)
                    .setMessage("Apakah anda ingin mengedit divisi \n(" + BagianDivisi + ")")
                    .setPositiveButton(R.string.yes_confirm, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            String sDivisi = etBagianDivisi.getText().toString();
                            String sGajiPokok = etGajiPokok.getText().toString();

                            StoreDivisi storeDivisi = new StoreDivisi(sDivisi, sGajiPokok);

                            databaseReference.child(iddivisi).setValue(storeDivisi).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(getApplicationContext(), "Data berhasil di edit", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getApplicationContext(), "Terjadi kesalahan saat menedit data, periksa koneksi internet dan coba lagi!", Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    }).setNegativeButton(R.string.no_confirm, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.setCancelable(true);
            alertDialog.show();
        }
    }

    private void deleteDivisi(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.delete_pegawai)
                .setMessage("Apakah anda yakin ingin menghapus divisi \n(" + BagianDivisi + ")")
                .setPositiveButton(R.string.yes_confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        databaseReference.child(iddivisi).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(getApplicationContext(),"Data berhasil di hapus", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(),"Terjadi kesalahan saat menghapus data, periksa koneksi internet dan coba lagi!", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                }).setNegativeButton(R.string.no_confirm, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(true);
        alertDialog.show();
    }
}


package com.kp.penggajian.ui.jabatan;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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
import com.kp.penggajian.ui.bagian.StoreDivisi;
import com.kp.penggajian.ui.pegawai.TambahDataPegawai;

import java.util.ArrayList;

public class UpdateJabatan extends AppCompatActivity {

    TextInputEditText etJabatan, etTunjanganJabatan;
    TextInputLayout lJabatan, lTunjanganJabatan;
    DatabaseReference databaseReference;
    String idjabatan, Jabatan, TunjanganJabatan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_jabatan);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("DataJabatan");

        etJabatan = findViewById(R.id.id_jabatan);
        etTunjanganJabatan = findViewById(R.id.tunjangan_jabatan);

        lJabatan = findViewById(R.id.lJabatan);
        lTunjanganJabatan = findViewById(R.id.lGajiJabatan);

        idjabatan = getIntent().getStringExtra("idJabatan");

        databaseReference.child(idjabatan).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    Jabatan = snapshot.child("sJabatan").getValue().toString();
                    TunjanganJabatan = snapshot.child("sTunjanganJabatan").getValue().toString();

                    etJabatan.setText(Jabatan);
                    etTunjanganJabatan.setText(TunjanganJabatan);
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
        getMenuInflater().inflate(R.menu.update_jabatan_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;
            case R.id.update_jabatan:
                updateDivisi();
                return true;
            case R.id.delete_jabatan:
                deleteDivisi();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



    private boolean validateJabatan() {
        String val = lJabatan.getEditText().getText().toString();

        if (val.isEmpty()){
            lJabatan.setError(getString(R.string.empty_field));
            return false;
        } else {
            lJabatan.setError(null);
            lJabatan.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateGajiJabatan() {
        String val = lTunjanganJabatan.getEditText().getText().toString();

        if (val.isEmpty()){
            lTunjanganJabatan.setError(getString(R.string.empty_field));
            return false;
        } else {
            lTunjanganJabatan.setError(null);
            lTunjanganJabatan.setErrorEnabled(false);
            return true;
        }
    }

    private void updateDivisi() {
        if (!validateJabatan() | !validateGajiJabatan()) {
            return;
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.update_divisi)
                    .setMessage("Apakah anda ingin mengedit jabatan \n(" + Jabatan + ")")
                    .setPositiveButton(R.string.yes_confirm, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            String sJabatan = etJabatan.getText().toString();
                            String sGajiJabatan = etTunjanganJabatan.getText().toString();

                            StoreJabatan storeJabatan = new StoreJabatan(sJabatan, sGajiJabatan);
                            databaseReference.child(idjabatan).setValue(storeJabatan).addOnSuccessListener(new OnSuccessListener<Void>() {
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
                            int utjabatan = Integer.parseInt(sGajiJabatan);

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
                .setMessage("Apakah anda yakin ingin menghapus jabatan \n(" + Jabatan + ")")
                .setPositiveButton(R.string.yes_confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        databaseReference.child(idjabatan).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
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


package com.kp.penggajian.ui.gaji;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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

import org.jetbrains.annotations.NotNull;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Map;

public class UpdateGajiPegawai extends AppCompatActivity {

    TextInputEditText etNamaPegawai, etGajiPokok, etGajiPegawai, etTunjanganJabatan, etTunjanganKeluarga
            , etTunjanganBeras, etTunjanganKinerja, etJumlahKotor, etDapenma, etJamsostek, etpph;

    TextInputLayout lNamaPegawai, lGajiPokok, lGajiPegawai, lTunjanganJabatan;


    DatabaseReference databaseReference;
    String idgaji, NamaPegawai, GajiPegawai, TunjanganJabatan, TunjanganKeluarga, TunjanganBeras
            , TunjanganKinerja, JumlahKotor, Dapenma, JamSostek, PPH21;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_gaji);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("DataPegawai");

        etNamaPegawai = findViewById(R.id.nama_pegawai);
        etGajiPokok = findViewById(R.id.gaji_pokok);
        etTunjanganJabatan = findViewById(R.id.tunjangan_jabatan);
        etGajiPegawai = findViewById(R.id.gaji_pegawai);
        etTunjanganKeluarga = findViewById(R.id.tunjangan_keluarga);
        etTunjanganBeras = findViewById(R.id.tunjangan_beras);
        etTunjanganKinerja = findViewById(R.id.tunjangan_kinerja);
        etJumlahKotor = findViewById(R.id.jumlah_kotor);
        etDapenma = findViewById(R.id.dapenma);
        etJamsostek = findViewById(R.id.jamsostek);
        etpph = findViewById(R.id.pph21);


        lNamaPegawai = findViewById(R.id.lNamaPegawai);
        lGajiPokok = findViewById(R.id.lGajiPokok);
        lGajiPegawai = findViewById(R.id.lGajiPegawai);
        lTunjanganJabatan = findViewById(R.id.lTunjanganJabatan);

        idgaji = getIntent().getStringExtra("idGaji");

        databaseReference.child(idgaji).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    NamaPegawai = snapshot.child("sNamaPegawai").getValue().toString();
                    GajiPegawai = snapshot.child("sGajiPegawai").getValue().toString();
                    TunjanganKeluarga = snapshot.child("sTunjanganKeluarga").getValue().toString();
                    TunjanganBeras = snapshot.child("sTunjanganBeras").getValue().toString();
                    TunjanganKinerja = snapshot.child("sTunjanganKinerja").getValue().toString();
                    JumlahKotor = snapshot.child("sJumlahKotor").getValue().toString();
                    Dapenma = snapshot.child("sDapenma").getValue().toString();
                    JamSostek = snapshot.child("sJamsostek").getValue().toString();
                    PPH21 = snapshot.child("sPPH21").getValue().toString();

                    databaseReference.orderByKey().equalTo(idgaji).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                                String gajiPokok = dataSnapshot.child("sDivisi").getValue().toString();
                                DatabaseReference dataGaji = (DatabaseReference) FirebaseDatabase.getInstance().getReference().child("DataBagianDivisi").child(gajiPokok);
                                dataGaji.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                                        String gaji = snapshot.child("sGajiPokok").getValue().toString();
                                        etGajiPokok.setText(gaji);
                                    }

                                    @Override
                                    public void onCancelled(@NonNull @NotNull DatabaseError error) {
                                        throw error.toException();
                                    }
                                });

                                String tunjangan = dataSnapshot.child("sJabatan").getValue().toString();
                                DatabaseReference dataTunjangan = (DatabaseReference) FirebaseDatabase.getInstance().getReference().child("DataJabatan").child(tunjangan);
                                dataTunjangan.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                                        String jabatan = snapshot.child("sTunjanganJabatan").getValue().toString();
                                        etTunjanganJabatan.setText(jabatan);
                                    }

                                    @Override
                                    public void onCancelled(@NonNull @NotNull DatabaseError error) {
                                        throw error.toException();
                                    }
                                });
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull @NotNull DatabaseError error) {

                        }
                    });

                    etNamaPegawai.setText(NamaPegawai);
                    etGajiPegawai.setText(GajiPegawai);
                    etTunjanganKeluarga.setText(TunjanganKeluarga);
                    etTunjanganBeras.setText(TunjanganBeras);
                    etTunjanganKinerja.setText(TunjanganKinerja);
                    etJumlahKotor.setText(JumlahKotor);
                    etDapenma.setText(Dapenma);
                    etJamsostek.setText(JamSostek);
                    etpph.setText(PPH21);

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
        getMenuInflater().inflate(R.menu.update_gaji_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;
            case R.id.update_gaji:
                updateDivisi();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



    private boolean validateDivisi() {
        String val = lNamaPegawai.getEditText().getText().toString();

        if (val.isEmpty()){
            lNamaPegawai.setError(getString(R.string.empty_field));
            return false;
        } else {
            lNamaPegawai.setError(null);
            lNamaPegawai.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateGaji() {
        String val = lGajiPokok.getEditText().getText().toString();

        if (val.isEmpty()){
            lGajiPokok.setError(getString(R.string.empty_field));
            return false;
        } else {
            lGajiPokok.setError(null);
            lGajiPokok.setErrorEnabled(false);
            return true;
        }
    }

    private void updateDivisi() {
        String sGajiPokok = etGajiPokok.getText().toString().trim();
        String sTunjanganJabatan = etTunjanganJabatan.getText().toString().trim();
        String sTunjanganKeluarga = etTunjanganKeluarga.getText().toString().trim();
        String sTunjanganBeras = etTunjanganBeras.getText().toString().trim();
        String sTunjanganKinerja = etTunjanganKinerja.getText().toString().trim();
        String sJumlahKotor = etJumlahKotor.getText().toString().trim();
        String sDapenma = etDapenma.getText().toString().trim();
        String sJamsostek = etJamsostek.getText().toString().trim();
        String sPPH21 = etpph.getText().toString().trim();

        if (sTunjanganKeluarga.isEmpty() | sTunjanganBeras.isEmpty() | sTunjanganKinerja.isEmpty() | sJumlahKotor.isEmpty() | sDapenma.isEmpty() | sJamsostek.isEmpty() | sPPH21.isEmpty()) {
            etTunjanganKeluarga.setText("0");
            etTunjanganBeras.setText("0");
            etTunjanganKinerja.setText("0");
            etJumlahKotor.setText("0");
            etDapenma.setText("0");
            etJamsostek.setText("0");
            etpph.setText("0");
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.update_divisi)
                    .setMessage("Apakah anda ingin mengedit divisi \n(" + NamaPegawai + ")")
                    .setPositiveButton(R.string.yes_confirm, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            int gajiPokok = Integer.parseInt(sGajiPokok);
                            int tunJabatan = Integer.parseInt(sTunjanganJabatan);
                            int tunKeluarga = Integer.parseInt(sTunjanganKeluarga);
                            int tunBeras = Integer.parseInt(sTunjanganBeras);
                            int tunKinerja = Integer.parseInt(sTunjanganKinerja);
                            int jumKotor = Integer.parseInt(sJumlahKotor);
                            int Dapenma = Integer.parseInt(sDapenma);
                            int jamSostek = Integer.parseInt(sJamsostek);
                            int pph = Integer.parseInt(sPPH21);

                            double totalin = (gajiPokok + tunJabatan + tunKeluarga + tunBeras + tunKinerja - jumKotor - Dapenma - jamSostek - pph);

                            String sGajiPegawai = formatRupiah(totalin);

                                StoreGaji storeGaji = new StoreGaji(sTunjanganKeluarga, sTunjanganBeras, sTunjanganKinerja, sJumlahKotor, sDapenma, sJamsostek, sPPH21, sGajiPegawai);
                                Map<String, Object> postValues = storeGaji.toMap();

                                databaseReference.child(idgaji).updateChildren(postValues).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(getApplicationContext(), "Data berhasil di edit", Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(getApplicationContext(), "Terjadi kesalahan saat mengedit data, periksa koneksi internet dan coba lagi!", Toast.LENGTH_LONG).show();
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

    private String formatRupiah(Double number) {
        Locale localeid = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeid);
        return formatRupiah.format(number);
    }
}


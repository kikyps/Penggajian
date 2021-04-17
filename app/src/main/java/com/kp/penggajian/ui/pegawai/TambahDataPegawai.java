package com.kp.penggajian.ui.pegawai;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kp.penggajian.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TambahDataPegawai extends AppCompatActivity {

    final Calendar myCalendar = Calendar.getInstance();
    TextInputEditText etNik, etNamaPegawai, etGolongan, etTglLahir, etTglPensiun, etJabatan, etDevisi, etArea;
    Button btnTambah;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tambah_data_pegawai);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btnTambah = findViewById(R.id.tambah_data);
        etNik = findViewById(R.id.nik_pegawai);
        etNamaPegawai = findViewById(R.id.nama_pegawai);
        etGolongan = findViewById(R.id.golongan);
        etTglLahir = findViewById(R.id.tgl_lahir);
        etTglPensiun = findViewById(R.id.tgl_pensiun);
        etJabatan = findViewById(R.id.jabatan);
        etDevisi = findViewById(R.id.divisi);
        etArea = findViewById(R.id.area);

        etNik.addTextChangedListener(tambahData);
        etNamaPegawai.addTextChangedListener(tambahData);
        etGolongan.addTextChangedListener(tambahData);
        etTglLahir.addTextChangedListener(tambahData);
        etTglPensiun.addTextChangedListener(tambahData);
        etJabatan.addTextChangedListener(tambahData);
        etDevisi.addTextChangedListener(tambahData);
        etArea.addTextChangedListener(tambahData);

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateTglLahir();
            }
        };
        etTglLahir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myCalendar.setTime(Calendar.getInstance().getTime());
                new DatePickerDialog(TambahDataPegawai.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        etTglPensiun = findViewById(R.id.tgl_pensiun);
        DatePickerDialog.OnDateSetListener date2 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateTglPensiun();
            }
        };
        etTglPensiun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String et1 = etTglLahir.getText().toString();
                if (et1.isEmpty()){
                    Snackbar.make(view,"Isi tanggal lahir terlebih dahulu!", Snackbar.LENGTH_LONG).show();
                } else {
                    new DatePickerDialog(TambahDataPegawai.this, date2, myCalendar
                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                }
            }
        });

        btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sNik = etNik.getText().toString().trim();
                String sNamaPegawai = etNamaPegawai.getText().toString().trim();
                String sGolongan = etGolongan.getText().toString().trim();
                String sTglLahir = etTglLahir.getText().toString().trim();
                String sTglPensiun = etTglPensiun.getText().toString().trim();
                String sJabatan = etJabatan.getText().toString().trim();
                String sDivisi = etDevisi.getText().toString().trim();
                String sArea = etArea.getText().toString().trim();

                AlertDialog.Builder builder = new AlertDialog.Builder(TambahDataPegawai.this);
                builder.setTitle(getResources().getString(R.string.add_data))
                        .setMessage(getResources().getString(R.string.add_pegawai_message))
                        .setPositiveButton(getResources().getString(R.string.yes_confirm), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                StoreDataPegawai storeDataPegawai = new StoreDataPegawai(sNik, sNamaPegawai, sGolongan, sTglLahir, sTglPensiun, sJabatan, sDivisi, sArea);

                                databaseReference.child("DataPegawai").push().setValue(storeDataPegawai).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.add_pegawai_success), Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.failed_data_upload), Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        })
                        .setNegativeButton(getResources().getString(R.string.no_confirm), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                builder.setCancelable(true);
                builder.show();
            }
        });

        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    private TextWatcher tambahData = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String nik = etNik.getText().toString().trim();
            String nama = etNamaPegawai.getText().toString().trim();
            String golongan = etGolongan.getText().toString().trim();
            String tgllahir = etTglLahir.getText().toString().trim();
            String tglpensi = etTglPensiun.getText().toString().trim();
            String jabatan = etJabatan.getText().toString().trim();
            String divisi = etDevisi.getText().toString().trim();
            String area = etArea.getText().toString().trim();

            btnTambah.setEnabled(!nik.isEmpty() && !nama.isEmpty() && !golongan.isEmpty() && !tgllahir.isEmpty() && !tglpensi.isEmpty() && !jabatan.isEmpty() && !divisi.isEmpty() && !area.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    private void updateTglLahir() {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        Date today = myCalendar.getTime();
        myCalendar.add(Calendar.YEAR, 60);
        Date expiredDate = myCalendar.getTime();

        etTglLahir.setText(sdf.format(today));
        etTglPensiun.setText(sdf.format(expiredDate));
    }

    @Override
    protected void onStart() {
        super.onStart();
        btnTambah.setEnabled(false);
    }

    private void updateTglPensiun() {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        etTglPensiun.setText(sdf.format(myCalendar.getTime()));
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
package com.kp.penggajian.ui.pegawai;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kp.penggajian.R;
import com.kp.penggajian.ui.bagian.StoreDivisi;
import com.kp.penggajian.ui.jabatan.StoreJabatan;

import org.jetbrains.annotations.NotNull;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TambahDataPegawai extends AppCompatActivity {

    final Calendar myCalendar = Calendar.getInstance();
    TextInputEditText etNik, etNamaPegawai, etGolongan, etTglLahir, etTglPensiun, etNoHp, etAlamat;
    Button btnTambah;
    ImageButton btnContact;
    DatabaseReference dataJabatan, dataDivisi, databaseReference = FirebaseDatabase.getInstance().getReference();

    private Spinner divisiSpinner, jabatanSpinner;
    private ArrayList<String> divisiSpin = new ArrayList<>();
    private ArrayList<String> jabatanSpin = new ArrayList<>();
    private ArrayList<StoreDivisi> divisiData = new ArrayList<>();
    private ArrayList<StoreJabatan> jabatanData = new ArrayList<>();

    String selectDivisi, selectJabatan, keyDivisi, keyJabatan;

    int jabatanValue, divisiValue;

    private static final int CONTACT_PERMISSION_CODE = 1;
    private static final int CONTACT_PICK_CODE = 2;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tambah_data_pegawai);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btnTambah = findViewById(R.id.tambah_data);
        btnContact = findViewById(R.id.contact_picker);
        etNik = findViewById(R.id.nik_pegawai);
        etNamaPegawai = findViewById(R.id.nama_pegawai);
        etGolongan = findViewById(R.id.golongan);
        etTglLahir = findViewById(R.id.tgl_lahir);
        etTglPensiun = findViewById(R.id.tgl_pensiun);
        divisiSpinner = findViewById(R.id.divisi);
        jabatanSpinner = findViewById(R.id.jabatan);

        etNoHp = findViewById(R.id.nohp);
        etAlamat = findViewById(R.id.alamat);

        etNik.addTextChangedListener(tambahData);
        etNamaPegawai.addTextChangedListener(tambahData);
        etGolongan.addTextChangedListener(tambahData);
        etTglLahir.addTextChangedListener(tambahData);
        etTglPensiun.addTextChangedListener(tambahData);
        etNoHp.addTextChangedListener(tambahData);
        etAlamat.addTextChangedListener(tambahData);

        showSpinnerDivisi();
        showSpinnerJabatan();

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
                new DatePickerDialog(TambahDataPegawai.this, R.style.my_dialog_theme, date, myCalendar
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
                    new DatePickerDialog(TambahDataPegawai.this, R.style.my_dialog_theme, date2, myCalendar
                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                }
            }
        });

        btnContact.setOnClickListener(v -> {
            if (checkContactPermission()){
                pickContact();
            } else {
                //Permission Not Granted Request
                requestContactPermission();
            }
        });

        btnTambah.setOnClickListener(view -> {
            String sNik = etNik.getText().toString().trim();
            String sNamaPegawai = etNamaPegawai.getText().toString().trim();
            String sGolongan = etGolongan.getText().toString().trim();
            String sTglLahir = etTglLahir.getText().toString().trim();
            String sTglPensiun = etTglPensiun.getText().toString().trim();
            String sDivisi = keyDivisi.trim();
            String sJabatan = keyJabatan.trim();
            String sNoHp = etNoHp.getText().toString().trim();
            String sAlamat = etAlamat.getText().toString().trim();
            String sTunjanganKeluarga = "0";
            String sTunjanganBeras = "0";
            String sTunjanganKinerja = "0";
            String sJumlahKotor = "0";
            String sDapenma = "0";
            String sJamsostek = "0";
            String sPPH21 = "0";

            double sum = divisiValue + jabatanValue;

            String sGajiPegawai = formatRupiah(sum);

            AlertDialog.Builder builder = new AlertDialog.Builder(TambahDataPegawai.this);
            builder.setTitle(getResources().getString(R.string.add_data))
                    .setMessage(getResources().getString(R.string.add_pegawai_message))
                    .setPositiveButton(getResources().getString(R.string.yes_confirm), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            StoreDataPegawai storeDataPegawai = new StoreDataPegawai(sNik, sNamaPegawai, sGolongan, sTglLahir
                                    , sTglPensiun, sDivisi, sJabatan, sNoHp, sAlamat, sGajiPegawai, sTunjanganKeluarga
                                    , sTunjanganBeras, sTunjanganKinerja, sJumlahKotor, sDapenma, sJamsostek, sPPH21);

                            databaseReference.child("DataPegawai").push().setValue(storeDataPegawai).addOnSuccessListener(new OnSuccessListener<Void>() {
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
                    })
                    .setNegativeButton(getResources().getString(R.string.no_confirm), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
            builder.setCancelable(true);
            builder.show();
        });

        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    private void pickContact(){
        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(intent, CONTACT_PICK_CODE);
    }

    private boolean checkContactPermission(){
        //Check if contact permission was granted or not
        boolean result = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) == (PackageManager.PERMISSION_GRANTED);
        return result;
    }

    private void requestContactPermission(){
        //Permisson to request read contact
        String[] permission = {Manifest.permission.READ_CONTACTS};
        ActivityCompat.requestPermissions(this, permission, CONTACT_PERMISSION_CODE);
    }

    private String formatRupiah(Double number) {
        Locale localeid = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeid);
        return formatRupiah.format(number);
    };

    private void showSpinnerDivisi(){
        dataDivisi = FirebaseDatabase.getInstance().getReference().child("DataBagianDivisi");
        dataDivisi.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                divisiSpin.clear();
                for (DataSnapshot item : snapshot.getChildren()){
                    StoreDivisi storeDivisi = item.getValue(StoreDivisi.class);
                    divisiSpin.add(storeDivisi.getsBagianDivisi());
                    divisiData.add(storeDivisi);
                    //divisiSpin.add(item.child("sBagianDivisi").getValue(String.class));
                }
                ArrayAdapter<String > arrayAdapter = new ArrayAdapter<>(TambahDataPegawai.this, R.layout.spinner_data, divisiSpin);
                divisiSpinner.setAdapter(arrayAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        divisiSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedDivisi = divisiSpinner.getSelectedItem().toString();

                divisiValue = 0;
                selectDivisi = divisiData.get(i).getsGajiPokok();
                double pDivisi = Integer.parseInt(String.valueOf(selectDivisi));
                divisiValue += pDivisi;

                dataDivisi.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        for (DataSnapshot childSnapshot: snapshot.getChildren()) {
                            String nameDivisi = childSnapshot.child("sBagianDivisi").getValue().toString();

                            if (nameDivisi == selectedDivisi){
                                keyDivisi = childSnapshot.getKey();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void showSpinnerJabatan(){
        dataJabatan = FirebaseDatabase.getInstance().getReference().child("DataJabatan");
        dataJabatan.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                jabatanSpin.clear();
                for (DataSnapshot item : snapshot.getChildren()){
                    StoreJabatan storeJabatan = item.getValue(StoreJabatan.class);
                    jabatanSpin.add(storeJabatan.getsJabatan());
                    jabatanData.add(storeJabatan);
                    //jabatanSpin.add(item.child("sJabatan").getValue(String.class));
                }
                ArrayAdapter<String > arrayAdapter = new ArrayAdapter<>(TambahDataPegawai.this, R.layout.spinner_data, jabatanSpin);
                jabatanSpinner.setAdapter(arrayAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        jabatanSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedJabatan = jabatanSpinner.getSelectedItem().toString();

                jabatanValue = 0;
                selectJabatan = jabatanData.get(i).getsTunjanganJabatan();
                double pJabatan = Integer.parseInt(String.valueOf(selectJabatan));
                jabatanValue += pJabatan;

                dataJabatan.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        for (DataSnapshot childSnapshot: snapshot.getChildren()) {
                            String nameJabatan = childSnapshot.child("sJabatan").getValue().toString();

                            if (nameJabatan == selectedJabatan){
                                keyJabatan = childSnapshot.getKey();
                            }

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
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
            String divisi = divisiSpinner.getSelectedItem().toString();
            String jabatan = jabatanSpinner.getSelectedItem().toString();
            String nohp = etNoHp.getText().toString().trim();
            String alamat = etAlamat.getText().toString().trim();

            btnTambah.setEnabled(!nik.isEmpty() && !nama.isEmpty() && !golongan.isEmpty() && !tgllahir.isEmpty() && !tglpensi.isEmpty() && !jabatan.isEmpty() && !divisi.isEmpty() && !nohp.isEmpty() && !alamat.isEmpty());
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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //Handle Permission request result
        if (requestCode == CONTACT_PERMISSION_CODE){
            if (grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED);
            //Permission Granted can pick contact now
            pickContact();
        } else {
            //Permission denied
            Toast.makeText(TambahDataPegawai.this, R.string.permission_denied, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Handle intent result
        if (resultCode == RESULT_OK){
            //Calls when user click a contact from list
            if (requestCode == CONTACT_PICK_CODE){
                Cursor cursor1, cursor2;

                //Get data from intent
                Uri uri = data.getData();

                cursor1 = getContentResolver().query(uri, null, null, null, null);

                if (cursor1.moveToFirst()){
                    //get contact details
                    String contactId = cursor1.getString(cursor1.getColumnIndex(ContactsContract.Contacts._ID));
                    String contactName = cursor1.getString(cursor1.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                    String contactThumbnail = cursor1.getString(cursor1.getColumnIndex(ContactsContract.Contacts.PHOTO_THUMBNAIL_URI));
                    String idResult = cursor1.getString(cursor1.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
                    int idResultHold = Integer.parseInt(idResult);

                    if (idResultHold == 1){
                        cursor2 = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                null,
                                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId,
                                null,
                                null);

                        while (cursor2.moveToNext()){
                            // get phone number
                            String contactNumber = cursor2.getString(cursor2.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                            etNoHp.setText(contactNumber);
                        }
                        cursor2.close();
                    }
                    cursor1.close();
                }
            }
        } else {
            //Call when user click back button | dont pick contact
        }
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
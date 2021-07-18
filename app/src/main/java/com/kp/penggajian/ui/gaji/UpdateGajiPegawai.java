package com.kp.penggajian.ui.gaji;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

public class UpdateGajiPegawai extends AppCompatActivity {

    TextInputEditText etNamaPegawai, etGajiPokok, etDivisi, etGajiPegawai, etTunjanganJabatan, etTunjanganKeluarga
            , etTunjanganBeras, etTunjanganKinerja, etJumlahKotor, etDapenma, etJamsostek, etpph;

    TextInputLayout lNamaPegawai, lGajiPokok, lGajiPegawai, lTunjanganJabatan;

    DatabaseReference databaseReference;
    String idgaji, NamaPegawai, divisi, jabatan, GajiPegawai, gaji, TunjanganJabatan, TunjanganKeluarga, TunjanganBeras
            , TunjanganKinerja, JumlahKotor, Dapenma, JamSostek, PPH21;

    Bitmap bitmap, scaleBitmap;
    int pageWidth = 1200;
    DateFormat dateFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_gaji);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("DataPegawai");

        etNamaPegawai = findViewById(R.id.nama_pegawai);
        etDivisi = findViewById(R.id.divisi_pgawai);
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
                                String mydivisi = dataSnapshot.child("sDivisi").getValue().toString();
                                DatabaseReference dataDivisi = FirebaseDatabase.getInstance().getReference().child("DataBagianDivisi").child(mydivisi);
                                dataDivisi.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                                        divisi = snapshot.child("sBagianDivisi").getValue().toString();
                                        etDivisi.setText(divisi);
                                    }

                                    @Override
                                    public void onCancelled(@NonNull @NotNull DatabaseError error) {
                                        throw error.toException();
                                    }
                                });

                                String myjabatan = dataSnapshot.child("sJabatan").getValue().toString();
                                DatabaseReference dataJabatan = FirebaseDatabase.getInstance().getReference().child("DataJabatan").child(myjabatan);
                                dataJabatan.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                                        jabatan = snapshot.child("sJabatan").getValue().toString();
                                    }

                                    @Override
                                    public void onCancelled(@NonNull @NotNull DatabaseError error) {
                                        throw error.toException();
                                    }
                                });

                                String gajiPokok = dataSnapshot.child("sDivisi").getValue().toString();
                                DatabaseReference dataGaji = FirebaseDatabase.getInstance().getReference().child("DataBagianDivisi").child(gajiPokok);
                                dataGaji.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                                        gaji = snapshot.child("sGajiPokok").getValue().toString();
                                        etGajiPokok.setText(gaji);
                                    }

                                    @Override
                                    public void onCancelled(@NonNull @NotNull DatabaseError error) {
                                        throw error.toException();
                                    }
                                });

                                String tunjangan = dataSnapshot.child("sJabatan").getValue().toString();
                                DatabaseReference dataTunjangan = FirebaseDatabase.getInstance().getReference().child("DataJabatan").child(tunjangan);
                                dataTunjangan.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                                        TunjanganJabatan = snapshot.child("sTunjanganJabatan").getValue().toString();
                                        etTunjanganJabatan.setText(TunjanganJabatan);
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
            case R.id.print_gaji:
                PrintGaji();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void PrintGaji() {
        databaseReference.child(idgaji).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                String Nik = snapshot.child("sNik").getValue().toString();
                String NamaPeg = snapshot.child("sNamaPegawai").getValue().toString();
                String Gol = snapshot.child("sGolongan").getValue().toString();
                String TglLahir = snapshot.child("sTglLahir").getValue().toString();
                String TglPensiun = snapshot.child("sTglPensiun").getValue().toString();
                String NoHp = snapshot.child("sNoHp").getValue().toString();
                String Alamat = snapshot.child("sAlamat").getValue().toString();

                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.pdamtirta);
                scaleBitmap = Bitmap.createScaledBitmap(bitmap, 1200, 518, false);

                PdfDocument pdfDocument = new PdfDocument();
                Paint paint = new Paint();
                Paint titlePaint = new Paint();

                PdfDocument.PageInfo pageInfo
                        = new PdfDocument.PageInfo.Builder(1200, 2010, 1).create();
                PdfDocument.Page page = pdfDocument.startPage(pageInfo);

                Canvas canvas = page.getCanvas();
                canvas.drawBitmap(scaleBitmap, 0, 0, paint);

                titlePaint.setTextAlign(Paint.Align.CENTER);
                titlePaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
                titlePaint.setColor(Color.BLACK);
                titlePaint.setTextSize(70);
                canvas.drawText("Slip Gaji", pageWidth / 2, 590, titlePaint);

                paint.setTextAlign(Paint.Align.LEFT);
                paint.setColor(Color.BLACK);
                paint.setTextSize(40f);
                canvas.drawText("NIK : " + Nik, 20, 690, paint);
                canvas.drawText("Nama Pegawai : " + NamaPegawai, 20, 750, paint);
                canvas.drawText("No.Telp : " + NoHp, 20, 810, paint);
                canvas.drawText("Divisi : " + divisi, 20, 870, paint);
                canvas.drawText("Jabatan : " + jabatan, 20, 930, paint);

                paint.setTextAlign(Paint.Align.RIGHT);
                paint.setColor(Color.BLACK);
                paint.setTextSize(40f);
                dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                canvas.drawText("Tanggal : " + dateFormat.format(new Date().getTime()), pageWidth - 20, 690, paint);

                dateFormat = new SimpleDateFormat("HH:mm");
                canvas.drawText("Pukul : " + dateFormat.format(new Date().getTime()), pageWidth - 20, 750, paint);

                paint.setStyle(Paint.Style.STROKE);
                paint.setStrokeWidth(2);
                canvas.drawRect(20, 980, pageWidth - 20, 1060, paint);

                paint.setTextAlign(Paint.Align.LEFT);
                paint.setStyle(Paint.Style.FILL);
                canvas.drawText("No.", 40, 1030, paint);
                canvas.drawText("Menu Pesanan", 200, 1030, paint);
                canvas.drawText("Harga", 700, 1030, paint);
                canvas.drawText("Jumlah", 900, 1030, paint);
                canvas.drawText("Total", 1050, 1030, paint);

                canvas.drawLine(180, 990, 180, 1050, paint);
                canvas.drawLine(680, 990, 680, 1050, paint);
                canvas.drawLine(880, 990, 880, 1050, paint);
                canvas.drawLine(1030, 990, 1030, 1050, paint);

//                float subTotal = totalOne + totalTwo;
//                canvas.drawLine(400, 1200, pageWidth - 20, 1200, paint);
//                canvas.drawText("Sub Total", 700, 1250, paint);
//                canvas.drawText(":", 900, 1250, paint);
//                paint.setTextAlign(Paint.Align.RIGHT);
//                canvas.drawText(String.valueOf(subTotal), pageWidth - 40, 1250, paint);
//
//                paint.setTextAlign(Paint.Align.LEFT);
//                canvas.drawText("PPN (10%)", 700, 1300, paint);
//                canvas.drawText(":", 900, 1300, paint);
//                paint.setTextAlign(Paint.Align.RIGHT);
//                canvas.drawText(String.valueOf(subTotal * 10 / 100), pageWidth - 40, 1300, paint);
//                paint.setTextAlign(Paint.Align.LEFT);
//
//                paint.setColor(Color.rgb(247, 147, 30));
//                canvas.drawRect(680, 1350, pageWidth - 20, 1450, paint);
//
//                paint.setColor(Color.BLACK);
//                paint.setTextSize(50f);
//                paint.setTextAlign(Paint.Align.LEFT);
//                canvas.drawText("Total", 700, 1415, paint);
//                paint.setTextAlign(Paint.Align.RIGHT);
//                canvas.drawText(String.valueOf(subTotal + (subTotal * 10 / 100)), pageWidth - 40, 1415, paint);

                pdfDocument.finishPage(page);

                try {
                    String rootPath = Environment.getExternalStorageDirectory()
                            .getAbsolutePath() + "/PDAM/";

                    File root = new File(rootPath);
                    if (!root.exists()) {
                        root.mkdirs();
                    }
                    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                    Date now = new Date();
                    String dateNow = "_" + formatter.format(now);

                    File f = new File(rootPath + NamaPegawai + dateNow + ".pdf");
                    if (f.exists()) {
                        f.delete();
                    }
                    f.createNewFile();

                    pdfDocument.writeTo(new FileOutputStream(f));

                    pdfDocument.close();
                    Toast.makeText(UpdateGajiPegawai.this, "PDF sudah dibuat", Toast.LENGTH_LONG).show();

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(UpdateGajiPegawai.this, "Error!!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
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


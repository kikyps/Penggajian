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

                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.water_icon);
                scaleBitmap = Bitmap.createScaledBitmap(bitmap, 238, 218, false);

                PdfDocument pdfDocument = new PdfDocument();
                Paint paint = new Paint();
                Paint paint1 = new Paint();
                Paint linePDAM = new Paint();
                Paint titlePaint = new Paint();

                PdfDocument.PageInfo pageInfo
                        = new PdfDocument.PageInfo.Builder(1200, 2010, 1).create();
                PdfDocument.Page page = pdfDocument.startPage(pageInfo);

                Canvas canvas = page.getCanvas();
                canvas.drawBitmap(scaleBitmap, 20, 20, paint);

                titlePaint.setTextAlign(Paint.Align.LEFT);
                titlePaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
                titlePaint.setColor(Color.BLACK);
                titlePaint.setTextSize(47);
                canvas.drawText("PDAM TIRTA SIAK KOTA PEKANBARU", 320 , 100, titlePaint);

                paint.setTextAlign(Paint.Align.LEFT);
                paint.setTextSize(22f);
                canvas.drawText("JL. Jend.Sudirman, No.146, Tangkerang, Cinta Raja, Kec. Sail, Kota Pekanbaru, Riau 28121", 295, 160, paint);

                linePDAM.setStyle(Paint.Style.STROKE);
                linePDAM.setStrokeWidth(6);
                canvas.drawLine(295, 180, pageWidth - 40, 180, linePDAM);

                titlePaint.setTextAlign(Paint.Align.CENTER);
                titlePaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
                titlePaint.setColor(Color.BLACK);
                titlePaint.setTextSize(70);
                canvas.drawText("SLIP GAJI", pageWidth / 2, 320, titlePaint);

                paint.setTextAlign(Paint.Align.LEFT);
                paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
                paint.setColor(Color.BLACK);
                paint.setTextSize(40f);
                canvas.drawText("NIK : " + Nik, 20, 420, paint);
                canvas.drawText("Nama Pegawai : " + NamaPegawai, 20, 480, paint);
                canvas.drawText("No.Telp : " + NoHp, 20, 540, paint);
                canvas.drawText("Divisi : " + divisi, 20, 600, paint);
                canvas.drawText("Jabatan : " + jabatan, 20, 660, paint);

                paint.setTextAlign(Paint.Align.RIGHT);
                paint.setColor(Color.BLACK);
                paint.setTextSize(40f);
                dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                canvas.drawText("Tanggal : " + dateFormat.format(new Date().getTime()), pageWidth - 20, 420, paint);

                dateFormat = new SimpleDateFormat("HH:mm");
                canvas.drawText("Pukul : " + dateFormat.format(new Date().getTime()), pageWidth - 20, 480, paint);

                paint1.setTextAlign(Paint.Align.LEFT);
                paint1.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
                paint1.setColor(Color.BLACK);
                paint1.setTextSize(45);
                canvas.drawText("PENGHASILAN", 20, 750, paint1);
                canvas.drawText("POTONGAN", 20, 1360, paint1);

                paint1.setStyle(Paint.Style.STROKE);
                paint1.setStrokeWidth(3);
                canvas.drawLine(20, 760, 330, 760, paint1);
                canvas.drawLine(20, 1370, 260, 1370, paint1);

                paint.setStyle(Paint.Style.STROKE);
                paint.setStrokeWidth(2);
                canvas.drawRect(20, 780, pageWidth - 20, 1280, paint);

                paint.setTextAlign(Paint.Align.LEFT);
                paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
                paint.setStyle(Paint.Style.FILL);
                canvas.drawText("No.", 40, 830, paint);
                canvas.drawText("Keterangan", 160, 830, paint);
                canvas.drawText("Nominal", 780, 830, paint);

                canvas.drawLine(20, 860, pageWidth - 20, 860, paint);
                canvas.drawLine(130, 790, 130, 850, paint);
                canvas.drawLine(750, 790, 750, 850, paint);
                canvas.drawLine(750, 1210, 750, 1270, paint);
                canvas.drawLine(20, 1200, pageWidth - 20, 1200, paint);

                canvas.drawText("1.", 40, 920, paint);
                canvas.drawText("Gaji Pokok", 160, 920, paint);
                canvas.drawText(gaji, 780, 920, paint);

                canvas.drawText("2.", 40, 980, paint);
                canvas.drawText("Tunjangan Jabatan", 160, 980, paint);
                canvas.drawText(TunjanganJabatan, 780, 980, paint);

                canvas.drawText("3.", 40, 1040, paint);
                canvas.drawText("Tunjangan Keluarga", 160, 1040, paint);
                canvas.drawText(TunjanganKeluarga, 780, 1040, paint);

                canvas.drawText("4.", 40, 1100, paint);
                canvas.drawText("Tunjangan Beras", 160, 1100, paint);
                canvas.drawText(TunjanganBeras, 780, 1100, paint);

                canvas.drawText("5.", 40, 1160, paint);
                canvas.drawText("Tunjangan Kinerja", 160, 1160, paint);
                canvas.drawText(TunjanganKinerja, 780, 1160, paint);

                int gajiPe = Integer.parseInt(gaji);
                int tunJab = Integer.parseInt(TunjanganJabatan);
                int tunKel = Integer.parseInt(TunjanganKeluarga);
                int tunBer = Integer.parseInt(TunjanganBeras);
                int tunKin = Integer.parseInt(TunjanganKinerja);
                int totalPenghasilan = gajiPe + tunJab + tunKel + tunBer + tunKin;

                canvas.drawText("Total Penghasilan", 160, 1250, paint);
                canvas.drawText(String.valueOf(totalPenghasilan), 780, 1250, paint);

                paint.setStyle(Paint.Style.STROKE);
                paint.setStrokeWidth(2);
                canvas.drawRect(20, 1390, pageWidth - 20, 1820, paint);

                paint.setTextAlign(Paint.Align.LEFT);
                paint.setStyle(Paint.Style.FILL);
                canvas.drawText("No.", 40, 1440, paint);
                canvas.drawText("Keterangan", 160, 1440, paint);
                canvas.drawText("Nominal", 780, 1440, paint);

                canvas.drawLine(20, 1470, pageWidth - 20, 1470, paint);
                canvas.drawLine(130, 1400, 130, 1460, paint);
                canvas.drawLine(750, 1400, 750, 1460, paint);
                canvas.drawLine(750, 1750, 750, 1810, paint);
                canvas.drawLine(20, 1740, pageWidth - 20, 1740, paint);

                canvas.drawText("1.", 40, 1530, paint);
                canvas.drawText("Jumlah Kotor", 160, 1530, paint);
                canvas.drawText(JumlahKotor, 780, 1530, paint);

                canvas.drawText("2.", 40, 1590, paint);
                canvas.drawText("Dapenma", 160, 1590, paint);
                canvas.drawText(Dapenma, 780, 1590, paint);

                canvas.drawText("3.", 40, 1650, paint);
                canvas.drawText("Jamsostek", 160, 1650, paint);
                canvas.drawText(JamSostek, 780, 1650, paint);

                canvas.drawText("4.", 40, 1710, paint);
                canvas.drawText("PPH 21", 160, 1710, paint);
                canvas.drawText(JamSostek, 780, 1710, paint);

                int jumKotor = Integer.parseInt(JumlahKotor);
                int dapenma = Integer.parseInt(Dapenma);
                int jamSostek = Integer.parseInt(JamSostek);
                int pph = Integer.parseInt(PPH21);
                int totalPotongan = jumKotor + dapenma + jamSostek + pph;

                canvas.drawText("Total Potongan", 160, 1790, paint);
                canvas.drawText(String.valueOf(totalPotongan), 780, 1790, paint);

                double totalGaji = totalPenghasilan - totalPotongan;

                String totalGajiPegawai = formatRupiah(totalGaji);

                paint.setColor(Color.rgb(115, 204, 255));
                canvas.drawRect(110, 1860, pageWidth - 120, 1970, paint);

                paint.setTextAlign(Paint.Align.LEFT);
                paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
                paint.setColor(Color.BLACK);
                paint.setTextSize(60);
                canvas.drawText("Total Gaji", 160, 1940, paint);
                canvas.drawText("=", 500, 1940, paint);
                canvas.drawText(String.valueOf(totalGajiPegawai), 600, 1940, paint);

//                canvas.drawLine(550, 1460, pageWidth - 20, 1460, paint);
//                canvas.drawText("Sub Total", 700, 1250, paint);
//                canvas.drawText(":", 900, 1250, paint);
//                paint.setTextAlign(Paint.Align.RIGHT);
//                canvas.drawText(String.valueOf(subTotal), pageWidth - 40, 1250, paint);

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


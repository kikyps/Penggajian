package com.kp.penggajian.ui.pegawai;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kp.penggajian.R;

import org.w3c.dom.Text;

import java.util.zip.Inflater;

public class ShowDetailData extends AppCompatActivity {

    TextView labelName, mynik, myname, mygolongan, mytgllahir, mytglpensiun, myjabatan, mydivisi, myarea;
    DatabaseReference databaseReference;
    String iddata, Nik, NamaPegawai, Golongan, TglLahir, TglPensiun, Jabatan, Divisi, Area;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_detail_data);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("DataPegawai");

        labelName = findViewById(R.id.label_nama);
        mynik = findViewById(R.id.mynik);
        myname = findViewById(R.id.myname);
        mygolongan = findViewById(R.id.mygolongan);
        mytgllahir = findViewById(R.id.mytgllahir);
        mytglpensiun = findViewById(R.id.mypensiun);
        myjabatan = findViewById(R.id.myjabatan);
        mydivisi = findViewById(R.id.mydivisi);
        myarea = findViewById(R.id.myarea);

        iddata = getIntent().getStringExtra("idData");

        databaseReference.child(iddata).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    Nik = snapshot.child("sNik").getValue().toString();
                    NamaPegawai = snapshot.child("sNamaPegawai").getValue().toString();
                    Golongan = snapshot.child("sGolongan").getValue().toString();
                    TglLahir = snapshot.child("sTglLahir").getValue().toString();
                    TglPensiun = snapshot.child("sTglPensiun").getValue().toString();
                    Jabatan = snapshot.child("sJabatan").getValue().toString();
                    Divisi = snapshot.child("sDivisi").getValue().toString();
                    Area = snapshot.child("sArea").getValue().toString();

                    labelName.setText(NamaPegawai);
                    mynik.setText("Nik : \n" + Nik);
                    myname.setText("Nama Pegawai : \n" + NamaPegawai);
                    mygolongan.setText("Golongan : \n" + Golongan);
                    mytgllahir.setText("Tanggal Lahir : \n" + TglLahir);
                    mytglpensiun.setText("Tanggal Pensiun : \n" + TglPensiun);
                    myjabatan.setText("Jabatan : \n" + Jabatan);
                    mydivisi.setText("Divisi : \n" + Divisi);
                    myarea.setText("Area : \n" + Area);
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
        getMenuInflater().inflate(R.menu.showdetail_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;
            case R.id.delete_pegawai:
                deletePegawai();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void deletePegawai(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.delete_pegawai)
                .setMessage("Apakah anda yakin ingin menghapus data dengan nama \n(" + NamaPegawai + ")")
                .setPositiveButton(R.string.yes_confirm, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                databaseReference.child(iddata).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
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


package com.reynaldiwijaya.notes;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TambahNoteActivity extends AppCompatActivity {

    @BindView(R.id.edt_judul)
    EditText edtJudul;
    @BindView(R.id.edt_isi)
    EditText edtIsi;
    @BindView(R.id.btn_save)
    Button btnSave;

    // Buat variable untuk database
    private DBNoteHelper dbNoteHelper;
    // Buat Variable untuk menampung data dari user
    private String getJudul, getIsi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_note);
        ButterKnife.bind(this);

        //Untuk Mengganti Judul Toolbar
        setTitle("Add New Data");

        // Membuat Object untuk memanggil DB Helper
        dbNoteHelper = new DBNoteHelper(this);

    }

    @OnClick(R.id.btn_save)
    public void onViewClicked() {
        getData();
        saveData();
    }

    private void saveData() {
        // Membuat Object SQLiteDatabase dengan mode menulis
        SQLiteDatabase create = dbNoteHelper.getWritableDatabase();

        // Kita tampung data dari user ke dalam ContentValues agar meringkas
        ContentValues values = new ContentValues();
        values.put(DBNoteHelper.MyColumns.judul, getJudul);
        values.put(DBNoteHelper.MyColumns.isi, getIsi);

        // Kita Tambahkan data baru ke dalam table
        create.insert(DBNoteHelper.MyColumns.namaTabel, null, values);

        // Manampilkan pesan Toast
        Toast.makeText(this, "Success to Save Data", Toast.LENGTH_SHORT).show();

        //Menghapus isian Data dari user pada EditText
        clearData();

        // Menghancurkan Activity
        finish();
    }

    private void clearData() {
        edtIsi.setText("");
        edtJudul.setText("");
    }

    private void getData() {
        // Mengambil data inputan User
        getIsi = edtIsi.getText().toString();
        getJudul = edtJudul.getText().toString();
    }
}

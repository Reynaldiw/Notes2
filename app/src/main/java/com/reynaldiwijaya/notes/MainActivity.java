package com.reynaldiwijaya.notes;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.reynaldiwijaya.notes.adapter.NotesAdapter;
import com.reynaldiwijaya.notes.model.NotesModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.rvMain)
    RecyclerView rvMain;
    @BindView(R.id.fabMain)
    FloatingActionButton fabMain;

    // Variable untuk DB Helper
    private DBNoteHelper dbNoteHelper;
    // Penampung data
    private List<NotesModel> dataNotesList;
    // Membuat variable Adapter
    private NotesAdapter notesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //Membuat Object DB Helper
        dbNoteHelper = new DBNoteHelper(this);

        // Kita Inisiasikan Variable list
        dataNotesList = new ArrayList<>();

        //Kita ambil data dari SQLite
        getData();

        // Membuat Object Adapter
        notesAdapter = new NotesAdapter(this, dataNotesList);

        // Setting LayoutManager menjadi StaggeredGrid
        rvMain.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        // Memasang adapter ke RecyclerView
        rvMain.setAdapter(notesAdapter);
    }

    private void getData() {
        //Kita Membuat Object SQLite Database dengan mode read
        SQLiteDatabase readData = dbNoteHelper.getReadableDatabase();

        // Membuat Perintah Mengambil data
        String query = "SELECT * FROM " + DBNoteHelper.MyColumns.namaTabel
                + " ORDER BY " + DBNoteHelper.MyColumns.id_judul + " DESC";

        // Kita akan mengambil data menggunakan Cursor
        Cursor cursor = readData.rawQuery(query, null);

        // Arahkan Cursor ke awal
        cursor.moveToFirst();

        // Mengambilkan data secara berulang
        for (int count = 0 ; count < cursor.getCount() ; count++) {
            cursor.moveToPosition(count);
            dataNotesList.add(new NotesModel(cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2)));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Mengclear Data terlebih dahulu
        dataNotesList.clear();
        // Mengambil data
        getData();
        // Untuk memberi tahu adapter, jika ada data yang berubah...
        notesAdapter.notifyDataSetChanged();
    }

    @OnClick(R.id.fabMain)
    public void onViewClicked() {
        startActivity(new Intent(this, TambahNoteActivity.class));
    }
}

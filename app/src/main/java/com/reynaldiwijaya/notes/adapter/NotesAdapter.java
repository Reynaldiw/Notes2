package com.reynaldiwijaya.notes.adapter;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.reynaldiwijaya.notes.DBNoteHelper;
import com.reynaldiwijaya.notes.R;
import com.reynaldiwijaya.notes.model.NotesModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {

    private final Context context;
    private final List<NotesModel> dataNotesList;

    // Bundle Untuk Menampung data yang banyak menjadi 1
    private Bundle bundle;

    public NotesAdapter(Context context, List<NotesModel> dataNotesList) {
        this.context = context;
        this.dataNotesList = dataNotesList;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_notes, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        // Mengambil data dari DataNoteList
        final NotesModel dataNotes = dataNotesList.get(i);

        // Mengambil id_judul untuk kita gunakan pada saat delete atau update
        final String id = String.valueOf(dataNotes.getId_());

        viewHolder.tvJudul.setText(dataNotes.getJudul());
        viewHolder.tvIsi.setText(dataNotes.getIsi());

        // Membuat OnClick Pada OverFlow
        viewHolder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                // Membuat PopupMenu
                PopupMenu popupMenu = new PopupMenu(v.getContext(), v);
                //Inflate Desain XML untuk Popup Menu nya
                popupMenu.inflate(R.menu.popup_menu);
                // Untuk Membuat apa yang ada di tampilan  popup menu nya bisa di pencet / menjadi button
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.delete:
                                //Membuat Object DB Helper
                                DBNoteHelper dbNoteHelper = new DBNoteHelper(v.getContext());

                                //Membuat object SQLiteDatabase dengen Mode Write
                                SQLiteDatabase deleteData = dbNoteHelper.getWritableDatabase();

                                // Membuat Query untuk mencari id_judul
                                String selection = DBNoteHelper.MyColumns.id_judul + " LIKE ?";

                                // Mengambil data ID
                                String[] selectionArgs = {id};

                                // Perintah Delete
                                deleteData.delete(DBNoteHelper.MyColumns.namaTabel, selection, selectionArgs);

                                // Menghapus data di dalam list yang kita hapus
                                dataNotesList.remove(i);

                                // Memberitahu bahwa data telah terhapus
                                notifyItemRemoved(i);

                                // Meberitahu jarak yang baru
                                notifyItemRangeChanged(0, dataNotesList.size());

                                Toast.makeText(context, "Success to Delete", Toast.LENGTH_SHORT).show();

                                break;
                        }
                        return true;
                    }
                });
                popupMenu.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataNotesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_judul)
        TextView tvJudul;
        @BindView(R.id.tv_isi)
        TextView tvIsi;
        @BindView(R.id.overflow)
        ImageButton overflow;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}

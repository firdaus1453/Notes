package me.firdaus1453.notes.adapter;

import android.content.Context;
import android.content.Intent;
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

import java.util.List;

import me.firdaus1453.notes.Constant;
import me.firdaus1453.notes.DBNote;
import me.firdaus1453.notes.R;
import me.firdaus1453.notes.TambahNoteActivity;
import me.firdaus1453.notes.model.DataNotes;

/**
 * Created by firdaus1453 on 1/15/2019.
 */
public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {

    private final Context mainContext;
    private final List<DataNotes> dataNotesList;

    private Bundle bundle;

    public NotesAdapter(Context mainContext, List<DataNotes> dataNotesList) {
        this.mainContext = mainContext;
        this.dataNotesList = dataNotesList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(mainContext).inflate(R.layout.item_notes, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        // Mengambil datanote
        final DataNotes dataNotes = dataNotesList.get(i);
        final String id = String.valueOf(dataNotes.getId_());

        viewHolder.tvJudul.setText(dataNotes.getJudul());
        viewHolder.tvIsi.setText(dataNotes.getIsi());

        // Onclick untuk content
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle = new Bundle();
                bundle.putInt(Constant.TAG_ID, dataNotes.getId_());
                bundle.putString(Constant.TAG_JUDUL, dataNotes.getJudul());
                bundle.putString(Constant.TAG_ISI, dataNotes.getIsi());
                mainContext.startActivity(new Intent(mainContext, TambahNoteActivity.class).putExtras(bundle));
            }
        });

        viewHolder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                PopupMenu popupMenu = new PopupMenu(v.getContext(), v);
                popupMenu.inflate(R.menu.popup_menu);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.delete:
                                DBNote dbNote = new DBNote(v.getContext());
                                SQLiteDatabase deleteData = dbNote.getWritableDatabase();

                                // Membuat query untuk mencari id_judul
                                String selection = DBNote.MyColumns.id_judul + " LIKE ?";

                                // mengambil data ID
                                String[] selectionArgs = {String.valueOf(id)};
                                // Aksi Delete
                                deleteData.delete(DBNote.MyColumns.namaTabel, selection, selectionArgs);

                                // Menghapus data pada list recycleview
                                String position = String.valueOf(id.indexOf(id));
                                dataNotesList.remove(i);

                                notifyItemRemoved(i);
                                notifyItemRangeChanged(Integer.parseInt(position), dataNotesList.size());
                                Toast.makeText(mainContext, "Berhasil dihapus", Toast.LENGTH_SHORT).show();
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

        TextView tvJudul;
        TextView tvIsi;
        ImageButton overflow;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvIsi = itemView.findViewById(R.id.tvIsi);
            tvJudul = itemView.findViewById(R.id.tvJudul);
            overflow = itemView.findViewById(R.id.overflow);

        }
    }
}

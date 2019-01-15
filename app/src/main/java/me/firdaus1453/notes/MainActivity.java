package me.firdaus1453.notes;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import me.firdaus1453.notes.adapter.NotesAdapter;
import me.firdaus1453.notes.model.DataNotes;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView rvMain;
    FloatingActionButton fabMain;

    private DBNote dbNote;
    private List<DataNotes> dataNotesList;
    private NotesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        findViewById(R.id.fabMain).setOnClickListener(this);

        rvMain = findViewById(R.id.rvMain);

        // Membuat object database
        dbNote = new DBNote(this);

        // Membuat penampung untuk data note
        dataNotesList = new ArrayList<>();

        // Mengambil data note
        getData();

        // Mensetting recyclerview
        adapter = new NotesAdapter(this, dataNotesList);
        RecyclerView.LayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        rvMain.setLayoutManager(layoutManager);
        rvMain.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        dataNotesList.clear();
        getData();
        adapter.notifyDataSetChanged();
    }

    private void getData() {
        // Membuat object database
        SQLiteDatabase readData = dbNote.getReadableDatabase();
        String query = "SELECT * FROM " + DBNote.MyColumns.namaTabel + " ORDER BY " + DBNote.MyColumns.id_judul
                + " DESC";

        Cursor cursor = readData.rawQuery(query, null);

        cursor.moveToFirst();

        for (int count = 0; count < cursor.getCount(); count++) {
            cursor.moveToPosition(count);
            dataNotesList.add(new DataNotes(cursor.getInt(0), cursor.getString(1), cursor.getString(2)));
        }
    }

    @Override
    public void onClick(View v) {
        startActivity(new Intent(this, TambahNoteActivity.class));
    }
}

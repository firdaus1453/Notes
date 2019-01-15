package me.firdaus1453.notes;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.ButterKnife;

public class TambahNoteActivity extends AppCompatActivity {

    EditText edtJudul;
    EditText edtIsi;
    Button btnSave;

    // Membuat variable untuk database
    private DBNote dbNote;
    private String getJudul, getIsi;
    private int getId;

    private Bundle bundle;
    private Boolean isUpdate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_note);
        ButterKnife.bind(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnSave = findViewById(R.id.btnSave);
        edtIsi = findViewById(R.id.edtIsi);
        edtJudul = findViewById(R.id.edtJudul);

        dbNote = new DBNote(this);

        bundle = getIntent().getExtras();

        if (bundle == null) {
            addActivity();
            isUpdate = false;
        } else {
            isUpdate = true;
            updateActivity();
        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Mengambil data dari EditText
                getData();

                // Mensave atau update data
                if (isUpdate) {
                    updateData();
                } else {
                    saveData();
                }

                // Menghapus isian dari user pada EditText agar terlihat sudah tersimpan
                clearData();

                // Finish
                finish();
            }
        });

    }

    private void updateActivity() {
        setTitle("Update Data");

        getId = bundle.getInt(Constant.TAG_ID, 0);
        getJudul = bundle.getString(Constant.TAG_JUDUL);
        getIsi = bundle.getString(Constant.TAG_ISI);

        edtJudul.setText(getJudul);
        edtIsi.setText(getIsi);
    }


    private void addActivity() {
        setTitle("Add New Data");
    }

    private void updateData() {
        // Mendapatkan table dengan mode read
        SQLiteDatabase database = dbNote.getReadableDatabase();

        // Menampung data ke dalam content values
        // Membuat penampung data values
        ContentValues values = new ContentValues();
        values.put(DBNote.MyColumns.judul, getJudul);
        values.put(DBNote.MyColumns.isi, getIsi);

        // Membuat query pencarian data
        String selection = DBNote.MyColumns.id_judul + " LIKE ?";
        String[] selectionArgs = {String.valueOf(getId)};

        database.update(DBNote.MyColumns.namaTabel, values, selection, selectionArgs);
        Toast.makeText(this, "Berhasil diubah", Toast.LENGTH_SHORT).show();
    }

    private void clearData() {
        edtIsi.setText("");
        edtJudul.setText("");
    }

    private void saveData() {
        // Mendapatkan table dengan mode menulis
        SQLiteDatabase create = dbNote.getWritableDatabase();

        // Membuat penampung data values
        ContentValues values = new ContentValues();
        values.put(DBNote.MyColumns.judul, getJudul);
        values.put(DBNote.MyColumns.isi, getIsi);

        // Menambahkan baris baru ke dalam table
        create.insert(DBNote.MyColumns.namaTabel, null, values);
        Toast.makeText(this, "Tersimpan", Toast.LENGTH_SHORT).show();
    }

    private void getData() {
        getJudul = edtJudul.getText().toString();
        getIsi = edtIsi.getText().toString();
    }
}

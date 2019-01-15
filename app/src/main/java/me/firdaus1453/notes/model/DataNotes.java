package me.firdaus1453.notes.model;

/**
 * Created by firdaus1453 on 1/15/2019.
 */
public class DataNotes {
    private Integer id_;
    private String judul;
    private String isi;

    public DataNotes(Integer id_, String judul, String isi) {
        this.id_ = id_;
        this.judul = judul;
        this.isi = isi;
    }

    public Integer getId_() {
        return id_;
    }

    public String getJudul() {
        return judul;
    }

    public String getIsi() {
        return isi;
    }
}

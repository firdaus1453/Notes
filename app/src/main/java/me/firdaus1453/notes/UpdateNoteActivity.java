package me.firdaus1453.notes;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UpdateNoteActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.edtJudulUpdate)
    EditText edtJudulUpdate;
    @BindView(R.id.edtIsiUpdate)
    EditText edtIsiUpdate;
    @BindView(R.id.btnUpdate)
    Button btnUpdate;
    private DBNote dbNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_note);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btnUpdate)
    public void onViewClicked() {
    }
}

package com.android.manroid.demosqlite;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.manroid.adapter.NoteAdapter;
import com.android.manroid.model.NoteModel;
import com.android.manroid.source.NoteDataSource;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ListView lvNote;
    EditText edtNote;
    Button btnNote;

    NoteDataSource dataSource;
    ArrayList<NoteModel> arr = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvNote = (ListView)findViewById(R.id.lv_note);
        btnNote = (Button)findViewById(R.id.btn_add);
        edtNote = (EditText)findViewById(R.id.edt_note);

        btnNote.setOnClickListener(this);

        dataSource = new NoteDataSource(this);
        dataSource.open();
        
        updateListView();

    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (arr != null && arr.size() > 0) {
                lvNote.setVisibility(View.VISIBLE);

                NoteAdapter adapter = new NoteAdapter(MainActivity.this,(ArrayList<NoteModel>)arr);
                lvNote.setAdapter(adapter);
            }else {
                lvNote.setVisibility(View.INVISIBLE);
            }
        }
    };


    private void updateListView() {
        new Thread(){
            @Override
            public void run() {
                arr = dataSource.getAllNotes();
                handler.sendEmptyMessage(0);
            }
        }.start();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dataSource.close();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_add){
            String note = edtNote.getText().toString();
            if (note.trim().length()>0){
                dataSource.addNewNote(note);
                updateListView();
                edtNote.setText("");
            }else {
                Toast.makeText(this,"Please input your note",Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void deleteNote(NoteModel note){
        dataSource.deleteNote(note);
        updateListView();
        Toast.makeText(this,"Delete note success",Toast.LENGTH_LONG).show();

    }
}

package com.example.stickynotes;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.Calendar;

public class Edit extends AppCompatActivity {
    EditText noteTitle, noteDetails;
    Calendar c;
    String TodayDate;
    String time;
    Note note;
    NoteDatabase db;


  //  jhkjh
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        Intent i = getIntent();
        Long id = i.getLongExtra("ID", 0);
        db = new NoteDatabase(this);
        note = db.getnote(id);


        Toolbar toolbar = findViewById(R.id.toolBar);


        // for color

        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle(note.getTitle());
        noteTitle = findViewById(R.id.noteTitle);
        noteDetails = findViewById(R.id.noteDetails);
        noteTitle.setText(note.getTitle());
        noteDetails.setText(note.getContent());

        getSupportActionBar();
        noteTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 0) {
                    getSupportActionBar().setTitle(s);

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        // get current date  and time


        c = Calendar.getInstance();
        TodayDate = c.get(Calendar.YEAR) + "/" + (c.get(Calendar.MONTH) + 1) + "/" + c.get(Calendar.DAY_OF_MONTH);
        time = pad(c.get(Calendar.HOUR)) + ":" + pad(c.get(Calendar.MINUTE));

        Log.d("Calender", "Date & Time: " + TodayDate + "And" + time);
    }

    // use for 01:25 ; when we not use then 1:25 is result;

    private String pad(int i) {
        if (i < 10)
            return "0" + i;

        return String.valueOf(i);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.save_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.save) {
            if (noteTitle.getText().length() != 0) {
                note.setTitle(noteTitle.getText().toString());
                note.setContent(noteDetails.getText().toString());
                int id = db.editNote(note);
                if (id == note.getID()) {
                    Toast.makeText(this, "Updated SuccessFully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Updated Error", Toast.LENGTH_SHORT).show();
                }
                Intent i = new Intent(getApplicationContext(), Details.class);
                i.putExtra("ID", note.getID());
                startActivity(i);
            }


        }


        return super.onOptionsItemSelected(item);
    }


}



package com.example.lab4_matvii_sovhirenko_v1;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddNoteActivity extends AppCompatActivity {

    EditText nameNote, textNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        this.nameNote = findViewById(R.id.nameNote);
        this.textNote = findViewById(R.id.textNote);
    }

    public void onBtnSaveAndCloseClick(View view) {
        if (nameNote.getText().toString().trim().isEmpty() || textNote.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Note name and content cannot be empty", Toast.LENGTH_SHORT).show();
        } else {
            String noteName = this.nameNote.getText().toString();
            String noteContent = this.textNote.getText().toString();
            Note note = new Note(noteName, noteContent);
            NoteStorage.saveNote(this, note);

            finish();
        }
    }
}

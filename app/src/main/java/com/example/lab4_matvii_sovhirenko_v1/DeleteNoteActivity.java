package com.example.lab4_matvii_sovhirenko_v1;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class DeleteNoteActivity extends AppCompatActivity {
    ArrayAdapter<String> adapter;
    ListView vlNotesToDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_note);

        vlNotesToDelete = findViewById(R.id.vlNotesToDelete);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, getNoteList());
        vlNotesToDelete.setAdapter(adapter);

        vlNotesToDelete.setOnItemClickListener((parent, view, position, id) -> {
            ArrayList<Note> notes = NoteStorage.loadNotes(this);
            Note noteToDelete = notes.get(position);
            notes.remove(noteToDelete);
            NoteStorage.saveNotes(this, notes);
            adapter.remove(adapter.getItem(position));
            adapter.notifyDataSetChanged();

            finish();
        });
    }

    private ArrayList<String> getNoteList() {
        ArrayList<Note> notes = NoteStorage.loadNotes(this);
        ArrayList<String> noteList = new ArrayList<>();

        for (Note note : notes) {
            String subText = note.getNoteContent().length() <= 50 ? note.getNoteContent() : note.getNoteContent().substring(0, 50) + "...";
            String listItemText = note.getNoteName() + "\n" + subText;
            noteList.add(listItemText);
        }

        return noteList;
    }
}

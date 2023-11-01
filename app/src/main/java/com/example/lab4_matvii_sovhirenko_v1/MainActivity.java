package com.example.lab4_matvii_sovhirenko_v1;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> noteList = new ArrayList<>();
    ArrayAdapter<String> adapter;
    ListView listViewNotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.listViewNotes = findViewById(R.id.listViewNotes);
        this.adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, this.noteList);
        this.listViewNotes.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.note_actions_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.add_note) {
            Intent i = new Intent(this, AddNoteActivity.class);
            startActivity(i);
            return true;
        } else if (item.getItemId() == R.id.delete_note) {
            Intent i = new Intent(this, DeleteNoteActivity.class);
            startActivity(i);
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadNotesFromStorage();
    }

    private void loadNotesFromStorage() {
        ArrayList<Note> notes = NoteStorage.loadNotes(this);

        noteList.clear();

        for (Note note : notes) {
            String subText = note.getNoteContent().length() <= 50 ? note.getNoteContent() : note.getNoteContent().substring(0, 50) + "...";
            String listItemText = note.getNoteName() + "\n" + subText;
            noteList.add(listItemText);
        }

        adapter.notifyDataSetChanged();
    }
}

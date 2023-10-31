package com.example.lab4_matvii_sovhirenko_v1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> noteList = new ArrayList<>();
    ArrayAdapter<String> adapter;
    ListView listViewNotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.listViewNotes = findViewById(R.id.listViewNotes);
        this.adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, this.noteList);
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
        //I had to do this part of task with If else, because constants in the resource R class were declared in not proper way for switch case.
        //So, in other words, the constants are not final in a library project.
        //By trying to use switch case we will get an error of Constant expression required.
        if (item.getItemId() == R.id.add_note) {
            Intent i = new Intent(this, AddNoteActivity.class);
            startActivity(i);
            return true;
        }  else if (item.getItemId() == R.id.delete_note) {
            Intent i = new Intent(this, DeleteNoteActivity.class);
            startActivity(i);
            return true;
        }  else {
            return super.onOptionsItemSelected(item);
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences sharedPref = this.getSharedPreferences(Constants.NOTES_FILE, this.MODE_PRIVATE);
        String lastSavedNote = sharedPref.getString(Constants.NOTE_KEY, "NA");
        String lastSavedNoteDate = sharedPref.getString(Constants.NOTE_KEY_DATE, "2023-10-30");
        Set<String> savedSet = sharedPref.getStringSet(Constants.NOTES_ARRAY_KEY, null);

        if (savedSet != null) {
            this.noteList.clear();
            for (String noteString : savedSet) {
                try {
                    JSONObject noteObject = new JSONObject(noteString);
                    String noteName = noteObject.getString("name");
                    String noteContent = noteObject.getString("content");

                    // Get a short part of the note content (e.g., first 50 characters)
                    String subText = noteContent.length() <= 50 ? noteContent : noteContent.substring(0, 50) + "...";

                    // Combine note name and subtext
                    String listItemText = noteName + "\n" + subText;
                    this.noteList.add(listItemText);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            this.adapter.notifyDataSetChanged();
        }

        Snackbar.make(listViewNotes, String.format("%s: %s", getString(R.string.msg_last_saved_note), lastSavedNote), Snackbar.LENGTH_LONG).show();
        Toast.makeText(this, lastSavedNoteDate, Toast.LENGTH_LONG).show();
    }
}
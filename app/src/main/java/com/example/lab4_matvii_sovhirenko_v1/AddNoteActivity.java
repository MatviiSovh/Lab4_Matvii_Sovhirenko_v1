package com.example.lab4_matvii_sovhirenko_v1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

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
        }
        else {
            String noteName = this.nameNote.getText().toString();
            String noteContent = this.textNote.getText().toString();
            Date c = Calendar.getInstance().getTime();
            System.out.println("Current time => " + c);

            SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
            String formattedDate = df.format(c);
            SharedPreferences sharedPref = this.getSharedPreferences(Constants.NOTES_FILE, this.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();

            Set<String> savedSet = sharedPref.getStringSet(Constants.NOTES_ARRAY_KEY, null);
            Set<String> newSet = new HashSet<>();
            if (savedSet != null) {
                newSet.addAll(savedSet);
            }

            // Store both note name and content as a JSON object in the set
            JSONObject noteObject = new JSONObject();
            try {
                noteObject.put("name", noteName);
                noteObject.put("content", noteContent);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            newSet.add(noteObject.toString());

            editor.putString(Constants.NOTE_KEY, noteName);
            editor.putString(Constants.NOTE_KEY_DATE, formattedDate);
            editor.putStringSet(Constants.NOTES_ARRAY_KEY, newSet);
            editor.apply();

            finish();
        }
    }
}

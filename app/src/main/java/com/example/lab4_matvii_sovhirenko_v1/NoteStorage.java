package com.example.lab4_matvii_sovhirenko_v1;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class NoteStorage {
    private static final String FILENAME = "notes.json";

    public static void saveNote(Context context, Note note) {
        ArrayList<Note> notes = loadNotes(context);
        notes.add(note);
        saveNotes(context, notes);
    }

    public static ArrayList<Note> loadNotes(Context context) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(context.getFilesDir() + "/" + FILENAME));
            StringBuilder json = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                json.append(line);
            }
            Type type = new TypeToken<ArrayList<Note>>() {}.getType();
            return new Gson().fromJson(json.toString(), type);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    public static void saveNotes(Context context, ArrayList<Note> notes) {
        try {
            String json = new Gson().toJson(notes);
            FileOutputStream fos = context.openFileOutput(FILENAME, Context.MODE_PRIVATE);
            fos.write(json.getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

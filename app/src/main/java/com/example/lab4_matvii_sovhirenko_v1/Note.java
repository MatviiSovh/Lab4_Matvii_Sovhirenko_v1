package com.example.lab4_matvii_sovhirenko_v1;

public class Note {
    private String noteName;
    private String noteContent;

    public Note() {
    }

    public Note(String noteName, String noteContent) {
        this.noteName = noteName;
        this.noteContent = noteContent;
    }

    public String getNoteName() {
        return noteName;
    }

    public String getNoteContent() {
        return noteContent;
    }
}

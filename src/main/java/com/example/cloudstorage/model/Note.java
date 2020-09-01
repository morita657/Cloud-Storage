package com.example.cloudstorage.model;

public class Note {
    private String noteTitle;
    private String noteDescription;

    public Note(String noteTitle, String noteDescription) {
        this.noteTitle = noteTitle;
        this.noteDescription = noteDescription;
    }

    public String getNoteTitle(){
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle){
        this.noteTitle = noteTitle;
    }

    public String getNoteDescription(){
        return noteDescription;
    }

    public void setNoteDescription(String noteDescription){
        this.noteDescription = noteDescription;
    }
}

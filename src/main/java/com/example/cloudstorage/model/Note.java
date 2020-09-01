package com.example.cloudstorage.model;

public class Note {
    private Long noteId;
    private String noteTitle;
    private String noteDescription;

    public Note(String noteTitle, String noteDescription) {
        this.noteTitle = noteTitle;
        this.noteDescription = noteDescription;
    }

    public Long getNoteId(){
        return noteId;
    }

    public void setNoteId(Long noteId){
        this.noteId = noteId;
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

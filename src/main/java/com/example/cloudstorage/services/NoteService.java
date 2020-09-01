package com.example.cloudstorage.services;

import com.example.cloudstorage.mapper.NoteMapper;
import com.example.cloudstorage.model.Note;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {
    private NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper){
        this.noteMapper = noteMapper;
    }

    public List<Note> getNote(){
        return noteMapper.getAllNotes();
    }

    public boolean addNote(String noteTitle, String noteDescription, Long userid){
        try{
            Note newNote = new Note(noteTitle, noteDescription);
            noteMapper.create(newNote, userid);
            return true;
        }catch(Exception e){
            System.out.println("Note creation error: "+e);
            return false;
        }
    }

    public boolean editNote(String noteTitle, String noteDescription, Long userid, Long noteid){
        try{
            Note newNote = new Note(noteTitle, noteDescription);
            noteMapper.update(newNote, userid, noteid);
            return true;
        }catch(Exception e){
            System.out.println("Note creation error: "+e);
            return false;
        }
    }
}

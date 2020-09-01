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

    public boolean addNote(Note note, Long userid){
        try{
            noteMapper.create(note, userid);
            return true;
        }catch(Exception e){
            System.out.println("Note creation error: "+e);
            return false;
        }
    }

    public boolean editNote(Note note, Long userid){
        try{
            noteMapper.update(note, userid);
            return true;
        }catch(Exception e){
            System.out.println("Note edit error: "+e);
            return false;
        }
    }
    

    public boolean deleteNote(Long userid, Long noteid){
        try{
            noteMapper.delete(userid, noteid);
            return true;
        }
        catch(Exception e){
            System.out.println("Note delete error: "+e);
            return false;
        }
    }
}

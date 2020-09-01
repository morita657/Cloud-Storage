package com.example.cloudstorage.controller;

import com.example.cloudstorage.model.Note;
import com.example.cloudstorage.model.User;
import com.example.cloudstorage.services.NoteService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class NoteController {
    private NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }


    @PostMapping(value = "/add-note")
    public String addNote(Authentication authentication, String noteTitle, String noteDescription, Long noteId){
//        System.out.println("noteid on post: " + noteId);
        User user = (User) authentication.getPrincipal();
        if(noteId > 0){
//            PUT METHOD
            System.out.println("PUT METHOD: " + noteId);
            if(noteService.editNote(noteTitle, noteDescription, user.getUserId(), noteId)){
                return "redirect:/result?success";
            }
            else{
                return "redirect:/result?error";
            }
        }
        else{
//            POST METHOD
        System.out.println("this is the noteid for post: " + noteId);
        if(noteService.addNote(noteTitle, noteDescription, user.getUserId())){
            return "redirect:/result?success";
        }
        else{
            return "redirect:/result?error";
        }
        }
    }


    @GetMapping(value="/delete-note/{id}")
    public String deleteNote(Authentication authentication, @PathVariable("id") Long noteId){
        System.out.println("delete note id: " + noteId);
        User user = (User) authentication.getPrincipal();
        System.out.println("delete user id: " + user.getUserId());
        if(noteService.deleteNote(user.getUserId(), noteId)){
            return "redirect:/result?success";
        }
        else{
            return "redirect:/result?error";
        }
    }
}

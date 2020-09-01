package com.example.cloudstorage.controller;

import com.example.cloudstorage.model.Note;
import com.example.cloudstorage.model.User;
import com.example.cloudstorage.services.NoteService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class NoteController {
    private NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }


    @PostMapping(value = "/add-note")
    public String addNote(Authentication authentication, String noteTitle, String noteDescription){
        User user = (User) authentication.getPrincipal();
        if(noteService.addNote(noteTitle, noteDescription, user.getUserId())){
            return "redirect:/result?success";
        }
        else{
            return "redirect:/result?error";
        }
    }
}

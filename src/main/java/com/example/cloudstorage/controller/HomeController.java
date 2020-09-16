package com.example.cloudstorage.controller;

import com.example.cloudstorage.model.File;
import com.example.cloudstorage.model.User;
import com.example.cloudstorage.services.CredentialService;
import com.example.cloudstorage.services.FileService;
import com.example.cloudstorage.services.NoteService;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {
    private CredentialService credentialService;
    private FileService fileService;
    private NoteService noteService;

    @Autowired
    public HomeController(CredentialService credentialService, FileService fileService, NoteService noteService){
        this.credentialService = credentialService;
        this.fileService = fileService;
        this.noteService = noteService;
    }

    @GetMapping(value = {"/", "/dashboard"})
    public ModelAndView showIndex(Authentication authentication){
        User user = (User) authentication.getPrincipal();

        ModelAndView modelAndView = new ModelAndView("home");
        modelAndView.addObject("credentials", credentialService.getAllByUserId(user.getUserId()));
        modelAndView.addObject("files", fileService.getFile(user.getUserId()));
        modelAndView.addObject("notes", noteService.getNote());

        return modelAndView;
    }
}

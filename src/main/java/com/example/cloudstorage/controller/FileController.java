package com.example.cloudstorage.controller;

import com.example.cloudstorage.model.File;
import com.example.cloudstorage.model.User;
import com.example.cloudstorage.services.FileService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class FileController {
    private FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping(value = "/add-file")
    public String addFile(Authentication authentication, MultipartFile fileUpload){
        User user = (User) authentication.getPrincipal();
        if(fileService.addFile(fileUpload, user.getUserId())){
            return "redirect:/result?success";
        }
        else{
            return "redirect:/result?error";
        }
    }
}

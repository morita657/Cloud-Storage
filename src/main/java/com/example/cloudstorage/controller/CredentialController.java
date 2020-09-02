package com.example.cloudstorage.controller;

import com.example.cloudstorage.model.Credential;
import com.example.cloudstorage.model.User;
import com.example.cloudstorage.services.CredentialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CredentialController {
    private CredentialService credentialService;

    @Autowired
    public CredentialController(CredentialService credentialService) {
        this.credentialService = credentialService;
    }

    @PostMapping(value = "/add-credential")
    public String addCredential(Authentication authentication, Credential credential){
        User user = (User) authentication.getPrincipal();
        if(credentialService.createCredential(credential, user.getUserId())){
            return "redirect:/result?success";
        }
        return "redirect:/result?error";
    }
}

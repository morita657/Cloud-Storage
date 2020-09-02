package com.example.cloudstorage.controller;

import com.example.cloudstorage.model.Credential;
import com.example.cloudstorage.model.User;
import com.example.cloudstorage.services.CredentialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CredentialController {
    private CredentialService credentialService;

    @Autowired
    public CredentialController(CredentialService credentialService) {
        this.credentialService = credentialService;
    }

    @PostMapping(value = "/add-credential")
    public String addCredential(Authentication authentication, Credential credential, Long credentialId){
        User user = (User) authentication.getPrincipal();
        System.out.println("credential id: "+credentialId);
        if(credentialId>0){
//            PUT METHOD
            if(credentialService.updateCredential(credential, user.getUserId())){
                return "redirect:/result?success";
            }
            return "redirect:/result?error";
        }
        else{
            if(credentialService.createCredential(credential, user.getUserId())){
                return "redirect:/result?success";
            }
            return "redirect:/result?error";
        }
    }

//    delete-credential/{id}
    @GetMapping(value="/delete-credential/{id}")
    public String deleteNote(Authentication authentication, @PathVariable("id") Long credentialId){
        System.out.println("delete note id: " + credentialId);
        User user = (User) authentication.getPrincipal();
        System.out.println("delete user id: " + user.getUserId());
        if(credentialService.deleteCredential(credentialId, user.getUserId())){
            return "redirect:/result?success";
        }
        else{
            return "redirect:/result?error";
        }
    }
}

package com.example.cloudstorage.services;

import com.example.cloudstorage.mapper.CredentialsMapper;
import com.example.cloudstorage.model.Credential;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CredentialService {
    private CredentialsMapper credentialsMapper;
    private EncryptionService encryptionService;

    @Autowired
    public CredentialService(CredentialsMapper credentialsMapper, EncryptionService encryptionService){
        this.credentialsMapper = credentialsMapper;
        this.encryptionService = encryptionService;
    }

    public List<Credential> getAll(){
        List<Credential> credentials = credentialsMapper.findAll();

        if(credentials==null){
            return new ArrayList<>();
        }

        for(Credential credential:credentials){
            credential.setDecryptedPassword(decryptPassword(credential.getPassword(),credential.getKey()));
        }
        return credentials;
    }

    public List<Credential> getAllByUserId(Long userId){
        List<Credential> credentials = credentialsMapper.findByUserId(userId);

        if(credentials==null){
            return new ArrayList<>();
        }
        for(Credential credential:credentials){
            credential.setDecryptedPassword(decryptPassword(credential.getPassword(), credential.getKey()));
        }
        return credentials;
    }

    private String decryptPassword(String password, String key){
        return encryptionService.decryptValue(password, key);
    }
}

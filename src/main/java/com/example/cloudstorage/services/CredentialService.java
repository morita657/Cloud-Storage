package com.example.cloudstorage.services;

import com.example.cloudstorage.mapper.CredentialsMapper;
import com.example.cloudstorage.model.Credential;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialService {
    private CredentialsMapper credentialsMapper;
    private EncryptionService encryptionService;
    private HashService hashService;

    @Autowired
    public CredentialService(CredentialsMapper credentialsMapper, EncryptionService encryptionService, HashService hashService){
        this.credentialsMapper = credentialsMapper;
        this.encryptionService = encryptionService;
        this.hashService = hashService;
    }

    public List<Credential> getAll(){
        List<Credential> credentials = credentialsMapper.findAll();

        if(credentials==null){
            return new ArrayList<>();
        }

        for(Credential credential:credentials){
            credential.setDecryptedPassword(decryptPassword(credential.getPassword(),credential.getKey()));
            credential.setPassword(credential.getDecryptedPassword());
            credential.setKey(credential.getKey());
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
            credential.setPassword(credential.getDecryptedPassword());
            credential.setKey(credential.getKey());
        }
        return credentials;
    }

    private String decryptPassword(String password, String key){
        return encryptionService.decryptValue(password, key);
    }

    private Credential createEncryptedCredential(Credential credential){
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        credential.setKey(Base64.getEncoder().encodeToString(key));
        String hashedPassword =encryptionService.encryptValue(credential.getPassword(), credential.getKey());
        credential.setPassword(hashedPassword);
        return credential;
    }
    private Credential updateEncryptedCredential(Credential credential){
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        credential.setKey(Base64.getEncoder().encodeToString(key));
        String hashedPassword =encryptionService.encryptValue(credential.getPassword(), credential.getKey());
        credential.setPassword(hashedPassword);
        return credential;
    }



    public boolean createCredential(Credential credential, Long userid){
        try{
            Credential newCredential = createEncryptedCredential(credential);
            credentialsMapper.create(newCredential, userid);
            return true;
        }
        catch(Exception e){
            System.out.println("creation error :" + e);
            return false;
        }
    }

    public boolean updateCredential(Credential credential, Long userid){
        try{
            Credential updatedCredential = updateEncryptedCredential(credential);
            credentialsMapper.update(updatedCredential, userid);
            return true;
        }catch(Exception e){
            System.out.println("creation error :" + e);
            return false;
        }
    }
}

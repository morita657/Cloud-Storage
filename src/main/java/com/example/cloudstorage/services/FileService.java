package com.example.cloudstorage.services;

import com.example.cloudstorage.mapper.FileMapper;
import com.example.cloudstorage.model.File;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class FileService {
    private FileMapper fileMapper;

    public FileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    public List<File> getFile(){
        return fileMapper.getAllFiles();
    }

    public boolean addFile(MultipartFile file, Long userid){

        try{
            File newfile = new File(file.getOriginalFilename(), file.getContentType(), file.getSize(), file.getBytes());
            // Do not allow to upload empty file
            if(file.getOriginalFilename().length()<=0 || file.getContentType().length()<=0 || file.getSize()<=0){
                return false;
            }
            // Do not allow the duplicate file name
            for(File fileInfo : fileMapper.getAllFiles()){
                if(fileInfo.getFileName().equals(file.getOriginalFilename())){
                    return false;
                }
            }

            fileMapper.create(newfile, userid);
            return true;
        }catch(Exception e){
            System.out.println("Error: "+ e);
            return false;
        }
    }

    public boolean deleteFile(Long userid, Long fileid){
        try{
            fileMapper.delete(fileid, userid);
            return true;
        }catch(Exception e){
            System.out.println("Error: "+ e);
            return false;
        }
    }
}

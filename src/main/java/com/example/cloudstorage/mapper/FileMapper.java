package com.example.cloudstorage.mapper;

import com.example.cloudstorage.model.File;
import com.example.cloudstorage.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface FileMapper {
    @Select("select * from files")
    List<File> getAllFiles();

    @Select("insert into FILES (filename, contenttype, filesize, userid, filedata) values (#{file.fileName}, #{file.contentType}, #{file.fileSize}, #{userid}, #{file.fileData})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    Integer create(@Param("file") File file, Long userid);


}

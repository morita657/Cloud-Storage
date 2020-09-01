package com.example.cloudstorage.mapper;

import com.example.cloudstorage.model.Note;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface NoteMapper {
    @Select("select * from notes")
    List<Note> getAllNotes();

    @Select("insert into Notes (notetitle, notedescription, userid) values (#{note.noteTitle}, #{note.noteDescription}, #{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    Integer create(@Param("note") Note note , Long userid);
}

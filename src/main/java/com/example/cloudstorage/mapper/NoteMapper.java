package com.example.cloudstorage.mapper;

import com.example.cloudstorage.model.Note;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface NoteMapper {
    @Select("select * from notes")
    List<Note> getAllNotes();

    @Insert("insert into Notes (notetitle, notedescription, userid) values (#{note.noteTitle}, #{note.noteDescription}, #{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    Integer create(@Param("note") Note note , Long userid);


    @Update("update Notes set notetitle=#{note.noteTitle}, notedescription= #{note.noteDescription} where userid = #{userid} and noteid = #{noteid}")
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    Integer update(@Param("note") Note note , Long userid, Long noteid);



    @Delete("delte from Notes where userid = #{userid} and noteid = #{noteid}")
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    Integer delete(@Param("note") Long userid, Long noteid);
}

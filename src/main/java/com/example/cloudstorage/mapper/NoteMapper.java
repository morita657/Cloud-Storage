package com.example.cloudstorage.mapper;

import com.example.cloudstorage.model.Note;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface NoteMapper {
    @Select("select * from notes where userid= #{userid}")
    List<Note> getAllNotes(Long userid);

    @Insert("insert into Notes (notetitle, notedescription, userid) values (#{note.noteTitle}, #{note.noteDescription}, #{userid})")
    Integer create(@Param("note") Note note , Long userid);


    @Update("update Notes set notetitle=#{note.noteTitle}, notedescription= #{note.noteDescription} where userid = #{userid} and noteid = #{note.noteId}")
    Integer update(@Param("note") Note note, Long userid);



    @Delete("delete from Notes where userid = #{userid} and noteid = #{noteid}")
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    Integer delete(Long userid, Long noteid);
}

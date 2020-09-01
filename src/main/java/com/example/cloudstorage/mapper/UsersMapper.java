package com.example.cloudstorage.mapper;

import com.example.cloudstorage.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UsersMapper {

    @Select("select * from users")
    List<User> findAll();

    @Select("select * from users where users.userid = #{id}")
    User findById(Long id);

    @Insert("INSERT INTO users (username, password, firstname, lastname) VALUES (#{user.username}, #{user.password}, #{user.firstName}, #{user.lastName})")
    Integer create(@Param("user") User user);


    @Select("SELECT * FROM users WHERE users.username = #{username}")
    User findByUsername(String username);

}

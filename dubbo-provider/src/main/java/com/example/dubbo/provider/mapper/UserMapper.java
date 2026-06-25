package com.example.dubbo.provider.mapper;

import com.example.dubbo.api.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {

    @Insert("INSERT INTO user (username, password, mobile, mail) " +
            "VALUES (#{username}, #{password}, #{mobile}, #{mail})")
    @Options(useGeneratedKeys = true, keyProperty = "userid")
    int insert(User user);

    @Delete("DELETE FROM user WHERE userid = #{userid}")
    int deleteById(Long userid);

    @Update("UPDATE user SET username=#{username}, password=#{password}, " +
            "mobile=#{mobile}, mail=#{mail} WHERE userid=#{userid}")
    int update(User user);

    @Select("SELECT * FROM user WHERE userid = #{userid}")
    User findById(Long userid);

    @Select("SELECT * FROM user WHERE username = #{username}")
    User findByUsername(String username);

    @Select("SELECT * FROM user")
    List<User> findAll();
}
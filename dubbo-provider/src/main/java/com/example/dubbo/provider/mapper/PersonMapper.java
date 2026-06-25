package com.example.dubbo.provider.mapper;

import com.example.dubbo.api.Person;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PersonMapper {

    @Insert("INSERT INTO person (name, age, sex, address, mobile, email) " +
            "VALUES (#{name}, #{age}, #{sex}, #{address}, #{mobile}, #{email})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Person person);

    @Delete("DELETE FROM person WHERE id = #{id}")
    int deleteById(Long id);

    @Update("UPDATE person SET name=#{name}, age=#{age}, sex=#{sex}, " +
            "address=#{address}, mobile=#{mobile}, email=#{email} WHERE id=#{id}")
    int update(Person person);

    @Select("SELECT * FROM person WHERE id = #{id}")
    Person findById(Long id);

    @Select("SELECT * FROM person")
    List<Person> findAll();
}
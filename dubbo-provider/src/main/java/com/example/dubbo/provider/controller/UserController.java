package com.example.dubbo.provider.controller;

import com.example.dubbo.api.User;
import com.example.dubbo.provider.mapper.UserMapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserMapper mapper;

    public UserController(UserMapper mapper) {
        this.mapper = mapper;
    }

    @PostMapping
    public User create(@RequestBody User user) {
        mapper.insert(user);
        return user;
    }

    @DeleteMapping("/{userid}")
    public String delete(@PathVariable Long userid) {
        return mapper.deleteById(userid) > 0 ? "删除成功" : "记录不存在";
    }

    @PutMapping("/{userid}")
    public String update(@PathVariable Long userid, @RequestBody User user) {
        user.setUserid(userid);
        return mapper.update(user) > 0 ? "更新成功" : "记录不存在";
    }

    @GetMapping("/{userid}")
    public User getById(@PathVariable Long userid) {
        return mapper.findById(userid);
    }

    @GetMapping
    public List<User> list() {
        return mapper.findAll();
    }
}
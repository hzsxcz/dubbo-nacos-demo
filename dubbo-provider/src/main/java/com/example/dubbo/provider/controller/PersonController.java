package com.example.dubbo.provider.controller;

import com.example.dubbo.api.Person;
import com.example.dubbo.provider.mapper.PersonMapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {

    private final PersonMapper mapper;

    public PersonController(PersonMapper mapper) {
        this.mapper = mapper;
    }

    @PostMapping
    public Person create(@RequestBody Person person) {
        mapper.insert(person);
        return person;
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        return mapper.deleteById(id) > 0 ? "删除成功" : "记录不存在";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable Long id, @RequestBody Person person) {
        person.setId(id);
        return mapper.update(person) > 0 ? "更新成功" : "记录不存在";
    }

    @GetMapping("/{id}")
    public Person getById(@PathVariable Long id) {
        return mapper.findById(id);
    }

    @GetMapping
    public List<Person> list() {
        return mapper.findAll();
    }
}
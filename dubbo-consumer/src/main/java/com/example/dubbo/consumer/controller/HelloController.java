package com.example.dubbo.consumer.controller;

import com.example.dubbo.api.HelloService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Hello REST 控制器 — 通过 Dubbo 远程调用 Provider
 */
@RestController
public class HelloController {

    @DubboReference
    private HelloService helloService;

    @GetMapping("/hello")
    public String hello(@RequestParam(defaultValue = "World") String name) {
        return helloService.sayHello(name);
    }
}
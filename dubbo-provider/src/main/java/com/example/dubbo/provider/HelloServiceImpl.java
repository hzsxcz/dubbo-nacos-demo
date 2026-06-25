package com.example.dubbo.provider;

import com.example.dubbo.api.HelloService;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * HelloService 实现 — 注册为 Dubbo 服务，由 Nacos 暴露
 */
@DubboService
public class HelloServiceImpl implements HelloService {

    public String sayHello(String name) {
        return "Hello, " + name + "! (from Dubbo Provider)";
    }
}
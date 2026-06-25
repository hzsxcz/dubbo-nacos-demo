package com.example.dubbo.api;

/**
 * Hello 服务接口 — 由 provider 实现，consumer 远程调用
 */
public interface HelloService {
    String sayHello(String name);
}
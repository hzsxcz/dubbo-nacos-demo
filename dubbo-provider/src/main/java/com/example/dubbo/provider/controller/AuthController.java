package com.example.dubbo.provider.controller;

import com.example.dubbo.api.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, String> body) {
        String username = body.get("username");
        String password = body.get("password");
        Map<String, Object> result = new HashMap<>();

        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(new UsernamePasswordToken(username, password));
            result.put("success", true);
            result.put("message", "登录成功");
            result.put("sessionId", subject.getSession().getId());
        } catch (UnknownAccountException e) {
            result.put("success", false);
            result.put("message", "用户不存在");
        } catch (IncorrectCredentialsException e) {
            result.put("success", false);
            result.put("message", "密码错误");
        } catch (LockedAccountException e) {
            result.put("success", false);
            result.put("message", "账户已锁定");
        } catch (AuthenticationException e) {
            result.put("success", false);
            result.put("message", "认证失败: " + e.getMessage());
        }
        return result;
    }

    @PostMapping("/logout")
    public Map<String, Object> logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("message", "已退出");
        return result;
    }
}
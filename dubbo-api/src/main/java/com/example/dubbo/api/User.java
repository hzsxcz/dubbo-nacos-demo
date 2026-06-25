package com.example.dubbo.api;

import java.time.LocalDateTime;

/**
 * 用户实体
 */
public class User {

    private Long userid;
    private String username;
    private String password;
    private LocalDateTime createtime;
    private String mobile;
    private String mail;

    public User() {}

    public User(Long userid, String username, String password,
                LocalDateTime createtime, String mobile, String mail) {
        this.userid = userid;
        this.username = username;
        this.password = password;
        this.createtime = createtime;
        this.mobile = mobile;
        this.mail = mail;
    }

    public Long getUserid() { return userid; }
    public void setUserid(Long userid) { this.userid = userid; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public LocalDateTime getCreatetime() { return createtime; }
    public void setCreatetime(LocalDateTime createtime) { this.createtime = createtime; }

    public String getMobile() { return mobile; }
    public void setMobile(String mobile) { this.mobile = mobile; }

    public String getMail() { return mail; }
    public void setMail(String mail) { this.mail = mail; }
}
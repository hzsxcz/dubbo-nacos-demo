package com.example.dubbo.api;

/**
 * 人员信息实体
 */
public class Person {

    private Long id;
    private String name;
    private Integer age;
    private String sex;
    private String address;
    private String mobile;
    private String email;

    public Person() {}

    public Person(Long id, String name, Integer age, String sex,
                  String address, String mobile, String email) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.address = address;
        this.mobile = mobile;
        this.email = email;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Integer getAge() { return age; }
    public void setAge(Integer age) { this.age = age; }

    public String getSex() { return sex; }
    public void setSex(String sex) { this.sex = sex; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getMobile() { return mobile; }
    public void setMobile(String mobile) { this.mobile = mobile; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
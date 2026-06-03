package com.bean;

/**
 * 用户实体，与 A 同学登录模块共用。
 * 登录成功后 Session 中 loginUser 的 role 为 role 表 role_name：owner / admin
 */
public class User {
    private Integer id;
    private String username;
    private String password;
    private String name;
    private String phone;
    private String gender;
    private Integer roleId;
    /** 角色标识：owner / admin，对应 role.role_name */
    private String role;

    public User() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}

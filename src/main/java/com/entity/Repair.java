package com.entity;

import java.util.Date;

public class Repair {
    private Integer id;
    private String title;
    private String content;
    private Date createTime;
    private String status;
    private Integer userId;

    public Repair() {}

    public Repair(Integer id, String title, String content, Date createTime, String status, Integer userId) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createTime = createTime;
        this.status = status;
        this.userId = userId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}

package com.entity;

import java.util.Date;

public class Notice {
    private Integer id;
    private String title;
    private String content;
    private Date createTime;
    private String publisher;

    public Notice() {}

    public Notice(Integer id, String title, String content, Date createTime, String publisher) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createTime = createTime;
        this.publisher = publisher;
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

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }
}

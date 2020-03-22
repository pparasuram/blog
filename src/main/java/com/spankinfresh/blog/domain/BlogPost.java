package com.spankinfresh.blog.domain;

import java.util.Date;

public class BlogPost {
    private long id;
    private String category;
    private Date datePosted;
    private String title;
    private String content;
    public BlogPost() { }
    public BlogPost(long id, String category, Date datePosted,
                    String title, String content) {
        this.id = id;
        this.category = category;
        this.datePosted = datePosted;
        this.title = title;
        this.content = content;
    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Date getDatePosted() {
        return datePosted;
    }

    public void setDatePosted(Date datePosted) {
        this.datePosted = datePosted;
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
}

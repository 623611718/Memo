package com.example.lz.Bran;

/**
 * Created by Administrator on 2019/7/23.
 */

public class ContentBean {
    private String title;
    private String content;

    public ContentBean(String title, String content) {
        this.title = title;
        this.content = content;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public void setContent(String content){
        this.content = content;
    }

    public String getTitle(){
        return title;
    }
    public String getContent(){
        return content;
    }
}

package com.example.lz.Bean;

/**
 * Created by Administrator on 2019/7/25.
 */

public class ContactsEntity {
    /**
     * 数据库表ID
     */
    private int id;
    private String title;

    private String content;
    private String date;
    private String time;
    private int number; //唯一索引

    public ContactsEntity() {

    }

    public ContactsEntity(String title, String content, int number) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.number = number;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }


}

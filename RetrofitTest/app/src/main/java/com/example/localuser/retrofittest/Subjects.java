package com.example.localuser.retrofittest;

/**
 * Created by localuser on 2018/4/9.
 */

public class Subjects {
    private String title, year, id;

    public Subjects(String title, String year, String id) {
        this.title = title;
        this.year = year;
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getYear() {
        return year;
    }

    public String getId() {
        return id;
    }
}

package com.example.winsome.newsapp;

import android.graphics.Bitmap;

public class News {
    private Bitmap image;
    private String title;
    private String section;
    private String author;
    private String date;
    private String url;

    public News(Bitmap Image, String Title, String Section, String Author, String Date, String Url) {
        image = Image;
        title = Title;
        section = Section;
        author = Author;
        date = Date;
        url = Url;
    }

    public Bitmap getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public String getSection() {
        return section;
    }

    public String getAuthor(){
        return author;
    }

    public String getDate() {
        return date;
    }

    public String getUrl(){
        return url;
    }
}


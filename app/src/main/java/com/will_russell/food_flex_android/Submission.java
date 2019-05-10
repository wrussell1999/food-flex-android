package com.will_russell.food_flex_android;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

public class Submission {

    private String title;
    private String author;
    private Bitmap image;

    public static List<Submission> submissionList = new ArrayList<>();

    public Submission(String title, String author, Bitmap image) {
        this.title = title;
        this.author = author;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

}

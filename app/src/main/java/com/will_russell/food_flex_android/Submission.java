package com.will_russell.food_flex_android;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

public class Submission {

    private String title;
    private String author;
    private ArrayList<Bitmap> images;
    private boolean self;

    public static List<Submission> submissionList = new ArrayList<>();

    public Submission(String title, String author, ArrayList<Bitmap> images) {
        this.title = title;
        this.author = author;
        this.images = images;
        self = false;
    }

    public Submission(String title, String author, ArrayList<Bitmap> images, boolean self) {
        this.title = title;
        this.author = author;
        this.images = images;
        this.self = self;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public ArrayList<Bitmap> getImages() {
        return images;
    }

    public Bitmap getImage(int index) {
        return images.get(index);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setImages(ArrayList<Bitmap> images) {
        this.images = images;
    }

    public void setImage(int index, Bitmap image) {
        this.images.set(index, image);
    }
}

package com.shencangblue.jin.recyclerviewdemo;

import android.media.Image;

public class Fruit {

    private String name;

    public Fruit(String name, int imageId) {
        this.name = name;
        this.imageId = imageId;
    }

    private int imageId=0;

    public String getName() {
        return name;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public void setName(String name) {
        this.name = name;
    }
}

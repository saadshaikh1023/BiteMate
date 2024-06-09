package com.example.bitemate;

public class ItemModel {
    private int image;
    private String name, storename, place;

    public ItemModel(int image, String name, String storename, String place) {
        this.image = image;
        this.name = name;
        this.storename = storename;
        this.place = place;
    }

    public int getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public String getStorename() {
        return storename;
    }

    public String getPlace() {
        return place;
    }
}

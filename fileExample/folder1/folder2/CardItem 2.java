package com.example.progetto;

public class CardItem {

    private String imageResource;
    private String placeName;
    private String date;
    private String placeDescription;

    public CardItem(String image, String placeName, String date, String description){
        this.imageResource = image;
        this.placeDescription = description;
        this.date = date;
        this.placeName = placeName;
    }

    public String getImageResource() {
        return imageResource;
    }

    public String getPlaceName() {
        return placeName;
    }

    public String getDate() {
        return date;
    }

    public String getPlaceDescription() {
        return placeDescription;
    }
}

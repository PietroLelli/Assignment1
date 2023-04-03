package com.example.progettomobile;

public class CardItem {
    private String imageResource;
    private String productName;
    private String productPrice;
    private String productDescription;

    public CardItem(String imageResource, String productName, String productPrice, String productDescription){
        this.imageResource = imageResource;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productDescription = productDescription;

    }

    public String getImageResource() {
        return imageResource;
    }

    public String getProductName() {
        return productName;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public String getProductDescription() {
        return productDescription;
    }
}

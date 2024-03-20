package com.example.inventorysystem;

public class DealerItemDetail {
    private String itemName;
    private String brand;
    private int price;
    private int quantity;
    private String dealerName;

    public DealerItemDetail(String itemName, String brand, int price, int quantity, String dealerName) {
        this.itemName = itemName;
        this.brand = brand;
        this.price = price;
        this.quantity = quantity;
        this.dealerName = dealerName;
    }

    // Getters and setters for itemName, brand, price, quantity, and dealerName

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getDealerName() {
        return dealerName;
    }

    public void setDealerName(String dealerName) {
        this.dealerName = dealerName;
    }
}
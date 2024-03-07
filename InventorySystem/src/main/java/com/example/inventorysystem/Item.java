package com.example.inventorysystem;

import javafx.scene.image.Image;

import java.time.LocalDate;

public class Item {
    private String code;
    private String name;
    private String brand;
    private double price;
    private int quantity;
    private String category;
    private LocalDate purchasedDate;
    private Image imageView;

    public Item(String code, String name, String brand, double price, int quantity, String category, LocalDate purchasedDate, Image imageView) {
        this.code = code;
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
        this.purchasedDate = purchasedDate;
        this.imageView = imageView;
    }

    public String toFileString() {
        String sb = code + "," +
                name + "," +
                brand + "," +
                price + "," +
                quantity + "," +
                category + "," +
                purchasedDate + "," +
                imageView;
        return sb;
    }



    // Getters and setters for all the properties

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {this.brand = brand;}

    public double getPrice() {return price;}

    public void setPrice(double price) {this.price = price;}

    public int getQuantity() {return quantity;}

    public void setQuantity(int quantity) {this.quantity = quantity;}

    public String getCategory() {return category;}

    public void setCategory(String category) {this.category = category;}

    public LocalDate getPurchasedDate() {return purchasedDate;}

    public void setPurchasedDate(LocalDate purchasedDate) {this.purchasedDate = purchasedDate;}

    public Image getImage() {return imageView;}

    public void setImage(Image imageView) {this.imageView = imageView;}

    // Override toString() to provide a meaningful representation of the item
    @Override
    public String toString() {
        return "Item{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", brand='" + brand + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", category='" + category + '\'' +
                ", purchasedDate=" + purchasedDate +
                ", imageView=" + imageView +
                "}\n";
    }

    public Image getItemImage() {
        return imageView;
    }


    public Image getImagePath() {
        return imageView;
    }
}












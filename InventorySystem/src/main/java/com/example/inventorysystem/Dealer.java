package com.example.inventorysystem;


import javafx.beans.property.SimpleStringProperty;

public class Dealer {
    private SimpleStringProperty name;
    private SimpleStringProperty location;
    private SimpleStringProperty contact;

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name = new SimpleStringProperty(name);
    }

    public String getLocation() {
        return location.get();
    }

    public void setLocation(String location) {
        this.location = new SimpleStringProperty(location);
    }

    public String getContact() {
        return contact.get();
    }

    public void setContact(String Contact) {
        this.contact = new SimpleStringProperty(Contact);
    }

    public Dealer(String name, String location, String contact){
        this.name = new SimpleStringProperty(name);
        this.location = new SimpleStringProperty(location);
        this.contact = new SimpleStringProperty(contact);
    }
}

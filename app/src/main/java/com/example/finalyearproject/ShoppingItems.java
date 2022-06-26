package com.example.finalyearproject;

public class ShoppingItems {

    public String itemName, itemQuantity, itemShopper, itemAccom;

    public ShoppingItems() {
    }

    public ShoppingItems(String itemName, String itemQuantity, String itemShopper, String itemAccom) {
        this.itemName = itemName;
        this.itemQuantity = itemQuantity;
        this.itemShopper = itemShopper;
        this.itemAccom = itemAccom;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(String itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public String getItemShopper() {
        return itemShopper;
    }

    public void setItemShopper(String itemShopper) {
        this.itemShopper = itemShopper;
    }

    public String getItemAccom() {
        return itemAccom;
    }

    public void setItemAccom(String itemAccom) {
        this.itemAccom = itemAccom;
    }
}

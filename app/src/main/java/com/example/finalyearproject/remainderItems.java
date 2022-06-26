package com.example.finalyearproject;

public class remainderItems {

    public String utility, dueDate, cost, itemAccom;

    public remainderItems() {
    }

    public remainderItems(String utility, String dueDate, String cost, String itemAccom) {
        this.utility = utility;
        this.dueDate = dueDate;
        this.cost = cost;
        this.itemAccom = itemAccom;
    }

    public String getUtility() {
        return utility;
    }

    public void setUtility(String utility) {
        this.utility = utility;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getItemAccom() {
        return itemAccom;
    }

    public void setItemAccom(String itemAccom) {
        this.itemAccom = itemAccom;
    }
}

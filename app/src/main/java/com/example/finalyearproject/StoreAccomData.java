package com.example.finalyearproject;

import java.util.ArrayList;

public class StoreAccomData {

    public String accommodationId;
    public ArrayList shoppingList, complaintList, remainderList;

    public StoreAccomData() {
    }

    public StoreAccomData(String accommodationId, ArrayList shoppingList, ArrayList complaintList, ArrayList remainderList) {
        this.accommodationId = accommodationId;
        this.shoppingList = shoppingList;
        this.complaintList = complaintList;
        this.remainderList = remainderList;
    }
}

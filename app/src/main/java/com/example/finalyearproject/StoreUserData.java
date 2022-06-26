package com.example.finalyearproject;

public class StoreUserData {
    public String firstName, lastName, phoneNumber, address, city, country, postCode, accomId, photo;

    public StoreUserData(){};
    public StoreUserData(String userFirstname, String userLastName, String userPhone, String userAddress, String userCity, String userCountry, String userPostCode, String userAccom, String userPhoto) {

        this.firstName = userFirstname;
        this.lastName = userLastName;
        this.phoneNumber = userPhone;
        this.address = userAddress;
        this.city = userCity;
        this.country = userCountry;
        this.postCode = userPostCode;
        this.accomId = userAccom;
        this.photo = userPhoto;
    }
}

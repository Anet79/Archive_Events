package com.anet.archiveevents.objects;

import java.util.HashMap;

public class User {

    private String UID;
    private String fullName;
    private String lastName;
    private String phoneNumber;
    private String password;
    private String email;
    private String role;
    private String rank;
    private String base;
    private String profileImgUrl;
    private Boolean haveAllDetails;
    private HashMap<String,String>listOfMyEvents;
    private HashMap<String,String>listOfMyFavoriteEvents;


    public User() {}

    public User(String UID, String password , String fullName, String phoneNumber, String email, String role, String rank, String base, String profileImgUrl) {
        this.UID = UID;
        this.password = password;
        this.email = email;
        this.fullName=fullName;
        this.phoneNumber=phoneNumber;
        this.role=role;
        this.base=base;
        this.rank=rank;
        this.profileImgUrl=profileImgUrl;
        this.haveAllDetails=false;



    }

//    public User(String UID, String password , String name, String lastName, String phoneNumber, String email, String role, String rank, String base, String profileImgUrl, HashMap<String, String> listOfMyEvents, HashMap<String, String> listOfMyFavoriteEvents) {
//        this.UID = UID;
//        this.fullName = name;
//        this.lastName = lastName;
//        this.phoneNumber = phoneNumber;
//        this.email = email;
//        this.password= password;
//        this.role = role;
//        this.rank = rank;
//        this.base = base;
//        this.profileImgUrl = profileImgUrl;
//        this.listOfMyEvents = listOfMyEvents;
//        this.listOfMyFavoriteEvents = listOfMyFavoriteEvents;
//    }

    public Boolean getHaveAllDetails() {
        return haveAllDetails;
    }

    public void setHaveAllDetails(Boolean haveAllDetails) {
        this.haveAllDetails = haveAllDetails;
    }

    public HashMap<String, String> getListOfMyEvents() {
        return listOfMyEvents;
    }

    public HashMap<String, String> getListOfMyFavoriteEvents() {
        return listOfMyFavoriteEvents;
    }

    public void setListOfMyEvents(HashMap<String, String> listOfMyEvents) {
        this.listOfMyEvents = listOfMyEvents;
    }

    public void setListOfMyFavoriteEvents(HashMap<String, String> listOfMyFavoriteEvents) {
        this.listOfMyFavoriteEvents = listOfMyFavoriteEvents;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public String getProfileImgUrl() {
        return profileImgUrl;
    }

    public void setProfileImgUrl(String profileImgUrl) {
        this.profileImgUrl = profileImgUrl;
    }

    @Override
    public String toString() {
        return "User{" +
                "UID='" + UID + '\'' +
                ", name='" + fullName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                ", rank='" + rank + '\'' +
                ", base='" + base + '\'' +
                ", profileImgUrl='" + profileImgUrl + '\'' +
                ", listOfMyEvents=" + listOfMyEvents +
                ", listOfMyFavoriteEvents=" + listOfMyFavoriteEvents +
                '}';
    }
}

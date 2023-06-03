package com.anet.archiveevents.objects;

import com.anet.archiveevents.objects.User;

import java.util.HashMap;

public class DairyEvents {

    private HashMap<String,String> lastEvents;
    private HashMap<String,String> listOfMyEvents;
    private HashMap<String,String> listOfMyFavoriteEvents;
    private User myUser;

    public DairyEvents() {
    }

    public DairyEvents(HashMap<String, String> lastEvents, HashMap<String, String> listOfMyEvents, HashMap<String, String> listOfMyFavoriteEvents, User myUser) {
        this.lastEvents = lastEvents;
        this.listOfMyEvents = listOfMyEvents;
        this.listOfMyFavoriteEvents = listOfMyFavoriteEvents;
        this.myUser = myUser;
    }


    public HashMap<String, String> getLastEvents() {
        return lastEvents;
    }

    public void setLastEvents(HashMap<String, String> lastEvents) {
        this.lastEvents = lastEvents;
    }

    public HashMap<String, String> getListOfMyEvents() {
        return listOfMyEvents;
    }

    public void setListOfMyEvents(HashMap<String, String> listOfMyEvents) {
        this.listOfMyEvents = listOfMyEvents;
    }

    public HashMap<String, String> getListOfMyFavoriteEvents() {
        return listOfMyFavoriteEvents;
    }

    public void setListOfMyFavoriteEvents(HashMap<String, String> listOfMyFavoriteEvents) {
        this.listOfMyFavoriteEvents = listOfMyFavoriteEvents;
    }

    public User getMyUser() {
        return myUser;
    }

    public void setMyUser(User myUser) {
        this.myUser = myUser;
    }

    @Override
    public String toString() {
        return "DairyEvents{" +
                "lastEvents=" + lastEvents +
                ", listOfMyEvents=" + listOfMyEvents +
                ", listOfMyFavoriteEvents=" + listOfMyFavoriteEvents +
                ", myUser=" + myUser +
                '}';
    }
}

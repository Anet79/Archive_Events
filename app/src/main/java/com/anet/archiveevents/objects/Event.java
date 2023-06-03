package com.anet.archiveevents.objects;

import java.util.HashMap;
import java.util.UUID;

public class Event {

    private String category;
    private String title;
    private LandMark landMark;
    private String content;
    // this list will provide us all the upload user's media(video, photos and more)
    private HashMap<String,String> listOfMedia;
    private String eventUID;
    private String creatorUID;


    public Event() {}

    public Event(String creatorUID,String category, String title, LandMark landMark, String content, HashMap<String, String> listOfMedia) {
        this.eventUID= UUID.randomUUID().toString();
        this.category = category;
        this.title = title;
        this.landMark = landMark;
        this.content = content;
        this.listOfMedia = listOfMedia;
        this.creatorUID= creatorUID;

    }

    public String getEventUID() {
        return eventUID;
    }

    public void setEventUID(String eventUID) {
        this.eventUID = eventUID;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LandMark getLandMark() {
        return landMark;
    }

    public void setLandMark(LandMark landMark) {
        this.landMark = landMark;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public HashMap<String, String> getListOfMedia() {
        return listOfMedia;
    }

    public void setListOfMedia(HashMap<String, String> listOfMedia) {
        this.listOfMedia = listOfMedia;
    }

    public String getCreatorUID() {
        return creatorUID;
    }

    public void setCreatorUID(String creatorUID) {
        this.creatorUID = creatorUID;
    }

    @Override
    public String toString() {
        return "Event{" +
                "category='" + category + '\'' +
                ", title='" + title + '\'' +
                ", landMark=" + landMark +
                ", content='" + content + '\'' +
                ", listOfMedia=" + listOfMedia +
                ", eventUID='" + eventUID + '\'' +
                ", creatorUID='" + creatorUID + '\'' +
                '}';
    }
}

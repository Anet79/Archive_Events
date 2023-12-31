package com.anet.archiveevents.objects;

import com.google.android.gms.maps.model.LatLng;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Event {

    private String category;
    private String title;
    private LandMark landMark;
    private String content;
    // this list will provide us all the upload user's media(video, photos and more)
    private ArrayList<String> listOfMedia;
    private String eventUID;
    private String creatorUID;
    private String area;
    //private String eventImage;
    private String eventDate;


    public Event() {}

    public Event(String creatorUID, String category, String title, LandMark landMark, String content, ArrayList<String> listOfMedia, String area/*,String eventImage*/) {
        this.eventUID= UUID.randomUUID().toString();
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy");
        String str = formatter.format(date);
        this.eventDate=str;
        this.category = category;
        this.title = title;
        this.landMark = landMark;
        this.content = content;
        this.listOfMedia = listOfMedia;
        this.creatorUID= creatorUID;
        this.area=area;
        //this.eventImage= eventImage;

    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
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


    public ArrayList<String> getListOfMedia() {
        return listOfMedia;
    }

    public void setListOfMedia(ArrayList<String> listOfMedia) {
        this.listOfMedia = listOfMedia;
    }

    public String getCreatorUID() {
        return creatorUID;
    }

    public void setCreatorUID(String creatorUID) {
        this.creatorUID = creatorUID;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }


 /*   public String getEventImage() {
        return eventImage;
    }

    public void setEventImage(String eventImage) {
        this.eventImage = eventImage;
    }*/

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
                ", area='" + area + '\'' +
                ", eventDate='" + eventDate + '\'' +
                '}';
    }
}

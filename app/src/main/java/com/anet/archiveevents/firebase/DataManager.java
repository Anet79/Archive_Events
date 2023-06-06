package com.anet.archiveevents.firebase;

import com.anet.archiveevents.Keys;
import com.anet.archiveevents.objects.Event;
import com.anet.archiveevents.objects.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

public class DataManager {
    //Singleton
    private static DataManager single_instance = null;

    //Firebase
    private final FirebaseAuth firebaseAuth;    // for the login with phone number
    private final FirebaseDatabase realTimeDB;  // for save objects data
    private final FirebaseStorage storage;      // for pictures and videos

    private User currentUser;
    private String currentEventUid;
    private Event currentEvent;


    //Constructor
    public DataManager() {
        firebaseAuth = FirebaseAuth.getInstance();
        realTimeDB = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();
    }

    public static DataManager initHelper() {
        if (single_instance == null) {
            single_instance = new DataManager();
        }

        return single_instance;
    }


    public static DataManager getInstance() {
        return single_instance;
    }

    public FirebaseDatabase getRealTimeDB() {
        return realTimeDB;
    }

    public FirebaseAuth getFirebaseAuth() {
        return firebaseAuth;
    }

    public FirebaseStorage getStorage() {
        return storage;
    }





    public DatabaseReference eventListReference(){
        return realTimeDB.getReference().child(Keys.KEY_LIST_EVENTS);
    }

    public DatabaseReference usersListReference(){
        return realTimeDB.getReference().child(Keys.KEY_USERS);
    }

    public Event getCurrentEvent() {
        return currentEvent;
    }

    public DataManager setCurrentEvent(Event currentEvent) {
        this.currentEvent = currentEvent;
        return this;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public DataManager setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
        return this;
    }

    public String getCurrentEventUid() {
        return currentEventUid;
    }

    public void setCurrentEventUid(String currentEventUid) {
        this.currentEventUid = currentEventUid;
    }
}

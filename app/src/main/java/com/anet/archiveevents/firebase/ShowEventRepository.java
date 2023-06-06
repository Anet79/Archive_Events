package com.anet.archiveevents.firebase;

import androidx.lifecycle.MutableLiveData;

import com.anet.archiveevents.Keys;
import com.anet.archiveevents.objects.Event;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;

public class ShowEventRepository {

    private Event event;

    private MutableLiveData<Event> specificEvent;
    private DataManager dataManager;


    public ShowEventRepository() {
        dataManager = DataManager.getInstance();

        specificEvent= new MutableLiveData<>();
        loadSpecificEvent();

    }



    public void setSpecificEvent(MutableLiveData<Event> specificEvent) {
        this.specificEvent = specificEvent;
    }


    public MutableLiveData<Event> getSpecificEvent() {
        return specificEvent;
    }


    public void loadSpecificEvent(){

        DatabaseReference myRef = dataManager.getRealTimeDB().getReference(Keys.KEY_LIST_EVENTS);
        myRef.child(dataManager.getCurrentEventUid()).get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    Event loadedEvent = dataSnapshot.getValue(Event.class);

                    specificEvent.setValue(loadedEvent);
                }

            }


        });

    }



}

package com.anet.archiveevents.firebase;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.anet.archiveevents.EventItemClicked;
import com.anet.archiveevents.Keys;
import com.anet.archiveevents.objects.Event;
import com.anet.archiveevents.objects.LandMark;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DairyEventRepository {
    private DataManager dataManager;
    private ArrayList<Event> allEvents;

    private ArrayList<Event> allEventsSearch;
    private ArrayList<Event> allMyEvents;
    private MutableLiveData<ArrayList<Event>> events;

    private MutableLiveData<ArrayList<Event>> myEvents;

    private MutableLiveData<ArrayList<Event>> eventsSearch;
    private EventItemClicked eventOnClicked;


    public DairyEventRepository() {
        dataManager = DataManager.getInstance();
       events=new MutableLiveData<>();
        eventsSearch = new MutableLiveData<>();
        myEvents=new MutableLiveData<>();
        allEvents=new ArrayList<>();
        allEventsSearch=new ArrayList<>();
        allMyEvents=new ArrayList<>();
      // initAllEventsArray();


    }
//    public void initAllEventsArray(){
//        loadEventData();
//        loadMyEventData();
//
//
//    }


    public MutableLiveData<ArrayList<Event>> getAllEvents() {

        return events;
    }

    public MutableLiveData<ArrayList<Event>> getAllEventsSearch() {

        return eventsSearch;
    }



    public MutableLiveData<ArrayList<Event>> getAllMyEvents() {

        return myEvents;
    }

    public void loadEventData() {
        DatabaseReference myRef = dataManager.getRealTimeDB().getReference(Keys.KEY_LIST_EVENTS);
        myRef.get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        allEvents.add(snapshot.getValue(Event.class));

                    }

                    events.setValue(allEvents);
                }

            }


        });

    }

    private void loadMyEventData() {
        DatabaseReference myRef = dataManager.getRealTimeDB().getReference(Keys.KEY_LIST_EVENTS);
        myRef.get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {


                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        for(DataSnapshot ds : snapshot.getChildren()){
                            if (ds.getValue().equals(dataManager.getCurrentUser().getUID())) {
                                allMyEvents.add(snapshot.getValue(Event.class));
                                continue;
                            }
                        }



                    }
                    myEvents.setValue(allMyEvents);



                }
            }

        });

    }


    public MutableLiveData<ArrayList<Event>> getEventListFromFirebase(String arrayType){
        DatabaseReference myRef = dataManager.getRealTimeDB().getReference(Keys.KEY_LIST_EVENTS);

       // MutableLiveData<ArrayList<Event>> data = new MutableLiveData<>();


        if (arrayType.equals("0")){

            myRef.get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
                @Override
                public void onSuccess(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            allEvents.add(snapshot.getValue(Event.class));

                        }
                        events.setValue(allEvents);




                        for (int i = 0; i <allEvents.size() ; i++) {
                            Log.d("allEvents",allEvents.get(i).toString());
                        }



                        //eventOnClicked.eventClicked();

                    }
                }

            });

        }
        else if (arrayType.equals("1"))
            myRef.get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
                @Override
                public void onSuccess(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {


                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            for(DataSnapshot ds : snapshot.getChildren()){
                                if (ds.getValue().equals(dataManager.getCurrentUser().getUID())) {
                                    allMyEvents.add(snapshot.getValue(Event.class));
                                    continue;
                                }
                            }



                        }
                        events.setValue(allMyEvents);


                    }
                }

            });




        return events;







    }

  /*  public void filterList(String newText) {
        //TODO- make kay word search- lan,lug and title
        List<Event> allEventForSearch=new ArrayList<>();


    }*/

    public void filterList(String newText) {
      //  newText="jjjj";
        DatabaseReference eventsRef = dataManager.getRealTimeDB().getReference(Keys.KEY_LIST_EVENTS);
        Query query = eventsRef.orderByChild(Keys.KEY_EVENT_TITLE).equalTo(newText);
        Log.d("ptt",query.toString());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()) {
                    Event event=dataSnapshot1.getValue(Event.class);
                    if(event.getTitle().equalsIgnoreCase(newText)){
                        //Log.d("ptt ","yuval" +"/n"+event.toString() );
                        allEventsSearch.add(event);

                    }
                }
                eventsSearch.setValue(allEventsSearch);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle the error case
            }
        });
    }



    public void searchEventWithLandMark(LandMark landMarkUser){
        DatabaseReference myRef = dataManager.getRealTimeDB().getReference(Keys.KEY_LIST_EVENTS);

        myRef.get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        for(DataSnapshot ds : snapshot.getChildren()){
//                            if (ds.getValue() ) {
//                                allMyEvents.add(snapshot.getValue(Event.class));
//                                continue;
//                            }
                        }



                    }
                    myEvents.setValue(allMyEvents);



                }
            }

        });


    }
}









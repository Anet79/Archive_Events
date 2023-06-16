package com.anet.archiveevents.firebase;

import android.app.appsearch.SearchResult;

import androidx.lifecycle.MutableLiveData;

import com.anet.archiveevents.Keys;
import com.anet.archiveevents.objects.Event;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SearchOnTheMapRepository {

    private DataManager dataManager;
//    private ArrayList<Event> allEventsSearch;
    private MutableLiveData<ArrayList<Event>> eventsSearch;
    private MutableLiveData<Boolean> haveItemInList;
    ArrayList<Event> allEventsSearch;


    public SearchOnTheMapRepository() {
        eventsSearch = new MutableLiveData<>();
        dataManager = DataManager.getInstance();

        haveItemInList = new MutableLiveData<>();
        allEventsSearch=new ArrayList<>();

        if(allEventsSearch.isEmpty()){
            haveItemInList.postValue(false);
        }
    }

    public MutableLiveData<ArrayList<Event>> getAllEventsSearch() {

        return eventsSearch;
    }

    public void saveCurrentEventForDataManager(Event event){
        dataManager.setCurrentEvent(event);
    }

    public MutableLiveData<Boolean> getHaveItemInList() {
        return haveItemInList;
    }

    public void performSearch(String newQuery) {
        DatabaseReference eventsRef = dataManager.getRealTimeDB().getReference(Keys.KEY_LIST_EVENTS);
        Query query = eventsRef.orderByChild(Keys.KEY_EVENT_TITLE).equalTo(newQuery);



        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()) {
                    Event event=dataSnapshot1.getValue(Event.class);
                    if(event.getTitle().equalsIgnoreCase(newQuery)){
                        //Log.d("ptt ","yuval" +"/n"+event.toString() );
                        allEventsSearch.add(event);
                        haveItemInList.postValue(true);
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












//    public List<SearchResult> performSearch(String query) {
//
//        return null;
//    }
}

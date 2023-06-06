package com.anet.archiveevents.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.anet.archiveevents.firebase.DairyEventRepository;
import com.anet.archiveevents.objects.Event;
import com.anet.archiveevents.objects.LandMark;

import java.util.ArrayList;
import java.util.List;

public class DairyEventViewModel extends ViewModel {

   private MutableLiveData<ArrayList<Event>> allEvents;

    private MutableLiveData<ArrayList<Event>> allEventsSearch;

    private ArrayList<Event>eventArrayList;
    private DairyEventRepository eventsDetailsRepository;
    private String searchText;

    private MutableLiveData<ArrayList<Event>> allMyEvents;
    private LiveData<ArrayList<Event>> allNewEvents;



    public DairyEventViewModel() {
        allEvents=new MutableLiveData<>();
        allEventsSearch=new MutableLiveData<>();
        allNewEvents=new MutableLiveData<>();
        allMyEvents=new MutableLiveData<>();
        eventsDetailsRepository= new DairyEventRepository();
        allEventsSearch=eventsDetailsRepository.getAllEventsSearch();
        allEvents=eventsDetailsRepository.getAllEvents();
        allMyEvents=eventsDetailsRepository.getAllMyEvents();
        allNewEvents= eventsDetailsRepository.getEventListFromFirebase("0");


    }
    public void loadAllEvents(){
        eventsDetailsRepository.loadEventData();
    }


    public LiveData<ArrayList<Event>> getAllEventsData(){
        return allEvents;
    }

    public LiveData<ArrayList<Event>> getAllMyEventsData(){
        return allMyEvents;
    }

    public LiveData<ArrayList<Event>> getAllEventsSearchData(){
        return allEventsSearch;
    }

    public LiveData<ArrayList<Event>> getEventList(String arrayType) {
        return allNewEvents;
    }

    public void filterList(String newText) {
        eventsDetailsRepository.filterList(newText);
    }

}

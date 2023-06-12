package com.anet.archiveevents.viewModel;

import android.app.appsearch.SearchResult;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.anet.archiveevents.firebase.SearchOnTheMapRepository;
import com.anet.archiveevents.objects.Event;

import java.util.ArrayList;
import java.util.List;

public class SearchOnTheMapViewModel extends ViewModel {

    private SearchOnTheMapRepository searchOnTheMapRepository;
    private MutableLiveData<ArrayList<Event>> eventsSearch;
    private MutableLiveData<Boolean> haveItemInList;


    public SearchOnTheMapViewModel() {
        searchOnTheMapRepository = new SearchOnTheMapRepository();
        eventsSearch = new MutableLiveData<>();
        haveItemInList = new MutableLiveData<>();
        haveItemInList= searchOnTheMapRepository.getHaveItemInList();
        eventsSearch= searchOnTheMapRepository.getAllEventsSearch();
    }


    public void performSearch(String newQuery) {

        searchOnTheMapRepository.performSearch(newQuery);


    }


    public SearchOnTheMapRepository getSearchOnTheMapRepository() {
        return searchOnTheMapRepository;
    }

    public void setSearchOnTheMapRepository(SearchOnTheMapRepository searchOnTheMapRepository) {
        this.searchOnTheMapRepository = searchOnTheMapRepository;
    }

    public MutableLiveData<ArrayList<Event>> getEventsSearch() {
        return eventsSearch;
    }

    public void setEventsSearch(MutableLiveData<ArrayList<Event>> eventsSearch) {
        this.eventsSearch = eventsSearch;
    }

    public MutableLiveData<Boolean> getHaveItemInList() {
        return haveItemInList;
    }

    public void setHaveItemInList(MutableLiveData<Boolean> haveItemInList) {
        this.haveItemInList = haveItemInList;
    }
}

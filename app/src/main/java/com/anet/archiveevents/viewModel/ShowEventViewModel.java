package com.anet.archiveevents.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.anet.archiveevents.firebase.ShowEventRepository;
import com.anet.archiveevents.objects.Event;
import com.anet.archiveevents.objects.User;

public class ShowEventViewModel extends ViewModel {
    private MutableLiveData<Event> getSpecificEvent;
    private ShowEventRepository showEventRepository;


    public ShowEventViewModel() {

        showEventRepository= new ShowEventRepository();

        getSpecificEvent= new MutableLiveData<>();
        getSpecificEvent=showEventRepository.getSpecificEvent();




    }


    public MutableLiveData<Event> getGetSpecificEvent() {
        return getSpecificEvent;
    }

    public void setGetSpecificEvent(MutableLiveData<Event> getSpecificEvent) {
        this.getSpecificEvent = getSpecificEvent;
    }
}

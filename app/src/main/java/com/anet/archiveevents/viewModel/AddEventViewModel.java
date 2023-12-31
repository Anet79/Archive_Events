package com.anet.archiveevents.viewModel;

import android.app.Application;
import android.content.Context;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.anet.archiveevents.firebase.AddEventRepository;
import com.anet.archiveevents.firebase.AuthenticationRepository;
import com.anet.archiveevents.objects.LandMark;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class AddEventViewModel extends ViewModel {

    private MutableLiveData<String> date = new MutableLiveData<>();

   // private Context context;
    private MutableLiveData<String> eventName = new MutableLiveData<>();
    private MutableLiveData<String> username = new MutableLiveData<>();
    private MutableLiveData<String> location = new MutableLiveData<>();
    private MutableLiveData<String> category = new MutableLiveData<>();
    private AddEventRepository addEventRepository;
    private MutableLiveData<Boolean> addEventMutableLiveData;
    private MutableLiveData<Boolean> addCompleteEventMutableLiveData;

    private MutableLiveData<Boolean> eventAdded;


    public AddEventViewModel() {


        addEventRepository=new AddEventRepository();
       // addEventMutableLiveData=addEventRepository.getAddEventMutableLiveData();
        addEventMutableLiveData=addEventRepository.getAddEventMutableLiveData();
        addCompleteEventMutableLiveData=addEventRepository.getAddCompleteEventMutableLiveData();
        eventAdded=addEventRepository.getEventAdded();

    }

    public MutableLiveData<Boolean> getAddCompleteEventMutableLiveData() {
        return addCompleteEventMutableLiveData;
    }

    public MutableLiveData<String> getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date.setValue(date);
    }

    public MutableLiveData<String> getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName.setValue(eventName);
    }

    public MutableLiveData<String> getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username.setValue(username);
    }

    public MutableLiveData<String> getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location.setValue(location);
    }

    public MutableLiveData<String> getCategory() {
        return category;
    }

    public MutableLiveData<Boolean> getEventAdded() {
        return eventAdded;
    }

    public void setEventAdded(MutableLiveData<Boolean> eventAdded) {
        this.eventAdded = eventAdded;
    }

    public void setCategory(String category) {
        this.category.setValue(category);
    }

    public void saveEvent(String title, String category, LandMark landMark, String content, String area) {
        // Event(String creatorUID,String category, String title, LandMark landMark, String content, HashMap<String, String> listOfMedia)
        // Implement the logic to save the event with the entered details
        // For example, create an AddedEvent object with the entered data and save it to a data source
//        Event event = new Event(
//                date.getValue(),
//                eventName.getValue(),
//                username.getValue(),
//                location.getValue(),
//                category.getValue()
//        );
        /**
         TODO- implement the method
         //Save the addedEvent object to the data source
         Event event = new Event("",category, eventName, username, location, category);

        // Pass the event object to the repository or service for saving-firebase
         EventRepository.getInstance().saveEvent(event);
         **/

        addEventRepository.saveEvent(title,category,landMark,content,area);
    }


    public void uploadVideo( ArrayList<Uri> list) {
        addEventRepository.uploadVideo(list);

    }

    public LiveData<Boolean> getUploadStatusLiveData() {
        return addEventMutableLiveData;
    }

    public void upLoadsFiles(ArrayList<Uri> mArrayUri) {
        addEventRepository.upLoadsFiles(mArrayUri);
    }
}

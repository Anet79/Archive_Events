package com.anet.archiveevents.firebase;

import android.app.Application;
import android.content.Context;
import android.net.Uri;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.anet.archiveevents.Keys;
import com.anet.archiveevents.objects.Event;
import com.anet.archiveevents.objects.GpsTracker;
import com.anet.archiveevents.objects.LandMark;
import com.anet.archiveevents.objects.User;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

public class AddEventRepository {
    private Application application;
    private MutableLiveData<Event> newEvent ;
    private MutableLiveData<Boolean> addEventMutableLiveData;
    private MutableLiveData<Boolean> addCompleteEventMutableLiveData;
    private FirebaseStorage storage;
    private HashMap<String,String>allEventMedia;
    private GpsTracker gpsService;
    private DataManager dataManager;




    public AddEventRepository() {
        newEvent=new MutableLiveData<>();
        addEventMutableLiveData=new MutableLiveData<>();
        storage = FirebaseStorage.getInstance();
        allEventMedia= new HashMap<>();
        dataManager= DataManager.getInstance();
        addCompleteEventMutableLiveData=new MutableLiveData<>();


    }


    public MutableLiveData<Boolean> getAddCompleteEventMutableLiveData() {
        return addCompleteEventMutableLiveData;
    }

    public MutableLiveData<Event> getNewEvent() {
        return newEvent;
    }

    public void setNewEvent(MutableLiveData<Event> newEvent) {
        this.newEvent = newEvent;
    }

    public MutableLiveData<Boolean> getAddEventMutableLiveData() {
        return addEventMutableLiveData;
    }

    public void setAddEventMutableLiveData(MutableLiveData<Boolean> addEventMutableLiveData) {
        this.addEventMutableLiveData = addEventMutableLiveData;
    }



    public void uploadVideo(Uri videoUri){
        StorageReference videoRef= storage.getReference().child("videos/"+ videoUri.getLastPathSegment());
        if (videoUri != null) {
            // save the selected video in Firebase storage
         //   final StorageReference reference = FirebaseStorage.getInstance().getReference("Files/" + System.currentTimeMillis() + "." + getfiletype(videouri));
            videoRef.putFile(videoUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                    while (!uriTask.isSuccessful()) ;
                    // get the link of video
                    String downloadUri = uriTask.getResult().toString();
                    DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("media");
                    HashMap<String, String> map = new HashMap<>();
                    map.put("videolink", downloadUri);
                    allEventMedia.put("media",downloadUri);
                    reference1.child("" + System.currentTimeMillis()).setValue(map);
                    addEventMutableLiveData.setValue(true);


                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    // Error, Image not uploaded
                   addEventMutableLiveData.setValue(false);
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                // Progress Listener for loading
                // percentage on the dialog box
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    // show the progress bar
                    double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                 //   progressDialog.setMessage("Uploaded " + (int) progress + "%");
                }
            });
        }
    }

    public void saveEvent(String title, String category, LandMark landMark, String content, String area) {

        DatabaseReference myRef =  dataManager.getRealTimeDB().getReference(Keys.KEY_LIST_EVENTS);
        DatabaseReference myRef01 =  dataManager.getRealTimeDB().getReference(Keys.KEY_LIST_FOR_LAND_MARKS);


      // if(addEventMutableLiveData.getValue()==true) {
            Event newEvent = new Event(dataManager.getCurrentUser().getUID(), category, title, landMark, content, allEventMedia,area);
            myRef.child(newEvent.getEventUID()).setValue(newEvent).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                addCompleteEventMutableLiveData.setValue(true);


                }
            });


/*            myRef01.child(landMark.toString()).setValue(newEvent).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {




            }
        });*/




    }







}

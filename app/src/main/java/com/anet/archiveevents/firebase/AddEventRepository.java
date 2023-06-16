package com.anet.archiveevents.firebase;

import android.app.Application;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.anet.archiveevents.Keys;
import com.anet.archiveevents.objects.Event;
import com.anet.archiveevents.objects.GpsTracker;
import com.anet.archiveevents.objects.LandMark;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AddEventRepository {
    private Application application;
    private MutableLiveData<Event> newEvent;
    private MutableLiveData<Boolean> addEventMutableLiveData;
    private MutableLiveData<Boolean> addCompleteEventMutableLiveData;
    private FirebaseStorage storage;
    private List< String> allEventMedia;

    private GpsTracker gpsService;
    private ArrayList<String>allMediaArrayList;

    private DataManager dataManager;

    private MutableLiveData<Boolean> eventAdded;


    public AddEventRepository() {
        newEvent = new MutableLiveData<>();
        addEventMutableLiveData = new MutableLiveData<>();
        storage = FirebaseStorage.getInstance();
        allEventMedia = new ArrayList<>();

        dataManager = DataManager.getInstance();
        addCompleteEventMutableLiveData = new MutableLiveData<>();
        allMediaArrayList=new ArrayList<>();

        dataManager= DataManager.getInstance();
        addCompleteEventMutableLiveData=new MutableLiveData<>();
        eventAdded = new MutableLiveData<>();


    }


    public MutableLiveData<Boolean> getAddCompleteEventMutableLiveData() {
        return addCompleteEventMutableLiveData;
    }

    public MutableLiveData<Boolean> getEventAdded() {
        return eventAdded;
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


    public void uploadVideo(ArrayList<Uri> list) {

        if (!list.isEmpty()) {
            for (Uri uri : list) {
                // save the selected video in Firebase storage
                StorageReference videoRef = storage.getReference().child("videos/");

                videoRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                        while (!uriTask.isSuccessful()) ;
                        // get the link of video
                        String downloadUri = uriTask.getResult().toString();
                        DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("media");
                        HashMap<String, String> map = new HashMap<>();
                        map.put("videolink", downloadUri);
                       // allEventMedia.put("media", downloadUri);
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
    }

    public void saveEvent(String title, String category, LandMark landMark, String content, String area) {

        DatabaseReference myRef = dataManager.getRealTimeDB().getReference(Keys.KEY_LIST_EVENTS);
        DatabaseReference myRef01 = dataManager.getRealTimeDB().getReference(Keys.KEY_LIST_FOR_LAND_MARKS);
        StorageReference reference = FirebaseStorage.getInstance().getReference("Files/" + System.currentTimeMillis() );


        // if(addEventMutableLiveData.getValue()==true) {
        Event newEvent = new Event(dataManager.getCurrentUser().getUID(), category, title, landMark, content, allMediaArrayList, area);
        myRef.child(newEvent.getEventUID()).setValue(newEvent).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {



                addCompleteEventMutableLiveData.setValue(true);


            }
        });


        //reference.child(newEvent.getEventUID()).listAll(allEventMedia).




/*            myRef01.child(landMark.toString()).setValue(newEvent).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {




            }
        });*/


    }

    public void upLoadsFiles(ArrayList<Uri> list) {
        DatabaseReference myRef = dataManager.getRealTimeDB().getReference(Keys.KEY_LIST_EVENTS);

        StorageReference videoRef = storage.getReference().child("videos/");

        if (list.size() > 0) {
            //spotDialog.show();
            int counter = 0;
            // final String firebasePushID = firestore.collection(currentUser.getUid()).document().getId();
            for (int i = 0; i < list.size(); i++) {
                StorageReference videoRef1 = videoRef.child(i+"23");

                Uri perFile = list.get(i);
                counter++;
                final int finalCounter = counter;
                final int finalI = i;
                videoRef1.putFile(perFile).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        if (task.isSuccessful()) {
                            videoRef1.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull Task<Uri> task) {
                                    if (task.isSuccessful()) {
                                        String uri = String.valueOf(task.getResult());

                                        //Log.e(LOGCODE, uri);
//                                        allEventMedia.put("fileName", uri);
                                        allMediaArrayList.add(uri);

                                        addEventMutableLiveData.setValue(true);
                                    } else {
                                        addEventMutableLiveData.setValue(false);

                                    }

                                }
                            });

                        }

                        else {
//                            spotDialog.dismiss();
//                            Log.e(LOGCODE, Objects.requireNonNull(task.getException()).getMessage());
//                            Toast.makeText(AddTransaction.this, "Couldn't upload file ", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }
    }


}

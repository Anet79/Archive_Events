package com.anet.archiveevents.firebase;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.anet.archiveevents.Keys;
import com.anet.archiveevents.objects.Compline;
import com.anet.archiveevents.objects.Event;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;

public class AddComplineRepository {


    private MutableLiveData<Boolean> addCompleteEComplineMutableLiveData;
    private Compline compline;
    private DataManager dataManager;


    public AddComplineRepository() {
        this.addCompleteEComplineMutableLiveData=new MutableLiveData<>();
        dataManager= DataManager.getInstance();
    }

    public MutableLiveData<Boolean> getAddCompleteComplineMutableLiveData() {
        return addCompleteEComplineMutableLiveData;
    }

    public void saveCompline(String title, String mail, String name, String content) {
        DatabaseReference myRef =  dataManager.getRealTimeDB().getReference(Keys.KEY_ADD_LIST_COMPLAINT);
        // if(addEventMutableLiveData.getValue()==true) {
        compline = new Compline(title,name,mail,content);
        myRef.child(compline.getComplineUID()).setValue(compline).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                addCompleteEComplineMutableLiveData.setValue(true);


            }
        });

    }


}

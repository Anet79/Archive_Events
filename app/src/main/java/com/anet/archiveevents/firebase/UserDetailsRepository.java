package com.anet.archiveevents.firebase;

import android.app.Application;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.anet.archiveevents.Keys;
import com.anet.archiveevents.OnUserStatusListener;
import com.anet.archiveevents.objects.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.UploadTask;

import java.nio.charset.StandardCharsets;

public class UserDetailsRepository {

    private MutableLiveData<User> UserInfoLiveData;
    private MutableLiveData<Boolean> addUserLoggedLiveData;
    private AuthenticationRepository authenticationRepository;
    private DataManager dataManager;
    private String myDownloadUri;



    public UserDetailsRepository() {
        dataManager=DataManager.getInstance();
        this.UserInfoLiveData = new MutableLiveData<>();
        authenticationRepository = new AuthenticationRepository();
        addUserLoggedLiveData = new MutableLiveData<>();


    }

    public MutableLiveData<Boolean> getAddUserLoggedLiveData() {
        return addUserLoggedLiveData;
    }

    public MutableLiveData<User> getUserInfoLiveData() {
        return UserInfoLiveData;
    }


    public void addUser(String fullName, String rank, String phoneNumber, String role, String base, String profileImgUrl) {
        String UID = authenticationRepository.getFirebaseUserMutableLiveData().getValue().getUid();
        String email = authenticationRepository.getFirebaseUserMutableLiveData().getValue().getEmail();
        DatabaseReference myRef = dataManager.getRealTimeDB().getReference(Keys.KEY_USERS);
        byte[] profilePic= profileImgUrl.getBytes(StandardCharsets.UTF_8);
        User newUser = new User(UID, "1234", fullName, phoneNumber, email, role, rank, base, profileImgUrl);
        myRef.child(UID).setValue(newUser).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {


//                //Start The upload task
//                UploadTask uploadTask = dataManager.getStorage().getReference().child(Keys.KEY_PROFILE_PICTURES).child(dataManager.getCurrentUser().getUID()).putBytes(profilePic);
//                uploadTask.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
//                        if (task.isSuccessful()) {
//                            //If upload was successful, We want to get the image url from the storage
//                            dataManager.getStorage().getReference().child(Keys.KEY_PROFILE_PICTURES).child(dataManager.getCurrentUser().getUID()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                                @Override
//                                public void onSuccess(Uri uri) {
//                                    //Set the profile URL to the object we created
//                                    myDownloadUri = uri.toString();
//
//
//                                    //View Indicates the process of the image uploading Done by making the button Enabled
//                                    //    panel_BTN_add.setEnabled(true);
//                                }
//                            });
//                        }
                        addUserLoggedLiveData.postValue(true);
                        dataManager.setCurrentUser(newUser);
                        UserInfoLiveData.postValue(newUser);


                    }
                });



            }

//            public void getUser(OnUserStatusListener listener) {
//                DatabaseReference myRef = dataManager.getRealTimeDB().getReference(Keys.KEY_USERS).child(dataManager.getFirebaseAuth().getCurrentUser().getUid());
//                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        User user = dataSnapshot.getValue(User.class);
//                        if (user != null) {
//
//                            listener.onUserLoggedIn(user);
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//                        // Handle the error
//                    }
//                });
//
//
//            }
        }
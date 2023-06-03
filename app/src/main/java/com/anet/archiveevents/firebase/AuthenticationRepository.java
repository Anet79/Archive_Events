package com.anet.archiveevents.firebase;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.anet.archiveevents.Keys;
import com.anet.archiveevents.objects.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class AuthenticationRepository {

    private MutableLiveData<FirebaseUser> firebaseUserMutableLiveData;
    private MutableLiveData<Boolean> userLoggedMutableLiveData;
    private DataManager dataManager;
    private MutableLiveData<Boolean> userAllReadyExistMutableLiveData;


    public AuthenticationRepository() {

        firebaseUserMutableLiveData = new MutableLiveData<>();
        userLoggedMutableLiveData = new MutableLiveData<>();
        dataManager = DataManager.getInstance();
        userAllReadyExistMutableLiveData = new MutableLiveData<>();

        if (dataManager.getFirebaseAuth().getCurrentUser() != null) {
            firebaseUserMutableLiveData.postValue(dataManager.getFirebaseAuth().getCurrentUser());

        }
    }

    public MutableLiveData<Boolean> getUserAllReadyExistMutableLiveData() {
        return userAllReadyExistMutableLiveData;
    }

    public MutableLiveData<FirebaseUser> getFirebaseUserMutableLiveData() {
        return firebaseUserMutableLiveData;
    }

    public MutableLiveData<Boolean> getUserLoggedMutableLiveData() {
        return userLoggedMutableLiveData;
    }

    public void register(String email, String password) {
        dataManager.getFirebaseAuth().createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.d("pttt", "onComplete");
                    firebaseUserMutableLiveData.setValue(dataManager.getFirebaseAuth().getCurrentUser());

                } else {
                    Log.d("pttt", "Fail");
                    //  Toast.makeText(application,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void login(String email, String password) {
        dataManager.getFirebaseAuth().signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    firebaseUserMutableLiveData.setValue(dataManager.getFirebaseAuth().getCurrentUser());
                    checkIfUserExists(dataManager.getFirebaseAuth().getCurrentUser().getUid());


                } else {
                    //   Toast.makeText(application,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void signOut() {
        dataManager.getFirebaseAuth().signOut();
        userLoggedMutableLiveData.postValue(true);

    }
//check if the user already signup
    private void checkIfUserExists(String userId) {
        DatabaseReference usersRef = dataManager.getRealTimeDB().getReference(Keys.KEY_USERS);
        Query query = usersRef.orderByKey().equalTo(userId);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // User already exists

                    loadCurrentUser(userId);


                } else {
                    // User does not exist
                    userAllReadyExistMutableLiveData.postValue(false);


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle the error case
            }
        });
    }

    public void loadCurrentUser(String userUid) {
        DatabaseReference myRef = dataManager.usersListReference().child(userUid);
        myRef.get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    User u = dataSnapshot.getValue(User.class);
                    dataManager.setCurrentUser(u);
                    userAllReadyExistMutableLiveData.postValue(true);

                }
            }
        });

    }

}


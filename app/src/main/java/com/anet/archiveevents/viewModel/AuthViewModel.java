package com.anet.archiveevents.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.anet.archiveevents.firebase.AuthenticationRepository;
import com.google.firebase.auth.FirebaseUser;

public class AuthViewModel extends ViewModel {

    private MutableLiveData<FirebaseUser>firebaseUserMutableLiveData;
    private AuthenticationRepository authenticationRepository;
    private MutableLiveData<Boolean>loggedStatus;
    private MutableLiveData<Boolean> userAllReadyExistMutableLiveData;



    public MutableLiveData<FirebaseUser> getFirebaseUserMutableLiveData() {
        return firebaseUserMutableLiveData;
    }

    public MutableLiveData<Boolean> getLoggedStatus() {
        return loggedStatus;
    }


    public AuthViewModel() {

        authenticationRepository=new AuthenticationRepository();
        firebaseUserMutableLiveData=authenticationRepository.getFirebaseUserMutableLiveData();
        loggedStatus=authenticationRepository.getUserLoggedMutableLiveData();
        userAllReadyExistMutableLiveData=authenticationRepository.getUserAllReadyExistMutableLiveData();

    }


    public MutableLiveData<Boolean> getUserAllReadyExistMutableLiveData() {
        return userAllReadyExistMutableLiveData;
    }

    public void register(String email, String password){
        authenticationRepository.register(email,password);
    }

    public void signIn(String email, String password){
        authenticationRepository.login( email, password);
    }

    public void signOut(){
        authenticationRepository.signOut();
    }

}

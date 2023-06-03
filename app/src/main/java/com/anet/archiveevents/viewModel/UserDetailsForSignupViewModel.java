package com.anet.archiveevents.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.anet.archiveevents.OnUserStatusListener;
import com.anet.archiveevents.firebase.AuthenticationRepository;
import com.anet.archiveevents.firebase.UserDetailsRepository;
import com.anet.archiveevents.objects.User;
import com.google.firebase.auth.FirebaseUser;

public class UserDetailsForSignupViewModel extends ViewModel {

    private MutableLiveData<User> createUserDetailsMutableLiveData;
    private UserDetailsRepository userDetailsRepository;
    private MutableLiveData<Boolean>addUserStatus;


    public UserDetailsForSignupViewModel() {


        userDetailsRepository=new UserDetailsRepository();
        createUserDetailsMutableLiveData=userDetailsRepository.getUserInfoLiveData();
        //TODO-check status
        addUserStatus=userDetailsRepository.getAddUserLoggedLiveData();


    }

    public MutableLiveData<Boolean> getAddUserStatus() {
        return addUserStatus;
    }

    public void addUserDetails(String fullName, String rank, String phoneNumber, String role, String base, String profileImgUrl){
        userDetailsRepository.addUser(fullName,rank,phoneNumber,role,base,profileImgUrl);

    }
//    public void getUser(OnUserStatusListener listener) {
//        userDetailsRepository.getUser(listener);
//    }


    public LiveData<User> getCreateUserDetailsMutableLiveData() {
        return createUserDetailsMutableLiveData;
    }

    public void getBytesForProfilePicture( byte[] bytes){

    }
}

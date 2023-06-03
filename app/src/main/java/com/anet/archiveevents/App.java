package com.anet.archiveevents;

import android.app.Application;

import com.anet.archiveevents.firebase.DataManager;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();



        //Initiate FireBase Managers
        DataManager.initHelper();
    }
}

package com.anet.archiveevents.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.anet.archiveevents.firebase.AddComplineRepository;
import com.anet.archiveevents.firebase.AddEventRepository;
import com.anet.archiveevents.objects.LandMark;

public class AddComplineViewModel extends ViewModel {

    private AddComplineRepository addComplineRepository;
    private MutableLiveData<Boolean> addCompleteComplineMutableLiveData;

    public AddComplineViewModel() {

        addComplineRepository=new AddComplineRepository();

        addCompleteComplineMutableLiveData=addComplineRepository.getAddCompleteComplineMutableLiveData();

    }

    public MutableLiveData<Boolean> getAddCompleteComplineMutableLiveData() {
        return addCompleteComplineMutableLiveData;
    }

    public void saveCompline(String title, String mail, String name, String content) {


        addComplineRepository.saveCompline(title,mail,name,content);
    }
}

package com.anet.archiveevents.view;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.anet.archiveevents.R;
import com.anet.archiveevents.firebase.DataManager;
import com.anet.archiveevents.objects.User;
import com.anet.archiveevents.viewModel.UserDetailsForSignupViewModel;
import com.bumptech.glide.Glide;


public class ProfilePageFragment extends Fragment {
    private ImageView recipe_item_IMG_img;

    private  ActionBar actionBar;
    private TextView profile_page_name;
    private TextView profile_page_role;
    private TextView profile_page_phone_number;
    private TextView profile_page_email;
    private TextView profile_page_location;
    private DataManager dataManager;

    private ImageButton profile_back_button;

    private NavController navController;





    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataManager=DataManager.getInstance();

        /*AppCompatActivity activity = (AppCompatActivity) getActivity();
        ActionBar actionBar = activity.getSupportActionBar();*/



        // providing title for the ActionBar
       // actionBar.setTitle("פרופיל");


        /*actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);*/


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile_page, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViews(view);
        initData();

        profile_back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.popBackStack();
            }
        });


    }



    private void initData() {
        profile_page_name.setText( dataManager.getCurrentUser().getRank() +dataManager.getCurrentUser().getFullName());
        profile_page_phone_number.setText(dataManager.getCurrentUser().getPhoneNumber());
        profile_page_email.setText(dataManager.getCurrentUser().getEmail());
        profile_page_location.setText(dataManager.getCurrentUser().getBase());
        profile_page_role.setText(dataManager.getCurrentUser().getRole());

        Uri myUri = Uri.parse(dataManager.getCurrentUser().getProfileImgUrl());
        Glide.with(ProfilePageFragment.this)
                .load(myUri)
                .into(recipe_item_IMG_img);


    }

    private void findViews(View view) {
        profile_page_name = view.findViewById(R.id.profile_page_name);
        profile_page_role = view.findViewById(R.id.profile_page_role);
        profile_page_phone_number = view.findViewById(R.id.profile_page_phone_number);
        profile_page_email = view.findViewById(R.id.profile_page_email);
        profile_page_location = view.findViewById(R.id.profile_page_location);
        recipe_item_IMG_img = view.findViewById(R.id.recipe_item_IMG_img);
        profile_back_button=view.findViewById(R.id.profile_back_button);
        navController = Navigation.findNavController(view);
    }


}
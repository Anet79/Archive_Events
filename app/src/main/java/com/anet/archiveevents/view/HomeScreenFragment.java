package com.anet.archiveevents.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.anet.archiveevents.R;
import com.anet.archiveevents.adapters.PagerAdapterFoeDairyEvents;
import com.anet.archiveevents.objects.LandMark;
import com.anet.archiveevents.viewModel.AddEventViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;


public class HomeScreenFragment extends Fragment {
    private BottomNavigationView bottom_navigation;
    private NavController navController;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        findViews(view);

        bottom_navigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.home:




                        break;
                    case R.id.search:






                        break;
                        // add event flow
                    case R.id.add:
                        navController.navigate(R.id.action_homeScreenFragment_to_addEventFragment);







                        break;
                        // view profile page
                    case R.id.user_profile:
                        navController.navigate(R.id.action_homeScreenFragment_to_profilePageFragment);







                        break;
                    case R.id.news:



                        navController.navigate(R.id.action_homeScreenFragment_to_dairyEventsFragment2);






                        break;
                }

                return true;
            }
        });







    }

    private void findViews(View view) {
        bottom_navigation=view.findViewById(R.id.bottom_navigation);
        navController= Navigation.findNavController(view);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_screen, container, false);
    }
}
package com.anet.archiveevents.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.anet.archiveevents.EventItemClicked;
import com.anet.archiveevents.R;
import com.anet.archiveevents.adapters.EventsAdapter;
import com.anet.archiveevents.adapters.MapEventsAdapter;
import com.anet.archiveevents.adapters.PagerAdapterFoeDairyEvents;
import com.anet.archiveevents.objects.Event;
import com.anet.archiveevents.objects.GpsTracker;
import com.anet.archiveevents.objects.LandMark;
import com.anet.archiveevents.viewModel.AddEventViewModel;
import com.anet.archiveevents.viewModel.DairyEventViewModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;


public class HomeScreenFragment extends Fragment implements OnMapReadyCallback {
    private BottomNavigationView bottom_navigation;
    private NavController navController;
    private  LandMark newOne;
    private RecyclerView news_page_RECYC_reports_2;

    private GoogleMap mMap;
    private DairyEventViewModel dairyEventViewModel;
    private SearchView search_view;
    private MapEventsAdapter eventsAdapter;

    private GpsTracker gpsTracker;
    private FloatingActionButton location_home_screen;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dairyEventViewModel = new ViewModelProvider(this).get(DairyEventViewModel.class);


    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        findViews(view);

        search_view.clearFocus();

        search_view.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
//                dairyEventViewModel.getAllEventsSearchData().observe(getViewLifecycleOwner(), new Observer<ArrayList<Event>>() {
//                    @Override
//                    public void onChanged(ArrayList<Event> events) {
//                        // Update the adapter with the new data
//                        eventsAdapter.setEvents(events);
//
//                        //  eventsAdapter.notifyDataSetChanged();
//                    }
//
//
//
//
//                });
                dairyEventViewModel.filterList(newText);




                return true;
            }
        });











        SupportMapFragment mapFragment =(SupportMapFragment)getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        bottom_navigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.home:





                        break;
                    case R.id.search:
                        navController.navigate(R.id.action_homeScreenFragment_to_addComplineFragment);






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

        dairyEventViewModel.getHaveItemInList().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){

                   // news_page_RECYC_reports_2= view.findViewById(R.id.news_page_RECYC_reports_2);
                     eventsAdapter= new MapEventsAdapter();
                     eventsAdapter.setEvents(dairyEventViewModel.getAllEventsSearchData().getValue());

                    //eventsAdapter= dairyEventViewModel.getMapEventsAdapter();
                    news_page_RECYC_reports_2.setLayoutManager(new LinearLayoutManager(getContext()));
                    news_page_RECYC_reports_2.setHasFixedSize(true);
                    news_page_RECYC_reports_2.setItemAnimator(new DefaultItemAnimator());
                    news_page_RECYC_reports_2.setAdapter(eventsAdapter);
                }

            }
        });

        //navController = Navigation.findNavController(requireActivity(), R.id.action_dairyEventFragment_to_showEventFragment);

//        NavHostFragment navHostFragment =
//                (NavHostFragment) getParentFragmentManager().findFragmentById(R.id.nav_host_fragment);
//        NavController navController = navHostFragment.getNavController();

        // navController=Navigation.findNavController(view);



//        eventsAdapter.setEventsClickListener(new EventItemClicked() {
//            @Override
//            public void eventClicked(Event event, int position) {
//                dairyEventViewModel.setCurrentEventToDataManager(event.getEventUID());
//               // Navigation.findNavController(view).navigate(R.id.showEventFragment);
//                navController.navigate(R.id.action_dairyEventFragment_to_showEventFragment);
//
//            }
//
//        });

        initButtons();







    }

    private void initButtons() {

        location_home_screen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                gpsTracker = new GpsTracker(requireContext());
                if  (gpsTracker.canGetLocation()) {
                    newOne=  new LandMark(gpsTracker.getLatitude(),gpsTracker.getLongitude());
                } else {
                    gpsTracker.showSettingsAlert();
                   // newOne=new LandMark(33.33,32.33);
                }


            }
        });
    }

    private void filterList(String Text) {

    }

    private void findViews(View view) {
        bottom_navigation=view.findViewById(R.id.bottom_navigation);
        navController= Navigation.findNavController(view);
        search_view=view.findViewById(R.id.search_view);
        location_home_screen=view.findViewById(R.id.location_home_screen);
        news_page_RECYC_reports_2= view.findViewById(R.id.news_page_RECYC_reports_2);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_home_screen, container, false);




        return view;


    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap=googleMap;
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions()
                .position(sydney)
                .title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

    }
}
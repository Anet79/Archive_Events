package com.anet.archiveevents.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.anet.archiveevents.R;
import com.anet.archiveevents.adapters.EventsAdapter;
import com.anet.archiveevents.adapters.MapEventsAdapter;
import com.anet.archiveevents.objects.Event;
import com.anet.archiveevents.objects.GpsTracker;
import com.anet.archiveevents.objects.LandMark;
import com.anet.archiveevents.viewModel.DairyEventViewModel;
import com.anet.archiveevents.viewModel.SearchOnTheMapViewModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;


public class HomeScreenFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener,
        GoogleMap.OnMarkerDragListener,
        SeekBar.OnSeekBarChangeListener,
        GoogleMap.OnInfoWindowLongClickListener,
        GoogleMap.OnInfoWindowCloseListener, GoogleMap.OnMarkerClickListener {
    private BottomNavigationView bottom_navigation;
    private final List<Marker> mMarkerRainbow = new ArrayList<Marker>();
    private NavController navController;
    private Marker marker;

    private LandMark newOne;
    private RecyclerView news_page_RECYC_reports_2;
    private ArrayList<Marker> searchResultMarkers;
    private GoogleMap mMap;
    private DairyEventViewModel dairyEventViewModel;
    private SearchView search_view;
    private MapEventsAdapter eventsAdapter;
    private ArrayList<Event> eventsToShow;
    private GpsTracker gpsTracker;
    private FloatingActionButton location_home_screen;
    private SearchOnTheMapViewModel searchOnTheMapViewModel;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dairyEventViewModel = new ViewModelProvider(this).get(DairyEventViewModel.class);
        eventsToShow = new ArrayList<>();

    }
    @SuppressLint("MissingInflatedId")
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
              /*      case R.id.search:
                        navController.navigate(R.id.action_homeScreenFragment_to_searchOnTheMapFragment);


                        break;*/
                    // add event flow
                    case R.id.add:
                        navController.navigate(R.id.action_homeScreenFragment_to_addEventFragment);


                        break;
                    // view profile page
                    case R.id.user_profile:
                        navController.navigate(R.id.action_homeScreenFragment_to_profilePageFragment);


                        break;
                    case R.id.customer_support:
                        navController.navigate(R.id.action_homeScreenFragment_to_addComplineFragment);


                        break;
                    case R.id.news:


                        navController.navigate(R.id.action_homeScreenFragment_to_dairyEventsFragment2);


                        break;
                }

                return true;
            }
        });



        initButtons();

    }

    private void initButtons() {

        location_home_screen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                gpsTracker = new GpsTracker(requireContext());
                if (gpsTracker.canGetLocation()) {
                    newOne = new LandMark(gpsTracker.getLatitude(), gpsTracker.getLongitude());
                } else {
                    gpsTracker.showSettingsAlert();
                    // newOne=new LandMark(33.33,32.33);
                }


            }
        });
    }



    private void findViews(View view) {
        bottom_navigation = view.findViewById(R.id.bottom_navigation);
        navController = Navigation.findNavController(view);
        search_view = view.findViewById(R.id.search_view);
        location_home_screen = view.findViewById(R.id.location_home_screen);
        news_page_RECYC_reports_2 = view.findViewById(R.id.news_page_RECYC_reports_2);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_screen, container, false);

        searchOnTheMapViewModel = new ViewModelProvider(this).get(SearchOnTheMapViewModel.class);
        searchOnTheMapViewModel.getEventsSearch().observe(getViewLifecycleOwner(), new Observer<ArrayList<Event>>() {
            @Override
            public void onChanged(ArrayList<Event> events) {
                // Update the UI with the search results
                if (!events.isEmpty()) {
                    eventsToShow = events;
                    eventsAdapter = new MapEventsAdapter();
                    eventsAdapter.setEvents(events);

                    //eventsAdapter= dairyEventViewModel.getMapEventsAdapter();


                    //updateUI(eventsToShow);
                    onMapReady(mMap);

                }
            }


        });

        searchResultMarkers = new ArrayList<>();

        // Find the search view
        search_view = view.findViewById(R.id.search_view);

        // Find the map fragment and initialize the map
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);

        search_view.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchOnTheMapViewModel.performSearch(query);


                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Handle text changes in the search view if needed
                return false;
            }
        });
        mapFragment.getMapAsync(this);
        return view;


    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;


        // Add lots of markers to the map.
        addMarkersToMap();


        // Setting an info window adapter allows us to change the both the contents and look of the
        // info window.
        //mMap.setInfoWindowAdapter(new CustomInfoWindowAdapter());

        // Set listeners for marker events.  See the bottom of this class for their behavior.
        mMap.setOnInfoWindowClickListener(this);
        mMap.setOnMarkerDragListener(this);
        mMap.setOnInfoWindowCloseListener(this);
        mMap.setOnInfoWindowLongClickListener(this);



        // Create a LatLngBounds that includes the city of Adelaide in Australia.
        LatLng ADELAIDE = new LatLng(29.4965, 34.2675);
        LatLng ADELAIDE_1 = new LatLng(33.2774, 35.8761);

        LatLngBounds bounds = new LatLngBounds.Builder()
                .include(ADELAIDE).include(ADELAIDE_1).build();



        // Constrain the camera target to the Adelaide bounds.
        mMap.setLatLngBoundsForCameraTarget(bounds);
        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 50));
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ADELAIDE.getCenter(), 10));

        // Set the onMarkerClickListener
        mMap.setOnMarkerClickListener(this);

        news_page_RECYC_reports_2.setLayoutManager(new GridLayoutManager(requireContext(),3));
        news_page_RECYC_reports_2.setHasFixedSize(true);
        news_page_RECYC_reports_2.setItemAnimator(new DefaultItemAnimator());
        news_page_RECYC_reports_2.setAdapter(eventsAdapter);


    }

    private void addMarkersToMap() {

       if(marker!=null){
           marker.remove();
       }



        for (int i = 0; i < eventsToShow.size(); i++) {

            LatLng eventLocation = new LatLng(eventsToShow.get(i).getLandMark().getLatitude(), (eventsToShow.get(i).getLandMark().getLongitude()));
            double radius = 1000; // Radius in meters

            // A few more markers for good measure.
            MarkerOptions markerOptions = new MarkerOptions()
                    .position(eventLocation)
                    .title(eventsToShow.get(i).getTitle())
                    .snippet(eventsToShow.get(i).getEventDate());


            marker = mMap.addMarker(markerOptions);
            marker.setTag(eventsToShow.get(i));


            //  searchResultMarker = mMap.addMarker(markerOptions);
            //mMarkerRainbow.add(marker);\
            CircleOptions circleOptions = new CircleOptions()
                    .center(eventLocation)
                    .radius(radius)
                    .strokeWidth(2)
                    .strokeColor(Color.RED)
                    .fillColor(Color.argb(70, 255, 0, 0)); // Transparent red color

            Circle circle = mMap.addCircle(circleOptions);


        }
    }
    private boolean checkReady() {
        if (mMap == null) {
            Toast.makeText(requireContext(),"Not Ready", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (!checkReady()) {
            return;
        }
        float rotation = seekBar.getProgress();
        for (Marker marker : mMarkerRainbow) {
            marker.setRotation(rotation);
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @SuppressLint("ResourceType")
    @Override
    public void onInfoWindowClick(@NonNull Marker marker) {

        Fragment fragment = new ShowEventFragment();
        Event infoData = (Event) marker.getTag();
        dairyEventViewModel.setCurrentEventToDataManager(infoData.getEventUID());
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.map, fragment);
        search_view.setVisibility(View.INVISIBLE);
        location_home_screen.setVisibility(View.INVISIBLE);
        bottom_navigation.setVisibility(View.INVISIBLE);

        navController.navigate(R.id.homeScreenFragment);
        //fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        //fragmentTransaction.addToBackStack(null);


        Toast.makeText(requireContext(), "Click Info Window", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onInfoWindowClose(@NonNull Marker marker) {

    }

    @Override
    public void onInfoWindowLongClick(@NonNull Marker marker) {

    }

    @Override
    public boolean onMarkerClick(@NonNull Marker marker) {
        // below line is use to move our camera to the specific location.
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(), 10.0f));

        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                return null; // Return null to use the default info window layout
            }

            @Override
            public View getInfoContents(Marker marker) {
                // Create a custom view to show the search result information
                View infoView = getLayoutInflater().inflate(R.layout.content_event_for_home_screen, null);

                // Populate the view with the search result data
                TextView event_map_LBL_event_name = infoView.findViewById(R.id.event_map_LBL_event_name);
                TextView event_map_LBL_created_date= infoView.findViewById(R.id.event_map_LBL_created_date);
                TextView event_map_LBL_content = infoView.findViewById(R.id.event_map_LBL_content);

                Event infoData = (Event) marker.getTag();

                // Populate the views with the relevant search result data
                event_map_LBL_event_name.setText(infoData.getTitle());
                event_map_LBL_content.setText(infoData.getContent());
                event_map_LBL_created_date.setText(infoData.getEventDate());

                return infoView;
            }
        });

        marker.showInfoWindow();
        return true;
    }

    @Override
    public void onMarkerDrag(@NonNull Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(@NonNull Marker marker) {

    }

    @Override
    public void onMarkerDragStart(@NonNull Marker marker) {

    }
}
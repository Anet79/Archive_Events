package com.anet.archiveevents.view;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.anet.archiveevents.R;

import com.anet.archiveevents.objects.Event;
import com.anet.archiveevents.viewModel.SearchOnTheMapViewModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;


public class SearchOnTheMapFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener,
        GoogleMap.OnMarkerDragListener,
        SeekBar.OnSeekBarChangeListener,
        GoogleMap.OnInfoWindowLongClickListener,
        GoogleMap.OnInfoWindowCloseListener, GoogleMap.OnMarkerClickListener {

    private final List<Marker> mMarkerRainbow = new ArrayList<Marker>();
    private GoogleMap mMap;
    private SeekBar mRotationBar;
    private MapView mapView;
    private SearchView search_view;
    private ArrayList<Marker> searchResultMarkers;
    private Marker searchResultMarker;
    private ArrayList<Event> eventsToShow;
    private SearchOnTheMapViewModel searchOnTheMapViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        eventsToShow = new ArrayList<>();



    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_search_on_the_map, container, false);

        searchOnTheMapViewModel = new ViewModelProvider(this).get(SearchOnTheMapViewModel.class);
        searchOnTheMapViewModel.getEventsSearch().observe(getViewLifecycleOwner(), new Observer<ArrayList<Event>>() {
            @Override
            public void onChanged(ArrayList<Event> events) {
                // Update the UI with the search results
                if (!events.isEmpty()) {
                    eventsToShow = events;
                    //updateUI(eventsToShow);
                    onMapReady(mMap);

                }
            }


        });

        searchResultMarkers = new ArrayList<>();

        // Find the search view
        search_view = rootView.findViewById(R.id.search_view);
        mRotationBar = rootView.findViewById(R.id.rotationSeekBar);
        mRotationBar.setMax(360);
        mRotationBar.setOnSeekBarChangeListener(this);
        // Find the map fragment and initialize the map
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);

        search_view.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchOnTheMapViewModel.performSearch(newText);


                return true;
            }
        });
        mapFragment.getMapAsync(this);

        return rootView;
    }

    private void updateUI(ArrayList<Event> searchResults) {

        // searchResultMarkers=new ArrayList<>();
        for (Marker marker : searchResultMarkers) {
            marker.remove();
        }


        for (Event result : eventsToShow) {

            LatLng eventLocation = new LatLng(result.getLandMark().getLatitude(), (result.getLandMark().getLongitude()));
            MarkerOptions markerOptions = new MarkerOptions()
                    .position(eventLocation)
                    .title(result.getTitle());

            Marker marker = mMap.addMarker(markerOptions);
            marker.setTag(result); // Set the search result as the marker's tag
            searchResultMarkers.add(marker);
            searchResultMarker = mMap.addMarker(markerOptions);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(eventLocation, 40.0f));


        }

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

    }

    private void addMarkersToMap() {

//        for (Marker marker : searchResultMarkers) {
//            marker.remove();
//        }

        searchResultMarker.remove();

        for (int i = 0; i < eventsToShow.size(); i++) {

            LatLng eventLocation = new LatLng(eventsToShow.get(i).getLandMark().getLatitude(), (eventsToShow.get(i).getLandMark().getLongitude()));
            double radius = 1000; // Radius in meters

            // A few more markers for good measure.
            MarkerOptions markerOptions=new MarkerOptions()
                    .position(eventLocation)
                    .title(eventsToShow.get(i).getTitle())
                    .snippet(eventsToShow.get(i).getEventDate());


           Marker marker = mMap.addMarker(markerOptions);
            marker.setTag(eventsToShow.get(i));


            searchResultMarker = mMap.addMarker(markerOptions);
            //mMarkerRainbow.add(marker);\
            CircleOptions circleOptions = new CircleOptions()
                    .center(eventLocation)
                    .radius(radius)
                    .strokeWidth(2)
                    .strokeColor(Color.RED)
                    .fillColor(Color.argb(70, 255, 0, 0)); // Transparent red color

            Circle circle = mMap.addCircle(circleOptions);





        }

        // Creates a marker rainbow demonstrating how to create default marker icons of different
        // hues (colors).
        float rotation = mRotationBar.getProgress();
        //boolean flat = mFlatBox.isChecked();

        int numMarkersInRainbow = 1;
        for (int i = 0; i < numMarkersInRainbow; i++) {
            Marker marker = mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(
                            -30 + 10 * Math.sin(i * Math.PI / (numMarkersInRainbow - 1)),
                            135 - 10 * Math.cos(i * Math.PI / (numMarkersInRainbow - 1)))).title("Marker " + i)
                    .icon(BitmapDescriptorFactory.defaultMarker(i * 360 / numMarkersInRainbow))
                    .rotation(rotation));
            mMarkerRainbow.add(marker);
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

    @Override
    public void onInfoWindowClose(@NonNull Marker marker) {

    }

    @Override
    public void onInfoWindowLongClick(@NonNull Marker marker) {

    }

    @Override
    public void onInfoWindowClick(@NonNull Marker marker) {


        Toast.makeText(requireContext(), "Click Info Window", Toast.LENGTH_SHORT).show();

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


}
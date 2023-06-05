package com.anet.archiveevents;

import com.anet.archiveevents.objects.Event;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public interface onMapReady {

    void onMap(GoogleMap googleMap, ArrayList<Event>allEventOnMap);

}

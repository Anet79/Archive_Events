package com.anet.archiveevents.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.anet.archiveevents.EventItemClicked;
import com.anet.archiveevents.R;
import com.anet.archiveevents.adapters.EventsAdapter;
import com.anet.archiveevents.adapters.PagerAdapterFoeDairyEvents;
import com.anet.archiveevents.objects.Event;
import com.anet.archiveevents.viewModel.DairyEventViewModel;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class DairyEventsMyEventsFragment extends Fragment {

    private RecyclerView news_page_RECYC_reports;
    private DairyEventViewModel dairyEventViewModel;
    private EventsAdapter eventsAdapter;
    private PagerAdapterFoeDairyEvents pagerAdapterFoeDairyEvents;
    private String arrayType;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        dairyEventViewModel = new ViewModelProvider(this).get(DairyEventViewModel.class);
        //dairyEventViewModel.loadAllEvents();
        dairyEventViewModel.getAllMyEventsData().observe(getViewLifecycleOwner(), new Observer<ArrayList<Event>>() {
            @Override
            public void onChanged(ArrayList<Event> events) {
                // Update the adapter with the new data
                eventsAdapter.setEvents(events);

//                eventsAdapter.notifyDataSetChanged();
            }


        });
        eventsAdapter.setEventClickListener(new EventItemClicked() {
            @Override
            public void eventClicked(Event event, int position) {
                dairyEventViewModel.setCurrentEventToDataManager(event.getEventUID());
                Navigation.findNavController(view).navigate(R.id.showEventFragment);


            }

        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_dairy_events_my_events, container, false);
        findViews(view);
        eventsAdapter= new EventsAdapter();
        news_page_RECYC_reports.setLayoutManager(new GridLayoutManager(this.getContext(),4));
        news_page_RECYC_reports.setHasFixedSize(true);
        news_page_RECYC_reports.setItemAnimator(new DefaultItemAnimator());


        news_page_RECYC_reports.setAdapter(eventsAdapter);
        return view;
    }

    private void findViews(View view) {

        news_page_RECYC_reports= view.findViewById(R.id.news_page_RECYC_reports);
    }
}

package com.anet.archiveevents.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.anet.archiveevents.EventItemClicked;
import com.anet.archiveevents.R;
import com.anet.archiveevents.adapters.EventsAdapter;
import com.anet.archiveevents.objects.Event;
import com.anet.archiveevents.viewModel.AuthViewModel;
import com.anet.archiveevents.viewModel.DairyEventViewModel;
import com.anet.archiveevents.viewModel.SearchOnTheMapViewModel;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;


public class DairyEventFragment extends Fragment  {

    private RecyclerView news_page_RECYC_reports_2;

    private DairyEventViewModel dairyEventViewModel;
    private EventsAdapter eventsAdapter;
    private NavController navController;
    private SearchView dairy_event_search_view;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        news_page_RECYC_reports_2= view.findViewById(R.id.news_page_RECYC_reports_2);
        //navController = Navigation.findNavController(requireActivity(), R.id.action_dairyEventFragment_to_showEventFragment);

        NavHostFragment navHostFragment =
                (NavHostFragment) getParentFragmentManager().findFragmentById(R.id.nav_host_fragment);
        NavController navController = navHostFragment.getNavController();

       // navController=Navigation.findNavController(view);

        eventsAdapter= new EventsAdapter();
        news_page_RECYC_reports_2.setLayoutManager(new GridLayoutManager(this.getContext(),3));
        news_page_RECYC_reports_2.setHasFixedSize(true);
        news_page_RECYC_reports_2.setItemAnimator(new DefaultItemAnimator());

        // news_page_RECYC_reports_1.setAdapter(eventsAdapter);
        news_page_RECYC_reports_2.setAdapter(eventsAdapter);
        dairyEventViewModel = new ViewModelProvider(this).get(DairyEventViewModel.class);
        //dairyEventViewModel.loadAllEvents();
        dairyEventViewModel.getAllEventsData().observe(getViewLifecycleOwner(), new Observer<ArrayList<Event>>() {
            @Override
            public void onChanged(ArrayList<Event> events) {
                // Update the adapter with the new data
                eventsAdapter.setEvents(events);

              //  eventsAdapter.notifyDataSetChanged();
            }




        });

        dairyEventViewModel.getAllEventsSearchData().observe(getViewLifecycleOwner(), new Observer<ArrayList<Event>>() {
            @Override
            public void onChanged(ArrayList<Event> searchEvents) {

                if(!searchEvents.isEmpty()){
                    eventsAdapter.setFilteredList(searchEvents);
                }

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

    private void findViews(View view) {
        news_page_RECYC_reports_2= view.findViewById(R.id.news_page_RECYC_reports_2);




    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_dairy_event, container, false);
      //  findViews(view);


        dairy_event_search_view = view.findViewById(R.id.dairy_event_search_view);

        dairy_event_search_view.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                dairyEventViewModel.filterList(newText);
                return true;
            }
        });



       // dairyEventViewModel.loadAllEvents();
        return view;
    }


//    @Override
//    public void eventClicked(Event event, int position) {
//        eventsAdapter.notifyDataSetChanged();
//    }
}
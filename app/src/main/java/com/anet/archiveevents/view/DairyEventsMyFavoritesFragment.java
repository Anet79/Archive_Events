package com.anet.archiveevents.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.anet.archiveevents.R;
import com.anet.archiveevents.adapters.EventsAdapter;
import com.anet.archiveevents.viewModel.DairyEventViewModel;

public class DairyEventsMyFavoritesFragment extends Fragment {


    private RecyclerView news_page_RECYC_reports;
    private DairyEventViewModel dairyEventViewModel;
    private EventsAdapter eventsAdapter;
    private String arrayType;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



//        dairyEventViewModel = new ViewModelProvider(this).get(DairyEventViewModel.class);
//        dairyEventViewModel.getAllMyEventsData().observe(getViewLifecycleOwner(), events -> {
//            // Update the adapter with the new data
//            eventsAdapter.setEvents(events);
//        });


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_dairy_event_my_favorite, container, false);
        findViews(view);

        news_page_RECYC_reports.setLayoutManager(new LinearLayoutManager(this.getContext()));
        news_page_RECYC_reports.setHasFixedSize(true);

        eventsAdapter= new EventsAdapter();
        news_page_RECYC_reports.setAdapter(eventsAdapter);
        return view;
    }

    private void findViews(View view) {

        news_page_RECYC_reports= view.findViewById(R.id.news_page_RECYC_reports);
    }
}

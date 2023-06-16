package com.anet.archiveevents.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.anet.archiveevents.R;
import com.anet.archiveevents.adapters.EventsAdapter;
import com.anet.archiveevents.adapters.MediaAdapter;
import com.anet.archiveevents.objects.Event;
import com.anet.archiveevents.viewModel.ShowEventViewModel;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

public class ShowEventFragment extends Fragment {

    private TextView show_event_view_location;
    private TextView show_event_view_category;
    private TextView show_event_view_area;
    private TextView show_event_view_details;

    private TextView show_event_view_title;
    private MaterialButton show_event_view_BTN_save;
    private ShowEventViewModel showEventViewModel;
    private NavController navController;
    private MediaAdapter mediaAdapter;
    private RecyclerView show_event_RYC_all_media;
    private ArrayList<String> allMediaForOneEvent;




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mediaAdapter=new MediaAdapter();
        show_event_RYC_all_media.setLayoutManager(new LinearLayoutManager(getContext()));
        show_event_RYC_all_media.setHasFixedSize(true);
        show_event_RYC_all_media.setItemAnimator(new DefaultItemAnimator());
        show_event_RYC_all_media.setAdapter(mediaAdapter);



        showEventViewModel=  new ViewModelProvider(this).get(ShowEventViewModel.class);
        showEventViewModel.getGetSpecificEvent().observe(this, new Observer<Event>() {
            @Override
            public void onChanged(Event event) {



                show_event_view_category.setText(event.getCategory());
                show_event_view_area.setText(event.getArea());
                show_event_view_details.setText(event.getContent());
                show_event_view_location.setText(event.getLandMark().toString());
                show_event_view_title.setText(event.getTitle());




                mediaAdapter.setMedia(event.getListOfMedia());


            }

        });

    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViews(view);

        navController= Navigation.findNavController(view);
        mediaAdapter=new MediaAdapter();



        show_event_view_BTN_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {





            }
        });



    }




    private void findViews(View view) {
        show_event_view_location=view.findViewById(R.id.show_event_view_location);
        show_event_view_category=view.findViewById(R.id.show_event_view_category);
        show_event_view_area=view.findViewById(R.id.show_event_view_area);
        show_event_view_details=view.findViewById(R.id.show_event_view_details);
        show_event_view_title=view.findViewById(R.id.show_event_view_title);
        show_event_view_BTN_save=view.findViewById(R.id.show_event_view_BTN_save);
        show_event_RYC_all_media= view.findViewById(R.id.show_event_RYC_all_media);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v= inflater.inflate(R.layout.fragment_show_event, container, false);

        // Inflate the layout for this fragment
        return v;
    }
}
package com.anet.archiveevents.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.anet.archiveevents.R;
import com.anet.archiveevents.objects.Event;
import com.anet.archiveevents.viewModel.ShowEventViewModel;
import com.google.android.material.button.MaterialButton;

public class ShowEventFragment extends Fragment {

    private TextView show_event_view_location;
    private TextView show_event_view_category;
    private TextView show_event_view_area;
    private TextView show_event_view_details;
    private MaterialButton show_event_view_BTN_save;
    private ShowEventViewModel showEventViewModel;
    private NavController navController;




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        showEventViewModel=  new ViewModelProvider(this).get(ShowEventViewModel.class);
        showEventViewModel.getGetSpecificEvent().observe(this, new Observer<Event>() {
            @Override
            public void onChanged(Event event) {
                //show_event_view_location.setText(event.g());
                show_event_view_category.setText(event.getCategory());
                show_event_view_area.setText(event.getArea());
                show_event_view_details.setText(event.getContent());

            }

        });

    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViews(view);
        navController= Navigation.findNavController(view);

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
        show_event_view_BTN_save=view.findViewById(R.id.show_event_view_BTN_save);


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_show_event, container, false);
    }
}
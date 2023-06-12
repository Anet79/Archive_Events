package com.anet.archiveevents.adapters;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.anet.archiveevents.EventItemClicked;
import com.anet.archiveevents.R;
import com.anet.archiveevents.firebase.DataManager;
import com.anet.archiveevents.objects.Event;

import java.util.ArrayList;

public class MapEventsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Event> events ;
    private EventItemClicked eventsClickListener;
    private DataManager dataManager=DataManager.getInstance();


    public MapEventsAdapter() {
        this.events = new ArrayList<>();
    }

    public void setEventsClickListener(EventItemClicked eventsClickListener) {
        this.eventsClickListener = eventsClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_event_for_home_screen, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
     ListViewHolder listViewHolder = (ListViewHolder) holder;
        Event event = getEvent(position);

        listViewHolder.event_map_LBL_event_name.setText(String.format("%s",event.getTitle()));
        //listViewHolder.event_map_LBL_created_date.setText(String.format("%s",event.getTitle()));
        listViewHolder.event_map_LBL_content.setText(String.format("%s",event.getContent()));





    }
    public Event getEvent(int position){
        return events.get(position);
    }

    @Override
    public int getItemCount() {
        return  events.size();
    }

    public void setEvents(ArrayList<Event> events) {
        this.events = events;
        notifyDataSetChanged();
    }

    private class ListViewHolder extends RecyclerView.ViewHolder {

        public TextView event_map_LBL_event_name;
        public TextView event_map_LBL_created_date;
        public TextView event_map_LBL_content;



        public ListViewHolder(View eventView) {
            super(eventView);
            this.event_map_LBL_event_name = eventView.findViewById(R.id.event_map_LBL_event_name);
            //this.event_map_LBL_created_date= eventView.findViewById(R.id.event_map_LBL_created_date);
            this.event_map_LBL_content = eventView.findViewById(R.id.event_map_LBL_content);

            eventView.setOnClickListener(v -> eventsClickListener.eventClicked(getEvent(getAdapterPosition()), getAdapterPosition()));

        }
    }
}

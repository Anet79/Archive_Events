package com.anet.archiveevents.adapters;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.anet.archiveevents.EventItemClicked;
import com.anet.archiveevents.Keys;
import com.anet.archiveevents.R;
import com.anet.archiveevents.firebase.DataManager;
import com.anet.archiveevents.objects.Event;
import com.anet.archiveevents.view.DairyEventFragment;
import com.anet.archiveevents.view.ProfilePageFragment;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class EventsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context activity;
    private ArrayList<Event> events ;
    private int totalCountImage= 3;
    private EventItemClicked eventsClickListener;
    public int[] imageViews= {
        R.drawable.picture1,
                R.drawable.picture2,
            R.drawable.picture3
    };
    private DataManager dataManager=DataManager.getInstance();

    public EventsAdapter() {
        this.events=new ArrayList<>();



    }

    public void setEvents(final ArrayList<Event> events) {
      //  this.events.clear();
        this.events = events;
     notifyDataSetChanged();
    }

    public void setFilteredList(ArrayList<Event> filteredList){
        this.events = filteredList;
        notifyDataSetChanged();
    }

    public EventsAdapter setEventClickListener(EventItemClicked eventsClickListener) {
        this.eventsClickListener = eventsClickListener;
        return this;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_report_view, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        ListViewHolder listViewHolder = (ListViewHolder) holder;
        Event event = getEvent(position);

        int index = (int)(Math.random() * totalCountImage);


        listViewHolder.report_page_category.setText(String.format("%s",event.getCategory()));
        listViewHolder.report_page_created_date.setText(String.format("%s",event.getEventDate()));
        listViewHolder.report_page_report_header.setText(String.format("%s",event.getTitle()));
        listViewHolder.report_page_content_event.setText(String.format("%s",event.getContent()));

        listViewHolder.content_report_IMG_image_report.setImageResource(imageViews[index]);








//        for (int i = 0; i < picUri.size(); i++)
//        {
//            // generating the index using Math.random()
//            int index = (int)(Math.random() * my_list.size());
//
//            System.out.println("Random Element is :"
//                    + my_list.get(index));
//        }
        Uri myUri = Uri.parse(dataManager.getCurrentUser().getProfileImgUrl());
//        Glide.with(this.)
//                .load(myUri)
//                .into( listViewHolder.report_page_IMG_user);

        //listViewHolder.list_file_IMG_icon.setImageResource(R.drawable.ic_pdf);

    }
    public Event getEvent(int position){
        return events.get(position);
    }

    @Override
    public int getItemCount() {
        return events.size();
    }



    private class ListViewHolder extends RecyclerView.ViewHolder {

        public TextView report_page_category;
        public TextView report_page_report_header;
        public TextView report_page_content_event;
        public TextView report_page_name;
        public TextView report_page_created_date;
        public  ImageView content_report_IMG_image_report;



        public ListViewHolder(View eventView) {
            super(eventView);
            this.report_page_category = eventView.findViewById(R.id.report_page_category);
            this.report_page_created_date=eventView.findViewById(R.id.report_page_created_date);
            this.report_page_report_header= eventView.findViewById(R.id.report_page_report_header);
            this.report_page_content_event= eventView.findViewById(R.id.report_page_content_event);
//            this.report_page_name= eventView.findViewById(R.id.report_page_name);
           this.content_report_IMG_image_report= eventView.findViewById(R.id.content_report_IMG_image_report);

            eventView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    eventsClickListener.eventClicked(getEvent(getAdapterPosition()), getAdapterPosition());


                }
            });

        }
    }
}




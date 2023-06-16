package com.anet.archiveevents.adapters;

import static com.google.common.io.Files.getFileExtension;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.anet.archiveevents.EventItemClicked;
import com.anet.archiveevents.MediaItemClicked;
import com.anet.archiveevents.R;
import com.anet.archiveevents.firebase.DataManager;
import com.anet.archiveevents.objects.Event;
import com.google.common.net.MediaType;

import java.util.ArrayList;

public class MediaAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<String> uriList;
    private MediaItemClicked eventsClickListener;
    private DataManager dataManager=DataManager.getInstance();


    public MediaAdapter() {
        this.uriList= new ArrayList<>();
    }

    public void setMedia(final ArrayList<String> uriList) {
        //  this.events.clear();
       this.uriList = uriList;

        notifyDataSetChanged();
    }

//    public void setFilteredList(ArrayList<String> filteredList){
//        this.events = filteredList;
//        notifyDataSetChanged();
//    }

    public MediaAdapter setEventClickListener(MediaItemClicked eventsClickListener) {
        this.eventsClickListener = eventsClickListener;
        return this;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_media, parent, false);
        return new MediaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MediaViewHolder listViewHolder = (MediaViewHolder) holder;
        String uri = uriList.get(position);
        MediaType mediaType = getMediaType(uri);

        // Bind data to views based on media type
        if (mediaType == MediaType.ANY_IMAGE_TYPE) {
            String fileName = getFileName(uri);
            listViewHolder.content_media_LBL_media_name.setText(fileName);

            listViewHolder.content_media_IMG_picture.setImageResource(R.drawable.ic_picture);

        } else if (mediaType == MediaType.ANY_VIDEO_TYPE) {
            listViewHolder.content_media_IMG_picture.setImageResource(R.drawable.ic_video);
            String fileName = getFileName(uri);
            listViewHolder.content_media_LBL_media_name.setText(fileName);

//            Uri videoUri = Uri.parse(uri);
//            holder.videoView.setVideoURI(videoUri);
//            holder.videoView.start();
        } else if (mediaType == MediaType.PDF) {
            listViewHolder.content_media_IMG_picture.setImageResource(R.drawable.ic_pdf);

            // Display PDF file name
            String fileName = getFileName(uri);
            listViewHolder.content_media_LBL_media_name.setText(fileName);
        }
        else {
            String fileName = getFileName(uri);
            listViewHolder.content_media_IMG_picture.setImageResource(R.drawable.ic_video);
            listViewHolder.content_media_LBL_media_name.setText(fileName);
        }


    }

    private MediaType getMediaType(String uri) {
        String fileExtension = getFileExtension(uri);
        if (fileExtension.equalsIgnoreCase("jpg") || fileExtension.equalsIgnoreCase("jpeg") || fileExtension.equalsIgnoreCase("png")) {
            return MediaType.ANY_IMAGE_TYPE;
        } else if (fileExtension.equalsIgnoreCase("mp4") || fileExtension.equalsIgnoreCase("avi") || fileExtension.equalsIgnoreCase("mkv")) {
            return MediaType.ANY_VIDEO_TYPE;
        } else if (fileExtension.equalsIgnoreCase("pdf")) {
            return MediaType.PDF;
        }
        return MediaType.ANY_AUDIO_TYPE;
    }

    private String getFileExtension(String uri) {
        String fileExtension = "";
        int extensionIndex = uri.lastIndexOf(".");
        if (extensionIndex != -1) {
            fileExtension = uri.substring(extensionIndex + 1);
        }
        return fileExtension;
    }

    private String getFileName(String uri) {
        String fileName = "";
        int lastIndex = uri.lastIndexOf("/");
        if (lastIndex != -1) {
            fileName = uri.substring(lastIndex + 1);
        }
        return fileName;
    }

//    public Event getEvent(int position){
//        return events.get(position);
//    }



    @Override
    public int getItemCount() {
        return uriList.size();
    }


    public String getMedia(int position){
        return uriList.get(position);
    }

    private class MediaViewHolder  extends RecyclerView.ViewHolder {

        public TextView content_media_LBL_media_name;
        public ImageView content_media_IMG_picture;


        public MediaViewHolder (View mediaView) {
            super(mediaView);

            this.content_media_LBL_media_name= mediaView.findViewById(R.id.content_media_LBL_media_name);
            this.content_media_IMG_picture= mediaView.findViewById(R.id.content_media_IMG_picture);

            mediaView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    eventsClickListener.mediaClicked(getMedia(getAdapterPosition()), getAdapterPosition());


                }
            });

        }
    }
}

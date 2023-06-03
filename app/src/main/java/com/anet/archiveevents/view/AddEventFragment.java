package com.anet.archiveevents.view;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;


import com.anet.archiveevents.R;
import com.anet.archiveevents.objects.LandMark;
import com.anet.archiveevents.viewModel.AddEventViewModel;


public class AddEventFragment extends Fragment {
    private RecyclerView mRecyclerView;
    //private RecyclerAdapt

    private EditText add_event_EDT_category;
    private EditText add_event_EDT_location;
    private EditText add_event_EDT_title;
    private NavController navController;

    private EditText add_event_EDT_details;
    private Button add_event_BTN_save;
    private AddEventViewModel addEventViewModel;
    private LinearLayout add_event_LRT_upload;
  //  private static final int REQUEST_CODE_PICK_VIDEO = 1;
    private static final int READ_EXTERNAL_STORAGE = 1;

    private static final int REQUEST_CODE_PERMISSION = 2;
    private static final int REQUEST_CODE_PICK_MEDIA = 1;





    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        findViews(view);

        add_event_BTN_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the details entered by the user
                String title = add_event_EDT_title.getText().toString();

                String content = add_event_EDT_details.getText().toString();
                LandMark newOne=new LandMark(33.33,32.33);
                String category = add_event_EDT_category.getText().toString();

                if(!content.isEmpty()&&!title.isEmpty()&&!category.isEmpty()){
                    addEventViewModel.saveEvent(title,category,newOne,content);
                }

            }
        });
        add_event_LRT_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  checkPermissionAndPickVideo();
                pickVideo();

            }
        });
        addEventViewModel = new ViewModelProvider(this).get(AddEventViewModel.class);
        addEventViewModel.getUploadStatusLiveData().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean uploadSuccessful) {
                if (uploadSuccessful) {
                    Toast.makeText(requireContext(), "Video uploaded successfully!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(requireContext(), "Failed to upload video!", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }


    private void findViews(View view) {
        add_event_EDT_category = view.findViewById(R.id.add_event_EDT_category);
        add_event_EDT_location = view.findViewById(R.id.add_event_EDT_location);
        add_event_EDT_title = view.findViewById(R.id.add_event_EDT_title);
        add_event_EDT_details = view.findViewById(R.id.add_event_EDT_details);
        add_event_BTN_save = view.findViewById(R.id.add_event_BTN_save);
        add_event_LRT_upload=view.findViewById(R.id.add_event_LRT_upload);
        navController = Navigation.findNavController(view);

    }



    private void pickVideo() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("video/*,image/*");
        startActivityForResult(intent, REQUEST_CODE_PICK_MEDIA);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                pickVideo();
            } else {
                Toast.makeText(requireContext(), "Permission denied!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_PICK_MEDIA && resultCode == RESULT_OK) {
            Uri videoUri = data.getData();
            if (videoUri != null) {
                addEventViewModel.uploadVideo(videoUri);
            } else {
                Toast.makeText(requireContext(), "Failed to retrieve video!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_event, container, false);
    }
}
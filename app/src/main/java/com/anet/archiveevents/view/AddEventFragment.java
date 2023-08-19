package com.anet.archiveevents.view;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.RecyclerView;


import com.anet.archiveevents.R;
import com.anet.archiveevents.objects.GpsTracker;
import com.anet.archiveevents.objects.LandMark;
import com.anet.archiveevents.viewModel.AddEventViewModel;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

import java.util.Objects;

import java.util.ArrayList;
import java.util.List;


public class AddEventFragment extends Fragment {
    private RecyclerView mRecyclerView;
    //private RecyclerAdapt

    private EditText add_event_EDT_category;
    private EditText add_event_EDT_location;
    private EditText add_event_EDT_title;
    private NavController navController;
    private EditText add_event_EDT_area;
    private EditText add_event_EDT_details;
    private Button add_event_BTN_save;

    private ImageButton back_button;
    private FloatingActionButton add_event_location;
    private AddEventViewModel addEventViewModel;
    private LinearLayout add_event_LRT_upload;
  //  private static final int REQUEST_CODE_PICK_VIDEO = 1;
    private static final int READ_EXTERNAL_STORAGE = 1;
    private LandMark newOne;
    private static final int REQUEST_CODE_PERMISSION = 2;
   private static final int PICK_IMAGE_MULTIPLE = 1;
    private static final int FILE_PICKER_REQUEST_CODE = 1;
    private GpsTracker gpsService;
    private BottomNavigationView bottom_navigation;

   private MaterialToolbar topAppBar;






    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // getActivity().setContentView(R.layout.fragment_add_event);




    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        findViews(view);


        add_event_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                gpsService = new GpsTracker(getContext());
                if  (gpsService.canGetLocation()) {
                    newOne=  new LandMark(gpsService.getLatitude(),gpsService.getLongitude());
                } else {
                    gpsService.showSettingsAlert();
                    newOne=new LandMark(33.33,32.33);
                }

            }
        });

        add_event_BTN_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the details entered by the user
                String title = add_event_EDT_title.getText().toString();
                String area= add_event_EDT_area.getText().toString();
                String content = add_event_EDT_details.getText().toString();
                String category = add_event_EDT_category.getText().toString();
                //LandMark newOne=new LandMark(33.33,32.33);





                if(!content.isEmpty()&&!title.isEmpty()&&!category.isEmpty()){

                    addEventViewModel.saveEvent(title,category,newOne,content,area);
                    addEventViewModel.getAddCompleteEventMutableLiveData().observe(getActivity(), new Observer<Boolean>() {
                        @Override
                        public void onChanged(Boolean aBoolean) {
                            if(aBoolean){
                                showSeccessDialog(view);
                            }else {
                                showErrorDialog(view);
                            }
                        }
                    });

                }else{
                    showErrorDialog(view);
                }


            }

            private void showErrorDialog(View view) {

                ConstraintLayout errorConstraintLayout=getActivity().findViewById(R.id.errorConstraintLayout);
                View view1=LayoutInflater.from(getContext()).inflate(R.layout.error_dialog,errorConstraintLayout);
                Button errorDone= view1.findViewById(R.id.errorDone);

                AlertDialog.Builder builder=  new AlertDialog.Builder(getContext());
                builder.setView(view1);
                final  AlertDialog alertDialog=builder.create();

                errorDone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                        Toast.makeText(getContext(), "Done", Toast.LENGTH_SHORT).show();
                        navController.popBackStack();
                    }
                });

                if(alertDialog.getWindow() != null){
                    alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                }
                alertDialog.show();
            }

            private void showSeccessDialog(View view) {
                ConstraintLayout successConstraintLayout=getActivity().findViewById(R.id.successConstraintLayout);
                View view1=LayoutInflater.from(getContext()).inflate(R.layout.success_dialog,successConstraintLayout);
                Button successDone= view1.findViewById(R.id.successDone);

                AlertDialog.Builder builder=  new AlertDialog.Builder(getContext());
                builder.setView(view1);
                final  AlertDialog alertDialog=builder.create();

                successDone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                        Toast.makeText(getContext(), "Done", Toast.LENGTH_SHORT).show();
                        navController.popBackStack();
                    }
                });

                if(alertDialog.getWindow() != null){
                    alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                }
                alertDialog.show();
            }
        });
        add_event_LRT_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  checkPermissionAndPickVideo();
                pickVideo();

            }
        });

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  checkPermissionAndPickVideo();
                /*navController.navigate(R.id.action_addEventFragment_to_homeScreenFragment);
                getActivity().finish();*/
                navController.popBackStack();

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

    private void onBackPressed() {

    }


    private void findViews(View view) {
        add_event_EDT_category = view.findViewById(R.id.add_event_EDT_category);

        add_event_EDT_title = view.findViewById(R.id.add_event_EDT_title);
        add_event_EDT_details = view.findViewById(R.id.add_event_EDT_details);
        add_event_BTN_save = view.findViewById(R.id.add_event_BTN_save);
        add_event_LRT_upload=view.findViewById(R.id.add_event_LRT_upload);
        add_event_EDT_area=view.findViewById(R.id.add_event_EDT_area);
        add_event_location=view.findViewById(R.id.add_event_location);
        back_button = view.findViewById(R.id.back_button);
        navController = Navigation.findNavController(view);

    }



    private void pickVideo() {

        // initialising intent
        Intent intent = new Intent();

        // setting type to select to be image
        intent.setType("*/*");

        // allowing multiple image to be selected
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_PICK);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_MULTIPLE);
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
        if (requestCode == PICK_IMAGE_MULTIPLE && resultCode == RESULT_OK && null != data) {
            ArrayList<Uri>mArrayUri=new ArrayList<>();
            // Get the Image from data
            if (data.getClipData() != null) {
                ClipData mClipData = data.getClipData();
                int cout = data.getClipData().getItemCount();
                for (int i = 0; i < cout; i++) {
                    // adding imageuri in array
                    Uri imageurl = data.getClipData().getItemAt(i).getUri();
                    mArrayUri.add(imageurl);
                }
                // setting 1st selected image into image switcher
//                imageView.setImageURI(mArrayUri.get(0));
//                position = 0;
            } else {
                Uri imageurl = data.getData();
                mArrayUri.add(imageurl);
//                imageView.setImageURI(mArrayUri.get(0));
//                position = 0;

            }
            addEventViewModel.upLoadsFiles(mArrayUri);

        } else {
            // show this if no image is selected
            Toast.makeText(getContext(), "You haven't picked Image", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_event, container, false);
    }
}
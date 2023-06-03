package com.anet.archiveevents.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.anet.archiveevents.Keys;
import com.anet.archiveevents.R;
import com.anet.archiveevents.firebase.DataManager;
import com.anet.archiveevents.viewModel.UserDetailsForSignupViewModel;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserDetailsFragment extends Fragment {


    private TextInputLayout user_details_EDT_user_full_name;
    private TextInputLayout user_details_EDT_user_rank;
    private TextInputLayout user_details_EDT_user_phone_number;
    private TextInputLayout user_details_EDT_user_role;
    private TextInputLayout user_details_EDT_user_base;
    private FloatingActionButton user_details_FAB_profile_pic;
    private MaterialButton user_details_BTN_accept;
    private UserDetailsForSignupViewModel userDetailsForSignupViewModel;
    private NavController navController;
    private CircleImageView user_details_IMG_user;
    private byte[] bytes;
    private DataManager dataManager;
    private String myDownloadUri;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataManager= DataManager.getInstance();

        userDetailsForSignupViewModel = new ViewModelProvider(this).get(UserDetailsForSignupViewModel.class);

        userDetailsForSignupViewModel.getAddUserStatus().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    navController.navigate(R.id.action_userDetailsFragment_to_homeScreenFragment);
                }
            }
        });


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews(view);
        initButtons();

    }

    private void initButtons() {

        user_details_IMG_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choseCover();
            }
        });

        user_details_FAB_profile_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choseCover();
            }
        });


//TODO - init profile pic (finish what we started)
        user_details_BTN_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullName = user_details_EDT_user_full_name.getEditText().getText().toString();
                String rank = user_details_EDT_user_rank.getEditText().getText().toString();
                String phoneNumber = user_details_EDT_user_phone_number.getEditText().getText().toString();
                String role = user_details_EDT_user_role.getEditText().getText().toString();
                String base = user_details_EDT_user_base.getEditText().getText().toString();


                if (myDownloadUri != null && !fullName.isEmpty() && !rank.isEmpty() && !phoneNumber.isEmpty() && !role.isEmpty() && !base.isEmpty()) {
                    userDetailsForSignupViewModel.addUserDetails(fullName, rank, phoneNumber, role, base, myDownloadUri);
                } else {
                  //  Toast.makeText(this, "Error: Null Data Received", Toast.LENGTH_SHORT).show();
                }
            }





        });
    }

    private void choseCover() {
        ImagePicker.with(this)
                .compress(1024)            //Final image size will be less than 1 MB(Optional)
                .crop(2f, 1f)
                .maxResultSize(1080, 1080)
                //Final image resolution will be less than 1080 x 1080(Optional)
                .start();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //View Indicates the process of the image uploading by Disabling the button
        user_details_FAB_profile_pic.setEnabled(false);

        //Reference to the exact path where we want the image to be store in Storage

        StorageReference userRef = dataManager.getStorage()
                .getReference()
                .child(Keys.KEY_PROFILE_PICTURES)
                .child(dataManager.getFirebaseAuth().getCurrentUser().getUid());
        //Get URI Data and place it in ImageView
        Uri uri = data.getData();
        user_details_IMG_user.setImageURI(uri);

        //Get the data from an ImageView as bytes
        user_details_IMG_user.setDrawingCacheEnabled(true);
        user_details_IMG_user.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) user_details_IMG_user.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] bytes = baos.toByteArray();

        //Start The upload task
        UploadTask uploadTask = userRef.putBytes(bytes);
        uploadTask.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()) {
                    //If upload was successful, We want to get the image url from the storage
                    userRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            //Set the profile URL to the object we created
                            myDownloadUri = uri.toString();


                            //View Indicates the process of the image uploading Done by making the button Enabled
                            user_details_FAB_profile_pic.setEnabled(true);
                        }
                    });
                }
            }
        });

    }


//    @Override
//    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        //View Indicates the process of the image uploading by Disabling the button
//        user_details_FAB_profile_pic.setEnabled(false);
//
//        //Get URI Data and place it in ImageView
//        Uri uri = data.getData();
//        user_details_IMG_user.setImageURI(uri);
//
//        //Get the data from an ImageView as bytes
//        user_details_IMG_user.setDrawingCacheEnabled(true);
//        user_details_IMG_user.buildDrawingCache();
//        Bitmap bitmap = ((BitmapDrawable) user_details_IMG_user.getDrawable()).getBitmap();
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
//        bytes = baos.toByteArray();
//
//
//
//    }


    private void initViews(View view) {


        user_details_EDT_user_full_name = view.findViewById(R.id.user_details_EDT_user_full_name);
        user_details_EDT_user_rank = view.findViewById(R.id.user_details_EDT_user_rank);
        user_details_EDT_user_phone_number = view.findViewById(R.id.user_details_EDT_user_phone_number);
        user_details_EDT_user_role = view.findViewById(R.id.user_details_EDT_user_role);
        user_details_EDT_user_base = view.findViewById(R.id.user_details_EDT_user_base);
        user_details_FAB_profile_pic = view.findViewById(R.id.user_details_FAB_profile_pic);
        user_details_BTN_accept = view.findViewById(R.id.user_details_BTN_accept);
        user_details_IMG_user = view.findViewById(R.id.user_details_IMG_user);
        navController = Navigation.findNavController(view);
    }

}
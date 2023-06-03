package com.anet.archiveevents.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.anet.archiveevents.OnUserStatusListener;
import com.anet.archiveevents.R;
import com.anet.archiveevents.objects.User;
import com.anet.archiveevents.viewModel.AuthViewModel;
import com.anet.archiveevents.viewModel.UserDetailsForSignupViewModel;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseUser;


public class SignInFragment extends Fragment {

    private TextInputLayout login_edt_user_password;
    private TextInputLayout login_edt_user_email;
    private Button signup_BTN_create_account;
    private NavController navController;
    private UserDetailsForSignupViewModel userDetailsForSignupViewModel;
    private AuthViewModel authViewModel;
    private User currentUser;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        authViewModel=new ViewModelProvider(this).get(AuthViewModel.class);

        // Get if the user details allReady exist
        authViewModel.getUserAllReadyExistMutableLiveData().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (!aBoolean){
                  navController.navigate(R.id.action_signInFragment2_to_userDetailsFragment);
                }
                else{
                    navController.navigate(R.id.action_signInFragment2_to_homeScreenFragment);


                }
            }
        });


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_in, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        login_edt_user_password = view.findViewById(R.id.login_edt_user_password);
        login_edt_user_email=  view.findViewById(R.id.login_edt_user_email);

        signup_BTN_create_account=  view.findViewById(R.id.signup_BTN_create_account);
        navController= Navigation.findNavController(view);



        signup_BTN_create_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email= login_edt_user_email.getEditText().getText().toString();
                String pass= login_edt_user_password.getEditText().getText().toString();

                if (!email.isEmpty()&& !pass.isEmpty()){
                    authViewModel.signIn(email,pass);

                  // Log.d("details", String.valueOf(userDetailsForSignupViewModel.getAddUserStatus().getValue())) ;
                }

            }
        });
    }


}
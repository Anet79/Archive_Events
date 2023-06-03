package com.anet.archiveevents.view;

import android.app.Activity;
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
import android.widget.Button;
import android.widget.TextView;

import com.anet.archiveevents.R;
import com.anet.archiveevents.viewModel.AuthViewModel;
import com.anet.archiveevents.viewModel.UserDetailsForSignupViewModel;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseUser;


public class SignUpFragment extends Fragment {

    private TextInputLayout signup_edt_user_password;
    private TextInputLayout signup_edt_user_email;

    private TextView signup_TVW_login_button;

    private Button signup_BTN_create_account;
    private Activity currentActivity;
    private NavController navController;
    private  String pass;
    private  String email;

    private AuthViewModel authViewModel;
    private UserDetailsForSignupViewModel userDetailsForSignupViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     //   currentActivity=getActivity();
        authViewModel=new ViewModelProvider(this).get(AuthViewModel.class);
        userDetailsForSignupViewModel=new ViewModelProvider(this).get(UserDetailsForSignupViewModel.class);

        authViewModel.getFirebaseUserMutableLiveData().observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                //TODO- we need to change our if condition ->dataManager.getCurrentUser.getUID!=null
                if (firebaseUser!=null){

                 //  navController.navigate(R.id.action_signUpFragment2_to_userDetailsFragment2);


                }
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        signup_edt_user_password = view.findViewById(R.id.signup_edt_user_password);
        signup_edt_user_email=  view.findViewById(R.id.signup_edt_user_email);
        signup_TVW_login_button=view.findViewById(R.id.signup_TVW_login_button);
        signup_BTN_create_account=  view.findViewById(R.id.signup_BTN_create_account);
        navController= Navigation.findNavController(view);

        signup_TVW_login_button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_signUpFragment2_to_signInFragment2);
            }
        });

        signup_BTN_create_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 email= signup_edt_user_email.getEditText().getText().toString();
                 pass= signup_edt_user_password.getEditText().getText().toString();

                if (!email.isEmpty()&& !pass.isEmpty()){
                    authViewModel.register(email,pass);
                   navController.navigate(R.id.action_signUpFragment2_to_userDetailsFragment2);

                }



            }
        });
    }
}
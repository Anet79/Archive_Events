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

import com.anet.archiveevents.R;
import com.anet.archiveevents.viewModel.AuthViewModel;
import com.google.android.material.button.MaterialButton;


public class SignOutFragment extends Fragment {

    private AuthViewModel authViewModel;
    private NavController navController;
    private MaterialButton signOut_BTN_create_account;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        authViewModel=new ViewModelProvider(this).get(AuthViewModel.class);

        authViewModel.getLoggedStatus().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean){
                    navController.navigate(R.id.action_signOutFragment2_to_signInFragment2);
                }
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_out, container, false);
    } 

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        signOut_BTN_create_account=view.findViewById(R.id.signOut_BTN_create_account);
        navController = Navigation.findNavController(view);

        signOut_BTN_create_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                authViewModel.signOut();
            }
        });
    }
}
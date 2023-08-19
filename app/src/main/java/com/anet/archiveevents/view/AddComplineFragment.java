package com.anet.archiveevents.view;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.anet.archiveevents.R;
import com.anet.archiveevents.objects.LandMark;
import com.anet.archiveevents.viewModel.AddComplineViewModel;
import com.anet.archiveevents.viewModel.AddEventViewModel;
import com.anet.archiveevents.viewModel.DairyEventViewModel;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

public class AddComplineFragment extends Fragment {

    private EditText add_compline_EDT_name;
    private EditText add_compline_EDT_email;
    private EditText add_compline_EDT_title;
    private EditText add_compline_EDT_details;
    private Button add_compline_BTN_save;

    private ImageButton add_complaint_back_button;
    private NavController navController;
    private AddComplineViewModel addComplineViewModel;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);





        findViews(view);
        addComplineViewModel = new ViewModelProvider(this).get(AddComplineViewModel.class);

        add_compline_BTN_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the details entered by the user
                String title = add_compline_EDT_title.getText().toString();
                String email = add_compline_EDT_email.getText().toString();
                String content = add_compline_EDT_details.getText().toString();
                String name = add_compline_EDT_name.getText().toString();

                if (!content.isEmpty() && !title.isEmpty() && !email.isEmpty() && !name.isEmpty()) {
                    addComplineViewModel.saveCompline(title, email, name, content);

                    addComplineViewModel.getAddCompleteComplineMutableLiveData().observe(getActivity(), new Observer<Boolean>() {
                        @Override
                        public void onChanged(Boolean aBoolean) {
                            if(aBoolean){
                                showSeccessDialog(v);
                            }else{
                                showErrorDialog(v);
                            }
                        }
                    });

                }else{
                    showErrorDialog(v);
                }

            }

            private void showSeccessDialog(View v) {
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

            private void showErrorDialog(View v) {
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
        });

        add_complaint_back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                navController.popBackStack();

            }
        });


    }


    private void findViews(View view){
        add_compline_EDT_name = view.findViewById(R.id.add_compline_EDT_name);
        add_compline_EDT_email = view.findViewById(R.id.add_compline_EDT_email);
        add_compline_EDT_title = view.findViewById(R.id.add_compline_EDT_title);
        add_compline_EDT_details = view.findViewById(R.id.add_compline_EDT_details);
        add_compline_BTN_save = view.findViewById(R.id.add_compline_BTN_save);
        add_complaint_back_button=view.findViewById(R.id.add_complaint_back_button);
        navController = Navigation.findNavController(view);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_compline_screen, container, false);
    }
}

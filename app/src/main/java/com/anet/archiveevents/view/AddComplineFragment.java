package com.anet.archiveevents.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.anet.archiveevents.R;
import com.anet.archiveevents.objects.LandMark;
import com.anet.archiveevents.viewModel.AddComplineViewModel;
import com.anet.archiveevents.viewModel.AddEventViewModel;

public class AddComplineFragment extends Fragment {

    private EditText add_compline_EDT_name;
    private EditText add_compline_EDT_email;
    private EditText add_compline_EDT_title;
    private EditText add_compline_EDT_details;
    private Button add_compline_BTN_save;
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
                }

            }
        });



    }
    private void findViews(View view){
        add_compline_EDT_name = view.findViewById(R.id.add_compline_EDT_name);
        add_compline_EDT_email = view.findViewById(R.id.add_compline_EDT_email);
        add_compline_EDT_title = view.findViewById(R.id.add_compline_EDT_title);
        add_compline_EDT_details = view.findViewById(R.id.add_compline_EDT_details);
        add_compline_BTN_save = view.findViewById(R.id.add_compline_BTN_save);
        navController = Navigation.findNavController(view);
    }
}

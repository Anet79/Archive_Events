package com.anet.archiveevents.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.anet.archiveevents.R;
import com.anet.archiveevents.adapters.EventsAdapter;
import com.anet.archiveevents.adapters.PagerAdapterFoeDairyEvents;
import com.anet.archiveevents.firebase.DairyEventRepository;
import com.anet.archiveevents.viewModel.DairyEventViewModel;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class DairyEventsFragment extends Fragment {

    private ViewPager2 viewPager;
    private TabLayout tabLayout;
    private static final String TITLE="";
    private DairyEventViewModel dairyEventViewModel;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dairyEventViewModel = new ViewModelProvider(this).get(DairyEventViewModel.class);






    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_dairy_events, container, false);

        viewPager =view.findViewById(R.id.viewPager);
        tabLayout=view.findViewById(R.id.tabLayout);
        PagerAdapterFoeDairyEvents pagerAdapter = new PagerAdapterFoeDairyEvents(this.getActivity());
        viewPager.setAdapter(pagerAdapter);




        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());

//                // Pass the array type based on the position
//                switch (tab.getPosition()) {
//                    case 0:
//                        dairyEventViewModel.getEventList("0");
//
//                        break;
//                    case 1:
//                        dairyEventViewModel.getEventList("1");
//
//                        break;
//                    case 2:
//
////                        dairyEventViewModel.getEventList("2");
//
//                        break;
//                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                tabLayout.getTabAt(position).select();
            }
        });



//        new TabLayoutMediator(tabLayout,viewPager,(tab, position) -> {
//
//        }).attach();

        return view;
    }


}
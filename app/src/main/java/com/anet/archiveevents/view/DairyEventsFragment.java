package com.anet.archiveevents.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anet.archiveevents.R;
import com.anet.archiveevents.adapters.PagerAdapterFoeDairyEvents;
import com.anet.archiveevents.viewModel.DairyEventViewModel;
import com.google.android.material.tabs.TabLayout;

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
        View view= inflater.inflate(R.layout.fragment_dairy_events_tabs, container, false);

        viewPager =view.findViewById(R.id.viewPager);
        tabLayout=view.findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("כל האירועים"));
        tabLayout.addTab(tabLayout.newTab().setText("האירועים שלי"));
        // Set TabLayout mode
        tabLayout.setTabMode(TabLayout.MODE_FIXED); // or MODE_FIXED

        // tabLayout.addTab(tabLayout.newTab().setText("My Favorite Events"));
       // tabLayout.setTabGravity(TabLayout.GRAVITY_START);
        PagerAdapterFoeDairyEvents pagerAdapter = new PagerAdapterFoeDairyEvents(this.getActivity());
        viewPager.setAdapter(pagerAdapter);
     //   viewPager.addOnLayoutChangeListener((View.OnLayoutChangeListener) new TabLayout.TabLayoutOnPageChangeListener(tabLayout));



        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());


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
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });



//        new TabLayoutMediator(tabLayout,viewPager,(tab, position) -> {
//
//        }).attach();

        return view;
    }


}
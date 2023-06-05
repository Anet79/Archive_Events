package com.anet.archiveevents.adapters;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.anet.archiveevents.view.DairyEventFragment;
import com.anet.archiveevents.view.DairyEventsMyEventsFragment;
import com.anet.archiveevents.view.DairyEventsMyFavoritesFragment;
import com.anet.archiveevents.view.NewDairyEventsFragment;

public class PagerAdapterFoeDairyEvents extends FragmentStateAdapter {
    public PagerAdapterFoeDairyEvents(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }



    @NonNull
    @Override
    public Fragment createFragment(int position) {

        // Pass the array type based on the position
        switch (position) {
            case 0:
                return new DairyEventFragment();

            case 1:
                return new DairyEventsMyEventsFragment();
            case 2:
                return new DairyEventsMyFavoritesFragment();
            default:
                return null;

        }


    }

    @Override
    public int getItemCount() {
        return 3;
    }




    @Nullable

    public CharSequence getPageTitle(int position) {
        // Return the title for each tab
        switch (position) {
            case 0:
                return "My Favorite";
            case 1:
                return "My Event";
            case 2:
                return "All Events";
            default:
                return null;
        }
    }
}


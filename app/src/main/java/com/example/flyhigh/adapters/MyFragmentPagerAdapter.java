package com.example.flyhigh.adapters;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.flyhigh.fireBase_user_package.FlyHighUser;
import com.example.flyhigh.ui.ChatFragment;
import com.example.flyhigh.ui.ContainerFragment;
import com.example.flyhigh.ui.ItineraryFragment;
import com.example.flyhigh.ui.RequestHelpFragment;


public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

    private static final String TAG = "MyFragmentStatePagerAda";

    private RequestHelpFragment mRequestHelpFragment;
    private ChatFragment mChatFragment;
    private ContainerFragment mContainerFragment;
    private ItineraryFragment mItineraryFragment;


    private boolean clientAttended;

    public MyFragmentPagerAdapter(@NonNull FragmentManager fm, FlyHighUser flyHighUser) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        Log.d(TAG, "MyFragmentStatePagerAdapter: uId is: " + flyHighUser.get_uId() + ", and username is: " + flyHighUser.get_username());

        mRequestHelpFragment = RequestHelpFragment.newInstance(flyHighUser.get_uId());
        mChatFragment = ChatFragment.newInstance(flyHighUser);
        mContainerFragment = ContainerFragment.newInstance(flyHighUser, clientAttended);
        mItineraryFragment = new ItineraryFragment();
    }

    private static final int NUMBER_OF_FRAGMENTS = 3;

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return mItineraryFragment;
            case 1:
                return  mContainerFragment;
            case 2:
                return  mChatFragment;

        }
        return null;
    }

    @Override
    public int getCount() {
        return NUMBER_OF_FRAGMENTS;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        if (object instanceof ContainerFragment) {
            ((ContainerFragment) object).updateData(clientAttended);
        }
        return super.getItemPosition(object);
    }

    public void setBoolean(Boolean aBoolean) {
        clientAttended = Boolean.TRUE.equals(aBoolean);
        notifyDataSetChanged();
    }
}

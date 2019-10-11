package com.example.flyhigh.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.flyhigh.R;
import com.example.flyhigh.adapters.MyFragmentPagerAdapter;
import com.example.flyhigh.custom_artifacts.MyViewPager;
import com.example.flyhigh.databinding.FragmentFlyhighMainBinding;
import com.example.flyhigh.drawable_engine.DrawableBank;
import com.example.flyhigh.drawable_engine.MenuItemRecolorer;
import com.example.flyhigh.fireBase_user_package.FlyHighUser;
import com.example.flyhigh.fireBase_user_package.UserFactory;


public class FlyHighMainFragment extends Fragment {

    private static final String TAG = "FlyHighMain";

    public FlyHighMainFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentFlyhighMainBinding binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_flyhigh_main, container, false);

        FlyHighUser flyHighUser = getUser();


        final MyFragmentPagerAdapter myFragmentPagerAdapter = new MyFragmentPagerAdapter(getActivity().getSupportFragmentManager(), flyHighUser);
        binding.mainContainer.setAdapter(myFragmentPagerAdapter);
//        final MyFragmentStatePagerAdapter myFragmentStatePagerAdapter = new MyFragmentStatePagerAdapter(getActivity().getSupportFragmentManager(), flyHighUser);
//        binding.mainContainer.setAdapter(myFragmentStatePagerAdapter);


        final View.OnClickListener menuButton = pagerMenu(binding);
        binding.page1.setOnClickListener(menuButton);
        binding.page2.setOnClickListener(menuButton);
        binding.page3.setOnClickListener(menuButton);

        final MenuItemRecolorer recolorer = new MenuItemRecolorer(getActivity());
        final DrawableBank[] bank = {
                new DrawableBank(R.drawable.circle, binding.page1),
                new DrawableBank(R.drawable.triangle, binding.page2),
                new DrawableBank(R.drawable.square, binding.page3)
        };

        recolorer
                .recolorBank(bank)
                .fromPassiveColor(R.color.colorInactive)
                .towardsActiveColor(R.color.colorActive);

        binding.mainContainer.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                recolorer.accordingTo(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        binding.mainContainer.setCurrentItem(1, true);
        binding.mainContainer.setCurrentItem(0, true);


        return binding.getRoot();
    }

    private View.OnClickListener pagerMenu(FragmentFlyhighMainBinding mBinding) {
        return new View.OnClickListener() {

            MyViewPager mMyViewPager = mBinding.mainContainer;
            @Override
            public void onClick(View view) {
                int id = view.getId();
                if (id == mBinding.page1.getId()) {
                    mMyViewPager.setCurrentItem(0, true);
                } else if (id == mBinding.page2.getId()) {
                    mMyViewPager.setCurrentItem(1, true);
                } else if (id == mBinding.page3.getId()) {
                    mMyViewPager.setCurrentItem(2, true);
                }
            }
        };
    }


    private FlyHighUser getUser() {
        SharedPreferences prefs = getActivity().getSharedPreferences(LoginActivity.CHAT_PREFS, getActivity().MODE_PRIVATE);

        String uID = prefs.getString(LoginActivity.USER_ID, null);
        String userName = prefs.getString(LoginActivity.USER_NAME, null);

        Log.d(TAG, "getUser: Strings are: " + uID + " and " + userName);

        return new FlyHighUser(uID, userName);
//        return UserFactory.getInstance().getNewFlyHighUser(uID, userName);
    }
}

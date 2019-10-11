/*
 *     Created by WeiYi Li
 *     2015-08-07
 *     weiyi.just2@gmail.com
 *     li2.me
 */
package com.example.flyhigh.ui;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.flyhigh.R;
import com.example.flyhigh.databinding.FragmentContainerBinding;
import com.example.flyhigh.fireBase_user_package.FlyHighUser;

import java.util.Date;


public class ContainerFragment extends Fragment {
    
    private static final String TAG = "Fragment4";
    private static final String EXTRA_CLIENT_ATTENDED = "com.maidenhair.flyhigh.ui.ContainerFragment.EXTRA_CLIENT_ATTENDED";

    private boolean clientAttended;
    private int mFragmentContainerId;
    private FlyHighUser mFlyHighUser;

    public static ContainerFragment newInstance(FlyHighUser user, boolean clientAttended) {

        Log.d(TAG, "newInstance: ");
        
        Bundle args = new Bundle();
        args.putBoolean(EXTRA_CLIENT_ATTENDED, clientAttended);
        args.putString(LoginActivity.USER_NAME, user.get_username());
        args.putString(LoginActivity.USER_ID, user.get_uId());
        
        ContainerFragment fragment = new ContainerFragment();
        fragment.setArguments(args);
        return fragment;
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate()");
        super.onCreate(savedInstanceState);
        clientAttended = getArguments().getBoolean(EXTRA_CLIENT_ATTENDED);
        String uId = getArguments().getString(LoginActivity.USER_ID);
        String userName = getArguments().getString(LoginActivity.USER_NAME);
        mFlyHighUser = new FlyHighUser(uId, userName);
    }
    
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy()");
    }
    
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.d(TAG, "onAttach()");
    }
    
    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "onDetach()");
    }    

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView()");

        FragmentContainerBinding binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_container, container, false);

        FrameLayout fragmentContainer = (FrameLayout) binding.fragmentContainer;
        int fragmentContainerId = R.id.fragmentContainer;
        mFragmentContainerId = fragmentContainerId;
        fragmentContainer.setId(fragmentContainerId);
        
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment oldFragment = fm.findFragmentById(fragmentContainerId);
        Fragment newFragment;
        if (oldFragment != null) {
            ft.remove(oldFragment);
        }
        if (clientAttended) {
            newFragment = ChatFragment.newInstance(mFlyHighUser);
        } else {
            newFragment = RequestHelpFragment.newInstance(mFlyHighUser.get_uId());
        }
//        if (mFragmentToShow == 0) {
//            newFragment = Page0Fragment.newInstance(mDate);
//        } else {
//            newFragment = Page1Fragment.newInstance(mContent);
//        }
        ft.add(fragmentContainerId, newFragment);
        ft.commit();
        Log.d(TAG, "add fragment " + newFragment.getClass().getSimpleName());
        
        return /*view*/binding.getRoot();
    }
    
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "onDestroyView()");
    }
    
    // To replace fragment in ViewPager, we implement a fragment with a framelayout, we call it as ContainerFragment,
    // We pass a variable "fragmentToShow" to nofity the ContainerFragment,
    // depending on "fragmentToShow", the ContainerFragment decide whether replace old fragment with new fragment, or
    // update old fragment.
    
    public void updateData(boolean clientAttended) {
        Log.d(TAG, "updateData: ");
//        Log.d(TAG, String.format("updateData(fragmentToShow=%d, Date=%s, Content=%s", fragmentToShow, formatDate(date), content));
//        mDate = date;
//        mContent = content;

        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        if (this.clientAttended != clientAttended) {
            Log.d(TAG, "replace fragment");
            this.clientAttended = clientAttended;
            if (clientAttended) {
                ft.replace(mFragmentContainerId, ChatFragment.newInstance(mFlyHighUser));
            } else {
                ft.replace(mFragmentContainerId, RequestHelpFragment.newInstance(mFlyHighUser.get_uId()));
            }
            ft.commit();        
        } /*else {
            Fragment oldFragment = fm.findFragmentById(mFragmentContainerId);
            if (oldFragment != null) {
                Log.d(TAG, "update fragment: " + oldFragment.getClass().getSimpleName());
                if (oldFragment instanceof Page0Fragment) {
                    ((Page0Fragment) oldFragment).updateDate(mDate);
                } else if (oldFragment instanceof Page1Fragment) {
                    ((Page1Fragment) oldFragment).updateContent(mContent);
                }
            }
        }*/
    }
    
    
    
    
    
    
//    public void updateData(int fragmentToShow, Date date, String content) {
//        Log.d(TAG, String.format("updateData(fragmentToShow=%d, Date=%s, Content=%s", fragmentToShow, formatDate(date), content));
//        mDate = date;
//        mContent = content;
//
//        FragmentManager fm = getFragmentManager();
//        FragmentTransaction ft = fm.beginTransaction();
//        if (mFragmentToShow != fragmentToShow) {
//            Log.d(TAG, "replace fragment");
//            mFragmentToShow = fragmentToShow;
//            if (mFragmentToShow == 0) {
//                ft.replace(mFragmentContainerId, Page0Fragment.newInstance(mDate));
//            } else {
//                ft.replace(mFragmentContainerId, Page1Fragment.newInstance(mContent));
//            }
//            ft.commit();        
//        } else {
//            Fragment oldFragment = fm.findFragmentById(mFragmentContainerId);
//            if (oldFragment != null) {
//                Log.d(TAG, "update fragment: " + oldFragment.getClass().getSimpleName());
//                if (oldFragment instanceof Page0Fragment) {
//                    ((Page0Fragment) oldFragment).updateDate(mDate);
//                } else if (oldFragment instanceof Page1Fragment) {
//                    ((Page1Fragment) oldFragment).updateContent(mContent);
//                }
//            }
//        }
//    }
    
//    private static String formatDate(Date date) {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
//        return sdf.format(date);
//    }
}

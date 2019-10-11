package com.example.flyhigh.view_model;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.flyhigh.fireBase_user_package.FlyHighUser;


public class MyViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private static final String TAG = "MyViewModelFactory";

    private FlyHighUser flyHighUser;
    /*TODO: The controller is coupled at ViewModelFactory Initialization*/
    public MyViewModelFactory(FlyHighUser flyHighUser) {
        this.flyHighUser = flyHighUser;

        Log.d(TAG, "MyViewModelFactory: reference is: " + this.flyHighUser);
    }

    /*TODO: Then referenced to the ViewModel*/
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        SharedViewModel viewModel = new SharedViewModel(flyHighUser);
        return (T) viewModel;
    }
}

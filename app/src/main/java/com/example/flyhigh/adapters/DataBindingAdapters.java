package com.example.flyhigh.adapters;

import android.widget.TextView;

import androidx.databinding.BindingAdapter;


public class DataBindingAdapters {
    private static final String TAG = "DataBindingAdapters";

    @BindingAdapter("intHours")
    public static void setHours(TextView textView, String _time){


        String hours = _time.substring(0,2);

        textView.setText(hours);

    }

    @BindingAdapter("intMinutes")
    public static void setMinutes(TextView textView, String _time){

        String minutes = _time.substring(2);

        textView.setText(minutes);
    }

}

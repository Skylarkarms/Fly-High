package com.example.flyhigh.ui;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.flyhigh.R;
import com.example.flyhigh.adapters.ItineraryRecyclerViewAdapter;
import com.example.flyhigh.databinding.FragmentItineraryBinding;
import com.google.firebase.database.DataSnapshot;

import java.lang.ref.WeakReference;
import java.util.List;


public class ItineraryFragment extends Fragment implements FlyHighActivity.OnListListened {

    private static final String TAG = "ItineraryFragment";


    private WeakReference<FlyHighActivity> mContextWeakReference;
    private ItineraryRecyclerViewAdapter adapter;



    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof FlyHighActivity) {

            mContextWeakReference = new WeakReference<>((FlyHighActivity)context);

        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Log.d(TAG, "onCreateView: ");

        FragmentItineraryBinding binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_itinerary, container, false);



        binding.itineraryRecyclerView.setLayoutManager(new LinearLayoutManager(mContextWeakReference.get()));
        binding.itineraryRecyclerView.setHasFixedSize(true);


        adapter = new ItineraryRecyclerViewAdapter();

        binding.itineraryRecyclerView.setAdapter(adapter);


        return binding.getRoot();
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onActivityCreated: ");
        super.onActivityCreated(savedInstanceState);

        FlyHighActivity activity = mContextWeakReference.get();

        activity.ObserveNow(this);

    }


    @Override
    public void itineraryList(List<DataSnapshot> dataSnapshots) {
        Log.d(TAG, "itineraryList: snaps are: " + dataSnapshots.toString());
        adapter.submitList(dataSnapshots);
    }

}

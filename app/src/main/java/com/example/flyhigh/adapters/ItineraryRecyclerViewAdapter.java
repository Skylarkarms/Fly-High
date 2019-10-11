package com.example.flyhigh.adapters;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flyhigh.R;
import com.example.flyhigh.data.ItineraryActivity;
import com.example.flyhigh.data.ItineraryFlightInfo;
import com.example.flyhigh.databinding.ItineraryActivityBinding;
import com.example.flyhigh.databinding.ItineraryFlightInfoBinding;
import com.example.flyhigh.from_firebase_to_list.mutable_snapshot_adder.DefinerAdder;
import com.example.flyhigh.from_firebase_to_list.mutable_snapshot_adder.ValueClassRelator;
import com.google.firebase.database.DataSnapshot;

import java.util.List;

public class ItineraryRecyclerViewAdapter extends ListAdapter<DataSnapshot, RecyclerView.ViewHolder> {

    private static final String TAG = "ItineraryRecyclerViewAd";



    public ItineraryRecyclerViewAdapter() {
        super(DIFF_CALLBACK);
        Log.d(TAG, "ItineraryRecyclerViewAdapter: ");
    }

    private LayoutInflater layoutInflater;


    private final ValueClassRelator[] relations = {
            new ValueClassRelator("infodevuelo", ItineraryFlightInfo.class),
            new ValueClassRelator("actividad", ItineraryActivity.class)
    };

    private final DefinerAdder mDefinerAdder = new DefinerAdder(relations);



    private static final DiffUtil.ItemCallback<DataSnapshot> DIFF_CALLBACK = new DiffUtil.ItemCallback<DataSnapshot>() {
        @Override
        public boolean areItemsTheSame(@NonNull DataSnapshot oldItem, @NonNull DataSnapshot newItem) {
            return oldItem.getKey() == newItem.getKey();
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull DataSnapshot oldItem, @NonNull DataSnapshot newItem) {
                    return oldItem.getValue() == newItem.getValue();
        }
    };

    @Override
    public void submitList(@Nullable List<DataSnapshot> list) {
        Log.d(TAG, "submitList: list is: " + list.toString());

        notifyDataSetChanged();
        super.submitList(list);
    }

    @Override
    protected DataSnapshot getItem(int position) {
        return super.getItem(position);
    }

    private Object getCustomItem(int position) {
        return mDefinerAdder.defineByParentThenReturn2Object(getItem(position));
    }

    private int TYPE_ACTIVITY = 0;
    private int TYPE_FLIGHT = 1;

    @Override
    public int getItemViewType(int position) {


        if (getCustomItem(position).getClass() == ItineraryActivity.class) {
            return TYPE_ACTIVITY;
        } else {
            return TYPE_FLIGHT;
        }

    }



    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }

        if (viewType == TYPE_ACTIVITY) {
            ItineraryActivityBinding binding = DataBindingUtil.inflate(layoutInflater,
                    R.layout.itinerary_activity,
                    parent,
                    false);
            return new ActivityHolder(binding);

        } else {
            ItineraryFlightInfoBinding binding = DataBindingUtil.inflate(layoutInflater,
                    R.layout.itinerary_flight_info,
                    parent,
                    false);
            return new FlightInfoHolder(binding);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        Log.d(TAG, "onBindViewHolder: getItem is: " + getCustomItem(position));

        if (holder instanceof ActivityHolder) {
            ItineraryActivity activity = (ItineraryActivity)getCustomItem(position);
            ((ActivityHolder) holder).bind(activity);
        } else {
            ItineraryFlightInfo info = (ItineraryFlightInfo)getCustomItem(position);
            ((FlightInfoHolder) holder).bind(info);
        }

    }

    class ActivityHolder extends RecyclerView.ViewHolder {

        ItineraryActivityBinding binding;

        public ActivityHolder(@NonNull ItineraryActivityBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(ItineraryActivity activity) {
            binding.setItineraryActivity(activity);
        }

    }

    class FlightInfoHolder extends RecyclerView.ViewHolder {

        ItineraryFlightInfoBinding binding;

        public FlightInfoHolder(@NonNull ItineraryFlightInfoBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(ItineraryFlightInfo info) {
            binding.setFlightInfo(info);
        }

    }

}

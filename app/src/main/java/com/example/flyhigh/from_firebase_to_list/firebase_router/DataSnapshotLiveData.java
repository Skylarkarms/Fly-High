package com.example.flyhigh.from_firebase_to_list.firebase_router;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.List;

public class DataSnapshotLiveData extends LiveData<List<DataSnapshot>> {

    private static final String TAG = "DataSnapshotLiveData";

    private Captain mCaptain;

    private LiveDataListListener listListener;


    public void setVal(DataSnapshot dataSnapshot) {

        Log.d(TAG, "setVal: o is: " + dataSnapshot.toString());
        if (getValue() == null) {
            ArrayList<DataSnapshot> snapshots = new ArrayList<>();
            snapshots.add(dataSnapshot);
            setValue(snapshots);
        } else {
            getValue().add(dataSnapshot);
        }
    }


    public DataSnapshotLiveData(QueriesBank[] queriesBank) {
        Log.d(TAG, "DataSnapshotLiveData: queries");
        this.mCaptain = new Captain(queriesBank);
//        mDataSnapshots = new ArrayList<>();

    }

    public DataSnapshotLiveData() {
        Log.d(TAG, "DataSnapshotLiveData: ");
    }

    @Override
    protected void onActive() {
        Log.d(TAG, "onActive: ");
//        mCaptain.runThisLittleBitchOnComplete(this, new BulletSupplier.FirebaseRouterInterface() {
//                    @Override
//                    public void onComplete() {
//                        Log.d(TAG, "onComplete: LiveData value is: " + getValue().toString());
////                        listListener.onFullList(getValue());
//                    }
//                });
        mCaptain.runThisLittleBitch(this);

    }

    @Override
    protected void onInactive() {
        getValue().clear();
    }

    @Override
    public void observe(@NonNull LifecycleOwner owner, @NonNull Observer<? super List<DataSnapshot>> observer) {
        super.observe(owner, observer);
    }

    public void setLiveDataListListener(LiveDataListListener listListener) {
        this.listListener = listListener;
    }

    public interface LiveDataListListener {
        void onFullList(List<DataSnapshot> snapshots);
    }

}

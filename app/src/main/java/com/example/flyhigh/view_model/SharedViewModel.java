package com.example.flyhigh.view_model;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.example.flyhigh.fireBase_user_package.FlyHighUser;
import com.example.flyhigh.from_firebase_to_list.firebase_boolean_checker.BooleanDataSnapshot;
import com.example.flyhigh.from_firebase_to_list.firebase_router.AmmoTable;
import com.example.flyhigh.from_firebase_to_list.firebase_router.DataSnapshotLiveData;
import com.example.flyhigh.from_firebase_to_list.firebase_router.QueriesBank;
import com.google.firebase.database.FirebaseDatabase;

public class SharedViewModel extends ViewModel {

    private static final String TAG = "SharedViewModel";

    private QueriesBank[] queries;
    private DataSnapshotLiveData itinerariosData;
    private BooleanDataSnapshot booleanDataSnapshot;

    public SharedViewModel(FlyHighUser flyHighUser) {

        Log.d(TAG, "SharedViewModel: ");

        queries = new QueriesBank[] {
                new QueriesBank(FirebaseDatabase.getInstance().getReference(flyHighUser.get_uId()).child("actividad"), AmmoTable.CHILDS),
                new QueriesBank(FirebaseDatabase.getInstance().getReference(flyHighUser.get_uId()).child("infodevuelo"), AmmoTable.CHILDS)

        };

        itinerariosData = new DataSnapshotLiveData(queries);
        booleanDataSnapshot = new BooleanDataSnapshot().inside("users/" + flyHighUser.get_uId() + "/_clientAttended");
    }


    public DataSnapshotLiveData getItinerariosData() {
        return itinerariosData;
    }

    public BooleanDataSnapshot getBooleanDataSnapshot() {
        return booleanDataSnapshot;
    }

}

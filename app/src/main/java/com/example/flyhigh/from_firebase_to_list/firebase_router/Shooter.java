package com.example.flyhigh.from_firebase_to_list.firebase_router;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.lang.ref.WeakReference;


public class Shooter implements Runnable {

    private static final String TAG = "Shooter";

    private AmmoTable mAmmoTable;
    private WeakReference<DataSnapshotLiveData> mDataSnapshotLiveDataWeakReference;
//    private DataSnapshotLiveData mDataSnapshotLiveData;

    public Shooter(AmmoTable ammoTable) {
        this.mAmmoTable = ammoTable;
    }

    public void run() {
        
        
        
        for (QueriesBank query = mAmmoTable.reload();
             query != null;
             query = mAmmoTable.reload()) {

        mAmmoTable.pullTheTrigger();

            Log.d(TAG, "run: case is: " + query.getTypeOfListener());

            switch (query.getTypeOfListener()) {
                case AmmoTable.SINGLE_VALUE:
                    Log.d(TAG, "run: SINGLE_VALUE");
                    shoot2KillSingleValue(query.getQuery(), mAmmoTable);
                    break;
                case AmmoTable.CHILDS:
                    Log.d(TAG, "run: CHILDS");
                    shoot2KillMultipleChilds(query.getQuery(), mAmmoTable);
                    break;

            }
        }
    }

    private void shoot2KillSingleValue(Query query, AmmoTable ammoTable) {

        DataSnapshotLiveData liveData = mDataSnapshotLiveDataWeakReference.get();
        Log.d(TAG, "shoot2KillSingleValue: query is: " + query.toString());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d(TAG, "shoot2KillSingleValue: onDataChange: dataSnapshot is: " + dataSnapshot.toString());
                liveData.setVal(dataSnapshot);
                ammoTable.killConfirmed();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    
    private void shoot2KillMultipleChilds(Query query, AmmoTable ammoTable) {

        DataSnapshotLiveData liveData = mDataSnapshotLiveDataWeakReference.get();
        Log.d(TAG, "shoot2KillChild: query is: " + query.toString());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d(TAG, "shoot2KillChild: onDataChange: dataSnapshot is: " + dataSnapshot.getChildrenCount());
                for (DataSnapshot child: dataSnapshot.getChildren()
                     ) {
                    Log.d(TAG, "onDataChange: childs are: " + child.toString());
                    liveData.setVal(child);
                }
                ammoTable.killConfirmed();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public Shooter setTarget(DataSnapshotLiveData liveData) {
        this.mDataSnapshotLiveDataWeakReference = new WeakReference<>(liveData);
//        this.mDataSnapshotLiveData = liveData;
        return this;
    }
}

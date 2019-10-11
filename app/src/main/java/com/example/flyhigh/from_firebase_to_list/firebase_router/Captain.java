package com.example.flyhigh.from_firebase_to_list.firebase_router;

import android.util.Log;

public class Captain {
    private static final String TAG = "Captain";

    private QueriesBank[] mQueries;

    public Captain(QueriesBank[] queries) {

        Log.d(TAG, "Captain: ON YOUR FEET MAGGOT!!!");

        this.mQueries = queries;
    }





    public void runThisLittleBitchOnComplete(DataSnapshotLiveData liveData, BulletSupplier.FirebaseRouterInterface routerInterface) {

        Log.d(TAG, "runThisLittleBitch: ");

        AmmoTable ammoTable = new AmmoTable();
        (new Thread(new BulletSupplier(ammoTable).setQueries(mQueries).setOnComplete(routerInterface))).start();
        (new Thread(new Shooter(ammoTable).setTarget(liveData))).start();
    }

    public void runThisLittleBitch(DataSnapshotLiveData liveData) {

        Log.d(TAG, "runThisLittleBitch: ");

        AmmoTable ammoTable = new AmmoTable();
        (new Thread(new BulletSupplier(ammoTable).setQueries(mQueries))).start();
        (new Thread(new Shooter(ammoTable).setTarget(liveData))).start();
    }
}

package com.example.flyhigh.from_firebase_to_list.firebase_router;

import android.util.Log;

import java.lang.ref.WeakReference;


/**Change name to Loader*/
public class BulletSupplier implements Runnable {

    private static final String TAG = "BulletSupplier";

    private AmmoTable mAmmoTable;
    private QueriesBank[] queries;

    private FirebaseRouterInterface mFirebaseRouterInterface;

    public BulletSupplier(AmmoTable ammoTable) {
        this.mAmmoTable = ammoTable;
    }

    public BulletSupplier setQueries(QueriesBank[] queries) {

        Log.d(TAG, "setQueries: ");
        this.queries = queries;
        return this;
    }


    public void run() {
        Log.d(TAG, "run: ");

        for (QueriesBank query : queries) {
            Log.d(TAG, "run: query is: " + query.getQuery().toString());
            mAmmoTable.supply(query);
        }
        mAmmoTable.finish();
//        mFirebaseRouterInterface.onComplete();

    }

    public BulletSupplier setOnComplete(FirebaseRouterInterface firebaseRouterInterface) {
        this.mFirebaseRouterInterface = firebaseRouterInterface;
        return this;
    }

    public interface FirebaseRouterInterface {
        void onComplete();
    }
}

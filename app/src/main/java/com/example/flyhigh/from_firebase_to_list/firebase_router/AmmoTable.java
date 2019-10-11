package com.example.flyhigh.from_firebase_to_list.firebase_router;


import android.util.Log;

/**Change name to Queries*/
public class AmmoTable {
    private static final String TAG = "AmmoTable";
    private QueriesBank query;
    private volatile boolean targetDown = true;
    private volatile boolean bulletSupplied = false;

    public static final int SINGLE_VALUE = 0;
    public static final int CHILDS = 2;

    public synchronized QueriesBank reload() {
        Log.d(TAG, "reload: bulletSupplied is: " +bulletSupplied);

        while (!bulletSupplied) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                e.printStackTrace();
            }
        }
        if (query != null) {
            Log.d(TAG, "reload: current query is: " + this.query.getQuery().toString());
        }

        return query;
    }

    public synchronized void supply(QueriesBank query) {
        Log.d(TAG, "supply: targetDown is: " + targetDown);

        while (!targetDown) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                e.printStackTrace();
            }
        }
        targetDown = false;
        Log.d(TAG, "supply: targetDown is: " + targetDown);
        this.query = query;

        bulletSupplied = true;
        Log.d(TAG, "supply: current query is: " + this.query.getQuery().toString());
        notifyAll();
    }

    public synchronized void finish() {

        Log.d(TAG, "finish:");

        while (!targetDown) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                e.printStackTrace();
            }
        }

        targetDown = false;

        this.query = null;

        bulletSupplied = true;

        notifyAll();

    }

    public synchronized void killConfirmed() {

        targetDown = true;
        notifyAll();


    }

    public synchronized void pullTheTrigger() {
        Log.d(TAG, "pullTheTrigger: bulletSupplied is: " + bulletSupplied);
        while (!bulletSupplied) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                e.printStackTrace();
            }
        }

        bulletSupplied = false;
        notifyAll();

    }
}

package com.example.flyhigh.from_firebase_to_list.firebase_router;

import com.google.firebase.database.Query;

public class QueriesBank {

    private Query mQuery;
    private int mTypeOfListener;

    public QueriesBank(Query query, int typeOfListener) {
        mQuery = query;
        mTypeOfListener = typeOfListener;
    }

    public Query getQuery() {
        return mQuery;
    }

    public int getTypeOfListener() {
        return mTypeOfListener;
    }
}

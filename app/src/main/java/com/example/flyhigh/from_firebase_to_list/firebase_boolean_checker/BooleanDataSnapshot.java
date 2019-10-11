package com.example.flyhigh.from_firebase_to_list.firebase_boolean_checker;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class BooleanDataSnapshot extends MutableLiveData<Boolean> {
    private static final String TAG = "BooleanDataSnapshot";

    private FirebaseDatabase instance;
    private DatabaseReference ref;


    public BooleanDataSnapshot() {
        instance = FirebaseDatabase.getInstance();

    }

    public BooleanDataSnapshot inside(String path) {
        Log.d(TAG, "inside: ");
        ref = instance.getReference(path);

        Log.d(TAG, "inside: ref is: " + ref.toString());
        return this;
    }


    @Override
    protected void onActive() {
        super.onActive();

        ref.addValueEventListener(listenBoolean());
    }


    @Override
    protected void onInactive() {
        super.onInactive();

        ref.removeEventListener(listenBoolean());
    }

    @Override
    public void observe(@NonNull LifecycleOwner owner, @NonNull Observer<? super Boolean> observer) {
        super.observe(owner, observer);
    }

    private ValueEventListener listenBoolean() {
        return new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Boolean mBoolean = dataSnapshot.getValue(Boolean.class);
                setValue(mBoolean);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
    }
}

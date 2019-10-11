package com.example.flyhigh.fireBase_user_package;

import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserNodePusher {

    private static final String TAG = "UserNodePusher";
    
    private FirebaseDatabase instance;
    private DatabaseReference ref;
    
    private User userNode;
    private Object field;


    public UserNodePusher() {
        instance = FirebaseDatabase.getInstance();
    }

    public UserNodePusher pushThe(User user) {
        this.userNode = user;
        return this;
    }

    public UserNodePusher pushThe(Object field) {
        this.field = field;
        return this;
    }

    public UserNodePusher insideAnExistingPathCalled (String path) {
        ref = instance.getReference(path);

        Log.d(TAG, "insideAnExistingPathCalled: path is: " + path);

        return this;
    }

    public void byCreatingANewChildOfName (String childName) {

        ref.child(childName).setValue(userNode);

        Log.d(TAG, "To: Child name is: " + childName);

    }

    public void byCreatingANewChildOfName4Field (String childName) {

        ref.child(childName).setValue(field);

        Log.d(TAG, "To: Child name is: " + childName);

    }


}

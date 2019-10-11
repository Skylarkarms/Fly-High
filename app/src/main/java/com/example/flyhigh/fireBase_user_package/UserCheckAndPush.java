package com.example.flyhigh.fireBase_user_package;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


    /*TODO:Example*/

/**UserCheckAndPush checkAndPush = new UserCheckAndPush();
                    checkAndPush.checkForUserID(auth)
                            .inside("users")
                            .thenPush(userNode);*/




public class UserCheckAndPush {

private static final String TAG = "UserCheckAndPush";

private FirebaseDatabase instance;
private DatabaseReference ref;

UserNodePusher pusher = new UserNodePusher();

private boolean found;


private String userID;
private String field;

public UserCheckAndPush() {

    Log.d(TAG, "UserCheckAndPush: found 0 is: " + found);
    Log.d(TAG, "UserCheckAndPush: ");
    instance = FirebaseDatabase.getInstance();
}

public UserCheckAndPush checkForUserID(FirebaseAuth auth) {
    Log.d(TAG, "checkForUserID: ");
    this.userID = auth.getUid();
    return this;

}

public UserCheckAndPush checkForField(String field) {
    Log.d(TAG, "checkForUserID: ");
    this.field = field;
    return this;

}

public UserCheckAndPush inside(String path) {
    Log.d(TAG, "inside: ");
    ref = instance.getReference(path);

    Log.d(TAG, "inside: ref is: " + ref.toString());
    return this;
}



public void thenPush(User userNode) {

    Log.d(TAG, "thenPush: ");

    Log.d(TAG, "thenPush: path is: " + ref.toString());

    ref.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            Log.d(TAG, "onDataChange: snapshot has child: " + dataSnapshot.exists());

            if (!dataSnapshot.exists()) {
                pusher.pushThe(userNode)
                .insideAnExistingPathCalled(ref.getKey())
                .byCreatingANewChildOfName(userID);
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    });

}

public void thenPush(UserNotif userNotif) {

    Log.d(TAG, "thenPush: ");

    Log.d(TAG, "thenPush: path is: " + ref.toString());

    ref = ref.child(field);

    ref.addListenerForSingleValueEvent(new ValueEventListener() {
        @SuppressLint("RestrictedApi")
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            Log.d(TAG, "onDataChange: snapshot has child: " + dataSnapshot.exists());

            Log.d(TAG, "onDataChange: ref is: " + ref.toString());
            Log.d(TAG, "onDataChange: the parent of this ref is: " + ref.getParent().getPath().toString());



            if (!dataSnapshot.exists()) {
                pusher.pushThe(userNotif.isClientAttended())
                .insideAnExistingPathCalled(ref.getParent().getPath().toString())
                .byCreatingANewChildOfName4Field("_clientAttended");

                pusher.pushThe(userNotif.isHelpRequested())
                        .insideAnExistingPathCalled(ref.getParent().getPath().toString())
                        .byCreatingANewChildOfName4Field("_helpRequested");
            }

            Log.d(TAG, "onDataChange: The path in which it was delivered was: " + ref.getParent().getPath().toString());
            Log.d(TAG, "onDataChange: isClientAttended value is: " + userNotif.isClientAttended());
            Log.d(TAG, "onDataChange: isHelpRequested value is: " + userNotif.isHelpRequested());
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    });

}


}

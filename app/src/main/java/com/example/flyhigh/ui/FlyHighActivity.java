package com.example.flyhigh.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.example.flyhigh.R;
import com.example.flyhigh.databinding.FlyhighActivityBinding;
import com.example.flyhigh.fireBase_user_package.FlyHighUser;
import com.example.flyhigh.fireBase_user_package.UserCheckAndPush;
import com.example.flyhigh.fireBase_user_package.UserFactory;
import com.example.flyhigh.fireBase_user_package.UserNotif;
import com.example.flyhigh.from_firebase_to_list.firebase_router.DataSnapshotLiveData;
import com.example.flyhigh.view_model.MyViewModelFactory;
import com.example.flyhigh.view_model.SharedViewModel;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;

import java.lang.ref.WeakReference;
import java.util.List;

public class FlyHighActivity extends AppCompatActivity /*implements DataSnapshotLiveData.LiveDataListListener*/ {

    private static final String TAG = "FlyHighActivity";

    public FlyHighActivity() {
    }

    private WeakReference<DrawerLayout> mDrawerLayoutWeakReference;
    private NavController mNavController;

    private SharedViewModel viewModel;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FlyhighActivityBinding binding = DataBindingUtil.setContentView(this,
                R.layout.flyhigh_activity);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FlyHighUser flyHighUser = getUser();

        if (auth.getUid() == null || flyHighUser == null) {
            Intent intent = new Intent(FlyHighActivity.this, LoginActivity.class);

            finish();
            startActivity(intent);

        } else {

            Log.d(TAG, "onCreate: uid is: " + auth.getUid());

            auth.addAuthStateListener(authStateListener);




            Log.d(TAG, "onCreate: Uid is: " + flyHighUser.get_uId());
            Log.d(TAG, "onCreate: userName is: " + flyHighUser.get_username());

            UserNotif userNotif = UserFactory.getInstance().getNewUserNotif(false, false);

            UserCheckAndPush checkAndPush = new UserCheckAndPush();
            checkAndPush.inside("users/" + flyHighUser.get_uId())
                    .checkForField("_helpRequested")
                    .thenPush(userNotif);


            mDrawerLayoutWeakReference = new WeakReference<>(binding.drawerLayout);


            setSupportActionBar(binding.toolbar);
            mNavController = Navigation.findNavController(this, R.id.flyhigh_nav_fragment);
            NavigationUI.setupActionBarWithNavController(this, mNavController, mDrawerLayoutWeakReference.get());
            NavigationUI.setupWithNavController(binding.navigationView, mNavController);


            binding.navigationView.setNavigationItemSelectedListener(onItemSelected(auth));

            MyViewModelFactory factory = new MyViewModelFactory(flyHighUser);
            viewModel = ViewModelProviders.of(this, factory).get(SharedViewModel.class);
//            viewModel.getItinerariosData().setLiveDataListListener(this::onFullList);

        }

    }



    public void ObserveNow(OnListListened onListListened) {
        Log.d(TAG, "ObserveNow: ");
        viewModel.getItinerariosData().observe(this, new Observer<List<DataSnapshot>>() {
            @Override
            public void onChanged(List<DataSnapshot> dataSnapshots) {
                onListListened.itineraryList(dataSnapshots);
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(mNavController, mDrawerLayoutWeakReference.get());
    }

    @Override
    public void onBackPressed() {

        Log.d(TAG, "onBackPressed: ");
        if (mDrawerLayoutWeakReference.get().isDrawerOpen(GravityCompat.START)) {
            Log.d(TAG, "onBackPressed: Drawer Open");
            mDrawerLayoutWeakReference.get().closeDrawer(GravityCompat.START);
            Log.d(TAG, "onBackPressed: Drawer Closed because of Back Pressed");
        } else {
            Log.d(TAG, "onBackPressed: Drawer Closed continue with Back Pressed");
            super.onBackPressed();
        }
    }

    private FirebaseAuth.AuthStateListener authStateListener = new FirebaseAuth.AuthStateListener() {
        @Override
        public void onAuthStateChanged(@NonNull FirebaseAuth auth) {
            FirebaseUser firebaseUser = auth.getCurrentUser();
            if (firebaseUser == null) {
                Log.d(TAG, "onAuthStateChanged: ");
                Intent intent = new Intent(FlyHighActivity.this, LoginActivity.class);

                finish();
                startActivity(intent);
            }
        }
    };


    private NavigationView.OnNavigationItemSelectedListener onItemSelected(FirebaseAuth auth) {
        return new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                int id = menuItem.getItemId();

                if (id == R.id.log_out_button){
                    Log.d(TAG, "onNavigationItemSelected: signed out");
                    auth.signOut();
                }
                return false;
            }
        };
    }

    private FlyHighUser getUser() {
        SharedPreferences prefs = getSharedPreferences(LoginActivity.CHAT_PREFS, MODE_PRIVATE);

        String uID = prefs.getString(LoginActivity.USER_ID, null);
        String userName = prefs.getString(LoginActivity.USER_NAME, null);

        Log.d(TAG, "getUser: Strings are: " + uID + " and " + userName);

        return UserFactory.getInstance().getNewFlyHighUser(uID, userName);
    }

//    @Override
//    public void onFullList(List<DataSnapshot> snapshots) {
//        Log.d(TAG, "onFullList: list is: " + snapshots.toString());
//    }

    public interface OnListListened {
        void itineraryList(List<DataSnapshot> dataSnapshots);
    }

}

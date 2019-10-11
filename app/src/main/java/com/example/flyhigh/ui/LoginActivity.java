package com.example.flyhigh.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.flyhigh.R;
import com.example.flyhigh.databinding.ActivityLoginBinding;
import com.example.flyhigh.fireBase_user_package.FlyHighUser;
import com.example.flyhigh.fireBase_user_package.UserFactory;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.ref.WeakReference;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private BindingController controller;

    public static final String EMAIL = "Email";
    public static final String USER_NAME = "User name";
    public static final String USER_ID = "User ID";
    public static final String PASSWORD = "Password";

    public static final String CHAT_PREFS = "ChatPrefs";


    private final int REQUEST_CODE = 123;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Log.d(TAG, "onCreate: ");
        ActivityLoginBinding binding = DataBindingUtil.setContentView(this,
                R.layout.activity_login);

        controller = new BindingController(binding);

        // TODO: Grab an instance of FirebaseAuth
        FirebaseAuth auth = FirebaseAuth.getInstance();

        controller.changeFields("maybe.leon@user.com", "148157aC");
//        attemptLogin(binding, auth);

        binding.loginPassword.setOnEditorActionListener((textView, id, keyEvent) -> {
            if (id == R.integer.login || id == EditorInfo.IME_NULL) {
                attemptLogin(binding, auth);
                return true;
            }
            return false;
        });

        binding.loginSignInButton.setOnClickListener(signInExistingUser(binding,auth));



    }


    private View.OnClickListener signInExistingUser (ActivityLoginBinding binding, FirebaseAuth auth) {
        return v -> attemptLogin(binding, auth);
    }


    // TODO: Complete the attemptLogin() method
    private void attemptLogin(ActivityLoginBinding binding, FirebaseAuth auth) {
        String email = binding.loginEmail.getText().toString();
        String password = binding.loginPassword.getText().toString();

        boolean cancel = false;
        View focusView = null;
        View focusView2 = null;
        if (TextUtils.isEmpty(email)) {
            (binding.loginEmail).setError(getString(R.string.error_field_required));
            focusView = binding.loginEmail;
            cancel = true;
        }

        if (TextUtils.isEmpty(password)) {
            (binding.loginPassword).setError(getString(R.string.error_field_required));
            focusView2 = binding.loginPassword;
            cancel = true;
        }

        if (cancel) {
            if (focusView != null) {
                focusView.requestFocus();
            }
            if (focusView2 != null) {
                focusView2.requestFocus();
            }
        } else {
            Toast.makeText(this, "Login in progress", Toast.LENGTH_LONG).show();
            /*}*/

            // TODO: Use FirebaseAuth to sign in with email & password

            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, task -> {
                if (!task.isSuccessful()) {
                    Log.d(TAG, "onComplete: Login Failed " + task.getException());
                    showErrorDialog("Failed to Log In");
                } else {
                    Log.d(TAG, "onComplete: Login Successful" + task.isSuccessful());

                    String uId = auth.getUid();


                    Log.d(TAG, "attemptLogin: ref is: " + FirebaseDatabase.getInstance().getReference("users/" + uId).toString());

                    FirebaseDatabase.getInstance().getReference("users/" + uId).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String username = String.valueOf(dataSnapshot.child("_username").getValue());

                            Log.d(TAG, "onDataChange: uid is: " + uId + " and username is: " + username);

                            FlyHighUser flyHighUser = UserFactory.getInstance().getNewFlyHighUser(uId, username);
                            save2Preferences(flyHighUser);

                            Intent intent = new Intent(LoginActivity.this, FlyHighActivity.class);

                            finish();
                            startActivity(intent);


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }
            });

        }
    }



    // TODO: Show error on screen with an alert dialog

    private void showErrorDialog (String s) {
        new AlertDialog.Builder(this)
        .setTitle("Oops")
        .setMessage(s)
        .setPositiveButton(android.R.string.ok, null)
        .setIcon(android.R.drawable.ic_dialog_alert)
        .show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult: ");


        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                assert data != null;
                controller.changeFields(data.getStringExtra(EMAIL),
                        data.getStringExtra(PASSWORD));

            }
        }

    }



    private class BindingController {

        private WeakReference<ActivityLoginBinding> binding;

        BindingController(ActivityLoginBinding binding) {
            Log.d(TAG, "BindingController: ");
            this.binding = new WeakReference<>(binding);
        }

        void changeFields(String email, String password) {
            binding.get().loginEmail.setText(email);
            binding.get().loginPassword.setText(password);
        }
    }


    private void save2Preferences(FlyHighUser flyHighUser) {
        Log.d(TAG, "save2Preferences: ");
        SharedPreferences prefs = getSharedPreferences(CHAT_PREFS, 0);
        prefs.edit().putString(USER_NAME, flyHighUser.get_username()).apply();
        prefs.edit().putString(USER_ID, flyHighUser.get_uId()).apply();
    }
}
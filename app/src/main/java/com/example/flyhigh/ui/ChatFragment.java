package com.example.flyhigh.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.flyhigh.R;
import com.example.flyhigh.adapters.ChatListAdapter;
import com.example.flyhigh.data.InstantMessage;
import com.example.flyhigh.databinding.FragmentChatBinding;
import com.example.flyhigh.fireBase_user_package.FlyHighUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class ChatFragment extends Fragment {

    private static final String TAG = "MainChatActivity";

    private AdapterController mAdapterController;
    private FlyHighUser mFlyHighUser;

    public static ChatFragment newInstance(FlyHighUser flyHighUser) {

        Bundle args = new Bundle();
        args.putString(LoginActivity.USER_NAME, flyHighUser.get_username());
        args.putString(LoginActivity.USER_ID, flyHighUser.get_uId());

        ChatFragment fragment = new ChatFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assert getArguments() != null;
        String userName = getArguments().getString(LoginActivity.USER_NAME);
        String uId = getArguments().getString(LoginActivity.USER_ID);
        
        mFlyHighUser = new FlyHighUser(uId, userName);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        FragmentChatBinding binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_chat, container, false);



        DatabaseReference mDatabaseReference = FirebaseDatabase.getInstance().getReference();

        final ChatListAdapter adapter = new ChatListAdapter(/*this,*/ mDatabaseReference,
                mFlyHighUser);
        mAdapterController = new AdapterController(binding, adapter);

        binding.messageInput.setOnEditorActionListener(editorAction(binding, mFlyHighUser, mDatabaseReference));
        binding.sendButton.setOnClickListener(sendMessageButton(binding, mFlyHighUser, mDatabaseReference));


        return binding.getRoot();
    }

    private EditText.OnEditorActionListener editorAction (FragmentChatBinding binding,
                                                          FlyHighUser flyHighUser,
                                                          DatabaseReference mDatabaseReference) {
        return (v, actionId, event) -> {
            sendMessage(binding, flyHighUser, mDatabaseReference);
            return true;
        };
    }

    private View.OnClickListener sendMessageButton(FragmentChatBinding binding,
                                                   FlyHighUser flyHighUser,
                                                   DatabaseReference mDatabaseReference) {
        return v -> sendMessage(binding, flyHighUser, mDatabaseReference);
    }


    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: ");

        mAdapterController.setFirebaseListener();
        mAdapterController.setAdapter();
    }

    private void sendMessage(FragmentChatBinding binding,
                             FlyHighUser flyHighUser,
                             DatabaseReference mDatabaseReference) {
        String input = binding.messageInput.getText().toString();
        Log.d(TAG, "sendMessage: Message is " + input);
        if (!input.equals("")) {
            InstantMessage chat = new InstantMessage(input, flyHighUser.get_username());
            mDatabaseReference
                    .child("chats").child(flyHighUser.get_uId())
                    .push()
                    .setValue(chat);
        }
        binding.messageInput.setText(null);
        // TODO: Grab the text the user typed in and push the message to Firebase

    }




    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: ");
        // TODO: Remove the Firebase event listener on the adapter.
        mAdapterController.cleanFirebaseAdapter();
        if (mAdapterController.mAdapter != null) mAdapterController.seteAdapterNull();

    }


    private class AdapterController {
        private FragmentChatBinding mBinding;
        private ChatListAdapter mAdapter;

        AdapterController(FragmentChatBinding binding, ChatListAdapter adapter) {
            mBinding = binding;
            mAdapter = adapter;
        }

        void setFirebaseListener() {
            mAdapter.setUp();
        }

        void setAdapter() {
            mBinding.chatListView.setAdapter(mAdapter);
        }
        void cleanFirebaseAdapter() {
            mAdapter.cleaUp();
        }

        void seteAdapterNull() {
            mBinding.chatListView.setAdapter(null);
        }
    }

}

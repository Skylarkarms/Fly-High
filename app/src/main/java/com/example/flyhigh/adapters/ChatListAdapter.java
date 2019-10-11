package com.example.flyhigh.adapters;

import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.example.flyhigh.R;
import com.example.flyhigh.data.InstantMessage;
import com.example.flyhigh.databinding.ChatMsgRowWithBindBinding;
import com.example.flyhigh.fireBase_user_package.FlyHighUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class ChatListAdapter extends BaseAdapter {
    private static final String TAG = "ChatListAdapter";

    private DatabaseReference mDatabaseReference;
    private FlyHighUser flyHighUser;

    private ArrayList<InstantMessage> mInstantMessages;

    private ChildEventListener listenDb(ArrayList<InstantMessage> messages) {
        return new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                InstantMessage message = dataSnapshot.getValue(InstantMessage.class);
                messages.add(message);
                Log.d(TAG, "onChildAdded: message added");
                notifyDataSetChanged();


            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
    }

    public ChatListAdapter(
                                      DatabaseReference reference,
                                      FlyHighUser flyHighUser) {
        mDatabaseReference = reference.child("chats").child(flyHighUser.get_uId());
        this.flyHighUser = flyHighUser;
        mInstantMessages = new ArrayList<>();
    }

    private class ViewHolder {

        private ChatMsgRowWithBindBinding binding;

        LinearLayout.LayoutParams authorParams;
        LinearLayout.LayoutParams messageParams;

        ViewHolder(ChatMsgRowWithBindBinding binding) {
            this.binding = binding;

            authorParams = (LinearLayout.LayoutParams) binding.author.getLayoutParams();
            messageParams = (LinearLayout.LayoutParams) binding.message.getLayoutParams();

        }

        void bindData (InstantMessage message) {
            binding.setMsgData(message);
        }

    }

    @Override
    public int getCount() {
        return mInstantMessages.size();
    }

    @Override
    public InstantMessage getItem(int position) {
        Log.d(TAG, "getItem: ");
        return mInstantMessages.get(position);
    }


    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.d(TAG, "getView: ");
        ChatMsgRowWithBindBinding binding;
        if (convertView == null) {

                LayoutInflater inflater = LayoutInflater.from(parent.getContext());
                binding = DataBindingUtil.inflate(inflater,
                        R.layout.chat_msg_row_with_bind,
                        parent,
                        false);
                convertView = binding.getRoot();

                final ViewHolder holder = new ViewHolder(binding);
                convertView.setTag(holder);
        }
        final InstantMessage message = getItem(position);
        final ViewHolder holder = (ViewHolder) convertView.getTag();

        boolean isMe = message.getAuthor().equals(flyHighUser);
        setChatRowAppearance(isMe, holder);

        holder.bindData(message);

        return convertView;
    }

    private void setChatRowAppearance (boolean isItMe, ViewHolder holder) {

        ChatMsgRowWithBindBinding binding = holder.binding;

        if (isItMe) {

            Log.d(TAG, "setChatRowAppearance: is it me?: " + isItMe);

            holder.authorParams.gravity = Gravity.START;
            holder.messageParams.gravity = Gravity.START;

            binding.author.setTextColor(Color.GREEN);
            binding.message.setBackgroundResource(R.drawable.bubble1);
        } else {

            Log.d(TAG, "setChatRowAppearance: is it me?: " + isItMe);

            holder.authorParams.gravity = Gravity.END;
            holder.messageParams.gravity = Gravity.END;


            binding.author.setTextColor(Color.BLUE);
            binding.message.setBackgroundResource(R.drawable.bubble2);
        }
    }

    public void setUp() {

        mDatabaseReference.addChildEventListener(listenDb(mInstantMessages));
    }
    public void cleaUp() {

        mDatabaseReference.removeEventListener(listenDb(mInstantMessages));
    }
}

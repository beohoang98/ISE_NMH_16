package com.example.thang.smartmoney.fragment;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatImageView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thang.smartmoney.MainActivity;
import com.example.thang.smartmoney.R;
import com.example.thang.smartmoney.database.FirebaseSync;
import com.squareup.picasso.Picasso;

import java.util.concurrent.Callable;

public class UserFragment extends Fragment implements View.OnClickListener {

    View view;
    Button logoutButton;
    Button syncButton;
    ProgressBar progressBar;

    private Uri avatar_url;
    private String username;
    private String email;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseSync.Init(getContext());
    }

    public void AnhXa() {
        AppCompatImageView avatarView = view.findViewById(R.id.frag_user_avatar);
        TextView usernameView = view.findViewById(R.id.frag_user_name);
        TextView emailView = view.findViewById(R.id.frag_user_email);
        logoutButton = view.findViewById(R.id.frag_user_button_logout);

        Picasso.get().load(avatar_url).into(avatarView);
        usernameView.setText(username);
        emailView.setText(email);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogOut();
            }
        });

        progressBar = view.findViewById(R.id.progressBar);
        syncButton = view.findViewById(R.id.sync_btn);
        syncButton.setOnClickListener(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_user, container, false);

        avatar_url = MainActivity.mUser.getPhotoUrl();
        username = MainActivity.mUser.getDisplayName();
        email = MainActivity.mUser.getEmail();

        if (username == "") username = "no name";

        AnhXa();

        return view;
    }

    public void LogOut() {
        MainActivity.mAuth.signOut();
        Intent login = new Intent(getActivity(), MainActivity.class);
        startActivity(login);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.sync_btn) {
            Log.d("Sync", "SYnc button clicked");
            progressBar.setVisibility(View.VISIBLE);
            final ColorStateList oldColorTint = syncButton.getBackgroundTintList();
            syncButton.setBackgroundTintList(getResources().getColorStateList(R.color.lightgrey));

            FirebaseSync.afterSync("btnSyncClick", new Callable() {
                @Override
                public Object call() throws Exception {
                    if (progressBar.getVisibility() != View.GONE) {
                        syncButton.setBackgroundTintList(oldColorTint);
                        progressBar.setVisibility(View.GONE);
                    }

                    Toast.makeText(getContext(), "Sync complete", Toast.LENGTH_SHORT).show();
                    Log.d("Sync", "Sync completed");
                    return null;
                }
            });
            FirebaseSync.syncDatabase();
        }
    }
}



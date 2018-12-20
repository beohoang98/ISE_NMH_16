package com.example.thang.smartmoney.fragment;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thang.smartmoney.MainActivity;
import com.example.thang.smartmoney.R;
import com.example.thang.smartmoney.database.FirebaseSync;
import com.example.thang.smartmoney.notification.NotifyService;
import com.squareup.picasso.Picasso;

import java.util.concurrent.Callable;

public class UserFragment extends Fragment
        implements View.OnClickListener, CompoundButton.OnCheckedChangeListener
{

    View view;
    Button logoutButton;
    Button syncButton;
    ProgressBar progressBar;
    NotifyService notifyService;

    private Uri avatar_url;
    private String username;
    private String email;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseSync.Init(getContext());
        if (!FirebaseSync.isLogin()) {
            startActivity(new Intent(getActivity(), MainActivity.class));
            getActivity().finish();
        }
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


        notifyService = new NotifyService(getActivity().getApplicationContext());
        SwitchCompat switchCompat = view.findViewById(R.id.switch_notify);
        switchCompat.setChecked(notifyService.isNotify());
        switchCompat.setOnCheckedChangeListener(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_user, container, false);

        if (!FirebaseSync.isLogin()) return view;

        avatar_url = FirebaseSync.getUser().getPhotoUrl();
        username = FirebaseSync.getUser().getDisplayName();
        email = FirebaseSync.getUser().getEmail();

        if (username == "") username = "no name";

        AnhXa();

        return view;
    }

    public void LogOut() {
        MainActivity.mAuth.signOut();
        Intent login = new Intent(getActivity(), MainActivity.class);
        login.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_TASK_ON_HOME);
        startActivity(login);
        getActivity().finish();
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

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (b) {
            notifyService.turnOn(16, 10, 0);
        } else {
            notifyService.turnOff();
        }
    }
}



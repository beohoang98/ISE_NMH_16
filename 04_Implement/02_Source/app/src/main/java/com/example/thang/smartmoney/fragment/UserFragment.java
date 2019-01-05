package com.example.thang.smartmoney.fragment;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
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

import com.example.thang.smartmoney.AboutActivity;
import com.example.thang.smartmoney.MainActivity;
import com.example.thang.smartmoney.R;
import com.example.thang.smartmoney.Tutorial_Activity;
import com.example.thang.smartmoney.database.FirebaseSync;
import com.example.thang.smartmoney.notification.NotifyService;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import java.util.concurrent.Callable;

public class UserFragment extends Fragment
        implements View.OnClickListener, CompoundButton.OnCheckedChangeListener
{

    View view;
    Button logoutButton;
    Button syncButton;
    Button tutoButton;
    ProgressBar progressBar;
    NotifyService notifyService;

    private Uri avatar_url;
    private String username;
    private String email;
    private Button aboutButton;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseSync.Init(getContext());
    }
    public void openAboutActivity(){
        Intent intent = new Intent(getActivity(), AboutActivity.class);
        startActivity(intent);
    }
    public void AnhXa() {
        AppCompatImageView avatarView = view.findViewById(R.id.frag_user_avatar);
        TextView usernameView = view.findViewById(R.id.frag_user_name);
        TextView emailView = view.findViewById(R.id.frag_user_email);
        logoutButton = view.findViewById(R.id.frag_user_button_logout);
        syncButton = view.findViewById(R.id.sync_btn);
        progressBar = view.findViewById(R.id.progressBar);
        tutoButton = view.findViewById(R.id.tutorial_button);

        if (!FirebaseSync.isLogin()) {
            syncButton.setEnabled(false);
            syncButton.setVisibility(View.GONE);
            logoutButton.setText(R.string.signin_title);
            logoutButton.setCompoundDrawables(
                    getResources().getDrawable(R.drawable.ic_add_circle, null),
                    null, null, null
            );
            avatarView.setImageResource(R.drawable.lo_go);
            usernameView.setText(getString(R.string.user_not_login));
        } else {
            Picasso.get().load(avatar_url).into(avatarView);
            usernameView.setText(username);
            emailView.setText(email);
        }
        logoutButton.setOnClickListener(this);
        syncButton.setOnClickListener(this);
        tutoButton.setOnClickListener(this);
        notifyService = new NotifyService(getActivity().getApplicationContext());
        SwitchCompat switchCompat = view.findViewById(R.id.switch_notify);
        switchCompat.setChecked(notifyService.isNotify());
        switchCompat.setOnCheckedChangeListener(this);

        aboutButton = view.findViewById(R.id.publisher_btn);
        aboutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAboutActivity();
            }
        });

        // tam thoi bo chuc nang Firebase Sync
        syncButton.setVisibility(View.GONE);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_user, container, false);

        if (FirebaseSync.isLogin()) {
            avatar_url = FirebaseSync.getUser().getPhotoUrl();
            username = FirebaseSync.getUser().getDisplayName();
            email = FirebaseSync.getUser().getEmail();
        }

        if (username == null || username.equals("")) username = "no name";

        AnhXa();

        return view;
    }

    public void Login() {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
    }

    public void LogOut() {
        FirebaseAuth.getInstance().signOut();
        Intent login = new Intent(getActivity(), MainActivity.class);
        login.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_TASK_ON_HOME);
        startActivity(login);
        getActivity().finish();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.sync_btn) {
            if (!FirebaseSync.isLogin()) {
                Toast.makeText(getActivity(), R.string.user_not_login, Toast.LENGTH_SHORT).show();
                return;
            }

            Log.d("Sync", "SYnc button clicked");
            progressBar.setVisibility(View.VISIBLE);
            final ColorStateList oldColorTint = syncButton.getBackgroundTintList();
            syncButton.setBackgroundTintList(getResources().getColorStateList(R.color.lightgrey, getContext().getTheme()));

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
        } else if (v.getId() == R.id.frag_user_button_logout) {
            if (FirebaseSync.isLogin()) {
                // logout
                LogOut();
            } else {
                Login();
            }
        } else  if (v.getId()== R.id.tutorial_button){
            Intent intent = new Intent(getActivity(), Tutorial_Activity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (b) {
            notifyService.turnOn(
                    getResources().getInteger(R.integer.reminder_hour),
                    getResources().getInteger(R.integer.reminder_minute),
                    0);
        } else {
            notifyService.turnOff();
        }
    }
}



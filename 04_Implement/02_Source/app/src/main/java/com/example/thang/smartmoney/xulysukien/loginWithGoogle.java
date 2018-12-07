package com.example.thang.smartmoney.xulysukien;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.thang.smartmoney.Login_Activity;
import com.example.thang.smartmoney.MainActivity;
import com.example.thang.smartmoney.R;
import com.example.thang.smartmoney.home_activity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

public class loginWithGoogle implements View.OnClickListener {

    View mView;
    Activity activity;
    Button mButton;

    GoogleSignInOptions gso;
    GoogleSignInClient mGGSignInCient;
    FirebaseAuth mAuth;

    final int RC_SIGN_IN = 0;

    @Override
    public void onClick(View v) {
        SigninGoogle();
    }

    public loginWithGoogle(Activity act, int buttonId) {
        activity = act;
        mView = act.getWindow().getDecorView().findViewById(android.R.id.content);
        mButton = mView.findViewById(buttonId);
        mButton.setOnClickListener(this);
        initGGSigin();
    }

    private void SigninGoogle() {
        Intent signInIntent = mGGSignInCient.getSignInIntent();
        activity.startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    public void onResult(@Nullable Intent data) {
        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
        try {
            GoogleSignInAccount account = task.getResult(ApiException.class);
            firebaseAuth(account);
        } catch (ApiException e) {
            Log.w("google-signin", "signInResult:failed code=" + e.getStatusCode());
        }
    }

    private void firebaseAuth(GoogleSignInAccount account) {
        Log.d("google-signin", "firebaseAuthWithGoogle:" + account.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("google-signin", "signInWithCredential:success");
                            MainActivity.mUser = mAuth.getCurrentUser();

                            Intent homeIntent = new Intent(mView.getContext(), home_activity.class);
                            activity.startActivity(homeIntent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("google-signin", "signInWithCredential:failure", task.getException());
                            Toast.makeText(mView.getContext(), "Authentication Failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    void initGGSigin() {
        mAuth = FirebaseAuth.getInstance();
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(activity.getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGGSignInCient = GoogleSignIn.getClient(activity, gso);
    }
}

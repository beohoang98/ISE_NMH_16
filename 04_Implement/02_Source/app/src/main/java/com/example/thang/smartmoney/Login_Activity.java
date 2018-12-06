package com.example.thang.smartmoney;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;


public class Login_Activity extends AppCompatActivity {
    static int RC_SIGN_IN = 0;
    EditText edtuser, edtpass;
    SignInButton btnlogin;
    GoogleSignInOptions gso;
    GoogleSignInClient mGGSignInCient;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_);

        anhxa();
        initGGSigin();
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SigninGoogle();
            }
        });
    }

    void anhxa() {
        btnlogin = findViewById(R.id.login_button_signin);
    }

    void LoGin() {
        String user = edtuser.getText().toString();
        String pass = edtpass.getText().toString();
        mAuth.signInWithEmailAndPassword(user, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //                  FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
                            Intent intent = new Intent(Login_Activity.this, home_activity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(Login_Activity.this, "loi dang nhap", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    void initGGSigin() {
        mAuth = FirebaseAuth.getInstance();
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGGSignInCient = GoogleSignIn.getClient(this, gso);
    }

    private void SigninGoogle() {
        Intent signInIntent = mGGSignInCient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuth(account);
            } catch (ApiException e) {
                Log.w("google-signin", "signInResult:failed code=" + e.getStatusCode());
            }
        }
    }

    private void firebaseAuth(GoogleSignInAccount account) {
        Log.d("google-signin", "firebaseAuthWithGoogle:" + account.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("google-signin", "signInWithCredential:success");
                            MainActivity.mUser = mAuth.getCurrentUser();

                            Intent homeIntent = new Intent(Login_Activity.this, home_activity.class);
                            startActivity(homeIntent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("google-signin", "signInWithCredential:failure", task.getException());
                            Toast.makeText(Login_Activity.this, "Authentication Failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}

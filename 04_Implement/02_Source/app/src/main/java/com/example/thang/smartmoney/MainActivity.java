package com.example.thang.smartmoney;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.thang.smartmoney.database.DBGiaoDich;
import com.example.thang.smartmoney.database.Database;
import com.example.thang.smartmoney.database.FirebaseSync;
import com.example.thang.smartmoney.model.ClassCategory;
import com.example.thang.smartmoney.xulysukien.loginWithGoogle;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends Activity {

    public static FirebaseAuth mAuth;
    public static FirebaseUser mUser;

    loginWithGoogle googleLoginHandler;
    Button btnLogin, btnSignup;
    TextView skipLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ClassCategory.loadFromDB(getApplicationContext());
        DBGiaoDich.init(getApplicationContext());

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        AnhXa();

        if (mUser == null) {
            Log.d("start", "login new");
        } else {
            Log.d("start", "re login");
            GoToHome();
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Login_Activity.class);
                startActivity(intent);
            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Signup_Activity.class);
                startActivity(intent);
            }
        });

        skipLink.setMovementMethod(LinkMovementMethod.getInstance());
        skipLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GoToHome();
            }
        });
    }

    void AnhXa() {
        googleLoginHandler = new loginWithGoogle(this, R.id.btnLoginGoogle);
        btnLogin = findViewById(R.id.btnLogin);
        btnSignup = findViewById(R.id.btnSignup);
        skipLink = findViewById(R.id.skip);
    }

    void GoToHome() {
        Intent homeIntent = new Intent(MainActivity.this, home_activity.class);

        // cai cho nay de thay giao dien chinh = home_activity, bam quay lai se khong tro ve login nua~
        homeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_TASK_ON_HOME);
        startActivity(homeIntent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        googleLoginHandler.onResult(data);
    }
}

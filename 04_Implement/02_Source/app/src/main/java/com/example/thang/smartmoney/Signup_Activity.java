package com.example.thang.smartmoney;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Signup_Activity extends AppCompatActivity {
    EditText edtuser1, edtpass1, edtconfirm1;
    Button btnsignup1;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_);
        anhxa();
        btnsignup1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignUp();
            }
        });

    }

    void anhxa() {
        edtuser1 = findViewById(R.id.edtuser1);
        edtpass1 = findViewById(R.id.edtpass1);
        edtconfirm1 = findViewById(R.id.edtconfirm1);
        btnsignup1 = findViewById(R.id.btnsignup1);
    }

    void SignUp() {
        mAuth = FirebaseAuth.getInstance();
        String user = edtuser1.getText().toString();
        String pass = edtpass1.getText().toString();
        String confirm = edtconfirm1.getText().toString();
        if (!pass.equals(confirm)) {
            Toast.makeText(this, "xác nhận mật khẩu sai", Toast.LENGTH_LONG).show();
        } else {
            mAuth.createUserWithEmailAndPassword(user, pass)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(Signup_Activity.this, "dang ki thanh cong", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(Signup_Activity.this, MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_TASK_ON_HOME);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(Signup_Activity.this
                                        ,getString(R.string.error_message) + task.getException().getMessage()
                                        ,Toast.LENGTH_SHORT)
                                        .show();
                            }
                        }
                    });
        }
    }
}

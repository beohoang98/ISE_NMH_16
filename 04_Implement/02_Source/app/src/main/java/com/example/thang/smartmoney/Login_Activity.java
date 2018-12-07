package com.example.thang.smartmoney;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login_Activity extends AppCompatActivity {
    EditText edtuser,edtpass;
    Button btnlogin;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_);

        anhxa();
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoGin();
            }
        });

    }

    void anhxa(){
        edtuser=findViewById(R.id.edtuser);
        edtpass=findViewById(R.id.edtpass);
        btnlogin=findViewById(R.id.btnlogin1);
    }

    void LoGin()
    {
        mAuth = FirebaseAuth.getInstance();
        String user=edtuser.getText().toString();
        String pass=edtpass.getText().toString();
        mAuth.signInWithEmailAndPassword(user, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if(task.isSuccessful())
                        {
                            //                  FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
                            Intent intent=new Intent(Login_Activity.this,home_activity.class);
                            startActivity(intent);
                        }
                        else
                        {
                            Toast.makeText(Login_Activity.this, "loi dang nhap", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
}

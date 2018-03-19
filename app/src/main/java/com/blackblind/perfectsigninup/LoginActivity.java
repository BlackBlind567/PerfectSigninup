package com.blackblind.perfectsigninup;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail;
    private EditText etPassword;
    private Button buttonLogin;
    private Button buttonChange;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() != null){
            startActivity(new Intent(LoginActivity.this, CheckActivity.class));
            finish();
        }

        progressDialog = new  ProgressDialog(this);

        etEmail = findViewById(R.id.login_email);
        etPassword = findViewById(R.id.login_password);
        buttonLogin = findViewById(R.id.btn_login);
        buttonChange = findViewById(R.id.btn_change_activity2);


        buttonChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,RegActivity.class));
            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String email = etEmail.getText().toString().trim();
                String pass = etPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)){

                    Toast.makeText(LoginActivity.this, "Please enter your email", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(pass)){

                    Toast.makeText(LoginActivity.this, "please enter your password", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressDialog.setMessage("User Logining....");
                progressDialog.show();

                firebaseAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(LoginActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                            finish();

                            startActivity(new Intent(getApplicationContext(), CheckActivity.class));
                        }
                        if (!task.isSuccessful()){
                            Toast.makeText(LoginActivity.this, "Login failed !! Please try again later", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        });

    }
}

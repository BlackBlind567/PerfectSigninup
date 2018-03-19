package com.blackblind.perfectsigninup;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegActivity extends AppCompatActivity  {

    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button buttonReg;
    private Button buttonNew;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);

        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() != null ){
            // profile activity here
            finish();
            startActivity(new Intent(getApplicationContext(), CheckActivity.class));

        }

        progressDialog = new  ProgressDialog(this);

        editTextEmail = findViewById(R.id.reg_email);
        editTextPassword = findViewById(R.id.reg_password);
        buttonReg = findViewById(R.id.btn_reg);
        buttonNew = findViewById(R.id.btn_change_activity);

        buttonReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = editTextEmail.getText().toString().trim();
                String pass = editTextPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)){

                    Toast.makeText(RegActivity.this, "Please enter your email", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(pass)){

                    Toast.makeText(RegActivity.this, "please enter your password", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressDialog.setMessage("User Registering....");
                progressDialog.show();

                firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
//                            finish();
//                            Toast.makeText(RegActivity.this, "Registreted Successfully", Toast.LENGTH_SHORT).show();
//                            startActivity(new Intent(RegActivity.this, LoginActivity.class));

                            if (firebaseAuth.getCurrentUser() != null){
                                startActivity(new Intent(RegActivity.this, CheckActivity.class));
                                finish();
                            }
                        }
                        if (!task.isSuccessful()){
                            Toast.makeText(RegActivity.this, "May be email already has been registered.Please make sure", Toast.LENGTH_SHORT).show();
                        }
                        progressDialog.dismiss();

                    }
                });

            }
        });

        buttonNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegActivity.this, LoginActivity.class));
            }
        });


    }




//    @Override
//    public void onClick(View view){
//        if (view == buttonReg){
//            // Goto in new activity
//            startActivity(new Intent(RegActivity.this, MainActivity.class));
//        }
//
//        if (view == buttonNew){
//            // Goto in new activity
//            registerUser();
//        }
//
//    }

//    private void registerUser() {
//        String email = editTextEmail.getText().toString().trim();
//        String pass = editTextPassword.getText().toString().trim();
//
//        if (TextUtils.isEmpty(email)){
//
//            Toast.makeText(this, "please enter your email", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        if (TextUtils.isEmpty(pass)){
//
//            Toast.makeText(this, "please enter your password", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        progressDialog.setMessage("User Registering....");
//        progressDialog.show();
//
//        firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//                if (task.isSuccessful()){
//                    Toast.makeText(RegActivity.this, "Registreted Successfully", Toast.LENGTH_SHORT).show();
//                    startActivity(new Intent(RegActivity.this, MainActivity.class));
//                }
//                if (!task.isSuccessful()){
//                    Toast.makeText(RegActivity.this, "May be email already has been registered.Please make sure", Toast.LENGTH_SHORT).show();
//                }
//
//            }
//        });
    }


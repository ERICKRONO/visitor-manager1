package com.ericktech.vistorsmanager.Security;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ericktech.vistorsmanager.Appointments;
import com.ericktech.vistorsmanager.Home;
import com.ericktech.vistorsmanager.LoginActivity;
import com.ericktech.vistorsmanager.MainActivity;
import com.ericktech.vistorsmanager.PasswordActivity;
import com.ericktech.vistorsmanager.R;
import com.ericktech.vistorsmanager.Registration;
import com.ericktech.vistorsmanager.office.Admin;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Security extends AppCompatActivity {


    EditText email,password;
    Button login,signup;
    TextView forgot_password;
    ProgressDialog mProgressDialog;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security);

        //      firebase Instance
        auth = FirebaseAuth.getInstance();
        auth.getCurrentUser();

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        signup = findViewById(R.id.signup);
        forgot_password = findViewById(R.id.tvForgotPassword);

        mProgressDialog = new ProgressDialog(this);

        forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Security.this, PasswordActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Registration.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }
        });


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProgressDialog.setTitle("Logging in the user");
                mProgressDialog.setMessage("Please wait....");
                mProgressDialog.show();

                String enteredEmail,enteredPassword;
                enteredEmail = email.getText().toString().trim();
                enteredPassword = password.getText().toString().trim();

                if (TextUtils.isEmpty(enteredEmail)){
                    mProgressDialog.dismiss();
                    email.setError("Field can't be empty!");
                    return;
                }
                if (!(enteredEmail.contains("@"))){
                    mProgressDialog.dismiss();
                    email.setError("Enter a valid email!");
                    return;
                }
                if (TextUtils.isEmpty(enteredPassword)){
                    mProgressDialog.dismiss();
                    password.setError("Field can't be empty");
                    return;
                }
                if (enteredPassword.length() <7  ){
                    mProgressDialog.dismiss();
                    password.setError("Access Denied");
                    startActivity(new Intent(Security.this, MainActivity.class));

                    return;

                }
                if (enteredPassword.length() < 5 ){
                    mProgressDialog.dismiss();
                    password.setError("Access Denied");
                    return;

                }
//                authenticate through firebase;
                auth.signInWithEmailAndPassword(enteredEmail,enteredPassword)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (!task.isSuccessful()){
                                    mProgressDialog.dismiss();
                                    Toast.makeText(Security.this, "Sign In failed", Toast.LENGTH_SHORT).show();
                                }else {
                                    mProgressDialog.dismiss();
                                    // Intent intent = new Intent(getApplicationContext(),Home.class);
                                    finish();
                                    startActivity(new Intent(Security.this, Home.class));
                                }
                            }
                        });

            }
        });

    }
}


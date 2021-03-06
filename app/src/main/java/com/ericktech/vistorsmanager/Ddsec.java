package com.ericktech.vistorsmanager;

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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Ddsec extends AppCompatActivity {EditText fName,lname,pNumber,email,password;
    Button registerNow;
    TextView login;
    ProgressDialog mProgressDialog;
    FirebaseAuth auth;
    ProgressBar progressbar;

    DatabaseReference mDD;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        auth = FirebaseAuth.getInstance();
        mDD = FirebaseDatabase.getInstance().getReference().child("DDsec");


        fName = findViewById(R.id.fName);
        lname = findViewById(R.id.lName);
        pNumber = findViewById(R.id.pNumber);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        registerNow = findViewById(R.id.register1);
        progressbar = findViewById(R.id.progressregistation);
        login = findViewById(R.id.logbtn);


        mProgressDialog = new ProgressDialog(this);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Ddsec.this, LoginActivity.class);
                startActivity(intent);
            }
        });


        registerNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerNow();
            }
        });

    }

    private void registerNow() {

        progressbar.setVisibility(View.VISIBLE);

        final String enteredfName,enteredlName,enteredpNumber,enteredEmail,enteredPassword;
        enteredfName = fName.getText().toString().trim();
        enteredlName = lname.getText().toString().trim();
        enteredpNumber = pNumber.getText().toString().trim();
        enteredEmail = email.getText().toString().trim();
        enteredPassword = password.getText().toString().trim();

        if (TextUtils.isEmpty(enteredfName)){

            fName.setError("Field can't be empty!");
            mProgressDialog.dismiss();
            return;
        }
        if (TextUtils.isEmpty(enteredlName)){


            lname.setError("Field can't be empty!");
            progressbar.setVisibility(View.GONE);
            return;
        }
        if (TextUtils.isEmpty(enteredpNumber)){
            //   mProgressDialog.dismiss();

            pNumber.setError("Field can't be empty!");
            progressbar.setVisibility(View.GONE);
            return;
        }
        if (enteredpNumber.length() < 10){

            progressbar.setVisibility(View.GONE);
            pNumber.setError("Input proper phone number");
            // mProgressDialog.dismiss();
        }
        if (TextUtils.isEmpty(enteredEmail)){
            //   mProgressDialog.dismiss();
            progressbar.setVisibility(View.GONE);
            email.setError("Field can't be empty!");
            return;
        }
        if (!(enteredEmail.contains("@"))){
//                    email.setError("Enter a valid email!");
            progressbar.setVisibility(View.GONE);
            return;
        }
        if (TextUtils.isEmpty(enteredPassword)){
            password.setError("Field can't be empty");
            progressbar.setVisibility(View.GONE);
            return;
        }
        if (enteredPassword.length() > 5){

            progressbar.setVisibility(View.GONE);
            password.setError("Password characters must be more than 6");
            mProgressDialog.dismiss();
            return;
        }

//                Registering the user
        auth.createUserWithEmailAndPassword(enteredEmail,enteredPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()){
                    // mProgressDialog.dismiss();


                    progressbar.setVisibility(View.GONE);
                    Toast.makeText(Ddsec.this, "Registration failed!", Toast.LENGTH_SHORT).show();
                }else{

                    String userId = auth.getCurrentUser().getUid();
                    DatabaseReference currentUser = mDD.child(userId);

                    currentUser.child("F_name").setValue(enteredfName);
                    currentUser.child("s_name").setValue(enteredlName);
                    currentUser.child("phone").setValue(enteredpNumber);
                    currentUser.child("email").setValue(enteredEmail);

                    progressbar.setVisibility(View.GONE);

                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            }
        });

    }
}

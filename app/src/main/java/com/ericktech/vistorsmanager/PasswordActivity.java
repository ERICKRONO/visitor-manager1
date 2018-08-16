package com.ericktech.vistorsmanager;

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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class PasswordActivity extends AppCompatActivity {
    EditText PasswordEmail;
    Button btnPasswordReset;
    TextView back;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        mAuth = FirebaseAuth.getInstance();
        PasswordEmail = findViewById(R.id.etPasswordEmail);
        btnPasswordReset = findViewById(R.id.btnPasswordReset);
        back = findViewById(R.id.back_btn);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PasswordActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        btnPasswordReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String resetEmail = PasswordEmail.getText().toString();
                if (TextUtils.isEmpty(resetEmail)){
                    PasswordEmail.setError("email is required!");
                    return;
                }

                if (!(resetEmail.contains("@"))){
                    PasswordEmail.setError("Enter a valid email address!");
                    return;
                }

                mAuth.sendPasswordResetEmail(resetEmail)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(PasswordActivity.this, "We have sent you instruction to rest your password in your email", Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(PasswordActivity.this, "Failed to send reset email", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
}

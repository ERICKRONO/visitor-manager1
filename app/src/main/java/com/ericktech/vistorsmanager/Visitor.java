package com.ericktech.vistorsmanager;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class Visitor extends AppCompatActivity {

    ImageView vphoto;
    EditText vname, vid, vpnumber, viam;
    Button vrequest;
    Spinner vspinner2;


    FirebaseDatabase database;
    DatabaseReference mDataUser, mDatabase;


    FirebaseAuth mAuth;
    FirebaseUser mUser;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visitor);

        mAuth = FirebaseAuth.getInstance();
        mUser =  mAuth.getCurrentUser();


        mDataUser = FirebaseDatabase.getInstance().getReference().child("").child(mUser.getUid());

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Visitors");
        //mDatabase = FirebaseDatabase.getInstance().getReference().child("Visitors");


        vname = findViewById(R.id.vname);
        vid = findViewById(R.id.vid);
        vpnumber = findViewById(R.id.vpnumber);
        vspinner2 = findViewById(R.id.vspinner2);
        vrequest = findViewById(R.id.vrequest);

        vrequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendrequest();
            }
        });


       // Button camera = (Button)findViewById(R.id.vphotobtn);
       // vphoto = (ImageView)findViewById(R.id.vphoto);

      //  camera.setOnClickListener(new View.OnClickListener() {
        //    @Override
      //      public void onClick(View v) {
    //            Intent intent = new Intent (MediaStore.ACTION_IMAGE_CAPTURE);
  //              startActivityForResult(intent,0);
//
       //     }
     //   });
    }

    public void sendrequest() {
        final String Name = vname.getText().toString().trim();
        final String Id = vid.getText().toString().trim();
        final String Phone = vpnumber.getText().toString().trim();
        final String Destination = vspinner2.getSelectedItem().toString();

        if (!TextUtils.isEmpty(Name) || !TextUtils.isEmpty(Id) || !TextUtils.isEmpty(Phone) || !TextUtils.isEmpty(Destination)) {


            final DatabaseReference newPost = mDatabase.push();

            mDataUser.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    newPost.child("name").setValue(Name);
                    newPost.child("id").setValue(Id);
                    newPost.child("phone").setValue(Phone);
                    newPost.child("destination").setValue(Destination);
                    newPost.child("uid").setValue(mUser.getUid());

                    newPost.child("captureName").setValue(dataSnapshot.child("security").getValue()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()) {

                                Intent intent = new Intent(getApplicationContext(), Home.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);

                            } else {

                                Toast.makeText(getApplicationContext(), "Something went wrong !", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });

                }


                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // do nothing
                }
            });

        } else  {

            Toast.makeText(this, "All fields required !", Toast.LENGTH_SHORT).show();
        }

    }


    //@Override
    //protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        //Bitmap bitmap = (Bitmap)data.getExtras().get("data");
      //  vphoto.setImageBitmap(bitmap);

    }
//}

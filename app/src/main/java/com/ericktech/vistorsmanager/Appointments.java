package com.ericktech.vistorsmanager;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ericktech.vistorsmanager.one.vistorObject;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Appointments extends AppCompatActivity {

    Button Home;

    RecyclerView vistorsIn;


    DatabaseReference mData, mUser;
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointments);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if (firebaseAuth.getCurrentUser() == null) {
                    /////////
                }
            }
        };

        mData = FirebaseDatabase.getInstance().getReference().child("Appointment");

        mUser = FirebaseDatabase.getInstance().getReference().child("security");


        vistorsIn = findViewById(R.id.approvedLIST);
        Home = findViewById(R.id.hbtt);
        vistorsIn.setLayoutManager(new LinearLayoutManager(this));
           }




    @Override
    protected void onStart() {
        super.onStart();

        mAuth.addAuthStateListener(mAuthListener);

        FirebaseRecyclerAdapter<vistorObject, Home.vistorAdapter> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<vistorObject, Home.vistorAdapter>(
                vistorObject.class,
                R.layout.activity_apoint_one,
                Home.vistorAdapter.class,
                mData
        ) {

            //@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            protected void populateViewHolder(Home.vistorAdapter viewHolder, vistorObject model, int position) {

                final String post_key = getRef(position).getKey();

                viewHolder.setname(model.getName());
                viewHolder.setid(model.getIdNumber());
                viewHolder.setid(model.getPhone());
                viewHolder.setdestination(model.getDestination());


                //viewHolder.RemoveButton(post_key);


                viewHolder.remove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        mData.child(post_key).removeValue();

                        Toast.makeText(Appointments.this, "Removed !", Toast.LENGTH_SHORT).show();

                    }
                });



            }
        };

        vistorsIn.setAdapter(firebaseRecyclerAdapter);

    }


    public static class vistorAdapter extends RecyclerView.ViewHolder{

        View view;

        TextView remove;

        DatabaseReference mData;
        FirebaseAuth mAuth2;

        public vistorAdapter(View itemView) {
            super(itemView);
            view = itemView;
            remove = view.findViewById(R.id.idRemove);

            mData = FirebaseDatabase.getInstance().getReference().child("Visitors");
            mAuth2 = FirebaseAuth.getInstance();

        }



        void  setname(String name) {
            TextView textView =  view.findViewById(R.id.idName);
            textView.setText(name);
        }

        void  setIdNumber(String name) {
            TextView textView =  view.findViewById(R.id.idNationalId);
            textView.setText(name);

        }
        void  setPhone(String name) {
            TextView textView =  view.findViewById(R.id.idNationalId);
            textView.setText(name);

        }

        void  setdestination(String name) {
            TextView textView =  view.findViewById(R.id.destination);
            textView.setText(name);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_one, menu);

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                logoutActivity();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }    }

    private void logoutActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

}

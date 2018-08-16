package com.ericktech.vistorsmanager;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Home extends AppCompatActivity {

   TextView welcome_to_tuk,visitors_in,notifications,Visitors_in;
   public Button arrival,check_out;
//   RecyclerView vistorsIn;


   DatabaseReference mData, mUser;
   FirebaseAuth mAuth;
   FirebaseAuth.AuthStateListener mAuthListener;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if (firebaseAuth.getCurrentUser() == null) {
                    /////////
                }
            }
        };

        mData = FirebaseDatabase.getInstance().getReference().child("Visitors");

        mUser = FirebaseDatabase.getInstance().getReference().child("security");


       arrival = findViewById(R.id.harrival);
        Visitors_in = findViewById(R.id.tvvisitorsin);
//        vistorsIn = findViewById(R.id.approvedLIST);
//        vistorsIn.setLayoutManager(new LinearLayoutManager(this));
//
        Visitors_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, Visitors_in.class);
                startActivity(intent);
            }
        });

        arrival.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Home.this, Visitor.class);
                startActivity(intent);
            }
        });

    }


    @Override
    protected void onStart() {
        super.onStart();

        mAuth.addAuthStateListener(mAuthListener);

        FirebaseRecyclerAdapter<vistorObject, vistorAdapter> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<vistorObject, vistorAdapter>(
                vistorObject.class,
                R.layout.visitor_one,
                vistorAdapter.class,
                mData
        ) {

            //@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            protected void populateViewHolder(vistorAdapter viewHolder, vistorObject model, int position) {

                final String post_key = getRef(position).getKey();

                viewHolder.setname(model.getName());
                viewHolder.setid(model.getIdNumber());
                viewHolder.setdestination(model.getDestination());


               //viewHolder.RemoveButton(post_key);


               viewHolder.remove.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {

                       mData.child(post_key).removeValue();

                       Toast.makeText(Home.this, "Removed !", Toast.LENGTH_SHORT).show();

                   }
               });



            }
        };

//        vistorsIn.setAdapter(firebaseRecyclerAdapter);

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

        void  setid(String name) {
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

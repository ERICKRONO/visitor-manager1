package com.ericktech.vistorsmanager.one;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ericktech.vistorsmanager.Home;
import com.ericktech.vistorsmanager.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class vistorAppointment extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    EditText  v_name, v_phone;
    TextView picDate, submit;
    FirebaseAuth mAuth;
    FirebaseUser mUser;

    DatabaseReference mDatabase, mDataUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vistor_appointment);

        mAuth = FirebaseAuth.getInstance();
        mUser =  mAuth.getCurrentUser();

        mDataUser = FirebaseDatabase.getInstance().getReference().child("").child(mUser.getUid());
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Appointment");

        v_name = findViewById(R.id.v_name);
       v_phone = findViewById(R.id.v_phone);
       picDate = findViewById(R.id.picDate);
       submit = findViewById(R.id.SumitData);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               sendData();
            }
        });
    }

    private void sendData() {
        final String Name = v_name.getText().toString().trim();
        final String Phone = v_phone.getText().toString().trim();
        String Date = picDate.getText().toString().trim();

        if (!TextUtils.isEmpty(Name) || !TextUtils.isEmpty(Phone) || !TextUtils.isEmpty(Date)) {

            final DatabaseReference newPost = mDatabase.push();
            mDataUser.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    newPost.child("name").setValue(Name);
                    newPost.child("phone").setValue(Phone);
                    newPost.child("uid").setValue(mUser.getUid());

                    newPost.child("s_name").setValue(dataSnapshot.child("security").getValue()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()) {

                                Toast.makeText(getApplicationContext(), "Appointment Success !", Toast.LENGTH_SHORT).show();
                                v_name.setText("");
                                v_phone.setText("");
                                picDate.setText("");


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


        } else {
            Toast.makeText(vistorAppointment.this, "All data required !", Toast.LENGTH_SHORT).show();
        }

    }

    public void datePicker(View view){

        DatePickerFragment fragment = new DatePickerFragment();
        fragment.show(getFragmentManager(), "time picker");
    }

    public void showDatePicker() {
        DialogFragment newFragment = new MyDatePickerFragment();
        newFragment.show(getFragmentManager(), "date picker");
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar cal = new GregorianCalendar(year, month, dayOfMonth);
        setDate(cal);
    }


    private void setDate(final Calendar calendar) {
        final DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM);
        ((TextView) findViewById(R.id.showDate)).setText(dateFormat.format(calendar.getTime()));

    }

    public static class DatePickerFragment extends DialogFragment {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);


            return new DatePickerDialog(getActivity(),
                    (DatePickerDialog.OnDateSetListener)
                            getActivity(), year, month, day);
        }

    }
}

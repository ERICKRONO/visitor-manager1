package com.ericktech.vistorsmanager;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

public class test extends AppCompatActivity {
    //Button selectDate = findViewById(R.id.btnDate);
    //TextView date = findViewById(R.id.tvSelectedDate);




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

//        selectDate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });
//        DatePickerDialog datePickerDialog = new DatePickerDialog(test.this,
//                new DatePickerDialog.OnDateSetListener() {
//                    @Override
//                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
//
//                    }
//                }, 0, 0, 0);
//        datePickerDialog.show();
    }
}

package com.ericktech.vistorsmanager.one;


import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.widget.DatePicker;
import android.widget.Toast;

import com.ericktech.vistorsmanager.R;

public class DatePickerWidgetActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.datepicker);

        DatePicker dp = findViewById(R.id.datePicker);
        dp.setOnDateChangedListener(dateChangedListener);
    }
    private DatePicker.OnDateChangedListener dateChangedListener =
            new DatePicker.OnDateChangedListener(){
                @Override
                public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
                    Toast.makeText(DatePickerWidgetActivity.this, "picked date is " + datePicker.getDayOfMonth() +
                            " / " + (datePicker.getMonth()+1) +
                            " / " + datePicker.getYear(), Toast.LENGTH_SHORT).show();
                }
            };

}
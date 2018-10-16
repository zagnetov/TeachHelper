package com.example.zagne_000.teachhelper.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.Toast;

import com.example.zagne_000.teachhelper.R;

import java.sql.Date;
import java.util.Calendar;

public class CalendarActivity extends AppCompatActivity {
    CalendarView calendar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        calendar = (CalendarView)findViewById(R.id.calendarView2);
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                Toast.makeText(getBaseContext(),"Selected date: " + dayOfMonth + " / " + (month + 1) + " / " + year, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(CalendarActivity.this, PairlistActivity.class);
                intent.putExtra("year", String.valueOf(year - 1900));
                intent.putExtra("month", String.valueOf(month));
                intent.putExtra("day", String.valueOf(dayOfMonth));
                startActivity(intent);
            }
        });
    }
}

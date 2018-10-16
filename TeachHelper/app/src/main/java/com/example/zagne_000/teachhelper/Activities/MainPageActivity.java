package com.example.zagne_000.teachhelper.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.zagne_000.teachhelper.R;

public class MainPageActivity extends AppCompatActivity {
    private Button openCalendarButton;
    private Button openGroupButton;
    private Button openSubjectButton;
    private Button openStudentButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        openCalendarButton = findViewById(R.id.btn_calendar);
        openStudentButton = findViewById(R.id.btn_students);
        openStudentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainPageActivity.this, StudentlistActivity.class);
                startActivity(intent);
            }
        });
        openCalendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainPageActivity.this, CalendarActivity.class);
                startActivity(intent);
            }
        });
        openGroupButton = findViewById(R.id.btn_groups);
        openGroupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainPageActivity.this, GrouplistActivity.class);
                startActivity(intent);
            }
        });
        openSubjectButton = findViewById(R.id.btn_subjects);
        openSubjectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainPageActivity.this, SubjectlistActivity.class);
                startActivity(intent);
            }
        });
    }

}

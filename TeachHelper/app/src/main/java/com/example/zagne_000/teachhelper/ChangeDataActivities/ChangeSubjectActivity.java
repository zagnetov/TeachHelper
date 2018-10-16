package com.example.zagne_000.teachhelper.ChangeDataActivities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.zagne_000.teachhelper.Activities.GrouplistActivity;
import com.example.zagne_000.teachhelper.Activities.SubjectlistActivity;
import com.example.zagne_000.teachhelper.R;

public class ChangeSubjectActivity extends AppCompatActivity {
    EditText editText;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changesubject);
        editText = (EditText)findViewById(R.id.change_subject_editText);
        btn = (Button)findViewById(R.id.change_subject_btn_id);
        Intent intent = getIntent();
        final String id = intent.getStringExtra("id");
        String groupnumber = intent.getStringExtra("subject");
        editText.setText(groupnumber);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newEntry = editText.getText().toString();
                if(editText.length() != 0){
                    Intent intent = new Intent(ChangeSubjectActivity.this, SubjectlistActivity.class);
                    intent.putExtra("id", id);
                    intent.putExtra("subject", newEntry);
                    setResult(RESULT_OK, intent);
                    finish();
                }else{
                    Toast.makeText(ChangeSubjectActivity.this, "Enter the groupnumber", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}

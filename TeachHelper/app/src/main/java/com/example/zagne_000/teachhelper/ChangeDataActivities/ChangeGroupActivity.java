package com.example.zagne_000.teachhelper.ChangeDataActivities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.zagne_000.teachhelper.Activities.GrouplistActivity;
import com.example.zagne_000.teachhelper.R;

public class ChangeGroupActivity extends AppCompatActivity {
    EditText editText;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changegroup);
        editText = (EditText)findViewById(R.id.change_group_editText);
        btn = (Button)findViewById(R.id.change_group_btn_id);
        Intent intent = getIntent();
        final String id = intent.getStringExtra("id");
        String groupnumber = intent.getStringExtra("groupnumber");
        editText.setText(groupnumber);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newEntry = editText.getText().toString();
                if(editText.length() != 0){
                    Intent intent = new Intent(ChangeGroupActivity.this, GrouplistActivity.class);
                    intent.putExtra("id", id);
                    intent.putExtra("groupnumber", newEntry);
                    setResult(RESULT_OK, intent);
                    finish();
                }else{
                    Toast.makeText(ChangeGroupActivity.this, "Enter the groupnumber", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}

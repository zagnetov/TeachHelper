package com.example.zagne_000.teachhelper.ChangeDataActivities;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.zagne_000.teachhelper.Activities.PairlistActivity;
import com.example.zagne_000.teachhelper.Activities.SubjectlistActivity;
import com.example.zagne_000.teachhelper.R;
import com.example.zagne_000.teachhelper.db_helper.GroupDataBaseHelper;
import com.example.zagne_000.teachhelper.db_helper.SubjectDataBaseHelper;
import com.example.zagne_000.teachhelper.model.Group;
import com.example.zagne_000.teachhelper.model.Subject;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class ChangePairActivity extends AppCompatActivity {
    EditText editText1;
    EditText editText2;
    Spinner spinner1;
    Spinner spinner2;
    Button btn;
    GroupDataBaseHelper myDB1;
    SubjectDataBaseHelper myDB2;
    Cursor data;
    ArrayList<Group> groups;
    ArrayList<Subject> subjects;
    int listindex;
    String id = null;
    public void onDestroy(){
        super.onDestroy();
        // Закрываем подключение и курсор
        myDB1.close();
        data.close();
    }

    public void reviewlist_groups(){
        try {
            groups = new ArrayList<Group>();
            data = myDB1.getListContents();
            /*String[] from = new String[]
                    {data.getColumnName(data.getColumnIndex("[groupnumber]") + 1).replaceAll(" ", ""),
                            data.getColumnName(data.getColumnIndex("[groupnumber]") + 2).replaceAll(" ","")};
            int[] to = new int[]{R.id.id_group_textView, R.id.groupnumber_textView};*/

            if (data.moveToFirst()) {
                groups.add(new Group(data.getString(data.getColumnIndex("[groupnumber]") + 1),
                        data.getString(data.getColumnIndex("[groupnumber]") + 2)));
                listindex = Integer.parseInt(data.getString(data.getColumnIndex("[groupnumber]") + 1));
                while (data.moveToNext()) {
                    groups.add(new Group(data.getString(data.getColumnIndex("[groupnumber]") + 1),
                            data.getString(data.getColumnIndex("[groupnumber]") + 2)));
                    listindex = Integer.parseInt(data.getString(data.getColumnIndex("[groupnumber]") + 1));
                }
            }
            try {
                spinner1 = (Spinner)findViewById(R.id.change_group_spinner);
                ArrayAdapter<Group> adapter = new ArrayAdapter<>(this, R.layout.activity_item, R.id.textView, groups); //selected item will look like a spinner set from XML
                adapter.setDropDownViewResource(R.layout.activity_item);
                spinner1.setAdapter(adapter);
            }catch(Exception e){
                System.out.println("не отрабатывает вывод");
            }
            System.out.println("курсор возвращён");
        }catch (Exception e){
            System.out.println("курсор не возвращён");
        }
    }

    public void reviewlist_subjects(){
        try {
            subjects = new ArrayList<Subject>();
            data = myDB2.getListContents();
            /*String[] from = new String[]
                    {data.getColumnName(data.getColumnIndex("[groupnumber]") + 1).replaceAll(" ", ""),
                            data.getColumnName(data.getColumnIndex("[groupnumber]") + 2).replaceAll(" ","")};
            int[] to = new int[]{R.id.id_group_textView, R.id.groupnumber_textView};*/

            if (data.moveToFirst()) {
                subjects.add(new Subject(data.getString(data.getColumnIndex("[name_subject]") + 1),
                        data.getString(data.getColumnIndex("[name_subject]") + 2)));
                listindex = Integer.parseInt(data.getString(data.getColumnIndex("[name_subject]") + 1));
                while (data.moveToNext()) {
                    subjects.add(new Subject(data.getString(data.getColumnIndex("[name_subject]") + 1),
                            data.getString(data.getColumnIndex("[name_subject]") + 2)));
                    listindex = Integer.parseInt(data.getString(data.getColumnIndex("[name_subject]") + 1));
                }
            }
            try {
                spinner2 = (Spinner)findViewById(R.id.change_subject_spinner);
                ArrayAdapter<Subject> adapter = new ArrayAdapter<>(this, R.layout.activity_item, R.id.textView, subjects); //selected item will look like a spinner set from XML
                adapter.setDropDownViewResource(R.layout.activity_item);
                spinner2.setAdapter(adapter);
            }catch(Exception e){
                System.out.println("не отрабатывает вывод");
            }
            System.out.println("курсор возвращён");
        }catch (Exception e){
            System.out.println("курсор не возвращён");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changepair);
        editText1 = (EditText)findViewById(R.id.change_time_editText);
        btn = (Button)findViewById(R.id.change_subject_btn_id);
        editText2 = (EditText)findViewById(R.id.change_topic_editText);
        spinner1 = (Spinner)findViewById(R.id.change_group_spinner);
        spinner2 = (Spinner)findViewById(R.id.change_subject_spinner);
        Intent intent = getIntent();
       final String action = intent.getStringExtra("action");
        final String time;
        final String topic;
        if(action.equalsIgnoreCase("update")){
            id = intent.getStringExtra("id");
            time = intent.getStringExtra("time");
            topic = intent.getStringExtra("topic");
            editText1.setText(time);
            editText2.setText(topic);
        }

        myDB1 = new GroupDataBaseHelper(this);
        myDB1.getReadableDatabase();
        try {
            myDB1.createDataBase();
            System.out.println("база данных создана");
            try {
                myDB1.openDataBase();
                System.out.println("база данных открыта");
            } catch (SQLException e) {
                System.out.println("не удалось открыть базу данных");
                e.printStackTrace();
            }
        } catch (IOException e) {
            System.out.println("не удалось создать базу данных базу данных");
            e.printStackTrace();
        }
        reviewlist_groups();
        myDB2 = new SubjectDataBaseHelper(this);
        myDB2.getReadableDatabase();
        try {
            myDB2.createDataBase();
            System.out.println("база данных создана");
            try {
                myDB2.openDataBase();
                System.out.println("база данных открыта");
            } catch (SQLException e) {
                System.out.println("не удалось открыть базу данных");
                e.printStackTrace();
            }
        } catch (IOException e) {
            System.out.println("не удалось создать базу данных базу данных");
            e.printStackTrace();
        }
        reviewlist_subjects();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String time = editText1.getText().toString();
                String topic = editText2.getText().toString();
                if(editText1.length() != 0 && editText2.length() != 0){
                    Intent intent = new Intent(ChangePairActivity.this, PairlistActivity.class);
                    if(action.equalsIgnoreCase("update")) {
                        if(!(id.isEmpty())) {
                            intent.putExtra("id", id);
                        }
                    }
                    intent.putExtra("time", time);
                    intent.putExtra("topic", topic);
                    Group group = (Group) spinner1.getSelectedItem();
                    Subject subject = (Subject) spinner2.getSelectedItem();
                    intent.putExtra("id_group", group.getId_group());
                    intent.putExtra("groupnumber", group.getGroupnumber());
                    intent.putExtra("id_subject", subject.getId_subject());
                    intent.putExtra("name_subject", subject.getName_subject());
                    setResult(RESULT_OK, intent);
                    finish();
                }else{
                    Toast.makeText(ChangePairActivity.this, "Enter the data", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}

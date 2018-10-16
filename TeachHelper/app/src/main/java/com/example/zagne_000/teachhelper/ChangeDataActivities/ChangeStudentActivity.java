package com.example.zagne_000.teachhelper.ChangeDataActivities;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.zagne_000.teachhelper.Activities.SubjectlistActivity;
import com.example.zagne_000.teachhelper.R;
import com.example.zagne_000.teachhelper.db_helper.GroupDataBaseHelper;
import com.example.zagne_000.teachhelper.model.Group;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class ChangeStudentActivity extends AppCompatActivity {
    EditText editText1;
    EditText editText2;
    Spinner spinner;
    Button btn;
    GroupDataBaseHelper myDB;
    Cursor data;
    ArrayList<Group> groups;
    int listindex;
    String id = null;
    public void onDestroy(){
        super.onDestroy();
        // Закрываем подключение и курсор
        myDB.close();
        data.close();
    }

    public void reviewlist(){
        try {
            groups = new ArrayList<Group>();
            data = myDB.getListContents();
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
                spinner = (Spinner)findViewById(R.id.change_group_spinner);
                ArrayAdapter<Group> adapter = new ArrayAdapter<>(this, R.layout.activity_item, R.id.textView, groups); //selected item will look like a spinner set from XML
                adapter.setDropDownViewResource(R.layout.activity_item);
                spinner.setAdapter(adapter);
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
        setContentView(R.layout.activity_changestudent);
        editText1 = (EditText)findViewById(R.id.change_firstname_editText);
        btn = (Button)findViewById(R.id.change_subject_btn_id);
        editText2 = (EditText)findViewById(R.id.change_secondname_editText);
        spinner = (Spinner)findViewById(R.id.change_group_spinner);
        Intent intent = getIntent();
       final String action = intent.getStringExtra("action");
        final String firstname;
        final String secondname;
        if(action.equalsIgnoreCase("update")){
            id = intent.getStringExtra("id");
            firstname = intent.getStringExtra("firstname");
            secondname = intent.getStringExtra("secondname");
            editText1.setText(firstname);
            editText2.setText(secondname);
        }

        myDB = new GroupDataBaseHelper(this);
        myDB.getReadableDatabase();
        try {
            myDB.createDataBase();
            System.out.println("база данных создана");
            try {
                myDB.openDataBase();
                System.out.println("база данных открыта");
            } catch (SQLException e) {
                System.out.println("не удалось открыть базу данных");
                e.printStackTrace();
            }
        } catch (IOException e) {
            System.out.println("не удалось создать базу данных базу данных");
            e.printStackTrace();
        }
        reviewlist();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstname = editText1.getText().toString();
                String secondname = editText2.getText().toString();
                if(editText1.length() != 0 && editText2.length() != 0){
                    Intent intent = new Intent(ChangeStudentActivity.this, SubjectlistActivity.class);
                    if(action.equalsIgnoreCase("update")) {
                        if(!(id.isEmpty())) {
                            intent.putExtra("id", id);
                        }
                    }
                    intent.putExtra("firstname", firstname);
                    intent.putExtra("secondname", secondname);
                    Group group = (Group) spinner.getSelectedItem();
                    intent.putExtra("id_group", group.getId_group());
                    intent.putExtra("groupnumber", group.getGroupnumber());
                    setResult(RESULT_OK, intent);
                    finish();
                }else{
                    Toast.makeText(ChangeStudentActivity.this, "Enter the data", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}

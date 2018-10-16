package com.example.zagne_000.teachhelper.Activities;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.zagne_000.teachhelper.ChangeDataActivities.ChangeGroupActivity;
import com.example.zagne_000.teachhelper.ChangeDataActivities.ChangeSubjectActivity;
import com.example.zagne_000.teachhelper.R;
import com.example.zagne_000.teachhelper.db_helper.GroupDataBaseHelper;
import com.example.zagne_000.teachhelper.db_helper.PairDataBaseHelper;
import com.example.zagne_000.teachhelper.db_helper.SubjectDataBaseHelper;
import com.example.zagne_000.teachhelper.model.Group;
import com.example.zagne_000.teachhelper.model.Subject;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class SubjectlistActivity extends AppCompatActivity {
    ListView listView_subjects;
    SubjectDataBaseHelper myDB;
    Cursor data;
    ArrayList<Subject> subjects;
    Button btnAdd;
    Button btnDelAll;
    EditText editText_subject;
    static int index_position;
    int listindex;
    int delta_index = 1;
    public static final int IDM_CHANGE = 101;
    public static final int IDM_DELETE = 102;
    private static final int SECOND_ACTIVITY_RESULT_CODE = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subjectlist);
        listView_subjects = (ListView)findViewById(R.id.listview_subject_id);
        registerForContextMenu(listView_subjects);
        myDB = new SubjectDataBaseHelper(this);
        btnAdd = (Button)findViewById(R.id.add_subject_btn_id);
        btnDelAll = (Button)findViewById(R.id.delete_all_subjects_btn_id);
        editText_subject = (EditText)findViewById(R.id.edit_text_subject_id);

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
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newEntry = editText_subject.getText().toString();
                if(editText_subject.length() != 0){
                    AddData(newEntry);
                    editText_subject.setText("");
                }else{
                    Toast.makeText(SubjectlistActivity.this, "Enter the subject", Toast.LENGTH_LONG).show();
                }
            }
        });
        btnDelAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newEntry = editText_subject.getText().toString();
                DeleteAllData();
            }
        });
    }

    @Override
    public void onResume() {
        reviewlist();
        super.onResume();

    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        // Закрываем подключение и курсор
        myDB.close();
        data.close();
    }

    public void reviewlist(){
        try {
            subjects = new ArrayList<Subject>();
            data = myDB.getListContents();

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
                listView_subjects = (ListView)findViewById(R.id.listview_subject_id);
                ArrayAdapter<Subject> adapter = new ArrayAdapter<>(this, R.layout.activity_item, R.id.textView, subjects);
                listView_subjects.setAdapter(adapter);
                listView_subjects.deferNotifyDataSetChanged();
            }catch(Exception e){
                System.out.println("не отрабатывает вывод");
            }
            System.out.println("курсор возвращён");
        }catch (Exception e){
            System.out.println("курсор не возвращён");
        }
    }
    public void AddData(String newEntry){
        Subject subject = new Subject(String.valueOf(listindex + 1), newEntry);
        boolean insertData = myDB.addData(subject);
        if(insertData == true){
            Toast.makeText(SubjectlistActivity.this, "Successfully added", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(SubjectlistActivity.this, "Something wrong", Toast.LENGTH_LONG).show();
        }
        reviewlist();
    }

    public void DeleteAllData(){
        boolean deleteAllData = myDB.deleteAllData();
        if(deleteAllData == true){
            Toast.makeText(SubjectlistActivity.this, "Successfully deleted", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(SubjectlistActivity.this, "Something wrong", Toast.LENGTH_LONG).show();
        }
        reviewlist();
    }
    public void DeleteData(int id){
        boolean deleteData = myDB.deleteData(id);
        if(deleteData == true){
            Toast.makeText(SubjectlistActivity.this, "Successfully deleted", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(SubjectlistActivity.this, "Something wrong", Toast.LENGTH_LONG).show();
        }
        reviewlist();
        //delta_index++;
    }
    public void UpdateData(int id, String newEntry){
        boolean updateData = myDB.updateData(id, newEntry);
        if(updateData == true){
            Toast.makeText(SubjectlistActivity.this, "Successfully changed", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(SubjectlistActivity.this, "Something wrong", Toast.LENGTH_LONG).show();
        }
        reviewlist();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(Menu.NONE, IDM_CHANGE, Menu.NONE, "Изменить");
        menu.add(Menu.NONE, IDM_DELETE, Menu.NONE, "Удалить");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SECOND_ACTIVITY_RESULT_CODE) {
            if (resultCode == RESULT_OK) {
                int id = Integer.parseInt(data.getStringExtra("id"));
                String subject = data.getStringExtra("subject");
                UpdateData(id, subject);
            }
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item)
    {
        CharSequence message;
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        index_position = info.position;
        Subject subject = (Subject) listView_subjects.getItemAtPosition(index_position);
        switch (item.getItemId())
        {
            case IDM_CHANGE:
                Intent intent = new Intent(SubjectlistActivity.this, ChangeSubjectActivity.class);

                intent.putExtra("id", subject.getId_subject());
                intent.putExtra("subject", subject.getName_subject());
                startActivityForResult(intent, SECOND_ACTIVITY_RESULT_CODE);
                break;
            case IDM_DELETE:
                DeleteData(Integer.parseInt(subject.getId_subject()));
                break;
            default:
                return super.onContextItemSelected(item);
        }
        return true;
    }
}

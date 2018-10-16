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
import android.widget.ListView;
import android.widget.Toast;

import com.example.zagne_000.teachhelper.ChangeDataActivities.ChangeStudentActivity;
import com.example.zagne_000.teachhelper.R;
import com.example.zagne_000.teachhelper.db_helper.StudentDataBaseHelper;
import com.example.zagne_000.teachhelper.model.Group;
import com.example.zagne_000.teachhelper.model.Student;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class StudentlistActivity extends AppCompatActivity {
    ListView listView_students;
    StudentDataBaseHelper myDB;
    Cursor data;
    ArrayList<Student> students;
    Button btnAdd;
    Button btnDelAll;
    static int index_position;
    int listindex;
    int delta_index = 1;
    public static final int IDM_CHANGE = 101;
    public static final int IDM_DELETE = 102;
    private static final int SECOND_ACTIVITY_RESULT_CODE1 = 0;
    private static final int SECOND_ACTIVITY_RESULT_CODE2 = 1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studentlist);
        listView_students = (ListView)findViewById(R.id.listview_student_id);
        registerForContextMenu(listView_students);
        myDB = new StudentDataBaseHelper(this);
        btnAdd = (Button)findViewById(R.id.add_student_btn_id);
        btnDelAll = (Button)findViewById(R.id.delete_all_students_btn_id);

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
                Intent intent = new Intent(StudentlistActivity.this, ChangeStudentActivity.class);
                intent.putExtra("action", "insert");
                startActivityForResult(intent, SECOND_ACTIVITY_RESULT_CODE1);
            }
        });
        btnDelAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
            students = new ArrayList<Student>();
            data = myDB.getListContents();
            /*String[] from = new String[]
                    {data.getColumnName(data.getColumnIndex("[groupnumber]") + 1).replaceAll(" ", ""),
                            data.getColumnName(data.getColumnIndex("[groupnumber]") + 2).replaceAll(" ","")};
            int[] to = new int[]{R.id.id_group_textView, R.id.groupnumber_textView};*/

            if (data.moveToFirst()) {
                students.add(new Student(data.getString(data.getColumnIndex("groupnumber") + 1),
                        data.getString(data.getColumnIndex("groupnumber") + 2),
                        data.getString(data.getColumnIndex("groupnumber") + 3),
                        new Group(data.getString(data.getColumnIndex("groupnumber") + 4),
                                data.getString(data.getColumnIndex("groupnumber") + 5))
                        ));
                listindex = Integer.parseInt(data.getString(data.getColumnIndex("groupnumber") + 1));
                System.out.println("listviewindex = " + listindex);
                while (data.moveToNext()) {
                    students.add(new Student(data.getString(data.getColumnIndex("groupnumber") + 1),
                            data.getString(data.getColumnIndex("groupnumber") + 2),
                            data.getString(data.getColumnIndex("groupnumber") + 3),
                            new Group(data.getString(data.getColumnIndex("groupnumber") + 4),
                                    data.getString(data.getColumnIndex("groupnumber") + 5))));
                    listindex = Integer.parseInt(data.getString(data.getColumnIndex("groupnumber") + 1));
                    System.out.println("listviewindex = " + listindex);
                }
            }
            try {
                listView_students = (ListView)findViewById(R.id.listview_student_id);
                ArrayAdapter<Student> adapter = new ArrayAdapter<>(this, R.layout.activity_item, R.id.textView, students);
                listView_students.setAdapter(adapter);
                listView_students.deferNotifyDataSetChanged();
            }catch(Exception e){
                System.out.println("не отрабатывает вывод");
            }
            System.out.println("курсор возвращён");
        }catch (Exception e){
            System.out.println("курсор не возвращён");
        }
    }
    public void AddData(String newEntry1, String newEntry2, Group group){
        Student student = new Student(String.valueOf(listindex + 1), newEntry1, newEntry2, group);
        boolean insertData = myDB.addData(student);
        if(insertData == true){
            Toast.makeText(StudentlistActivity.this, "Successfully added", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(StudentlistActivity.this, "Something wrong", Toast.LENGTH_LONG).show();
        }
        reviewlist();
    }

    public void DeleteAllData(){
        boolean deleteAllData = myDB.deleteAllData();
        if(deleteAllData == true){
            Toast.makeText(StudentlistActivity.this, "Successfully deleted", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(StudentlistActivity.this, "Something wrong", Toast.LENGTH_LONG).show();
        }
        reviewlist();
    }
    public void DeleteData(int id){
        boolean deleteData = myDB.deleteData(id);
        if(deleteData == true){
            Toast.makeText(StudentlistActivity.this, "Successfully deleted", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(StudentlistActivity.this, "Something wrong", Toast.LENGTH_LONG).show();
        }
        reviewlist();
        //delta_index++;
    }
    public void UpdateData(int id, Student student){
        boolean updateData = myDB.updateData(id, student);
        if(updateData == true){
            Toast.makeText(StudentlistActivity.this, "Successfully changed", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(StudentlistActivity.this, "Something wrong", Toast.LENGTH_LONG).show();
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
        if (requestCode == SECOND_ACTIVITY_RESULT_CODE2) {
            if (resultCode == RESULT_OK) {
                int id = Integer.parseInt(data.getStringExtra("id"));
                String firstname = data.getStringExtra("firstname");
                String secondname = data.getStringExtra("secondname");
                String id_group = data.getStringExtra("id_group");
                String groupnumber = data.getStringExtra("groupnumber");
                Student student = new Student(String.valueOf(id),firstname, secondname, new Group(id_group, groupnumber));
                UpdateData(id, student);
            }
        }
        else  if (requestCode == SECOND_ACTIVITY_RESULT_CODE1) {
            if (resultCode == RESULT_OK) {
               // int id = Integer.parseInt(data.getStringExtra("id"));
                String firstname = data.getStringExtra("firstname");
                String secondname = data.getStringExtra("secondname");
                String id_group = data.getStringExtra("id_group");
                String groupnumber = data.getStringExtra("groupnumber");
                AddData(firstname, secondname, new Group(id_group, groupnumber));
            }
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item)
    {
        CharSequence message;
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        index_position = info.position;
        Student student = (Student) listView_students.getItemAtPosition(index_position);
        switch (item.getItemId())
        {
            case IDM_CHANGE:
                Intent intent = new Intent(StudentlistActivity.this, ChangeStudentActivity.class);
                intent.putExtra("action", "update");
                intent.putExtra("id", student.getId_student());
                intent.putExtra("firstname", student.getFirstname());
                intent.putExtra("secondname", student.getSecondname());
                startActivityForResult(intent, SECOND_ACTIVITY_RESULT_CODE2);
                break;
            case IDM_DELETE:
                DeleteData(Integer.parseInt(student.getId_student()));
                break;
            default:
                return super.onContextItemSelected(item);
        }
        return true;
    }
}

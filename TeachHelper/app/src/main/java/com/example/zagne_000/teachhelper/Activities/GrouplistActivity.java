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
import com.example.zagne_000.teachhelper.R;
import com.example.zagne_000.teachhelper.db_helper.GroupDataBaseHelper;
import com.example.zagne_000.teachhelper.model.Group;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class GrouplistActivity extends AppCompatActivity {
    ListView listView_groups;
    GroupDataBaseHelper myDB;
    Cursor data;
    ArrayList<Group> groups;
    Button btnAdd;
    Button btnDelAll;
    EditText editText_group;
    static int index_position;
    int listindex;
    int delta_index = 1;
    public static final int IDM_CHANGE = 101;
    public static final int IDM_DELETE = 102;
    private static final int SECOND_ACTIVITY_RESULT_CODE = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grouplist);
        listView_groups = (ListView)findViewById(R.id.listview_group_id);
        registerForContextMenu(listView_groups);
        myDB = new GroupDataBaseHelper(this);
        btnAdd = (Button)findViewById(R.id.add_group_btn_id);
        btnDelAll = (Button)findViewById(R.id.delete_all_groups_btn_id);
        editText_group = (EditText)findViewById(R.id.edit_text_group_id);

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
                String newEntry = editText_group.getText().toString();
                if(editText_group.length() != 0){
                    AddData(newEntry);
                    editText_group.setText("");
                }else{
                    Toast.makeText(GrouplistActivity.this, "Enter the groupnumber", Toast.LENGTH_LONG).show();
                }
            }
        });
        btnDelAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newEntry = editText_group.getText().toString();
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
                listView_groups = (ListView)findViewById(R.id.listview_group_id);
                ArrayAdapter<Group> adapter = new ArrayAdapter<>(this, R.layout.activity_item, R.id.textView, groups);
                listView_groups.setAdapter(adapter);
                listView_groups.deferNotifyDataSetChanged();
            }catch(Exception e){
                System.out.println("не отрабатывает вывод");
            }
            System.out.println("курсор возвращён");
        }catch (Exception e){
            System.out.println("курсор не возвращён");
        }
    }
    public void AddData(String newEntry){
        Group group = new Group(String.valueOf(listindex + 1), newEntry);
        boolean insertData = myDB.addData(group);
        if(insertData == true){
            Toast.makeText(GrouplistActivity.this, "Successfully added", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(GrouplistActivity.this, "Something wrong", Toast.LENGTH_LONG).show();
        }
        reviewlist();
    }

    public void DeleteAllData(){
        boolean deleteAllData = myDB.deleteAllData();
        if(deleteAllData == true){
            Toast.makeText(GrouplistActivity.this, "Successfully deleted", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(GrouplistActivity.this, "Something wrong", Toast.LENGTH_LONG).show();
        }
        reviewlist();
    }
    public void DeleteData(int id){
        boolean deleteData = myDB.deleteData(id);
        if(deleteData == true){
            Toast.makeText(GrouplistActivity.this, "Successfully deleted", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(GrouplistActivity.this, "Something wrong", Toast.LENGTH_LONG).show();
        }
        reviewlist();
        //delta_index++;
    }
    public void UpdateData(int id, String newEntry){
        boolean updateData = myDB.updateData(id, newEntry);
        if(updateData == true){
            Toast.makeText(GrouplistActivity.this, "Successfully changed", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(GrouplistActivity.this, "Something wrong", Toast.LENGTH_LONG).show();
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
                String groupnumber = data.getStringExtra("groupnumber");
                UpdateData(id, groupnumber);
            }
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item)
    {
        CharSequence message;
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        index_position = info.position;
        Group group = (Group)listView_groups.getItemAtPosition(index_position);
        switch (item.getItemId())
        {
            case IDM_CHANGE:
                Intent intent = new Intent(GrouplistActivity.this, ChangeGroupActivity.class);

                intent.putExtra("id", group.getId_group());
                intent.putExtra("groupnumber", group.getGroupnumber());
                startActivityForResult(intent, SECOND_ACTIVITY_RESULT_CODE);
                break;
            case IDM_DELETE:
                DeleteData(Integer.parseInt(group.getId_group()));
                break;
            default:
                return super.onContextItemSelected(item);
        }
        return true;
    }
}

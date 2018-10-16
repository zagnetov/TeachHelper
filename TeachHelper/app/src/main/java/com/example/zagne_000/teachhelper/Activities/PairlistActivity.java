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

import com.example.zagne_000.teachhelper.ChangeDataActivities.ChangePairActivity;
import com.example.zagne_000.teachhelper.ChangeDataActivities.ChangeStudentActivity;
import com.example.zagne_000.teachhelper.R;
import com.example.zagne_000.teachhelper.db_helper.PairDataBaseHelper;
import com.example.zagne_000.teachhelper.db_helper.StudentDataBaseHelper;
import com.example.zagne_000.teachhelper.model.Group;
import com.example.zagne_000.teachhelper.model.Pair;
import com.example.zagne_000.teachhelper.model.Student;
import com.example.zagne_000.teachhelper.model.Subject;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;

public class PairlistActivity extends AppCompatActivity {
    Date date;
    ListView listView_pairs;
    PairDataBaseHelper myDB;
    Cursor data;
    Cursor count;
    ArrayList<Pair> pairs;
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
        setContentView(R.layout.activity_pairlist);
        listView_pairs = (ListView)findViewById(R.id.listview_pair_id);
        registerForContextMenu(listView_pairs);
        myDB = new PairDataBaseHelper(this);
        btnAdd = (Button)findViewById(R.id.add_pair_btn_id);
        btnDelAll = (Button)findViewById(R.id.delete_all_pairs_btn_id);
        Intent intent = getIntent();
        date = new Date(Integer.parseInt(intent.getStringExtra("year")), Integer.parseInt(intent.getStringExtra("month")), Integer.parseInt(intent.getStringExtra("day")));
        System.out.println(date);
        System.out.println(date.toString());
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
                Intent intent = new Intent(PairlistActivity.this, ChangePairActivity.class);
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
        try{
            count = myDB.getCount();
            count.moveToFirst();
            listindex = Integer.parseInt(count.getString(count.getColumnIndex("[count]") + 1));
            count.close();
        }
        catch(android.database.SQLException e){
            System.out.println("Не получаю кол-во записей review");
        }
        try {
            pairs = new ArrayList<Pair>();

            /*String[] from = new String[]
                    {data.getColumnName(data.getColumnIndex("[groupnumber]") + 1).replaceAll(" ", ""),
                            data.getColumnName(data.getColumnIndex("[groupnumber]") + 2).replaceAll(" ","")};
            int[] to = new int[]{R.id.id_group_textView, R.id.groupnumber_textView};*/


            data = myDB.getListContents(date);

            if (data.moveToFirst()) {
                pairs.add(new Pair(data.getString(data.getColumnIndex("[topic]") + 1),
                        new Group(data.getString(data.getColumnIndex("[topic]") + 2),
                        data.getString(data.getColumnIndex("[topic]") + 3)),
                        new Subject(data.getString(data.getColumnIndex("[topic]") + 4),
                                data.getString(data.getColumnIndex("[topic]") + 5)),
                                date,
                                data.getString(data.getColumnIndex("[topic]") + 6),
                                data.getString(data.getColumnIndex("[topic]") + 7)
                                ));
                //listindex = Integer.parseInt(data.getString(data.getColumnIndex("[topic]") + 1));
                System.out.println("listviewindex = " + listindex);
                while (data.moveToNext()) {
                    pairs.add(new Pair(data.getString(data.getColumnIndex("[topic]") + 1),
                            new Group(data.getString(data.getColumnIndex("[topic]") + 2),
                                    data.getString(data.getColumnIndex("[topic]") + 3)),
                            new Subject(data.getString(data.getColumnIndex("[topic]") + 4),
                                    data.getString(data.getColumnIndex("[topic]") + 5)),
                            date,
                            data.getString(data.getColumnIndex("[topic]") + 6),
                            data.getString(data.getColumnIndex("[topic]") + 7)
                    ));
                    //listindex = Integer.parseInt(data.getString(data.getColumnIndex("[topic]") + 1));
                    System.out.println("listviewindex = " + listindex);
                }
            }
            try {
                listView_pairs = (ListView)findViewById(R.id.listview_pair_id);
                ArrayAdapter<Pair> adapter = new ArrayAdapter<>(this, R.layout.activity_item, R.id.textView, pairs);
                listView_pairs.setAdapter(adapter);
                listView_pairs.deferNotifyDataSetChanged();
            }catch(Exception e){
                System.out.println("не отрабатывает вывод");
            }
            System.out.println("курсор возвращён");
        }catch (Exception e){
            System.out.println("курсор не возвращён");
        }
    }
    public void AddData(String newEntry1, String newEntry2, Group group, Subject subject){
        try{
            count = myDB.getCount();
            count.moveToFirst();
            listindex = Integer.parseInt(count.getString(count.getColumnIndex("[count]") + 1));
            count.close();
        }
        catch(android.database.SQLException e){
            System.out.println("Не получаю кол-во записей add");
        }
        Pair pair = new Pair(String.valueOf(listindex + 1), group, subject, date, newEntry1, newEntry2);
        boolean insertData = myDB.addData(pair);
        if(insertData == true){
            Toast.makeText(PairlistActivity.this, "Successfully added", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(PairlistActivity.this, "Something wrong", Toast.LENGTH_LONG).show();
        }
        reviewlist();
    }

    public void DeleteAllData(){
        boolean deleteAllData = myDB.deleteAllData(date);
        if(deleteAllData == true){
            Toast.makeText(PairlistActivity.this, "Successfully deleted", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(PairlistActivity.this, "Something wrong", Toast.LENGTH_LONG).show();
        }
        reviewlist();
    }
    public void DeleteData(int id){
        boolean deleteData = myDB.deleteData(id);
        if(deleteData == true){
            Toast.makeText(PairlistActivity.this, "Successfully deleted", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(PairlistActivity.this, "Something wrong", Toast.LENGTH_LONG).show();
        }
        reviewlist();
        //delta_index++;
    }
    public void UpdateData(int id, Pair pair){
        boolean updateData = myDB.updateData(id, pair);
        if(updateData == true){
            Toast.makeText(PairlistActivity.this, "Successfully changed", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(PairlistActivity.this, "Something wrong", Toast.LENGTH_LONG).show();
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
                System.out.println("id пары получен" + id);
                String id_group = data.getStringExtra("id_group");
                String groupnumber = data.getStringExtra("groupnumber");
                String id_subject = data.getStringExtra("id_subject");
                String name_subject = data.getStringExtra("name_subject");
                String topic = data.getStringExtra("topic");
                String time = data.getStringExtra("time");
                Pair pair = new Pair(String.valueOf(id), new Group(id_group, groupnumber), new Subject(id_subject, name_subject), date, time, topic);
                UpdateData(id, pair);
            }
        }
        else  if (requestCode == SECOND_ACTIVITY_RESULT_CODE1) {
            if (resultCode == RESULT_OK) {
               // int id = Integer.parseInt(data.getStringExtra("id"));
                String id_group = data.getStringExtra("id_group");
                String groupnumber = data.getStringExtra("groupnumber");
                String id_subject = data.getStringExtra("id_subject");
                String name_subject = data.getStringExtra("name_subject");
                String topic = data.getStringExtra("topic");
                String time = data.getStringExtra("time");
                AddData(time, topic, new Group(id_group, groupnumber), new Subject(id_subject, name_subject));
            }
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item)
    {
        CharSequence message;
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        index_position = info.position;
        Pair pair = (Pair) listView_pairs.getItemAtPosition(index_position);
        switch (item.getItemId())
        {
            case IDM_CHANGE:
                Intent intent = new Intent(PairlistActivity.this, ChangePairActivity.class);
                intent.putExtra("action", "update");
                intent.putExtra("id", pair.getId_pair());
                System.out.println("id_пары отправлен" + pair.getId_pair());
                intent.putExtra("id_group", pair.getGroup().getId_group());
                intent.putExtra("groupnumber", pair.getGroup().getGroupnumber());
                intent.putExtra("id_subject", pair.getSubject().getId_subject());
                intent.putExtra("name_subject", pair.getSubject().getName_subject());
                intent.putExtra("time", pair.getTime());
                intent.putExtra("topic", pair.getTopic());
                startActivityForResult(intent, SECOND_ACTIVITY_RESULT_CODE2);
                break;
            case IDM_DELETE:
                DeleteData(Integer.parseInt(pair.getId_pair()));
                break;
            default:
                return super.onContextItemSelected(item);
        }
        return true;
    }
}

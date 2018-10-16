package com.example.zagne_000.teachhelper.db_helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.zagne_000.teachhelper.model.Student;
import com.example.zagne_000.teachhelper.model.Subject;

public class StudentDataBaseHelper extends DataBaseHelper {
    /**
     * Конструктор
     * Принимает и сохраняет ссылку на переданный контекст для доступа к ресурсам приложения
     *
     * @param context
     */
    public static final String TABLE_NAME = "'Student'";
    public static final String TABLE_NAME2 = "'Group '";
    public static final String COL1 = "[_id_student ]";
    public static final String COL2 = "[firstname ]";
    public static final String COL3 = "[secondname ]";
    public static final String COL4 = "_id_group ";

    public StudentDataBaseHelper(Context context) {
        super(context);
    }

    public boolean addData(Student student) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL1, student.getId_student());
        contentValues.put(COL2, student.getFirstname());
        contentValues.put(COL3, student.getSecondname());
        contentValues.put(COL4, student.getGroup().getId_group());

        long result = db.insert(TABLE_NAME, null, contentValues);
        if(result == -1){
            return false;
        }
        else return  true;
    }
    public boolean deleteData(long rowId){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, COL1 + "=" + rowId, null);
        if(result == -1){
            return false;
        }
        else return  true;
    }
    public boolean updateData(long rowId, Student student) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, student.getFirstname());
        contentValues.put(COL3, student.getSecondname());
        contentValues.put(COL4, student.getGroup().getId_group());
        long result = db.update(TABLE_NAME, contentValues, COL1 + "=" + rowId, null);
        if(result == -1){
            return false;
        }
        else return  true;
    }

    public boolean deleteAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, null, null);
        if(result == -1){
            return false;
        }
        else return  true;
    }
    public Cursor getListContents(){
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = new String[]{COL1, COL2, COL3};
        String query = "SELECT [_id_student ], [firstname ], [secondname ],[_id_group ], [groupnumber ] FROM Student INNER JOIN [Group]\n" +
                "ON Student.[_id_group] = [Group].[_id_group ];";
        return db.rawQuery(query, null);
    }
}

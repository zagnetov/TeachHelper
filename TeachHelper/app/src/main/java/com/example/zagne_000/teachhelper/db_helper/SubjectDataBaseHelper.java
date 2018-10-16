package com.example.zagne_000.teachhelper.db_helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.zagne_000.teachhelper.model.Group;
import com.example.zagne_000.teachhelper.model.Subject;

public class SubjectDataBaseHelper extends DataBaseHelper {
    /**
     * Конструктор
     * Принимает и сохраняет ссылку на переданный контекст для доступа к ресурсам приложения
     *
     * @param context
     */
    public static final String TABLE_NAME = "'Subject '";
    public static final String COL1 = "[_id_subject ]";
    public static final String COL2 = "[name_subject ]";
    public SubjectDataBaseHelper(Context context) {
        super(context);
    }

    public boolean addData(Subject subject) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL1, subject.getId_subject());
        contentValues.put(COL2, subject.getName_subject());

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
    public boolean updateData(long rowId, String newEntry) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, newEntry);
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
        String[] columns = new String[]{COL1, COL2};
        return db.query(TABLE_NAME, null, null, null, null, null, null);
    }
}

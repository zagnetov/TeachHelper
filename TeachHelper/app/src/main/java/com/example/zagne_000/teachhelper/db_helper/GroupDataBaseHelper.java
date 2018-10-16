package com.example.zagne_000.teachhelper.db_helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.zagne_000.teachhelper.model.Group;

public class GroupDataBaseHelper extends DataBaseHelper {
    /**
     * Конструктор
     * Принимает и сохраняет ссылку на переданный контекст для доступа к ресурсам приложения
     *
     * @param context
     */
    public static final String TABLE_NAME = "'Group'";
    public static final String COL1 = "[_id_group ]";
    public static final String COL2 = "[groupnumber ]";
    public GroupDataBaseHelper(Context context) {
        super(context);
    }

    public boolean addData(Group group) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL1, group.getId_group());
        contentValues.put(COL2, group.getGroupnumber());

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

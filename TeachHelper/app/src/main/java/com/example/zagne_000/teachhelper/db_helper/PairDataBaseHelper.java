package com.example.zagne_000.teachhelper.db_helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.zagne_000.teachhelper.model.Pair;
import com.example.zagne_000.teachhelper.model.Student;

import java.sql.Date;

public class PairDataBaseHelper extends DataBaseHelper {
    /**
     * Конструктор
     * Принимает и сохраняет ссылку на переданный контекст для доступа к ресурсам приложения
     *
     * @param context
     */
    public static final String TABLE_NAME = "'Pair '";
    public static final String COL1 = "_id_pair ";
    public static final String COL2 = "_id_group ";
    public static final String COL3 = "_id_subject ";
    public static final String COL4 = "[date ]";
    public static final String COL5 = "[time ]";
    public static final String COL6 = "topic ";

    public PairDataBaseHelper(Context context) {
        super(context);
    }

    public boolean addData(Pair pair) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL1, pair.getId_pair());
        contentValues.put(COL2, pair.getGroup().getId_group());
        contentValues.put(COL3, pair.getSubject().getId_subject());
        contentValues.put(COL4, pair.getDate().toString());
        contentValues.put(COL5, pair.getTime());
        contentValues.put(COL6, pair.getTopic());

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
    public boolean updateData(long rowId, Pair pair) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, pair.getGroup().getId_group());
        contentValues.put(COL3, pair.getSubject().getId_subject());
        contentValues.put(COL4, pair.getDate().toString());
        contentValues.put(COL5, pair.getTime());
        contentValues.put(COL6, pair.getTopic());
        long result = db.update(TABLE_NAME, contentValues, COL1 + "=" + rowId, null);
        if(result == -1){
            return false;
        }
        else return  true;
    }

    public boolean deleteAllData(Date date){
        SQLiteDatabase db = this.getWritableDatabase();
        String[] args = new String[]{date.toString()};
        //long result = db.delete(TABLE_NAME, COL4, args);
        long result = db.delete(TABLE_NAME, null, null);
        if(result == -1){
            return false;
        }
        else return  true;
    }
    public Cursor getCount(){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT COUNT(*) as count FROM [Pair ]";
        return db.rawQuery(query, null);
    }

    public Cursor getListContents(Date date){
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = new String[]{COL1, COL2, COL3};
        String query = "SELECT [_id_pair], [_id_group], [groupnumber ], [_id_subject], [name_subject ],[time ], [topic] FROM [Pair ] INNER JOIN [Group]\n" +
                "ON [Pair ].[_id_group] = [Group].[_id_group ]\n" +
                "INNER JOIN [Subject ]\n" +
                "ON [Pair ].[_id_subject] = [Subject ].[_id_subject ]\n" +
                "WHERE [Pair ].[date ] = " + "'" + date + "'";
        return db.rawQuery(query, null);
    }
}
//" + "'" + date.getYear() + "-" + date.getMonth() + "-" + date.getDay() + "'"
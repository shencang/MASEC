package com.shencangblue.jin.notebooks;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;


public class SQLServer {
    private static final String TABLE_NOTEPAD ="notepad_tb";

    private MyDataBaseHelper helper;
    public SQLServer(Context context) {
        helper=new MyDataBaseHelper(
                context
                ,TABLE_NOTEPAD
                , null
                , 1);
    }
    /**
     * 添加
     * @param note
     * @return
     */
    public boolean add(Note note){
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("theme", note.getTheme());
        values.put("content", note.getContent());
        values.put("date",note.getDate());
        long insert = db.insert(TABLE_NOTEPAD, null, values);
        db.close();
        if(insert !=-1){
            return true;
        }else{
            return false;
        }
    }
    /**
     * 删除
     * @param id
     */
    public void delete(String id){
        SQLiteDatabase db = helper.getWritableDatabase();
//        db.execSQL("delete notepad_tb where id =?",new String[]{id});
        db.delete(TABLE_NOTEPAD, "_id=?", new String[]{id});
        db.close();
    }
    /**
     * 修改
     * @param id
     * @param theme
     * @param content
     * @param date
     */
    public void update(String id ,String theme, String content ,String date){
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("update "+TABLE_NOTEPAD+" set theme=?,content=?,date=? where id=?",new Object[]{theme,content,date});
        db.close();
    }
    /**
     *查找Theme
     * @param note
     * @return
     */
    public Note findName(Note note){
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from "+TABLE_NOTEPAD+" where theme=?", new String[]{note.getTheme()});
        Note notes = new Note();
        while(cursor.moveToNext()){
//            String name = cursor.getString(1);
            notes.setId(cursor.getString(0));
            notes.setTheme(cursor.getString(1));
            notes.setContent(cursor.getString(2));
            notes.setDate(cursor.getString(3));
        }
        cursor.close();
        db.close();
        return notes;
    }
    /**
     *查找id
     * @param id
     * @return
     */
    public Note findId(String id){
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from "+TABLE_NOTEPAD+" where _id=?", new String[]{id});
        Note notes = new Note();
        while(cursor.moveToNext()){
//            String name = cursor.getString(1);
            notes.setId(cursor.getString(0));
            notes.setTheme(cursor.getString(1));
            notes.setContent(cursor.getString(2));
            notes.setDate(cursor.getString(3));
        }
        cursor.close();
        db.close();
        return notes;
    }

    //查询所有学生
    public List<Note> findAll(){
        SQLiteDatabase db = helper.getReadableDatabase();
        List<Note> list =new ArrayList<Note>();
        Cursor cursor = db.query(TABLE_NOTEPAD
                , null
                , null
                , null
                , null
                , null
                , null);
        while(cursor.moveToNext()){
            //创建表对象
            Note note = new Note();
            String idStr = cursor.getString(0);
            String themeStr = cursor.getString(1);
            String contentStr = cursor.getString(2);
            String dateStr = cursor.getString(3);
            //添加到记事bean里面
            note.setId(idStr);
            note.setTheme(themeStr);
            note.setContent(contentStr);
            note.setDate(dateStr);
            list.add(note);
        }
        db.close();
        cursor.close();
        return list;

    }
}